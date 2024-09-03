package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.githubRepo
import co.touchlab.faktory.kmmBridgeExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File

class GithubReleaseArtifactManager(
    private val repository: String?, private val releaseString: String?, private val useExistingRelease: Boolean
) : ArtifactManager {
    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val releaseVersion = releaseString ?: project.version.toString()
        val repoName: String = repository ?: project.githubRepo

        val existingReleaseId = GithubCalls.findReleaseId(
            project, repoName, releaseVersion
        )

        project.logger.info("existingReleaseId: $existingReleaseId")

        if (existingReleaseId != null && !useExistingRelease) {
            throw GradleException("Release for '$releaseVersion' exists. Set 'useExistingRelease = true' to update existing releases.")
        }

        val idReply: Int = existingReleaseId ?: GithubCalls.createRelease(
            project, repoName, releaseVersion, null
        )

        project.logger.info("GitHub Release created with id: $idReply")

        val fileName = artifactName(project, version, useExistingRelease)

        val uploadUrl = GithubCalls.uploadZipFile(project, zipFilePath, repoName, idReply, fileName)
        return "${uploadUrl}.zip"
    }
}

private fun artifactName(project: Project, versionString: String, useExistingRelease: Boolean): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return if (useExistingRelease) {
        "$frameworkName-${versionString}-${(System.currentTimeMillis() / 1000)}.xcframework.zip"
    } else {
        "$frameworkName.xcframework.zip"
    }
}