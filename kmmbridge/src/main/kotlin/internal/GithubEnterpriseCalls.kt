package co.touchlab.faktory.internal

import co.touchlab.faktory.artifactmanager.GithubReleaseException
import co.touchlab.faktory.githubEnterpriseHost
import co.touchlab.faktory.githubEnterpriseRepoOwner
import co.touchlab.faktory.githubPublishToken
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.Project
import java.io.File
import java.net.URLEncoder
import java.time.Duration

object GithubEnterpriseCalls {

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(Duration.ofMinutes(5))
        .connectTimeout(Duration.ofMinutes(2))
        .writeTimeout(Duration.ofMinutes(5))
        .readTimeout(Duration.ofMinutes(2))
        .build()


    fun createRelease(project: Project, repo: String, tag: String, commitId: String?): Int {
        val gson = Gson()
        val token = project.githubPublishToken
        val host = project.githubEnterpriseHost
        val owner = project.githubEnterpriseRepoOwner

        val createReleaseBody = commitId?.let {
            CreateReleaseWithCommitBody(tag, it)
        } ?: CreateReleaseBody(tag)

        val createRequest = Request.Builder()
            .url("https://${host}/api/v3/repos/${owner}/${repo}/releases")
            .post(
                gson.toJson(createReleaseBody).toRequestBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return gson.fromJson(
            okHttpClient.newCall(createRequest).execute().body!!.string(),
            IdReply::class.java
        ).id
    }

    fun uploadZipFile(
        project: Project,
        zipFilePath: File,
        repo: String,
        releaseId: Int,
        fileName: String
    ): String {
        val gson = Gson()
        val token = project.githubPublishToken
        val host = project.githubEnterpriseHost
        val owner = project.githubEnterpriseRepoOwner

        val body = zipFilePath.asRequestBody("application/zip".toMediaTypeOrNull())

        val uploadRequest = Request.Builder()
            .url(
                "https://${host}/api/v3/repos/${owner}/${repo}/releases/${releaseId}/assets?name=${
                    URLEncoder.encode(
                        fileName, "UTF-8"
                    )
                }"
            ).post(body).addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/zip").build()

        val response = okHttpClient.newCall(uploadRequest).execute()
        if (response.code != 201) {
            throw GithubReleaseException("Upload call failed ${response.code}, ${response.message}")
        }
        val uploadResponseString = response.body!!.string()
        return gson.fromJson(uploadResponseString, UploadReply::class.java).url
    }

    fun findReleaseId(
        project: Project,
        repoName: String,
        artifactReleaseTag: String
    ): Int? {
        val token = project.githubPublishToken
        val host = project.githubEnterpriseHost
        val owner = project.githubEnterpriseRepoOwner

        val request: Request =
            Request.Builder()
                .url("https://${host}/api/v3/repos/${owner}/${repoName}/releases/tags/${artifactReleaseTag}")
                .get()
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", "Bearer $token").build()

        val responseString = okHttpClient.newCall(request).execute().body!!.string()
        return if (!responseString.contains("Not Found")) {
            Gson().fromJson(responseString, IdReply::class.java).id
        } else {
            null
        }
    }
}