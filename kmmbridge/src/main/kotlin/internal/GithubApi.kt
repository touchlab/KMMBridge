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

import org.gradle.api.Project
import java.io.File

interface GithubApi {
    fun findReleaseId(project: Project, repoName: String, artifactReleaseTag: String): Int?

    fun createRelease(project: Project, repo: String, tag: String, commitId: String?): Int

    fun uploadZipFile(project: Project, zipFilePath: File, repo: String, releaseId: Int, fileName: String): String
}