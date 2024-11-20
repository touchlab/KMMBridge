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

package co.touchlab.faktory.dependencymanager

import co.touchlab.faktory.TASK_GROUP_NAME
import co.touchlab.faktory.domain.SwiftToolVersion
import co.touchlab.faktory.domain.TargetPlatform
import co.touchlab.faktory.dsl.TargetPlatformDsl
import co.touchlab.faktory.findXCFrameworkAssembleTask
import co.touchlab.faktory.internal.PluginConfigState
import co.touchlab.faktory.kmmBridgeExtension
import co.touchlab.faktory.kotlin
import co.touchlab.faktory.layoutBuildDir
import co.touchlab.faktory.localdevmanager.LocalDevManager
import co.touchlab.faktory.urlFile
import co.touchlab.faktory.zipFilePath
import domain.konanTarget
import domain.swiftPackagePlatformName
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.File
import java.util.*

class SpmDependencyManager(
    /**
     * Folder where the Package.swift file lives
     */
    private val _swiftPackageFolder: String?,
    private val useCustomPackageFile: Boolean,
    private val perModuleVariablesBlock: Boolean,
    private val _swiftToolVersion: String,
    private val _targetPlatforms: TargetPlatformDsl.() -> Unit,
) : DependencyManager, LocalDevManager() {
    private fun ProviderFactory.swiftPackageFolder(projectDir: File): String =
        _swiftPackageFolder ?: this.findRepoRoot(projectDir)

    private fun ProviderFactory.swiftPackageFilePath(projectDir: File): String =
        "${stripEndSlash(swiftPackageFolder(projectDir))}/Package.swift"

    override fun configure(
        providers: ProviderFactory,
        project: Project,
        uploadTask: TaskProvider<Task>,
        publishRemoteTask: TaskProvider<Task>
    ) {
        val extension = project.kmmBridgeExtension
        val packageName = extension.frameworkName.get()
        val projectDir = project.projectDir

        if (useCustomPackageFile && !providers.hasKmmbridgeVariablesSection(projectDir, packageName)) {
            project.logger.error(buildPackageFileErrorMessage(packageName, perModuleVariablesBlock))
        }

        val swiftToolVersion = SwiftToolVersion.of(_swiftToolVersion)
            ?: throw IllegalArgumentException("Parameter swiftToolVersion should be not blank!")

        val targetPlatforms = TargetPlatformDsl().apply(_targetPlatforms)
            .targetPlatforms
            .ifEmpty {
                throw IllegalArgumentException("At least one target platform should be specified!")
            }

        val platforms = platforms(project, targetPlatforms)

        val updatePackageSwiftTask = project.tasks.register("updatePackageSwift") {
            group = TASK_GROUP_NAME
            val zipFile = project.zipFilePath()
            inputs.files(zipFile, project.urlFile)
            outputs.files(providers.swiftPackageFilePath(project.projectDir))

            val urlFile = project.urlFile

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val checksum = providers.findSpmChecksum(zipFile, projectDir)
                    val url = urlFile.readText()
                    if (useCustomPackageFile && providers.hasKmmbridgeVariablesSection(projectDir, packageName)) {
                        providers.modifyPackageFileVariables(projectDir, packageName, url, checksum)
                    } else if (useCustomPackageFile) {
                        // We warned you earlier, but you didn't fix it, so now we interrupt the publish process because it's
                        // probably not going to do what you want
                        error(buildPackageFileErrorMessage(packageName, perModuleVariablesBlock))
                    } else {
                        providers.writePackageFile(
                            projectDir,
                            extension.frameworkName.get(),
                            url,
                            checksum,
                            swiftToolVersion,
                            platforms
                        )
                    }

                    // TODO: Maybe write Package file path?
                }
            })
        }

        updatePackageSwiftTask.configure { dependsOn(uploadTask) }
        publishRemoteTask.configure { dependsOn(updatePackageSwiftTask) }
    }

    private fun ProviderFactory.hasKmmbridgeVariablesSection(projectDir: File, packageName: String): Boolean {
        val swiftPackageFile = File(swiftPackageFilePath(projectDir))
        val (startTag) = kmmBridgeVariablesForPackage(packageName, perModuleVariablesBlock)

        return swiftPackageFile.readText().contains(startTag)
    }

    private fun ProviderFactory.modifyPackageFileVariables(
        projectDir: File,
        packageName: String,
        url: String,
        checksum: String,
    ) {
        val packageFile = File(swiftPackageFilePath(projectDir))
        packageFile.writeText(
            getModifiedPackageFileText(
                packageFile.readText(),
                packageName,
                perModuleVariablesBlock,
                url,
                checksum
            )
        )
    }

    private fun ProviderFactory.writePackageFile(
        projectDir: File,
        packageName: String,
        url: String,
        checksum: String,
        swiftToolVersion: SwiftToolVersion,
        platforms: String
    ) {

        val swiftPackageFile = File(swiftPackageFilePath(projectDir))
        val packageText =
            makePackageFileText(packageName, url, checksum, perModuleVariablesBlock, swiftToolVersion, platforms)
        swiftPackageFile.parentFile.mkdirs()
        swiftPackageFile.writeText(packageText)
    }

    private fun ProviderFactory.findSpmChecksum(zipFilePath: File, projectDir: File): String {
        // checksum requires a package file, but doesn't require it to be real (and we might only have cocoapods)
        val packageSwiftFile = File(projectDir, "Package.swift")
        val hadPackageSwift = packageSwiftFile.exists()

        if (!hadPackageSwift) {
            packageSwiftFile.writeText("")
        }

        val checksumOut = exec {
            commandLine(
                "swift",
                "package",
                "compute-checksum",
                zipFilePath.path
            )
        }.standardOutput.asText.get()

        if (!hadPackageSwift) {
            packageSwiftFile.delete()
        }

        return checksumOut.trim()
    }

    override val needsGitTags: Boolean = true
    override fun configureLocalDev(pluginConfigState: PluginConfigState, providers: ProviderFactory, project: Project) {
        if (useCustomPackageFile) return // No local dev when using a custom package file

        val extension = project.kmmBridgeExtension
        project.tasks.register("spmDevBuild") {
            group = TASK_GROUP_NAME
            dependsOn(project.findXCFrameworkAssembleTask(NativeBuildType.DEBUG))

            val targetPlatforms = TargetPlatformDsl().apply(_targetPlatforms)
                .targetPlatforms
                .ifEmpty {
                    throw IllegalArgumentException("At least one target platform should be specified!")
                }

            val platforms = platforms(project, targetPlatforms)

            val projectDir = project.projectDir
            val packageFile = project.file(providers.swiftPackageFilePath(projectDir))
            val layoutBuildDir = project.layoutBuildDir
            val swiftPackageFolder = project.file(providers.swiftPackageFolder(projectDir))

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {

                    val swiftToolVersion = SwiftToolVersion.of(_swiftToolVersion)
                        ?: throw IllegalArgumentException("Parameter swiftToolVersion should be not blank!")

                    packageFile.writeText(
                        makeLocalDevPackageFileText(
                            swiftPackageFolder,
                            layoutBuildDir,
                            extension.frameworkName.get(),
                            swiftToolVersion,
                            platforms
                        )
                    )
                    println("spmDevBuild body done")
                }
            })
        }
    }

    private fun platforms(project: Project, targetPlatforms: List<TargetPlatform>): String =
        targetPlatforms.flatMap { platform ->
            project.kotlin.targets
                .withType<KotlinNativeTarget>()
                .asSequence()
                .filter { it.konanTarget.family.isAppleFamily }
                .filter { appleTarget -> platform.targets.firstOrNull { it.konanTarget == appleTarget.konanTarget } != null }
                .mapNotNull { it.konanTarget.family.swiftPackagePlatformName }
                .distinct()
                .map { platformName -> ".$platformName(.v${platform.version.name})" }
                .toList()
        }.joinToString(separator = ",\n")
}

