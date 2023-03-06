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

import co.touchlab.faktory.TEMP_PUBLISH_TAG_PREFIX
import co.touchlab.faktory.internal.ProcOutputException
import co.touchlab.faktory.internal.maxVersion
import co.touchlab.faktory.internal.prepVersionString
import co.touchlab.faktory.internal.procRunFailThrow
import co.touchlab.faktory.internal.procRunSequence
import org.gradle.api.Project

abstract class GitTagBasedVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String, versionWriter: VersionWriter): String {
        val versionPrefixTrimmed = prepVersionString(versionPrefix)

        versionWriter.initVersions(project)

        var maxCount = 0
        versionWriter.scanVersions(project) { sequence ->
            maxCount = maxVersion(versionPrefixTrimmed, sequence)
        }
        project.logger.info("KMMBridge: Max tag version $versionPrefixTrimmed$maxCount")

        // If we had a partial publish, make sure the next version we publish is greater
        var maxPartial = 0
        versionWriter.scanVersions(project) { sequence ->
            val partialVersionSequence = sequence
                .filter { it.startsWith(TEMP_PUBLISH_TAG_PREFIX) }
                .map { it.removePrefix(TEMP_PUBLISH_TAG_PREFIX) }
            maxPartial = maxVersion(versionPrefixTrimmed, partialVersionSequence)
        }
        project.logger.info("KMMBridge: Max partial tag version $versionPrefixTrimmed$maxPartial")

        val nextVersion = "${versionPrefixTrimmed}${maxOf(maxCount, maxPartial) + 1}"
        project.logger.info("KMMBridge: Next version is $nextVersion")
        return nextVersion
    }

    override val needsGitTags: Boolean = true
}
