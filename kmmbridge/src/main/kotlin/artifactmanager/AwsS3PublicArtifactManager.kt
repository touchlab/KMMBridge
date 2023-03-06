/*
 * Copyright (c) 2023 Touchlab.
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

package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.internal.obscureFileName
import org.gradle.api.Project
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
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
) : ArtifactManager {

    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val fileName = obscureFileName(project, version)
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
