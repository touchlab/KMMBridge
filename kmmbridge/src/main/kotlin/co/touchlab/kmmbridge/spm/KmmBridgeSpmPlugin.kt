/*
 * Copyright (c) 2024 Touchlab.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package co.touchlab.kmmbridge.spm

import co.touchlab.kmmbridge.BaseKMMBridgePlugin
import co.touchlab.kmmbridge.TASK_GROUP_NAME
import java.io.File
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.create

/**
 * Root-level plugin for generating Package.swift from all KMMBridge modules.
 *
 * This plugin automatically discovers all subprojects that use KMMBridge with SPM,
 * collects their metadata after publishing, and generates a unified Package.swift.
 *
 * Usage:
 * ```kotlin
 * // Root build.gradle.kts
 * plugins {
 *     id("co.touchlab.kmmbridge.spm")
 * }
 *
 * kmmBridgeSpm {
 *     packageName = "MySDK"  // Optional
 * }
 * ```
 */
class KmmBridgeSpmPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "kmmBridgeSpm"
        const val GENERATE_TASK_NAME = "generatePackageSwift"
        const val PUBLISH_ALL_TASK_NAME = "kmmBridgePublishAll"
        const val SPM_DEV_BUILD_ALL_TASK_NAME = "spmDevBuildAll"
        const val WRITE_SPM_METADATA_TASK_NAME = "writeSpmMetadata"
    }

    private val generator = PackageSwiftGenerator()
    private val logger: Logger = Logging.getLogger(KmmBridgeSpmPlugin::class.java)

    override fun apply(project: Project): Unit = with(project) {
        if (project != project.rootProject) {
            logger.warn("KmmBridgeSpmPlugin should be applied to the root project only")
        }

        val extension = extensions.create<KmmBridgeSpmExtension>(EXTENSION_NAME)

        // Set conventions (defaults)
        extension.packageName.convention(project.name)
        extension.swiftToolsVersion.convention("5.9")
        extension.outputDirectory.convention(project.projectDir)
        extension.includeModules.convention(emptySet())
        extension.excludeModules.convention(emptySet())

        val registry = project.kmmBridgeSpmRegistry()
        registry.get().markRootApplied()

        registerTasks(project, extension, registry)
    }

    /**
     * Registers the aggregator tasks. Every subproject's data flows through [registry] rather than
     * this project touching another project's live [Project]/[Task]/extension objects, and
     * cross-project task wiring uses path-string [Task.dependsOn] references - both Project
     * Isolation-safe patterns. The registry is only read from task-execution-time actions
     * ([Task.doLast]/[Task.doFirst]) or lazy [Provider]s, never at configuration time, since task
     * execution is always ordered after every project finishes configuring.
     */
    private fun registerTasks(project: Project, extension: KmmBridgeSpmExtension, registry: Provider<KmmBridgeSpmRegistry>) {
        val modulesProvider: Provider<List<KmmBridgeSpmRegistry.ModuleRegistration>> =
            registry.map { filterModules(it.modules(), extension) }

        // Register generatePackageSwift task
        val generateTask = project.tasks.register(GENERATE_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Generates Package.swift from all KMMBridge module metadata"
            usesService(registry)

            // Depend on all module metadata-write tasks, resolved lazily by task path
            dependsOn(modulesProvider.map { modules -> modules.map { "${it.path}:$WRITE_SPM_METADATA_TASK_NAME" } })

            val outputDir = extension.outputDirectory.get()
            val packageName = extension.packageName.get()
            val swiftToolsVersion = extension.swiftToolsVersion.get()

            // Declare the actual data this task reads so up-to-date checks and the build cache see
            // changes to a module's published metadata, not just this task's own output file.
            inputs.files(modulesProvider.map { modules -> modules.map { it.metadataFile } })
            inputs.property("packageName", packageName)
            inputs.property("swiftToolsVersion", swiftToolsVersion)
            outputs.file(File(outputDir, "Package.swift"))

            @Suppress("ObjectLiteralToLambda")
            doLast(
                object : Action<Task> {
                    override fun execute(t: Task) {
                        val modules = filterModules(registry.get().modules(), extension)
                        if (modules.isEmpty()) {
                            logger.warn(
                                "No KMMBridge modules with SPM found. Make sure subprojects apply 'co.touchlab.kmmbridge' and configure spm().",
                            )
                            return
                        }

                        val metadata = collectMetadata(modules)
                        if (metadata.isEmpty()) {
                            logger.warn("No module metadata found. Make sure modules have been published.")
                            return
                        }
                        if (metadata.size < modules.size) {
                            val missing = modules.map { it.frameworkName } - metadata.map { it.frameworkName }.toSet()
                            throw GradleException(
                                "Missing or unreadable SPM metadata for module(s): ${missing.joinToString()}. " +
                                    "Make sure all selected modules have been published (writeSpmMetadata ran successfully) " +
                                    "before generating Package.swift.",
                            )
                        }

                        val packageSwift = generator.generatePackageSwift(
                            packageName = packageName,
                            swiftToolsVersion = generator.resolveSwiftToolsVersion(
                                metadata.map { it.swiftToolsVersion },
                                swiftToolsVersion,
                            ),
                            modules = metadata,
                        )

                        val outputFile = File(outputDir, "Package.swift")
                        outputFile.parentFile?.mkdirs()
                        outputFile.writeText(packageSwift)
                        logger.lifecycle("Generated Package.swift with ${metadata.size} modules at ${outputFile.absolutePath}")
                    }
                },
            )
        }

        // Register kmmBridgePublishAll task that does everything
        project.tasks.register(PUBLISH_ALL_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Publishes all KMMBridge modules and generates Package.swift"
            usesService(registry)

            // Depend on all module kmmBridgePublish tasks, resolved lazily by task path. Modules
            // only register themselves once publishing is configured (ENABLE_PUBLISHING), so a
            // module without publishing enabled simply never appears here.
            dependsOn(modulesProvider.map { modules -> modules.map { "${it.path}:${BaseKMMBridgePlugin.PUBLISH_TASK_NAME}" } })

            // A dependency (not finalizedBy): finalizedBy would run generateTask even if a module's
            // publish failed, silently generating Package.swift from a mix of stale and new data and
            // masking the failed release. As a dependency, generateTask only runs - and this task
            // only succeeds - once every publish task it depends on (transitively, via writeSpmMetadata)
            // has completed successfully.
            dependsOn(generateTask)

            doFirst {
                if (filterModules(registry.get().modules(), extension).isEmpty()) {
                    logger.warn(
                        "Task $PUBLISH_ALL_TASK_NAME did not find any KMMBridge modules with SPM configured. " +
                            "Publishing is disabled or not configured (e.g. ENABLE_PUBLISHING not set); skipping Package.swift generation.",
                    )
                }
            }
        }

        // Register spmDevBuildAll task for local development
        project.tasks.register(SPM_DEV_BUILD_ALL_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Builds all XCFrameworks locally and generates Package.swift with local paths"
            usesService(registry)

            // Depend on all module XCFramework assemble tasks, resolved lazily by task path
            dependsOn(modulesProvider.map { modules -> modules.map { "${it.path}:${it.debugAssembleTaskName}" } })

            val outputDir = extension.outputDirectory.get()
            val packageName = extension.packageName.get()
            val defaultSwiftToolsVersion = extension.swiftToolsVersion.get()

            // Declare the actual data this task reads: each module's built XCFramework directory
            // (content changes when rebuilt), plus the configuration that shapes the output.
            inputs.files(modulesProvider.map { modules -> modules.map { it.debugXCFrameworkDir } })
            inputs.property("packageName", packageName)
            inputs.property("moduleSwiftToolsVersions", modulesProvider.map { modules -> modules.map { it.swiftToolsVersion } })
            outputs.file(File(outputDir, "Package.swift"))

            @Suppress("ObjectLiteralToLambda")
            doLast(
                object : Action<Task> {
                    override fun execute(t: Task) {
                        val modules = filterModules(registry.get().modules(), extension)
                        val localModules = collectLocalModuleInfo(modules, outputDir)
                        if (localModules.isEmpty()) {
                            logger.warn("No local XCFrameworks found. Make sure modules have been built.")
                            return
                        }

                        // Resolve the maximum Swift-tools version across modules, same as the
                        // published/remote generatePackageSwift path, rather than always using the
                        // root default.
                        val resolvedSwiftToolsVersion =
                            generator.resolveSwiftToolsVersion(localModules.map { it.swiftToolsVersion }, defaultSwiftToolsVersion)

                        val packageSwift = generator.generateLocalPackageSwift(
                            packageName = packageName,
                            swiftToolsVersion = resolvedSwiftToolsVersion,
                            modules = localModules,
                        )

                        val outputFile = File(outputDir, "Package.swift")
                        outputFile.parentFile?.mkdirs()
                        outputFile.writeText(packageSwift)
                        logger.lifecycle(
                            "Generated local Package.swift with ${localModules.size} modules at ${outputFile.absolutePath}",
                        )
                    }
                },
            )
        }
    }

    /**
     * Collect local module info from built XCFrameworks. Modules whose XCFramework directory
     * doesn't exist yet (not built) are skipped with a warning rather than emitting a
     * `.binaryTarget(path:)` that points at nothing.
     */
    private fun collectLocalModuleInfo(
        modules: List<KmmBridgeSpmRegistry.ModuleRegistration>,
        rootDir: File,
    ): List<PackageSwiftGenerator.LocalModuleInfo> = modules.mapNotNull { module ->
        if (!module.debugXCFrameworkDir.exists()) {
            logger.warn(
                "Skipping module ${module.path}: XCFramework not found at ${module.debugXCFrameworkDir.absolutePath}. " +
                    "Make sure '${module.debugAssembleTaskName}' has run for this module.",
            )
            return@mapNotNull null
        }

        // Calculate the relative path from root
        val relativePath = rootDir.toPath().relativize(module.debugXCFrameworkDir.toPath()).toString()

        PackageSwiftGenerator.LocalModuleInfo(
            frameworkName = module.frameworkName,
            localPath = relativePath,
            platforms = module.platforms,
            swiftToolsVersion = module.swiftToolsVersion,
        )
    }

    /**
     * Filter registered modules by the extension's include/exclude module paths.
     */
    private fun filterModules(
        allModules: List<KmmBridgeSpmRegistry.ModuleRegistration>,
        extension: KmmBridgeSpmExtension,
    ): List<KmmBridgeSpmRegistry.ModuleRegistration> {
        val includeModules = extension.includeModules.get()
        val excludeModules = extension.excludeModules.get()

        return allModules.filter { module ->
            val isIncluded = includeModules.isEmpty() || includeModules.contains(module.path)
            val isExcluded = excludeModules.contains(module.path)
            isIncluded && !isExcluded
        }
    }

    /**
     * Collect metadata from all modules.
     */
    private fun collectMetadata(modules: List<KmmBridgeSpmRegistry.ModuleRegistration>): List<SpmModuleMetadata> =
        modules.mapNotNull { module ->
            if (module.metadataFile.exists()) {
                try {
                    SpmModuleMetadata.fromFile(module.metadataFile)
                } catch (e: Exception) {
                    logger.warn("Failed to read metadata from ${module.metadataFile.absolutePath} (module ${module.path}): ${e.message}")
                    null
                }
            } else {
                logger.warn("Metadata file not found for module ${module.path}: ${module.metadataFile.absolutePath}")
                null
            }
        }
}