internal fun stripEndSlash(path: String): String {
    return if (path.endsWith("/")) {
        path.substring(0, path.length - 1)
    } else {
        path
    }
}

private fun makeLocalDevPackageFileText(
    swiftPackageFolder: File,
    layoutBuildDir: File,
    frameworkName: String,
    swiftToolVersion: SwiftToolVersion,
    platforms: String
): String {
    val swiftFolderPath = swiftPackageFolder.toPath()
    val projectBuildFolderPath = layoutBuildDir.toPath()
    val xcFrameworkPath =
        "${swiftFolderPath.relativize(projectBuildFolderPath)}/XCFrameworks/${NativeBuildType.DEBUG.getName()}"
    val packageFileString = """
// swift-tools-version:${swiftToolVersion.name}
import PackageDescription

let packageName = "$frameworkName"

let package = Package(
    name: packageName,
    platforms: [
        $platforms
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            path: "./${xcFrameworkPath}/\(packageName).xcframework"
        )
        ,
    ]
)
""".trimIndent()
    return packageFileString
}

internal fun getModifiedPackageFileText(
    oldPackageFile: String,
    packageName: String,
    perModuleVariablesBlock: Boolean,
    url: String,
    checksum: String,
): String = buildString {
    var editingManagedBlock = false
    val (startTag, endTag) = kmmBridgeVariablesForPackage(packageName, perModuleVariablesBlock)

    oldPackageFile.lines().forEach { line ->
        when {
            line.trim() == endTag -> {
                editingManagedBlock = false
            }

            editingManagedBlock -> {
                // Ignore old lines in our managed blocks because we've already edited them
            }

            line.trim() == startTag -> {
                editingManagedBlock = true
                val indent = line.split(startTag).first()

                appendLine(
                    makePackageDetailsText(packageName, url, checksum, perModuleVariablesBlock)
                        .prependIndent(indent)
                )
            }

            else -> {
                appendLine(line)
            }
        }
    }
}.removeSuffix("\n")

