package co.touchlab.faktory

import co.touchlab.faktory.co.touchlab.faktory.internal.procRun
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
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

    override fun doExtraConfiguration(project: Project, uploadTask: Task, publishRemoteTask: Task) {
        val updatePackageSwiftTask = project.task("updatePackageSwift") {
            group = TASK_GROUP_NAME
            val zipFile = project.zipFilePath()
            inputs.files(zipFile, project.urlFile)

            doLast {
                val originalPackageFile = project.readPackageFile()

                val checksum = project.findSpmChecksum(zipFile)
                val url = project.urlFile.readText()

                project.writePackageFile(packageName, url, checksum)
                val versionFile = project.versionFile
                versionFile.parentFile.mkdirs()
                val version = project.kmmBridgeVersion
                versionFile.writeText(version)

                procRun("git", "add", ".") { _, _ -> }
                procRun("git", "commit", "-m", "KMM SPM package release for $version") { _, _ -> }
                procRun("git", "tag", "-a", version, "-m", "KMM release version $version") { line, _ -> }

                project.writePackageFile(originalPackageFile)

                procRun("git", "add", ".") { _, _ -> }
                procRun("git", "commit", "-m", "KMM SPM package file revert") { _, _ -> }
                procRun("git", "push", "--follow-tags") { _, _ -> }
            }
        }

        updatePackageSwiftTask.dependsOn(uploadTask)
        publishRemoteTask.dependsOn(updatePackageSwiftTask)
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

private val Project.versionFile get() = file("$buildDir/faktory/version")

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