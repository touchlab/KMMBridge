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

import co.touchlab.faktory.*
import co.touchlab.faktory.internal.procRunWarnLog
import co.touchlab.faktory.localdevmanager.LocalDevManager
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset

class SpmDependencyManager(
    /**
     * Folder where the Package.swift file lives
     */
    private val _swiftPackageFolder: String?,
    private val useCustomPackageFile: Boolean,
    private val spmConfig: SpmConfig,
) : DependencyManager, LocalDevManager {
    private fun Project.swiftPackageFolder(): String = _swiftPackageFolder ?: this.findRepoRoot()
    private fun Project.swiftPackageFilePath(): String = "${stripEndSlash(swiftPackageFolder())}/Package.swift"

    override fun configure(project: Project, uploadTask: TaskProvider<Task>, publishRemoteTask: TaskProvider<Task>) {
        if (useCustomPackageFile && !project.hasKmmbridgeVariablesSection()) {
            project.logger.error(CUSTOM_PACKAGE_FILE_ERROR)
        }

        val extension = project.kmmBridgeExtension
        val updatePackageSwiftTask = project.tasks.register("updatePackageSwift") {
            group = TASK_GROUP_NAME
            val zipFile = project.zipFilePath()
            inputs.files(zipFile, project.urlFile, project.versionFile)
            outputs.files(project.swiftPackageFilePath())

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val checksum = project.findSpmChecksum(zipFile)
                    val url = project.urlFile.readText()
                    if (useCustomPackageFile && project.hasKmmbridgeVariablesSection()) {
                        project.modifyPackageFileVariables(extension.frameworkName.get(), url, checksum)
                    } else if (useCustomPackageFile) {
                        // We warned you earlier, but you didn't fix it, so now we interrupt the publish process because it's
                        // probably not going to do what you want
                        error(CUSTOM_PACKAGE_FILE_ERROR)
                    } else {
                        project.writePackageFile(extension.frameworkName.get(), url, checksum)
                    }

                    // TODO: Maybe write Package file path?
                }
            })
        }

        updatePackageSwiftTask.configure { dependsOn(uploadTask) }
        publishRemoteTask.configure { dependsOn(updatePackageSwiftTask) }
    }

    private fun Project.hasKmmbridgeVariablesSection(): Boolean {
        val swiftPackageFile = file(swiftPackageFilePath())
        return swiftPackageFile.readText().contains(KMMBRIDGE_VARIABLES_BEGIN)
    }

    private fun Project.modifyPackageFileVariables(
        packageName: String,
        url: String,
        checksum: String,
    ) {
        val packageFile = file(swiftPackageFilePath())
        packageFile.writeText(
            getModifiedPackageFileText(
                packageFile.readText(),
                packageName,
                url,
                checksum
            )
        )
    }

    private fun Project.writePackageFile(packageName: String, url: String, checksum: String) {
        val swiftPackageFile = file(swiftPackageFilePath())
        val packageText = makePackageFileText(
            packageName,
            spmConfig.swiftToolsVersion,
            spmConfig.getPlatformsAsFormattedText(),
            url, checksum
        )
        swiftPackageFile.parentFile.mkdirs()
        swiftPackageFile.writeText(packageText)
    }

    private fun Project.findSpmChecksum(zipFilePath: File): String {
        val os = ByteArrayOutputStream()

        // checksum requires a package file, but doesn't require it to be real (and we might only have cocoapods)
        val packageSwiftFile = file("Package.swift")
        val hadPackageSwift = packageSwiftFile.exists()

        if (!hadPackageSwift) {
            packageSwiftFile.writeText("")
        }

        exec {
            commandLine(
                "swift",
                "package",
                "compute-checksum",
                zipFilePath.path
            )
            standardOutput = os
        }

        if (!hadPackageSwift) {
            packageSwiftFile.delete()
        }

        return os.toByteArray().toString(Charset.defaultCharset()).trim()
    }

    private fun Project.writePackageFile(data: String) {
        file(swiftPackageFilePath()).writeText(data)
    }

    override val needsGitTags: Boolean = true
    override fun configureLocalDev(project: Project) {
        if (useCustomPackageFile) return // No local dev when using a custom package file

        val extension = project.kmmBridgeExtension
        project.tasks.register("spmDevBuild") {
            group = TASK_GROUP_NAME
            dependsOn(project.findXCFrameworkAssembleTask(NativeBuildType.DEBUG))

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    project.writePackageFile(
                        makeLocalDevPackageFileText(
                            project.swiftPackageFolder(),
                            spmConfig.swiftToolsVersion,
                            spmConfig.getPlatformsAsFormattedText(),
                            extension.frameworkName.get(),
                            project
                        )
                    )
                }
            })
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
    swiftPackageFolder: String,
    swiftPackageToolsVersion: String,
    swiftPackagePlatforms: String,
    frameworkName: String,
    project: Project,
): String {
    val swiftFolderPath = project.file(swiftPackageFolder).toPath()
    val projectBuildFolderPath = project.layoutBuildDir.toPath()
    val xcFrameworkPath =
        "${swiftFolderPath.relativize(projectBuildFolderPath)}/XCFrameworks/${NativeBuildType.DEBUG.getName()}"

    return """
// swift-tools-version:$swiftPackageToolsVersion
import PackageDescription

let packageName = "$frameworkName"

let package = Package(
    name: packageName,
    platforms: [
$swiftPackagePlatforms
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
}

private const val KMMBRIDGE_VARIABLES_BEGIN = "// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)"
private const val KMMBRIDGE_END = "// END KMMBRIDGE BLOCK"

internal fun getModifiedPackageFileText(
    oldPackageFile: String,
    packageName: String,
    url: String,
    checksum: String,
): String = buildString {
    var editingManagedBlock = false
    oldPackageFile.lines().forEach { line ->
        when {
            line.trim() == KMMBRIDGE_END -> {
                editingManagedBlock = false
            }

            editingManagedBlock -> {
                // Ignore old lines in our managed blocks because we've already edited them
            }

            line.trim() == KMMBRIDGE_VARIABLES_BEGIN -> {
                editingManagedBlock = true
                val indent = line.split(KMMBRIDGE_VARIABLES_BEGIN).first()
                appendLine(
                    """
                    $KMMBRIDGE_VARIABLES_BEGIN
                    let remoteKotlinUrl = "$url"
                    let remoteKotlinChecksum = "$checksum"
                    let packageName = "$packageName"
                    $KMMBRIDGE_END
                """.trimIndent().prependIndent(indent)
                )
            }

            else -> {
                appendLine(line)
            }
        }
    }
}.removeSuffix("\n")

private fun makePackageFileText(
    packageName: String,
    swiftPackageToolsVersion: String,
    swiftPackagePlatforms: String,
    url: String, checksum: String,
): String = """
// swift-tools-version:$swiftPackageToolsVersion
import PackageDescription

$KMMBRIDGE_VARIABLES_BEGIN
let remoteKotlinUrl = "$url"
let remoteKotlinChecksum = "$checksum"
let packageName = "$packageName"
$KMMBRIDGE_END

let package = Package(
    name: packageName,
    platforms: [
        ${swiftPackagePlatforms}
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
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)
""".trimIndent()

/**
 * For new projects that aren't in git repos, it's *probably* OK to just return the current folder
 * until this is resolved, or let the user enter it manually.
 */
private fun Project.findRepoRoot(): String {
    val results = procRunWarnLog("git", "rev-parse", "--show-toplevel")
    return if (results.isEmpty()) {
        "."
    } else {
        val repoFile = File(results.first())
        projectDir.toPath().relativize(repoFile.toPath()).toString()
    }
}

private val CUSTOM_PACKAGE_FILE_ERROR =
    """
    KMMBridge: SPM configured with useCustomPackageFile=true, but no custom variable block detected! Add the following lines to your package file to generate variables for binaryTarget() declaration:
        // BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
        // END KMMBRIDGE BLOCK
    """.trimIndent()



class SpmConfig {
    var swiftToolsVersion: String = "5.9"
    var platforms = mutableListOf<Platform>()

    fun platforms(block: List<Platform>.() -> Unit) {
        platforms.apply(block)
    }

    fun iOS(block: PlatformVersion.() -> Unit) {
        platforms.add(Platform(PlatformName.IOS, PlatformVersion().default("17").apply(block)))
    }

    fun macOS(block: PlatformVersion.() -> Unit) {
        platforms.add(Platform(PlatformName.MacOS, PlatformVersion().default("13").apply(block)))
    }

    fun tvOS(block: PlatformVersion.() -> Unit) {
        platforms.add(Platform(PlatformName.TvOS, PlatformVersion().default("17.3").apply(block)))
    }

    fun watchOS(block: PlatformVersion.() -> Unit) {
        platforms.add(Platform(PlatformName.WatchOS, PlatformVersion().default("9").apply(block)))
    }

    fun getPlatformsAsFormattedText(): String {
        val formattedPlatforms = platforms.joinToString(",\n") { platform ->
            "        .${platform.name.value}(.v${platform.version.version})"
        }
        return formattedPlatforms
    }

}

data class Platform(
    val name: PlatformName,
    val version: PlatformVersion,
)

enum class PlatformName(val value: String) {
    IOS("iOS"),
    MacOS("macOS"),
    TvOS("tvOS"),
    WatchOS("watchOS"),
}

class PlatformVersion {
    var version: String? = null

    fun v(version: String) {
        this.version = version
    }

    fun default(version: String): PlatformVersion {
        if (this.version == null) {
            this.version = version
        }
        return this
    }
}


