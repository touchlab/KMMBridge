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

import co.touchlab.faktory.internal.findNextVersion
import co.touchlab.faktory.internal.procRun
import co.touchlab.faktory.internal.procRunWarnLog
import org.gradle.api.Project

abstract class GitTagBasedVersionManager: VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String {
        val tagSet = mutableSetOf<String>()
        // Need to make sure we have all the tags. This may need to be configurable in the future for
        // more complex git setups. If call fails, we'll get a warning but keep going.
        project.procRunWarnLog("git", "pull", "--tags")
        procRun("git", "tag") { line, _ ->
            tagSet.add(line)
        }
        return findNextVersion(versionPrefix) {
            tagSet.contains(it)
        }
    }
}