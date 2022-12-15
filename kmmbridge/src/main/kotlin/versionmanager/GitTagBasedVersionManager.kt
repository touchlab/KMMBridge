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

package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.internal.*
import org.gradle.api.Project

abstract class GitTagBasedVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String {
        val versionPrefixTrimmed = prepVersionString(versionPrefix)

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

        var maxCount = 0

        procRunSequence("git", "tag") { sequence ->
            maxCount = maxVersion(versionPrefixTrimmed, sequence)
        }

        return "${versionPrefixTrimmed}${maxCount + 1}"
    }
}