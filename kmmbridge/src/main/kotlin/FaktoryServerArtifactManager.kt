package co.touchlab.faktory

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import java.io.File
import java.io.IOException
import java.time.Duration

class FaktoryServerArtifactManager : ArtifactManager {

    override fun deployArtifact(project: Project, zipFilePath: File, fileName: String): String {
        uploadArtifact(project, zipFilePath, fileName)
        return deployUrl(project, fileName)
    }

    private fun deployUrl(project: Project, zipFileName: String): String {
        val faktoryKey = project.faktoryReadKey ?: error("No Faktory key provided!")
        return faktoryReadUrl(zipFileName, faktoryKey)
    }

    private fun uploadArtifact(project: Project, zipFilePath: File, fileName: String) {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(Duration.ofMinutes(5))
            .connectTimeout(Duration.ofMinutes(2))
            .writeTimeout(Duration.ofMinutes(5))
            .readTimeout(Duration.ofMinutes(2))
            .build()

        val faktoryKey = project.faktorySecretKey ?: error("No Faktory secret key provided!")

        val request: Request = Request.Builder()
            .url(faktoryPutUrl(fileName, faktoryKey))
            .get()
            .build()

        val presignedUrl = okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Upload url generation failed! ${response.code}")

            Gson().fromJson(response.body!!.string(), S3PostUrlResponse::class.java).url
        }

        val s3Request: Request = Request.Builder()
            .url(presignedUrl)
            .put(zipFilePath.asRequestBody())
            .build()

        okHttpClient.newCall(s3Request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Upload failed! ${response.code}")
        }
    }

    data class S3PostUrlResponse(val url: String)

    private fun faktoryPutUrl(zipFileName: String, faktorySecretKey: String) =
        "$FAKTORY_SERVER/fk/pub/$zipFileName?faktoryKey=$faktorySecretKey"

    private fun faktoryReadUrl(zipFileName: String, faktoryReadKey: String) =
        "$FAKTORY_SERVER/fk/pubread/$zipFileName?faktoryKey=$faktoryReadKey"
}

private val isDev = System.getenv("FAKTORY_SERVER_LOCALDEV")?.toBoolean() ?: false
private val FAKTORY_SERVER = if (isDev) {
    "http://localhost:5003"
} else {
    "https://api.touchlab.dev"
}

private val Project.faktoryReadKey: String?
    get() = project.kmmBridgeExtension.faktoryReadKey.orNull ?: findStringProperty("FAKTORY_READ_KEY")
private val Project.faktorySecretKey: String? get() = findStringProperty("FAKTORY_SECRET_KEY")
