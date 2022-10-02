package co.touchlab.faktory

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.Project
import java.io.File
import java.net.URLEncoder
import java.time.Duration

/**
 * Helper function for gradle config. If using Github Actions CI, you can leave the arguments empty as the workflow
 * should provide the values needed.
 */
fun KmmBridgeExtension.githubRelease(
    repo: String? = null, artifactRelease: String? = null
) {
    artifactManager.set(GithubReleaseArtifactManager(repo, artifactRelease))
    versionManager.set(GithubReleaseVersionManager(repo))
}

class GithubReleaseArtifactManager(
    private val repoArg: String?, private val artifactReleaseArg: String?
) : ArtifactManager {
    private val okHttpClient =
        OkHttpClient.Builder().callTimeout(Duration.ofMinutes(5)).connectTimeout(Duration.ofMinutes(2))
            .writeTimeout(Duration.ofMinutes(5)).readTimeout(Duration.ofMinutes(2)).build()

    override fun deployArtifact(project: Project, zipFilePath: File): String {
        val repoName: String = repoArg ?: (project.findStringProperty("GITHUB_REPO")
            ?: throw IllegalArgumentException("GithubReleaseArtifactManager needs a repo param or property GITHUB_REPO")) as String

        val artifactReleaseTag = artifactReleaseArg ?: "kmm-artifacts-${project.kmmBridgeExtension.versionPrefix.get()}"

        val gson = Gson()
        val token = (project.property("GITHUB_PUBLISH_TOKEN")
            ?: throw IllegalArgumentException("GithubReleaseArtifactManager needs property GITHUB_PUBLISH_TOKEN")) as String

        val releaseId = releaseId(repoName, artifactReleaseTag, token, okHttpClient)

        val idReply: Int = if (releaseId == null) {
            val createReleaseBody = CreateReleaseBody(artifactReleaseTag)
            val createRequest = Request.Builder().url("https://api.github.com/repos/${repoName}/releases")
                .post(gson.toJson(createReleaseBody).toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader("Accept", "application/vnd.github+json").addHeader("Authorization", "Bearer $token").build()

            gson.fromJson(okHttpClient.newCall(createRequest).execute().body!!.string(), IdReply::class.java).id
        } else {
            releaseId
        }

        val body: RequestBody = zipFilePath.asRequestBody("application/zip".toMediaTypeOrNull())

        val fileName = artifactName(project, project.kmmBridgeVersion)

        val uploadRequest = Request.Builder().url(
            "https://uploads.github.com/repos/${repoName}/releases/${idReply}/assets?name=${
                URLEncoder.encode(
                    fileName, "UTF-8"
                )
            }"
        ).post(body).addHeader("Accept", "application/vnd.github+json").addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/zip").build()

        val response = okHttpClient.newCall(uploadRequest).execute()
        if (response.code != 201) {
            throw GithubReleaseException("Upload call failed ${response.code}, ${response.message}")
        }
        val uploadResponseString = response.body!!.string()
        val uploadUrl = gson.fromJson(uploadResponseString, UploadReply::class.java).url
        return "${uploadUrl}.zip"
    }
}

internal fun releaseId(
    repoName: String,
    artifactReleaseTag: String,
    token: String,
    okHttpClient: OkHttpClient
): Int? {
    val request: Request =
        Request.Builder().url("https://api.github.com/repos/${repoName}/releases/tags/${artifactReleaseTag}").get()
            .addHeader("Accept", "application/vnd.github+json").addHeader("Authorization", "Bearer $token").build()

    val responseString = okHttpClient.newCall(request).execute().body!!.string()
    return if (!responseString.contains("Not Found")) {
        Gson().fromJson(responseString, IdReply::class.java).id
    } else {
        null
    }
}


class GithubReleaseException(message: String, cause: Throwable? = null) : Exception(message, cause)

data class IdReply(var id: Int)

data class UploadReply(var url: String)

data class CreateReleaseBody(val tag_name: String)


private fun artifactName(project: Project, versionString: String): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "$frameworkName-${versionString}-${(System.currentTimeMillis()/1000)}.xcframework.zip"
}