/*
 * Copyright (c) 2022 Touchlab.
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
import localdevmanager.LocalDevManager
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
    private val _swiftPackageFolder: String?
) : DependencyManager, LocalDevManager {
    private fun Project.swiftPackageFolder(): String = _swiftPackageFolder ?: this.findRepoRoot()
    private fun Project.swiftPackageFilePath(): String = "${stripEndSlash(swiftPackageFolder())}/Package.swift"

    override fun configure(project: Project, uploadTask: TaskProvider<Task>, publishRemoteTask: TaskProvider<Task>) {
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
                    project.writePackageFile(extension.frameworkName.get(), url, checksum)
                    val version = project.versionFile.readText()
                    val versionWriter = extension.versionWriter.get()

                    // This feels like it shouldn't be here, but if we're trying to be precise with git operations,
                    // moving this would require leaking info about the file outside, which also seems weird. I'm
                    // still not sure we should try to be this precise with the git ops, considering this should
                    // pretty much always be in CI, but anyway.
                    versionWriter.runGitStatement(project, "add", project.file(project.swiftPackageFilePath()).absolutePath)
                    versionWriter.runGitStatement(project, "commit", "-m", "KMM SPM package release for $version")
                    versionWriter.runGitStatement(project, "push")
                }
            })
        }

        updatePackageSwiftTask.configure { dependsOn(uploadTask) }
        publishRemoteTask.configure { dependsOn(updatePackageSwiftTask) }
    }

    private fun Project.writePackageFile(packageName: String, url: String, checksum: String) {
        val swiftPackageFile = file(swiftPackageFilePath())
        val packageText = makePackageFileText(packageName, url, checksum)
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

private fun makeLocalDevPackageFileText(swiftPackageFolder: String, frameworkName: String, project: Project): String {
    val swiftFolderPath = project.file(swiftPackageFolder).toPath()
    val projectBuildFolderPath = project.buildDir.toPath()
    val xcFrameworkPath =
        "${swiftFolderPath.relativize(projectBuildFolderPath)}/XCFrameworks/${NativeBuildType.DEBUG.getName()}"
    val packageFileString = """
// swift-tools-version:5.3
import PackageDescription

let packageName = "$frameworkName"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
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

private fun makePackageFileText(packageName: String, url: String, checksum: String): String = """
// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "$url"
let remoteKotlinChecksum = "$checksum"
let packageName = "$packageName"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
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
