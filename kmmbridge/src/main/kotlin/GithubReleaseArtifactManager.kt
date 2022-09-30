package co.touchlab.faktory

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.Project
import java.io.File
import java.time.Duration
import java.util.UUID

class GithubReleaseArtifactManager(
    private val owner: String,
    private val repo: String,
    private val artifactRelease: String
) : ArtifactManager {
    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(Duration.ofMinutes(5))
        .connectTimeout(Duration.ofMinutes(2))
        .writeTimeout(Duration.ofMinutes(5))
        .readTimeout(Duration.ofMinutes(2))
        .build()

    override fun deployArtifact(project: Project, zipFilePath: File, remoteFileId: String): String {
        val gson = Gson()
        val token = (project.property("GITHUB_PUBLISH_TOKEN")?:throw IllegalArgumentException("GithubReleaseArtifactManager needs property GITHUB_PUBLISH_TOKEN")) as String
        val request: Request = Request.Builder()
            .url("https://api.github.com/repos/${owner}/${repo}/releases/tags/${artifactRelease}")
            .get()
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        val responseString = okHttpClient.newCall(request).execute().body!!.string()
        val releaseFound = !responseString.contains("Not Found")
        val idReply = if (!releaseFound) {
            val createReleaseBody = CreateReleaseBody(artifactRelease)
            val createRequest = Request.Builder()
                .url("https://api.github.com/repos/${owner}/${repo}/releases")
                .post(gson.toJson(createReleaseBody).toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", "Bearer $token")
                .build()

            gson.fromJson(okHttpClient.newCall(createRequest).execute().body!!.string(), IdReply::class.java)
        } else {
            gson.fromJson(responseString, IdReply::class.java)
        }

        val body: RequestBody = zipFilePath.asRequestBody("application/zip".toMediaTypeOrNull())

        val fileName = "${UUID.randomUUID().toString()}.zip"

        val uploadRequest = Request.Builder()
            .url("https://api.github.com/repos/${owner}/${repo}/releases/${idReply.id}/assets?name=${fileName}")
            .post(body)
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return gson.fromJson(okHttpClient.newCall(uploadRequest).execute().body!!.string(), UploadReply::class.java).url
    }

}

data class IdReply(var id:Int)

data class UploadReply(var url:String)

data class CreateReleaseBody(val tag_name: String)

/*
curl \
  -X POST \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer <YOUR-TOKEN>" \
  https://api.github.com/repos/OWNER/REPO/releases \
  -d '{

  "tag_name":"v1.0.0",
  "target_commitish":"master",
  "name":"v1.0.0",
  "body":"Description of the release",
  "draft":false,
  "prerelease":false,
  "generate_release_notes":false

  }'
 */