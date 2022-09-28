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

    override fun deployArtifact(project: Project, zipFilePath: File, remoteFileId: String): String {
        uploadArtifact(project, zipFilePath, remoteFileId)
        return deployUrl(project, remoteFileId)
    }

    private fun deployUrl(project: Project, remoteFileId: String): String {
        val faktoryKey = project.faktoryReadKey ?: error("No Faktory key provided!")
        return faktoryReadUrl(project, remoteFileId, faktoryKey)
    }

    private fun uploadArtifact(project: Project, zipFilePath: File, remoteFileId: String) {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(Duration.ofMinutes(5))
            .connectTimeout(Duration.ofMinutes(2))
            .writeTimeout(Duration.ofMinutes(5))
            .readTimeout(Duration.ofMinutes(2))
            .build()

        val faktoryKey = project.faktorySecretKey ?: error("No Faktory secret key provided!")

        val request: Request = Request.Builder()
            .url(faktoryPutUrl(project, remoteFileId, faktoryKey))
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

    data class S3PostUrlResponse(val url:String)

    private fun faktoryPutUrl(project: Project, remoteFileId: String, faktorySecretKey: String) =
        "$FAKTORY_SERVER/fk/pub/${artifactName(project, remoteFileId)}?faktoryKey=$faktorySecretKey"

    private fun faktoryReadUrl(project: Project, remoteFileId: String, faktoryReadKey: String) =
        "$FAKTORY_SERVER/fk/pubread/${artifactName(project, remoteFileId)}?faktoryKey=$faktoryReadKey"

    private fun artifactName(project: Project, checksum: String): String {
        val frameworkName = project.kmmBridgeExtension.frameworkName.get()
        val buildTypeString = project.kmmBridgeExtension.buildType.get().getName()
        return "$frameworkName-$buildTypeString-$checksum.xcframework.${project.kmmBridgeExtension.version}.zip"
    }
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
private fun Project.findStringProperty(name: String): String? {
    rootProject.extensions.getByType(ExtraPropertiesExtension::class.java).run {
        if (has(name))
            return get(name).toString()
    }
    return null
}
