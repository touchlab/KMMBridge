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

import co.touchlab.kmmbridge.TASK_GROUP_NAME
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import java.io.File

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
            project.logger.warn("No KMMBridge modules with SPM found. Make sure subprojects apply 'co.touchlab.kmmbridge' and configure spm().")
            return
        }

        project.logger.info("Found ${kmmBridgeModules.size} KMMBridge modules: ${kmmBridgeModules.map { it.path }}")

        // Register generatePackageSwift task
        val generateTask = project.tasks.register(GENERATE_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Generates Package.swift from all KMMBridge module metadata"

            // Depend on all module upload tasks
            kmmBridgeModules.forEach { module ->
                val uploadTask = module.tasks.findByName("uploadXCFramework")
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
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val metadata = collectMetadata(moduleProjects)
                    if (metadata.isEmpty()) {
                        project.logger.warn("No module metadata found. Make sure modules have been published.")
                        return
                    }

                    val packageSwift = generatePackageSwift(
                        packageName = packageName,
                        swiftToolsVersion = resolveSwiftToolsVersion(metadata, swiftToolsVersion),
                        modules = metadata
                    )

                    val outputFile = File(outputDir, "Package.swift")
                    outputFile.writeText(packageSwift)
                    project.logger.lifecycle("Generated Package.swift with ${metadata.size} modules at ${outputFile.absolutePath}")
                }
            })
        }

        // Register kmmBridgePublishAll task that does everything
        project.tasks.register(PUBLISH_ALL_TASK_NAME) {
            group = TASK_GROUP_NAME
            description = "Publishes all KMMBridge modules and generates Package.swift"

            // Depend on all module kmmBridgePublish tasks
            kmmBridgeModules.forEach { module ->
                val publishTask = module.tasks.findByName("kmmBridgePublish")
                if (publishTask != null) {
                    dependsOn(publishTask)
                }
            }

            // Then generate Package.swift
            finalizedBy(generateTask)
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
            val rootDir = project.projectDir

            outputs.file(File(outputDir, "Package.swift"))

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val localModules = collectLocalModuleInfo(moduleProjects, rootDir)
                    if (localModules.isEmpty()) {
                        project.logger.warn("No local XCFrameworks found. Make sure modules have been built.")
                        return
                    }

                    val packageSwift = generateLocalPackageSwift(
                        packageName = packageName,
                        swiftToolsVersion = swiftToolsVersion,
                        modules = localModules
                    )

                    val outputFile = File(outputDir, "Package.swift")
                    outputFile.writeText(packageSwift)
                    project.logger.lifecycle("Generated local Package.swift with ${localModules.size} modules at ${outputFile.absolutePath}")
                }
            })
        }
    }

    /**
     * Data class for local module info (used for spmDevBuildAll).
     */
    private data class LocalModuleInfo(
        val frameworkName: String,
        val localPath: String,
        val platforms: Map<String, String>
    )

    /**
     * Collect local module info from built XCFrameworks.
     */
    private fun collectLocalModuleInfo(modules: List<Project>, rootDir: File): List<LocalModuleInfo> {
        return modules.mapNotNull { module ->
            val kmmBridgeExt = module.extensions.findByName("kmmbridge") ?: return@mapNotNull null

            // Get framework name from extension
            val frameworkNameProp = kmmBridgeExt.javaClass.methods
                .find { it.name == "getFrameworkName" }
                ?.invoke(kmmBridgeExt)

            val frameworkName = when (frameworkNameProp) {
                is org.gradle.api.provider.Property<*> -> frameworkNameProp.orNull?.toString()
                else -> null
            } ?: return@mapNotNull null

            // Find XCFramework in build directory
            val buildDir = module.layout.buildDirectory.asFile.get()
            val debugXcFramework = File(buildDir, "XCFrameworks/debug/$frameworkName.xcframework")
            val releaseXcFramework = File(buildDir, "XCFrameworks/release/$frameworkName.xcframework")

            val xcFrameworkDir = when {
                debugXcFramework.exists() -> debugXcFramework
                releaseXcFramework.exists() -> releaseXcFramework
                else -> {
                    module.logger.warn("XCFramework not found for $frameworkName in ${buildDir.absolutePath}")
                    return@mapNotNull null
                }
            }

            // Calculate relative path from root
            val relativePath = rootDir.toPath().relativize(xcFrameworkDir.toPath()).toString()

            // Default platforms (we could enhance this to read from config)
            val platforms = mapOf("iOS" to "15", "macOS" to "15")

            LocalModuleInfo(
                frameworkName = frameworkName,
                localPath = relativePath,
                platforms = platforms
            )
        }
    }

    /**
     * Generate Package.swift with local paths for development.
     */
    private fun generateLocalPackageSwift(
        packageName: String,
        swiftToolsVersion: String,
        modules: List<LocalModuleInfo>
    ): String {
        val platforms = modules.flatMap { it.platforms.entries }
            .groupBy({ it.key }, { it.value })
            .mapValues { (_, versions) -> versions.maxWithOrNull(versionComparator) ?: versions.first() }

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
            // Check if this module has KMMBridge extension
            val hasKmmBridge = try {
                subproject.extensions.findByName("kmmbridge") != null
            } catch (e: Exception) {
                false
            }

            if (!hasKmmBridge) return@filter false

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
    private fun collectMetadata(modules: List<Project>): List<SpmModuleMetadata> {
        return modules.mapNotNull { module ->
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

    /**
     * Resolve the Swift tools version to use.
     * Uses the maximum version from all modules, or the configured default.
     */
    private fun resolveSwiftToolsVersion(modules: List<SpmModuleMetadata>, defaultVersion: String): String {
        val versions = modules.map { it.swiftToolsVersion }.filter { it.isNotBlank() }
        return if (versions.isNotEmpty()) {
            versions.maxWithOrNull(versionComparator) ?: defaultVersion
        } else {
            defaultVersion
        }
    }

    private val versionComparator = Comparator<String> { v1, v2 ->
        val parts1 = v1.split(".").mapNotNull { it.toIntOrNull() }
        val parts2 = v2.split(".").mapNotNull { it.toIntOrNull() }
        val maxLen = maxOf(parts1.size, parts2.size)
        for (i in 0 until maxLen) {
            val p1 = parts1.getOrElse(i) { 0 }
            val p2 = parts2.getOrElse(i) { 0 }
            if (p1 != p2) return@Comparator p1.compareTo(p2)
        }
        0
    }

    /**
     * Generate the complete Package.swift content.
     */
    private fun generatePackageSwift(
        packageName: String,
        swiftToolsVersion: String,
        modules: List<SpmModuleMetadata>
    ): String {
        val platforms = resolvePlatforms(modules)
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
            url: "${module.url}",
            checksum: "${module.checksum}"
        )"""
            }

        return """// swift-tools-version:$swiftToolsVersion
// Generated by KMMBridge - DO NOT EDIT MANUALLY
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
     * Resolve platforms by taking the maximum version for each platform across all modules.
     */
    private fun resolvePlatforms(modules: List<SpmModuleMetadata>): Map<String, String> {
        val platformVersions = mutableMapOf<String, MutableList<String>>()

        modules.forEach { module ->
            module.platforms.forEach { (platform, version) ->
                platformVersions.getOrPut(platform) { mutableListOf() }.add(version)
            }
        }

        return platformVersions.mapValues { (_, versions) ->
            versions.maxWithOrNull(versionComparator) ?: versions.first()
        }
    }
}