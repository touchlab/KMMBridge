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

package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.githubRepo
import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.kmmBridgeExtension
import org.gradle.api.Project
import java.io.File
import java.net.URLEncoder

class GithubReleaseArtifactManager(
    private val artifactReleaseArg: String?
) : ArtifactManager {
    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val deployUrl = deployUrl(project, version)
        val uploadUrl = GithubCalls.uploadZipFile(project, zipFilePath, deployUrl.url)
        return "${uploadUrl}.zip"
    }

    override fun deployUrl(project: Project, version: String): ArtifactManager.DeployUrl {
        val repoName: String = project.githubRepo
        val artifactReleaseTag = artifactReleaseArg ?: "kmm-artifacts-${project.kmmBridgeExtension.versionPrefix.get()}"
        val idReply: Int = GithubCalls.findReleaseId(project, repoName, artifactReleaseTag) ?: GithubCalls.createRelease(
            project,
            repoName,
            artifactReleaseTag,
            null
        )
        val fileName = artifactName(project, version)

        return ArtifactManager.DeployUrl(fileName, createUrl(repoName, idReply, fileName))
    }

    private fun createUrl(repo: String, releaseId: Int, fileName: String): String {
        return "https://uploads.github.com/repos/${repo}/releases/${releaseId}/assets?name=${
            URLEncoder.encode(
                fileName, "UTF-8"
            )
        }"
    }
}

class GithubReleaseException(message: String, cause: Throwable? = null) : Exception(message, cause)

private fun artifactName(project: Project, versionString: String): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "$frameworkName-${versionString}-${(System.currentTimeMillis()/1000)}.xcframework.zip"
}
