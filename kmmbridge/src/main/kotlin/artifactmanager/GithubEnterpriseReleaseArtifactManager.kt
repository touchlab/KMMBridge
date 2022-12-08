package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.githubEnterpriseHost
import co.touchlab.faktory.githubEnterpriseRepoOwner
import co.touchlab.faktory.githubRepo
import co.touchlab.faktory.internal.GithubEnterpriseCalls
import co.touchlab.faktory.kmmBridgeExtension
import org.gradle.api.Project
import java.io.File
import java.net.URLEncoder

class GithubEnterpriseReleaseArtifactManager(
    private val artifactReleaseArg: String?
): ArtifactManager {

    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val deployUrl = deployUrl(project, version)
        val uploadUrl = GithubEnterpriseCalls.uploadZipFile(project, zipFilePath, deployUrl.url)
        return "${uploadUrl}.zip"
    }

    override fun deployUrl(project: Project, version: String): ArtifactManager.DeployUrl {
        val repoName = project.githubRepo
        val artifactReleaseTag = artifactReleaseArg ?: "kmm-artifacts-${project.kmmBridgeExtension.versionPrefix.get()}"
        val fileName = artifactName(project, version)

        val idReply: Int = GithubEnterpriseCalls.findReleaseId(project, repoName, artifactReleaseTag) ?: GithubEnterpriseCalls.createRelease(
            project,
            repoName,
            artifactReleaseTag,
            null
        )
        return ArtifactManager.DeployUrl(fileName, createUrl(project, project.githubRepo, idReply, fileName))
    }

    private fun createUrl(project: Project, repo: String, releaseId: Int, fileName: String): String {
        val host = project.githubEnterpriseHost
        val owner = project.githubEnterpriseRepoOwner

        return "https://${host}/api/v3/repos/${owner}/${repo}/releases/${releaseId}/assets?name=${
            URLEncoder.encode(
                fileName, "UTF-8"
            )
        }"
    }
}

private fun artifactName(project: Project, versionString: String): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "$frameworkName-${versionString}-${(System.currentTimeMillis()/1000)}.xcframework.zip"
}