package co.touchlab.faktory

import co.touchlab.faktory.co.touchlab.faktory.internal.GithubCalls
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.gradle.api.Project
import java.io.File
import java.net.URLEncoder
import java.time.Duration

/**
 * Helper function for gradle config. If using Github Actions CI, you can leave the arguments empty as the workflow
 * should provide the values needed.
 */
fun KmmBridgeExtension.githubRelease(
    artifactRelease: String? = null
) {
    artifactManager.set(GithubReleaseArtifactManager(artifactRelease))
    versionManager.set(GitTagVersionManager)
}

class GithubReleaseArtifactManager(
    private val artifactReleaseArg: String?
) : ArtifactManager {
    override fun deployArtifact(project: Project, zipFilePath: File): String {
        val repoName: String = project.githubRepo

        val artifactReleaseTag = artifactReleaseArg ?: "kmm-artifacts-${project.kmmBridgeExtension.versionPrefix.get()}"

        val idReply: Int = GithubCalls.findReleaseId(project, repoName, artifactReleaseTag) ?: GithubCalls.createRelease(
            project,
            repoName,
            artifactReleaseTag,
            null
        )

        val fileName = artifactName(project, project.kmmBridgeVersion)

        val uploadUrl = GithubCalls.uploadZipFile(project, zipFilePath, repoName, idReply, fileName)
        return "${uploadUrl}.zip"
    }
}

class GithubReleaseException(message: String, cause: Throwable? = null) : Exception(message, cause)

data class IdReply(var id: Int)

data class UploadReply(var url: String)

private fun artifactName(project: Project, versionString: String): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "$frameworkName-${versionString}-${(System.currentTimeMillis()/1000)}.xcframework.zip"
}