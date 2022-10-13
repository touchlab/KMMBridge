package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.githubRepo
import co.touchlab.faktory.internal.GithubEnterpriseCalls
import co.touchlab.faktory.kmmBridgeExtension
import org.gradle.api.Project
import java.io.File

class GithubEnterpriseReleaseArtifactManager(
    private val artifactReleaseArg: String?
): ArtifactManager {

    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val repoName = project.githubRepo

        val artifactReleaseTag = artifactReleaseArg ?: "kmm-artifacts-${project.kmmBridgeExtension.versionPrefix.get()}"

        val idReply: Int = GithubEnterpriseCalls.findReleaseId(project, repoName, artifactReleaseTag) ?: GithubEnterpriseCalls.createRelease(
            project,
            repoName,
            artifactReleaseTag,
            null
        )

        val fileName = artifactName(project, version)

        val uploadUrl = GithubEnterpriseCalls.uploadZipFile(project, zipFilePath, repoName, idReply, fileName)
        return "${uploadUrl}.zip"
    }

}

private fun artifactName(project: Project, versionString: String): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "$frameworkName-${versionString}-${(System.currentTimeMillis()/1000)}.xcframework.zip"
}