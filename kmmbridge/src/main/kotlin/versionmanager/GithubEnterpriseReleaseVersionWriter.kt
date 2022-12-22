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

package versionmanager

import co.touchlab.faktory.githubRepo
import co.touchlab.faktory.internal.GithubEnterpriseCalls
import co.touchlab.faktory.internal.procRunFailLog
import co.touchlab.faktory.versionmanager.GitRemoteVersionWriter
import org.gradle.api.Project

object GithubEnterpriseReleaseVersionWriter : GitRemoteVersionWriter() {
    override fun writeFinalVersion(project: Project, version: String) {
        val commitId = project.procRunFailLog("git", "rev-parse", "HEAD").first()
        GithubEnterpriseCalls.createRelease(project, project.githubRepo, version, commitId)
    }
}