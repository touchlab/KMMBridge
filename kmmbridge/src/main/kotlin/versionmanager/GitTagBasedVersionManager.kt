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
import co.touchlab.faktory.internal.prepVersionString
import co.touchlab.faktory.internal.procRunSequence
import co.touchlab.faktory.internal.procRunWarnLog
import org.gradle.api.Project

abstract class GitTagBasedVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String {
        val versionPrefixTrimmed = prepVersionString(versionPrefix)

        // Need to make sure we have all the tags. This may need to be configurable in the future for
        // more complex git setups. If call fails, we'll get a warning but keep going.
        project.procRunFailLog("git", "pull", "--tags")

        var maxCount = 0

        procRunSequence("git", "tag") { sequence ->
            maxCount = maxVersion(versionPrefixTrimmed, sequence)
        }

        return "${versionPrefixTrimmed}${maxCount + 1}"
    }
}