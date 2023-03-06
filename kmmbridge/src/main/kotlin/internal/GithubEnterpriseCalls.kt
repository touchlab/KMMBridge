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

package co.touchlab.faktory.internal

import co.touchlab.faktory.findStringProperty
import org.gradle.api.Project

object GithubEnterpriseCalls : BaseGithubCalls() {

    private val Project.githubEnterpriseHost
        get() = (project.findStringProperty("GITHUB_ENTERPRISE_HOST")
            ?: throw IllegalArgumentException("KMMBridge Github operations need property GITHUB_ENTERPRISE_HOST"))

    private val Project.githubEnterpriseRepoOwner
        get() = (project.findStringProperty("GITHUB_REPO_OWNER")
            ?: throw IllegalArgumentException("KMMBridge Github operations need property GITHUB_REPO_OWNER"))

    override fun createUrl(project: Project, repoName: String): String {
        val host = project.githubEnterpriseHost
        val owner = project.githubEnterpriseRepoOwner
        return "https://${host}/api/uploads/repos/${owner}/${repoName}/releases"
    }
}