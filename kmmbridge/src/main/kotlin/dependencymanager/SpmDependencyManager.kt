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
import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset

class SpmDependencyManager(
    /**
     * Folder where the Package.swift file lives
     */
    private val _swiftPackageFolder: String?,
    private val packageName: String
) : DependencyManager {
    private fun Project.swiftPackageFolder(): String = _swiftPackageFolder ?: this.findRepoRoot()
    private fun Project.swiftPackageFilePath(): String = "${stripEndSlash(swiftPackageFolder())}/Package.swift"

    override fun configure(project: Project, uploadTask: Task, publishRemoteTask: Task) {
        val updatePackageSwiftTask = project.task("updatePackageSwift") {
            group = TASK_GROUP_NAME
            val zipFile = project.zipFilePath()
            inputs.files(zipFile, project.urlFile, project.versionFile)

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val checksum = project.findSpmChecksum(zipFile)
                    val url = project.urlFile.readText()

                    project.writePackageFile(packageName, url, checksum)

                    val version = project.versionFile.readText()

                    project.procRunFailLog("git", "add", ".")
                    project.procRunFailLog("git", "commit", "-m", "KMM SPM package release for $version")
                    project.procRunFailLog("git", "push")
                }
            })
        }

        updatePackageSwiftTask.dependsOn(uploadTask)
        publishRemoteTask.dependsOn(updatePackageSwiftTask)

        project.task("spmDevBuild") {
            group = TASK_GROUP_NAME
            dependsOn(project.findXCFrameworkAssembleTask(NativeBuildType.DEBUG))

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    project.writePackageFile(makeLocalDevPackageFileText(project.swiftPackageFolder(), packageName, project))
                }
            })
        }
    }

    private fun Project.writePackageFile(packageName: String, url: String, checksum: String){
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

    private fun Project.writePackageFile(data:String){
        file(swiftPackageFilePath()).writeText(data)
    }

    override val needsGitTags: Boolean = true
}

internal fun stripEndSlash(path: String): String {
    return if (path.endsWith("/")) {
        path.substring(0, path.length - 1)
    } else {
        path
    }
}

private fun makeLocalDevPackageFileText(swiftPackageFolder:String, packageName: String, project: Project): String {
    val swiftFolderPath = project.file(swiftPackageFolder).toPath()
    val projectFolderPath = project.projectDir.toPath()
    val xcFrameworkPath = "${swiftFolderPath.relativize(projectFolderPath)}/build/XCFrameworks/${NativeBuildType.DEBUG.getName()}"
    val packageFileString = """
// swift-tools-version:5.3
import PackageDescription

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
            path: "./${xcFrameworkPath}/\(packageName).xcframework"
        )
        ,
    ]
)
""".trimIndent()

    return packageFileString
}

private fun makePackageFileText(packageName: String, url:String, checksum: String): String = """
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

private fun Project.findRepoRoot(): String {
    val repoFile = File(procRunFailLog("git", "rev-parse", "--show-toplevel").first())
    return projectDir.toPath().relativize(repoFile.toPath()).toString()
}