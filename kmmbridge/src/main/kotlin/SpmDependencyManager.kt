package co.touchlab.faktory

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
    private val swiftPackageFolder: String
) : DependencyManager {

    private val swiftPackageFile: String
        get() = "${stripEndSlash(swiftPackageFolder)}/Package.swift"

    override fun doExtraConfiguration(project: Project, uploadTask: Task, publishRemoteTask: Task) {
        val updatePackageSwiftTask = project.task("updatePackageSwift") {
            group = TASK_GROUP_NAME
            val zipFile = project.zipFilePath()
            inputs.files(zipFile, project.urlFile)

            doLast {
                val checksum = project.findSpmChecksum(zipFile)
                val url = project.urlFile.readText()

                project.alterPackageFile(url, checksum)
            }
        }

        updatePackageSwiftTask.dependsOn(uploadTask)
        publishRemoteTask.dependsOn(updatePackageSwiftTask)
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

    private fun <R> Project.reviewPackageFile(block: (List<String>) -> R): R {
        val swiftPackageFile = file(swiftPackageFile)

        val allLines = swiftPackageFile.readLines().toList()

        return block(allLines)
    }

    private fun Project.alterPackageFileByLine(block: (String) -> String) {
        val swiftPackageFile = file(swiftPackageFile)
        val sb = StringBuilder()

        swiftPackageFile.forEachLine { line ->
            sb.append(block(line)).append("\n")
        }

        swiftPackageFile.delete()

        swiftPackageFile.outputStream().bufferedWriter().use {
            it.write(
                sb.toString()
            )
        }
    }

    private fun Project.alterPackageFile(remoteUrl: String, checksum: String) {
        validateFieldsInPackageSwift()

        val checksumFound = reviewPackageFile { lines ->
            lines.find { it.contains(checksum) } != null
        }

        alterPackageFileByLine { line ->
            if (!checksumFound && line.contains("let remoteKotlinUrl")) {
                val i = line.indexOf("let remoteKotlinUrl")
                return@alterPackageFileByLine "let remoteKotlinUrl = \"${remoteUrl}\"".padStart(i)
            }

            if (!checksumFound && line.contains("let remoteKotlinChecksum")) {
                val i = line.indexOf("let remoteKotlinChecksum")
                return@alterPackageFileByLine "let remoteKotlinChecksum = \"${checksum}\"".padStart(i)
            }

            return@alterPackageFileByLine line
        }

//        outputs.file(swiftPackageFile)
    }

    private fun Project.validateFieldsInPackageSwift() {
        val validPackageFile = reviewPackageFile { lines ->
            lines.find { it.contains("let remoteKotlinUrl") } != null &&
                    lines.find { it.contains("let remoteKotlinChecksum") } != null
        }

        if (!validPackageFile) {
            throw GradleException("Invalid Package.swift file")
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
