package co.touchlab.kmmbridge.test

import co.touchlab.kmmbridge.artifactmanager.ArtifactManager
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.gradle.api.GradleException
import org.gradle.api.Task
import java.io.File
import java.time.Duration

/**
 * Simple artifact manager that posts files to our server (Touchlab). This isn't designed for general usage. Just for
 * our tests. If there is more general demand for this functionality, reach out and we'll discuss ways of making it work.
 */
internal class TestUploadArtifactManager(private val server: String, private val code: String) : ArtifactManager {
    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String {
        val body: RequestBody = zipFilePath.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val uploadRequest = Request.Builder().url(
            "https://$server/infoadmin/storeTestZip"
        ).post(body).addHeader("code", code)
            .addHeader("Content-Type", "application/octet-stream").build()

        val okHttpClient =
            OkHttpClient.Builder().callTimeout(Duration.ofMinutes(5)).connectTimeout(Duration.ofMinutes(2))
                .writeTimeout(Duration.ofMinutes(5)).readTimeout(Duration.ofMinutes(2)).build()

        val response = okHttpClient.newCall(uploadRequest).execute()
        if (response.code >= 400) {
            throw GradleException("Test zip upload failed. Code: ${response.code}, message: ${response.message}")
        }

        val uploadResponseString = response.body!!.string()
        val url = Gson().fromJson(uploadResponseString, UploadReply::class.java).url

        return "https://$server$url"
    }

    data class UploadReply(var url: String)
}