package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.githubRepo
import co.touchlab.faktory.kmmBridgeExtension
import co.touchlab.faktory.versionFile
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File

class GithubReleaseArtifactManager(
    private val repository: String?, private val releaseString: String?, private val useExistingRelease: Boolean
) : ArtifactManager {
    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val releaseVersion = releaseString ?: project.versionFile.readText()
        val repoName: String = repository ?: project.githubRepo

        val existingReleaseId = GithubCalls.findReleaseId(
            project, repoName, releaseVersion
        )

        if (existingReleaseId != null && !useExistingRelease) {
            throw GradleException("Release for '$releaseVersion' exists. Set 'useExistingRelease = true' to update existing releases.")
        }

        val idReply: Int = existingReleaseId ?: GithubCalls.createRelease(
            project, repoName, releaseVersion, null
        )

        val fileName = artifactName(project, version)

        val uploadUrl = GithubCalls.uploadZipFile(project, zipFilePath, repoName, idReply, fileName)
        return "${uploadUrl}.zip"
    }
}

private fun artifactName(project: Project, versionString: String): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "$frameworkName-${versionString}-${(System.currentTimeMillis() / 1000)}.xcframework.zip"
}