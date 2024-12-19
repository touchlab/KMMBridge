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

package co.touchlab.kmmbridge.github.internal

import co.touchlab.kmmbridge.findStringProperty
import org.gradle.api.Project

internal val Project.githubPublishTokenOrNull: String?
    get() = project.property("GITHUB_PUBLISH_TOKEN") as String?

internal val Project.githubPublishUser: String?
    get() = project.findStringProperty("GITHUB_PUBLISH_USER")

internal val Project.githubRepoOrNull: String?
    get() {
        val repo = project.findStringProperty("GITHUB_REPO") ?: return null
        val repoWithoutGitSuffix = repo.removeSuffix(".git")
        val regex = Regex("((.*)[/:])?(?<owner>[^:/]+)/(?<repo>[^/]+)")
        val matchResult = regex.matchEntire(repoWithoutGitSuffix)
        if (matchResult != null) {
            return (matchResult.groups["owner"]!!.value + "/" + matchResult.groups["repo"]!!.value)
        } else {
            throw IllegalArgumentException("Incorrect Github repository path, should be \"Owner/Repo\"")
        }
    }

internal val Project.githubPublishToken
    get() = (project.property("GITHUB_PUBLISH_TOKEN")
        ?: throw IllegalArgumentException("KMMBridge Github operations need property GITHUB_PUBLISH_TOKEN")) as String

internal val Project.githubArtifactReleaseId
        get() = project.findStringProperty("GITHUB_ARTIFACT_RELEASE_ID")

internal val Project.githubArtifactIdentifierName
    get() = project.findStringProperty("GITHUB_ARTIFACT_IDENTIFIER_NAME")

internal val Project.githubRepo: String
    get() = githubRepoOrNull
        ?: throw IllegalArgumentException("KMMBridge Github operations need a repo param or property GITHUB_REPO")
