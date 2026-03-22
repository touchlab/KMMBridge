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
import co.touchlab.kmmbridge.dependencymanager.SpmDependencyManager
import co.touchlab.kmmbridge.internal.findXCFrameworkAssembleTask
import co.touchlab.kmmbridge.internal.kmmBridgeExtensionOrNull
import java.io.File
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.Logger
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

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

        // Register tasks after all projects are evaluated
        gradle.projectsEvaluated {
            registerTasks(project, extension)
        }
    }

    private fun registerTasks(project: Project, extension: KmmBridgeSpmExtension) {
        val kmmBridgeModules = findKmmBridgeModules(project, extension)

        if (kmmBridgeModules.isEmpty()) {
            project.logger.warn(
                "No KMMBridge modules with SPM found. Make sure subprojects apply 'co.touchlab.kmmbridge' and configure spm().",
            )
            return
        }

        project.logger.info("Found ${kmmBridgeModules.size} KMMBridge modules: ${kmmBridgeModules.map { it.path }}")

        // Register generatePackageSwift task
        val generateTask = project.tasks.register(GENERATE_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Generates Package.swift from all KMMBridge module metadata"

            // Depend on writeSpmMetadata tasks from each module
            kmmBridgeModules.forEach { module ->
                val metadataTask = module.tasks.findByName(WRITE_SPM_METADATA_TASK_NAME)
                if (metadataTask != null) {
                    dependsOn(metadataTask)
                }
            }

            val outputDir = extension.outputDirectory.get()
            val packageName = extension.packageName.get()
            val swiftToolsVersion = extension.swiftToolsVersion.get()
            val metadataFiles = kmmBridgeModules.map { module ->
                File(module.layout.buildDirectory.asFile.get(), SpmModuleMetadata.METADATA_FILE_NAME)
            }

            outputs.file(File(outputDir, "Package.swift"))

            @Suppress("ObjectLiteralToLambda")
            doLast(
                object : Action<Task> {
                    override fun execute(t: Task) {
                        val metadata = collectMetadata(metadataFiles, t.logger)
                        if (metadata.isEmpty()) {
                            t.logger.warn("No module metadata found. Make sure modules have been published.")
                            return
                        }

                        val packageSwift = PackageSwiftGenerator.generate(
                            packageName = packageName,
                            swiftToolsVersion = PackageSwiftGenerator.resolveSwiftToolsVersion(metadata, swiftToolsVersion),
                            modules = metadata,
                        )

                        val outputFile = File(outputDir, "Package.swift")
                        outputFile.parentFile?.mkdirs()
                        outputFile.writeText(packageSwift)
                        t.logger.lifecycle("Generated Package.swift with ${metadata.size} modules at ${outputFile.absolutePath}")
                    }
                },
            )
        }

        // Register kmmBridgePublishAll task that does everything
        project.tasks.register(PUBLISH_ALL_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Publishes all KMMBridge modules and generates Package.swift"

            var hasPublishTasks = false
            kmmBridgeModules.forEach { module ->
                val publishTask = module.tasks.findByName(BaseKMMBridgePlugin.PUBLISH_TASK_NAME)
                if (publishTask != null) {
                    dependsOn(publishTask)
                    hasPublishTasks = true
                }
            }

            if (hasPublishTasks) {
                finalizedBy(generateTask)
            } else {
                @Suppress("ObjectLiteralToLambda")
                doFirst(
                    object : Action<Task> {
                        override fun execute(t: Task) {
                            t.logger.warn(
                                "Task $PUBLISH_ALL_TASK_NAME: no '${BaseKMMBridgePlugin.PUBLISH_TASK_NAME}' tasks found " +
                                    "in any KMMBridge module. Publishing may be disabled (ENABLE_PUBLISHING not set) " +
                                    "or not configured.",
                            )
                        }
                    },
                )
            }
        }

        // Precompute local module info at configuration time (paths are stable after projectsEvaluated)
        val outputDir = extension.outputDirectory.get()
        val localModules = collectLocalModuleInfo(kmmBridgeModules.toList(), outputDir)

        // Register spmDevBuildAll task for local development
        project.tasks.register(SPM_DEV_BUILD_ALL_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Builds all XCFrameworks locally and generates Package.swift with local paths"

            // Depend on debug XCFramework assemble tasks (always DEBUG for local dev)
            kmmBridgeModules.forEach { module ->
                runCatching { module.findXCFrameworkAssembleTask(NativeBuildType.DEBUG) }
                    .onSuccess { dependsOn(it) }
                    .onFailure {
                        project.logger.warn("Could not find debug XCFramework assemble task for ${module.path}: ${it.message}")
                    }
            }

            val packageName = extension.packageName.get()
            val swiftToolsVersion = extension.swiftToolsVersion.get()

            outputs.file(File(outputDir, "Package.swift"))

            @Suppress("ObjectLiteralToLambda")
            doLast(
                object : Action<Task> {
                    override fun execute(t: Task) {
                        if (localModules.isEmpty()) {
                            t.logger.warn("No local XCFrameworks found. Make sure modules have been built.")
                            return
                        }

                        val packageSwift = generateLocalPackageSwift(
                            packageName = packageName,
                            swiftToolsVersion = swiftToolsVersion,
                            modules = localModules,
                        )

                        val outputFile = File(outputDir, "Package.swift")
                        outputFile.parentFile?.mkdirs()
                        outputFile.writeText(packageSwift)
                        t.logger.lifecycle(
                            "Generated local Package.swift with ${localModules.size} modules at ${outputFile.absolutePath}",
                        )
                    }
                },
            )
        }
    }

    /**
     * Data class for local module info (used for spmDevBuildAll).
     */
    private data class LocalModuleInfo(val frameworkName: String, val localPath: String, val platforms: Map<String, String>)

    /**
     * Collect local module info from built XCFrameworks.
     * Always uses DEBUG build type to match the debug assemble tasks depended on by spmDevBuildAll.
     */
    private fun collectLocalModuleInfo(modules: List<Project>, rootDir: File): List<LocalModuleInfo> {
        return modules.mapNotNull { module ->
            val kmmBridgeExt = module.kmmBridgeExtensionOrNull ?: return@mapNotNull null
            val frameworkName = kmmBridgeExt.frameworkName.orNull ?: return@mapNotNull null

            // Always use DEBUG for local dev to match the debug assemble task
            val xcFrameworkDir = module.layout.buildDirectory.asFile.get()
                .resolve("XCFrameworks/${NativeBuildType.DEBUG.getName()}/$frameworkName.xcframework")

            val relativePath = rootDir.toPath().relativize(xcFrameworkDir.toPath()).toString()

            val spmDependencyManager = kmmBridgeExt.dependencyManagers.get()
                .find { it is SpmDependencyManager } as? SpmDependencyManager
                ?: return@mapNotNull null

            LocalModuleInfo(
                frameworkName = frameworkName,
                localPath = relativePath,
                platforms = spmDependencyManager.parsePlatformsMap(module),
            )
        }
    }

    /**
     * Generate Package.swift with local paths for development.
     */
    private fun generateLocalPackageSwift(packageName: String, swiftToolsVersion: String, modules: List<LocalModuleInfo>): String {
        val platforms = modules.flatMap { it.platforms.entries }
            .groupBy({ it.key }, { it.value })
            .mapValues { (_, versions) -> versions.maxWithOrNull(PackageSwiftGenerator.versionComparator) ?: versions.first() }

        val platformsString = platforms.entries
            .sortedBy { it.key }
            .joinToString(",\n        ") { (platform, version) ->
                ".$platform(.v$version)"
            }

        val productsString = modules
            .sortedBy { it.frameworkName }
            .joinToString(",\n        ") { module ->
                ".library(name: \"${module.frameworkName}\", targets: [\"${module.frameworkName}\"])"
            }

        val targetsString = modules
            .sortedBy { it.frameworkName }
            .joinToString(",\n        ") { module ->
                """.binaryTarget(
            name: "${module.frameworkName}",
            path: "${module.localPath}"
        )"""
            }

        return """// swift-tools-version:$swiftToolsVersion
// Generated by KMMBridge (LOCAL DEV) - DO NOT COMMIT
// https://github.com/touchlab/KMMBridge
import PackageDescription

let package = Package(
    name: "$packageName",
    platforms: [
        $platformsString
    ],
    products: [
        $productsString
    ],
    targets: [
        $targetsString
    ]
)
"""
    }

    /**
     * Find all subprojects that have KMMBridge with SPM configured.
     */
    private fun findKmmBridgeModules(project: Project, extension: KmmBridgeSpmExtension): Set<Project> {
        val includeModules = extension.includeModules.get()
        val excludeModules = extension.excludeModules.get()

        return project.subprojects.filter { subproject ->
            val kmmBridgeExt = subproject.kmmBridgeExtensionOrNull ?: return@filter false

            // Only include modules that have SPM configured via SpmDependencyManager
            val hasSpm = kmmBridgeExt.dependencyManagers.get().any { it is SpmDependencyManager }
            if (!hasSpm) return@filter false

            val modulePath = subproject.path
            val isIncluded = includeModules.isEmpty() || includeModules.contains(modulePath)
            val isExcluded = excludeModules.contains(modulePath)

            isIncluded && !isExcluded
        }.toSet()
    }

    /**
     * Collect metadata from all modules.
     */
    private fun collectMetadata(metadataFiles: List<File>, logger: Logger): List<SpmModuleMetadata> =
        metadataFiles.mapNotNull { metadataFile ->
            if (metadataFile.exists()) {
                try {
                    SpmModuleMetadata.fromFile(metadataFile)
                } catch (e: Exception) {
                    logger.warn("Failed to read metadata from ${metadataFile.absolutePath}: ${e.message}")
                    null
                }
            } else {
                logger.warn("Metadata file not found: ${metadataFile.absolutePath}")
                null
            }
        }
}
