package co.touchlab.faktory.internal

import co.touchlab.faktory.githubEnterpriseHost
import co.touchlab.faktory.githubEnterpriseRepoOwner
import co.touchlab.faktory.githubPublishToken
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.Project
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
}