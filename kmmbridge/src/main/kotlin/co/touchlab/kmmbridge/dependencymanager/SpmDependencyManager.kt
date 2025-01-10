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

package co.touchlab.kmmbridge.dependencymanager

import co.touchlab.kmmbridge.TASK_GROUP_NAME
import co.touchlab.kmmbridge.dsl.TargetPlatformDsl
import co.touchlab.kmmbridge.internal.domain.SwiftToolVersion
import co.touchlab.kmmbridge.internal.domain.TargetPlatform
import co.touchlab.kmmbridge.internal.domain.konanTarget
import co.touchlab.kmmbridge.internal.domain.swiftPackagePlatformName
import co.touchlab.kmmbridge.internal.findXCFrameworkAssembleTask
import co.touchlab.kmmbridge.internal.kmmBridgeExtension
import co.touchlab.kmmbridge.internal.kotlin
import co.touchlab.kmmbridge.internal.layoutBuildDir
import co.touchlab.kmmbridge.internal.urlFile
import co.touchlab.kmmbridge.internal.zipFilePath
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.withType
import org.gradle.process.ExecOperations
import org.gradle.process.internal.ExecException
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset
import java.util.*
import javax.inject.Inject

internal class SpmDependencyManager(
    /**
     * Folder where the Package.swift file lives
     */
    private val _swiftPackageFolder: String?,
    private val useCustomPackageFile: Boolean,
    private val perModuleVariablesBlock: Boolean,
    private val _swiftToolVersion: String,
    private val _targetPlatforms: TargetPlatformDsl.() -> Unit,
) : DependencyManager {
    /**
     * For new projects that aren't in git repos, it's *probably* OK to just return the current folder
     * until this is resolved, or let the user enter it manually.
     */
    private fun Project.findRepoRoot(projectDir: File): String {
        val result = providers.of(GitRevParseValue::class.java) {}.get()
        val repoRootFile = if (result == "") {
            projectDir
        } else {
            File(result)
        }
        return repoRootFile.toString()
    }

    private fun Project.swiftPackageFile(projectDir: File): File {
        val swiftPackageFolderPath = _swiftPackageFolder ?: this.findRepoRoot(projectDir)
        return project.file("${stripEndSlash(swiftPackageFolderPath)}/Package.swift")
    }

    override fun configure(
        providers: ProviderFactory,
        project: Project,
        version: String,
        uploadTask: TaskProvider<Task>,
        publishRemoteTask: TaskProvider<Task>
    ) {
        val extension = project.kmmBridgeExtension
        val projectDir = project.projectDir
        val swiftToolVersion = SwiftToolVersion.of(_swiftToolVersion)
            ?: throw IllegalArgumentException("Parameter swiftToolVersion should be not blank!")
        val platforms = swiftTargetPlatforms(project)

        val swiftPackageFile = project.swiftPackageFile(project.rootDir)
        val packageName = extension.frameworkName.get()
        if (useCustomPackageFile && !hasKmmbridgeVariablesSection(swiftPackageFile, packageName)) {
            project.logger.error(buildPackageFileErrorMessage(packageName, perModuleVariablesBlock))
        }

        val updatePackageSwiftTask = project.tasks.register("updatePackageSwift") {
            group = TASK_GROUP_NAME
            val zipFile = project.zipFilePath()
            inputs.files(zipFile, project.urlFile)
            outputs.files(swiftPackageFile)

            val urlFile = project.urlFile

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val checksum = providers.findSpmChecksum(zipFile, projectDir)
                    val url = urlFile.readText()
                    if (useCustomPackageFile && hasKmmbridgeVariablesSection(
                            swiftPackageFile,
                            packageName
                        )
                    ) {
                        modifyPackageFileVariables(swiftPackageFile, packageName, url, checksum)
                    } else if (useCustomPackageFile) {
                        // We warned you earlier, but you didn't fix it, so now we interrupt the publish process because it's
                        // probably not going to do what you want
                        error(buildPackageFileErrorMessage(packageName, perModuleVariablesBlock))
                    } else {
                        writePackageFile(
                            swiftPackageFile,
                            extension.frameworkName.get(),
                            url,
                            checksum,
                            swiftToolVersion,
                            platforms
                        )
                    }
                }
            })
        }

        updatePackageSwiftTask.configure { dependsOn(uploadTask) }
        publishRemoteTask.configure { dependsOn(updatePackageSwiftTask) }
    }

    private fun hasKmmbridgeVariablesSection(swiftPackageFile: File, packageName: String): Boolean {
        val (startTag) = kmmBridgeVariablesForPackage(packageName, perModuleVariablesBlock)
        return swiftPackageFile.readText().contains(startTag)
    }

    private fun modifyPackageFileVariables(
        swiftPackageFile: File,
        packageName: String,
        url: String,
        checksum: String,
    ) {
        swiftPackageFile.writeText(
            getModifiedPackageFileText(
                swiftPackageFile.readText(),
                packageName,
                perModuleVariablesBlock,
                url,
                checksum
            )
        )
    }

    private fun writePackageFile(
        swiftPackageFile: File,
        packageName: String,
        url: String,
        checksum: String,
        swiftToolVersion: SwiftToolVersion,
        platforms: String
    ) {

        val packageText =
            makePackageFileText(
                packageName,
                url,
                checksum,
                perModuleVariablesBlock,
                swiftToolVersion,
                platforms
            )
        swiftPackageFile.parentFile.mkdirs()
        swiftPackageFile.writeText(packageText)
    }

    private fun ProviderFactory.findSpmChecksum(zipFilePath: File, swiftPackageFile: File): String {
        // checksum requires a package file, but doesn't require it to be real (and we might only have cocoapods)
        val hadPackageSwift = swiftPackageFile.exists()

        if (!hadPackageSwift) {
            swiftPackageFile.writeText("")
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
            swiftPackageFile.delete()
        }

        return checksumOut.trim()
    }

    override val needsGitTags: Boolean = true
    fun configureLocalDev(project: Project) {
        if (useCustomPackageFile) return // No local dev when using a custom package file

        val extension = project.kmmBridgeExtension
        val swiftToolVersion = SwiftToolVersion.of(_swiftToolVersion)
            ?: throw IllegalArgumentException("Parameter swiftToolVersion should be not blank!")
        val platforms = swiftTargetPlatforms(project)

        project.tasks.register("spmDevBuild") {
            description =
                "When using SPM, builds a debug version of the XCFramework and writes a local dev path to your Package.swift."
            group = TASK_GROUP_NAME
            dependsOn(project.findXCFrameworkAssembleTask(NativeBuildType.DEBUG))

            val swiftPackageFile = project.swiftPackageFile(project.rootDir)
            val layoutBuildDir = project.layoutBuildDir

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    swiftPackageFile.writeText(
                        makeLocalDevPackageFileText(
                            swiftPackageFile,
                            layoutBuildDir,
                            extension.frameworkName.get(),
                            swiftToolVersion,
                            platforms
                        )
                    )
                }
            })
        }
    }

    private fun swiftTargetPlatforms(project: Project): String {
        val targetPlatforms = TargetPlatformDsl().apply(_targetPlatforms)
            .targetPlatforms
            .ifEmpty {
                throw IllegalArgumentException("At least one target platform should be specified!")
            }

        val platforms = platforms(project, targetPlatforms)
        return platforms
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

/**
 * Runs a git command to grab the root of the git repo. If there is no git repo, return an empty string.
 */
abstract class GitRevParseValue : ValueSource<String, ValueSourceParameters.None> {
    @get:Inject
    abstract val execOperations: ExecOperations

    override fun obtain(): String {
        val output = ByteArrayOutputStream()
        val error = ByteArrayOutputStream()
        return try {
            execOperations.exec {
                try {
                    commandLine("git", "rev-parse", "--show-toplevel")
                    standardOutput = output
                    errorOutput = error
                } catch (e: Exception) {
                }
            }
            String(output.toByteArray(), Charset.defaultCharset()).lines().first()
        } catch (e: ExecException) {
            ""
        }
    }
}

internal fun stripEndSlash(path: String): String {
    return if (path.endsWith("/")) {
        path.substring(0, path.length - 1)
    } else {
        path
    }
}

private fun makeLocalDevPackageFileText(
    swiftPackageFile: File,
    layoutBuildDir: File,
    frameworkName: String,
    swiftToolVersion: SwiftToolVersion,
    platforms: String
): String {
    val swiftFolderPath = swiftPackageFile.parentFile.toPath()
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