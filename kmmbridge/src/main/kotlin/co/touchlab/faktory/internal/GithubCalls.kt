/*
 * Copyright (c) 2022 Touchlab.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package co.touchlab.faktory.internal

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

object GithubCalls {
    private val okHttpClient =
        OkHttpClient.Builder().callTimeout(Duration.ofMinutes(5)).connectTimeout(Duration.ofMinutes(2))
            .writeTimeout(Duration.ofMinutes(5)).readTimeout(Duration.ofMinutes(2)).build()

    fun createRelease(project: Project, repo: String, tag: String, commitId: String?): Int {
        val gson = Gson()
        val token = project.githubPublishToken
        val createReleaseBody = if (commitId == null) {
            CreateReleaseBody(tag)
        } else {
            CreateReleaseWithCommitBody(tag, commitId)
        }
        val createRequest = Request.Builder().url("https://api.github.com/repos/${repo}/releases")
            .post(gson.toJson(createReleaseBody).toRequestBody("application/json".toMediaTypeOrNull()))
            .addHeader("Accept", "application/vnd.github+json").addHeader("Authorization", "Bearer $token").build()

        return gson.fromJson(okHttpClient.newCall(createRequest).execute().body!!.string(), IdReply::class.java).id
    }

    fun uploadZipFile(project: Project, zipFilePath: File, repo:String, releaseId: Int, fileName: String):String{
        val gson = Gson()
        val token = project.githubPublishToken
        val body: RequestBody = zipFilePath.asRequestBody("application/zip".toMediaTypeOrNull())

        val uploadRequest = Request.Builder().url(
            "https://uploads.github.com/repos/${repo}/releases/${releaseId}/assets?name=${
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
        return gson.fromJson(uploadResponseString, UploadReply::class.java).url
    }

    fun findReleaseId(project: Project,
                      repoName: String,
                      artifactReleaseTag: String): Int?{
        val token = project.githubPublishToken
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
}

data class CreateReleaseWithCommitBody(val tag_name: String, val target_commitish: String)

data class CreateReleaseBody(val tag_name: String)

data class IdReply(var id: Int)

data class UploadReply(var url: String)

class GithubReleaseException(message: String, cause: Throwable? = null) : Exception(message, cause)