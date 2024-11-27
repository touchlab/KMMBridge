/*
 * Copyright (c) 2024 Touchlab.
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

package co.touchlab.kmmbridge.artifactmanager

import co.touchlab.kmmbridge.internal.kmmBridgeExtension
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.model.HeadObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.util.*

internal class AwsS3PublicArtifactManager(
    private val s3Region: String,
    private val s3Bucket: String,
    private val s3AccessKeyId: String,
    private val s3SecretAccessKey: String,
    private val makeArtifactsPublic: Boolean,
    private val altBaseUrl: String?,
) : ArtifactManager {

    lateinit var frameworkName: String

    override fun configure(
        project: Project,
        version: String,
        uploadTask: TaskProvider<Task>,
        kmmPublishTask: TaskProvider<Task>
    ) {
        frameworkName = project.kmmBridgeExtension.frameworkName.get()
    }

    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String {
        val fileName = obscureFileName(frameworkName, version)
        uploadArtifact(zipFilePath, fileName)
        return deployUrl(fileName)
    }

    /**
     * Compute the fully qualified URL for the artifact we just uploaded
     *
     * @see uploadArtifact
     */
    private fun deployUrl(zipFileName: String): String {
        val baseUrl = altBaseUrl ?: "https://${s3Bucket}.s3.${s3Region}.amazonaws.com"
        return "${baseUrl}/$zipFileName"
    }

    /**
     * If the artifact doesn't already exist in remote storage, upload it.  Note: if there
     * is a problem determining if it exists it's assumed not to be there and will be
     * uploaded.
     */
    @Suppress("NAME_SHADOWING")
    private fun uploadArtifact(zipFilePath: File, fileName: String) {
        val s3Client = S3Client.builder()
            .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .region(Region.of(s3Region))
            .credentialsProvider {
                AwsBasicCredentials.create(
                    s3AccessKeyId,
                    s3SecretAccessKey
                )
            }
            .build()

        s3Client.use { s3Client ->

            val headObjectRequest = HeadObjectRequest.builder()
                .bucket(s3Bucket)
                .key(fileName)
                .build()

            val exists = try {
                s3Client.headObject(headObjectRequest).sdkHttpResponse().isSuccessful
            } catch (e: Exception) {
                false
            }

            if (!exists) {
                val builder = PutObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(fileName)

                if (makeArtifactsPublic)
                    builder.acl("public-read")

                val putObjectRequest = builder.build()

                val requestBody = RequestBody.fromFile(zipFilePath)
                s3Client.putObject(putObjectRequest, requestBody)
            }
        }
    }
}

/**
 * Generate a file name that isn't guessable. Some artifact managers don't have auth guarding the urls.
 */
private fun obscureFileName(frameworkName: String, versionString: String): String {
    val randomId = UUID.randomUUID().toString()
    return "${frameworkName}-${versionString}-${randomId}.xcframework.zip"
}
