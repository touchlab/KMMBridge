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

package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.internal.ProcOutputException
import co.touchlab.faktory.internal.procRunFailLog
import co.touchlab.faktory.internal.procRunFailThrow
import co.touchlab.faktory.internal.procRunSequence
import org.gradle.api.Project

open class GitRemoteVersionWriter: VersionWriter {
    override fun initVersions(project: Project) {
        // Need to make sure we have all the tags. If no tags, we don't continue (but don't fail)
        // This will usually happen when doing local dev.
        try {
            project.procRunFailThrow("git", "pull", "--tags")
        } catch (e: ProcOutputException) {
            val localOk = e.output.any { it.contains("There is no tracking information for the current branch") }
            throw VersionException(
                localOk, if (localOk) {
                    "Version cannot be loaded. Publishing disabled (this is fine for local development)"
                } else {
                    "${e.message}\n${e.output.joinToString("\n")}"
                }
            )
        }
    }

    override fun scanVersions(project: Project, block: (Sequence<String>) -> Unit) {
        procRunSequence("git", "tag", block = block)
    }

    override fun writeMarkerVersion(project: Project, version: String) {
        project.procRunFailThrow("git", "tag", version)
        project.procRunFailThrow("git", "push", "origin", "tag", version)
    }

    override fun cleanMarkerVersions(project: Project, filter: (String) -> Boolean) {
        procRunSequence("git", "tag") { sequence ->
            val partialVersionSequence = sequence.filter(filter)
            partialVersionSequence.forEach { tag ->
                project.logger.warn("Deleting tag $tag")
                project.procRunFailThrow("git", "tag", "-d", tag)
                project.procRunFailThrow("git", "push", "origin", "-d", tag)
            }
        }
    }

    override fun writeFinalVersion(project: Project, version: String) {
        project.procRunFailLog("git", "tag", "-a", version, "-m", "KMM release version $version")
        project.procRunFailLog("git", "push", "--follow-tags")
    }

    override fun runGitStatement(project: Project, vararg params: String) {
        project.procRunFailLog("git", *params)
    }
}