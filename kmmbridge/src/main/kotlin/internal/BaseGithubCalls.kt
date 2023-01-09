package co.touchlab.faktory.internal

import artifactmanager.GithubReleaseException
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

abstract class BaseGithubCalls : GithubApi {

    /**
     * releasesSuffix - suffix for the url following the .../releases/
     */
    protected abstract fun createUrl(project: Project, repoName: String, releasesSuffix: String): String

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(Duration.ofMinutes(5))
        .connectTimeout(Duration.ofMinutes(2))
        .writeTimeout(Duration.ofMinutes(5))
        .readTimeout(Duration.ofMinutes(2))
        .build()

    override fun createRelease(project: Project, repo: String, tag: String, commitId: String?): Int {
        val gson = Gson()
        val token = project.githubPublishToken

        val createReleaseBody = createReleaseBody(commitId, tag)

        val createRequest = Request.Builder()
            .url(createUrl(project = project, repoName = repo, releasesSuffix = ""))
            .post(gson.toJson(createReleaseBody).toRequestBody("application/json".toMediaTypeOrNull()))
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return gson.fromJson(
            okHttpClient.newCall(createRequest).execute().body!!.string(),
            IdReply::class.java
        ).id
    }

    private fun createReleaseBody(commitId: String?, tag: String): Any = commitId?.let {
        CreateReleaseWithCommitBody(tag, commitId)
    } ?: CreateReleaseBody(tag)

    override fun uploadZipFile(
        project: Project,
        zipFilePath: File,
        repo: String,
        releaseId: Int,
        fileName: String
    ): String {
        val gson = Gson()
        val token = project.githubPublishToken
        val body: RequestBody = zipFilePath.asRequestBody("application/zip".toMediaTypeOrNull())

        val uploadRequest = Request.Builder()
            .url(
                createUrl(
                    project = project,
                    repoName = repo,
                    releasesSuffix = "${releaseId}/assets?name=${
                        URLEncoder.encode(
                            fileName, "UTF-8"
                        )
                    }"
                )
            )
            .post(body)
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/zip").build()

        val response = okHttpClient.newCall(uploadRequest).execute()
        if (response.code != 201) {
            throw GithubReleaseException("Upload call failed ${response.code}, ${response.message}")
        }
        val uploadResponseString = response.body!!.string()
        return gson.fromJson(uploadResponseString, UploadReply::class.java).url
    }

    override fun findReleaseId(
        project: Project,
        repoName: String,
        artifactReleaseTag: String
    ): Int? {
        val token = project.githubPublishToken
        val request: Request =
            Request.Builder()
                .url(createUrl(project = project, repoName = repoName, releasesSuffix = "tags/${artifactReleaseTag}"))
                .get()
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", "Bearer $token")
                .build()

        val responseString = okHttpClient.newCall(request).execute().body!!.string()
        return if (!responseString.contains("Not Found")) {
            Gson().fromJson(responseString, IdReply::class.java).id
        } else {
            null
        }
    }
}