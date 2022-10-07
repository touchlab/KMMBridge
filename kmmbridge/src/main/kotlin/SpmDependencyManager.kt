package co.touchlab.faktory

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
    private val swiftPackageFolder: String,
    private val packageName: String
) : DependencyManager {
    private val swiftPackageFilePath: String
        get() = "${stripEndSlash(swiftPackageFolder)}/Package.swift"

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
                    project.writePackageFile(makeLocalDevPackageFileText(swiftPackageFolder, packageName, project))
                }
            })
        }
    }

    private fun Project.writePackageFile(packageName: String, url: String, checksum: String){
        val swiftPackageFile = file(swiftPackageFilePath)
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

    private fun Project.readPackageFile(): String = file(swiftPackageFilePath).readText()
    private fun Project.writePackageFile(data:String){
        file(swiftPackageFilePath).writeText(data)
    }
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