private fun kmmBridgeVariablesForPackage(
    packageName: String,
    perModuleVariablesBlock: Boolean,
): Pair<String, String> {
    if (!perModuleVariablesBlock) {
        return "// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)" to "// END KMMBRIDGE BLOCK"
    }

    return "// BEGIN KMMBRIDGE VARIABLES BLOCK FOR '$packageName' (do not edit)" to "// END KMMBRIDGE BLOCK FOR '$packageName'"
}

private fun makePackageFileText(
    packageName: String,
    url: String,
    checksum: String,
    perModuleVariablesBlock: Boolean,
    swiftToolVersion: SwiftToolVersion,
    platforms: String
): String = """
// swift-tools-version:${swiftToolVersion.name}
import PackageDescription

${makePackageDetailsText(packageName, url, checksum, perModuleVariablesBlock)}

let package = Package(
    name: ${packageNameVariableName(packageName, perModuleVariablesBlock)},
    platforms: [
        $platforms
    ],
    products: [
        .library(
            name: ${packageNameVariableName(packageName, perModuleVariablesBlock)},
            targets: [${packageNameVariableName(packageName, perModuleVariablesBlock)}]
        ),
    ],
    targets: [
        .binaryTarget(
            name: ${packageNameVariableName(packageName, perModuleVariablesBlock)},
            url: ${urlVariableName(packageName, perModuleVariablesBlock)},
            checksum: ${checksumVariableName(packageName, perModuleVariablesBlock)}
        )
        ,
    ]
)
""".trimIndent()

private fun makePackageDetailsText(
    packageName: String,
    url: String,
    checksum: String,
    perModuleVariablesBlock: Boolean,
): String {
    val (startTag, endTag) = kmmBridgeVariablesForPackage(packageName, perModuleVariablesBlock)

    val remoteUrlVarName = urlVariableName(packageName, perModuleVariablesBlock)
    val remoteChecksumVarName = checksumVariableName(packageName, perModuleVariablesBlock)
    val remotePackageName = packageNameVariableName(packageName, perModuleVariablesBlock)

    return """
        $startTag
        let $remoteUrlVarName = "$url"
        let $remoteChecksumVarName = "$checksum"
        let $remotePackageName = "$packageName"
        $endTag
    """.trimIndent()
}

private fun urlVariableName(packageName: String, perModuleVariablesBlock: Boolean): String =
    if (perModuleVariablesBlock) {
        "remote${packageName}Url"
    } else {
        "remoteKotlinUrl"
    }

private fun checksumVariableName(packageName: String, perModuleVariablesBlock: Boolean): String =
    if (perModuleVariablesBlock) {
        "remote${packageName}Checksum"
    } else {
        "remoteKotlinChecksum"
    }

private fun packageNameVariableName(packageName: String, perModuleVariablesBlock: Boolean): String =
    if (perModuleVariablesBlock) {
        "${packageName.replaceFirstChar { it.lowercase(Locale.US) }}PackageName"
    } else {
        "packageName"
    }

/**
 * For new projects that aren't in git repos, it's *probably* OK to just return the current folder
 * until this is resolved, or let the user enter it manually.
 */
private fun ProviderFactory.findRepoRoot(projectDir: File): String {
    val results = exec {
        commandLine("git", "rev-parse", "--show-toplevel")
    }.standardOutput.asText.get().lines()

    return if (results.isEmpty()) {
        "."
    } else {
        val repoFile = File(results.first())
        projectDir.toPath().relativize(repoFile.toPath()).toString()
    }
}

private fun buildPackageFileErrorMessage(
    packageName: String,
    perModuleVariablesBlock: Boolean,
): String {
    val (beginTag, endTag) = kmmBridgeVariablesForPackage(packageName, perModuleVariablesBlock)

    return """
    KMMBridge: SPM configured with useCustomPackageFile=true, but no custom variable block detected! Add the following lines to your package file to generate variables for binaryTarget() declaration:
        $beginTag
        $endTag
    """.trimIndent()
}