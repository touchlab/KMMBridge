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
import co.touchlab.kmmbridge.internal.kmmBridgeExtension
import co.touchlab.kmmbridge.internal.kmmBridgeExtensionOrNull
import java.io.File
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
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

    private val generator = PackageSwiftGenerator()

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

            // Depend on all module upload tasks
            kmmBridgeModules.forEach { module ->
                val uploadTask = module.tasks.findByName(WRITE_SPM_METADATA_TASK_NAME)
                if (uploadTask != null) {
                    dependsOn(uploadTask)
                }
            }

            val outputDir = extension.outputDirectory.get()
            val packageName = extension.packageName.get()
            val swiftToolsVersion = extension.swiftToolsVersion.get()
            val moduleProjects = kmmBridgeModules.toList()

            outputs.file(File(outputDir, "Package.swift"))

            @Suppress("ObjectLiteralToLambda")
            doLast(
                object : Action<Task> {
                    override fun execute(t: Task) {
                        val metadata = collectMetadata(moduleProjects)
                        if (metadata.isEmpty()) {
                            project.logger.warn("No module metadata found. Make sure modules have been published.")
                            return
                        }

                        val packageSwift = generator.generatePackageSwift(
                            packageName = packageName,
                            swiftToolsVersion = generator.resolveSwiftToolsVersion(metadata, swiftToolsVersion),
                            modules = metadata,
                        )

                        val outputFile = File(outputDir, "Package.swift")
                        outputFile.parentFile?.mkdirs()
                        outputFile.writeText(packageSwift)
                        project.logger.lifecycle("Generated Package.swift with ${metadata.size} modules at ${outputFile.absolutePath}")
                    }
                },
            )
        }

        // Register kmmBridgePublishAll task that does everything
        project.tasks.register(PUBLISH_ALL_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Publishes all KMMBridge modules and generates Package.swift"

            // Depend on all module kmmBridgePublish tasks
            var hasPublishTasks = false
            kmmBridgeModules.forEach { module ->
                val publishTask = module.tasks.findByName(BaseKMMBridgePlugin.PUBLISH_TASK_NAME)
                if (publishTask != null) {
                    dependsOn(publishTask)
                    hasPublishTasks = true
                }
            }

            if (hasPublishTasks) {
                // Then generate Package.swift
                finalizedBy(generateTask)
            } else {
                doFirst {
                    project.logger.warn(
                        "Task $PUBLISH_ALL_TASK_NAME did not find any '${BaseKMMBridgePlugin.PUBLISH_TASK_NAME}' tasks in KMMBridge modules. " +
                            "Publishing is disabled or not configured (e.g. ENABLE_PUBLISHING not set); skipping Package.swift generation.",
                    )
                }
            }
        }

        // Register spmDevBuildAll task for local development
        project.tasks.register(SPM_DEV_BUILD_ALL_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Builds all XCFrameworks locally and generates Package.swift with local paths"

            // Depend on all module XCFramework assemble tasks
            kmmBridgeModules.forEach { module ->
                val assembleTask = module.tasks.findByName("assembleXCFramework")
                    ?: module.tasks.findByName("assembleDebugXCFramework")
                if (assembleTask != null) {
                    dependsOn(assembleTask)
                }
            }

            val outputDir = extension.outputDirectory.get()
            val packageName = extension.packageName.get()
            val swiftToolsVersion = extension.swiftToolsVersion.get()
            val moduleProjects = kmmBridgeModules.toList()

            outputs.file(File(outputDir, "Package.swift"))

            @Suppress("ObjectLiteralToLambda")
            doLast(
                object : Action<Task> {
                    override fun execute(t: Task) {
                        val localModules = collectLocalModuleInfo(moduleProjects, outputDir)
                        if (localModules.isEmpty()) {
                            project.logger.warn("No local XCFrameworks found. Make sure modules have been built.")
                            return
                        }

                        val packageSwift = generator.generateLocalPackageSwift(
                            packageName = packageName,
                            swiftToolsVersion = swiftToolsVersion,
                            modules = localModules,
                        )

                        val outputFile = File(outputDir, "Package.swift")
                        outputFile.parentFile?.mkdirs()
                        outputFile.writeText(packageSwift)
                        project.logger.lifecycle(
                            "Generated local Package.swift with ${localModules.size} modules at ${outputFile.absolutePath}",
                        )
                    }
                },
            )
        }
    }

    /**
     * Collect local module info from built XCFrameworks.
     */
    private fun collectLocalModuleInfo(modules: List<Project>, rootDir: File): List<PackageSwiftGenerator.LocalModuleInfo> {
        return modules.mapNotNull { module ->
            val kmmBridgeExt = module.kmmBridgeExtension

            // Get framework name from extension
            val frameworkName = kmmBridgeExt.frameworkName.orNull ?: return@mapNotNull null

            // For local dev (spmDevBuildAll), always use DEBUG build output
            val xcFrameworkDir = module.layout.buildDirectory.asFile.get()
                .resolve("XCFrameworks/${NativeBuildType.DEBUG.getName()}/$frameworkName.xcframework")

            // Calculate the relative path from root
            val relativePath = rootDir.toPath().relativize(xcFrameworkDir.toPath()).toString()

            // Read platforms from the SPM dependency manager configuration
            val spmDependencyBlock = kmmBridgeExt.dependencyManagers.get()
                .find { it is SpmDependencyManager } as? SpmDependencyManager
                ?: return@mapNotNull null

            PackageSwiftGenerator.LocalModuleInfo(
                frameworkName = frameworkName,
                localPath = relativePath,
                platforms = spmDependencyBlock.parsePlatformsMap(module),
            )
        }
    }

    /**
     * Find all subprojects that have KMMBridge with SPM configured.
     */
    private fun findKmmBridgeModules(project: Project, extension: KmmBridgeSpmExtension): Set<Project> {
        val includeModules = extension.includeModules.get()
        val excludeModules = extension.excludeModules.get()

        return project.subprojects.filter { subproject ->
            // Check if this module has KMMBridge extension with SPM configured
            val kmmBridgeExt = subproject.kmmBridgeExtensionOrNull ?: return@filter false

            // Only include modules that have SPM configured via SpmDependencyManager
            val hasSpm = kmmBridgeExt.dependencyManagers.get().any { it is SpmDependencyManager }
            if (!hasSpm) return@filter false

            // Check include/exclude filters
            val modulePath = subproject.path
            val isIncluded = includeModules.isEmpty() || includeModules.contains(modulePath)
            val isExcluded = excludeModules.contains(modulePath)

            isIncluded && !isExcluded
        }.toSet()
    }

    /**
     * Collect metadata from all modules.
     */
    private fun collectMetadata(modules: List<Project>): List<SpmModuleMetadata> = modules.mapNotNull { module ->
        val metadataFile = File(module.layout.buildDirectory.asFile.get(), SpmModuleMetadata.METADATA_FILE_NAME)
        if (metadataFile.exists()) {
            try {
                SpmModuleMetadata.fromFile(metadataFile)
            } catch (e: Exception) {
                module.logger.warn("Failed to read metadata from ${metadataFile.absolutePath}: ${e.message}")
                null
            }
        } else {
            module.logger.warn("Metadata file not found: ${metadataFile.absolutePath}")
            null
        }
    }
}
