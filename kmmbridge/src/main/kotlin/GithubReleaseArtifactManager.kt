package co.touchlab.faktory

import co.touchlab.faktory.internal.GithubCalls
import org.gradle.api.Project
import java.io.File

class GithubReleaseArtifactManager(
    private val artifactReleaseArg: String?
) : ArtifactManager {
    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val repoName: String = project.githubRepo

        val artifactReleaseTag = artifactReleaseArg ?: "kmm-artifacts-${project.kmmBridgeExtension.versionPrefix.get()}"

        val idReply: Int = GithubCalls.findReleaseId(project, repoName, artifactReleaseTag) ?: GithubCalls.createRelease(
            project,
            repoName,
            artifactReleaseTag,
            null
        )

        val fileName = artifactName(project, version)

        val uploadUrl = GithubCalls.uploadZipFile(project, zipFilePath, repoName, idReply, fileName)
        return "${uploadUrl}.zip"
    }
}

class GithubReleaseException(message: String, cause: Throwable? = null) : Exception(message, cause)

private fun artifactName(project: Project, versionString: String): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "$frameworkName-${versionString}-${(System.currentTimeMillis()/1000)}.xcframework.zip"
}
