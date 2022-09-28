package co.touchlab.faktory

import org.gradle.api.Project
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.HeadObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File

class AwsS3PublicArtifactManager(
    private val s3Region: String,
    private val s3Bucket: String,
    private val s3AccessKeyId: String,
    private val s3SecretAccessKey: String,
    private val makeArtifactsPublic: Boolean,
    private val altBaseUrl: String?,
    private val extension: KmmBridgeExtension,
) : ArtifactManager {

    override fun deployArtifact(project: Project, zipFilePath: File, remoteFileId: String): String {
        uploadArtifact(project, zipFilePath, remoteFileId)
        return deployUrl(project, remoteFileId)
    }

    /**
     * Compute the fully qualified URL for the artifact we just uploaded
     *
     * @see uploadArtifact
     */
    private fun deployUrl(project: Project, remoteFileId: String): String {
        val baseUrl = altBaseUrl ?: "https://${s3Bucket}.s3.${s3Region}.amazonaws.com"
        return "${baseUrl}/${artifactName(project, remoteFileId)}"
    }

    /**
     * If the artifact doesn't already exist in remote storage, upload it.  Note: if there
     * is a problem determining if it exists it's assumed not to be there and will be
     * uploaded.
     */
    private fun uploadArtifact(project: Project, zipFilePath: File, remoteFileId: String) {
        val s3Client = S3Client.builder()
            .region(Region.of(s3Region))
            .credentialsProvider {
                AwsBasicCredentials.create(
                    s3AccessKeyId,
                    s3SecretAccessKey
                )
            }
            .build()

        s3Client.use { s3Client ->
            val objectKeyName = artifactName(project, remoteFileId)

            val headObjectRequest = HeadObjectRequest.builder()
                .bucket(s3Bucket)
                .key(objectKeyName)
                .build()

            val exists = try {
                s3Client.headObject(headObjectRequest).sdkHttpResponse().isSuccessful
            } catch (e: Exception) {
                false
            }

            if (!exists) {
                val builder = PutObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(objectKeyName)

                if (makeArtifactsPublic)
                    builder.acl("public-read")

                val putObjectRequest = builder.build()

                val requestBody = RequestBody.fromFile(zipFilePath)
                s3Client.putObject(putObjectRequest, requestBody)
            }
        }
    }

    private fun artifactName(project: Project, remoteFileId: String): String {
        val frameworkName = extension.frameworkName.get()
        val buildTypeString = extension.buildType.get().getName()
        return "$frameworkName-$buildTypeString-$remoteFileId.xcframework.${project.kmmBridgeExtension.version}.zip"
    }
}
