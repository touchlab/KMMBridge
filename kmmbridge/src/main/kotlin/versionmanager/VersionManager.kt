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

import org.gradle.api.Project

interface VersionManager {
    /**
     * Compute a final version to use for publication, based on the plugin versionPrefix
     */
    fun getVersion(project: Project, versionPrefix: String, versionWriter: VersionWriter): String

    /**
     * Create a string to mark a provisional release. This is in case the release fail mid-process.
     */
    fun createMarkerVersion(project: Project, versionString: String): String? = null

    /**
     * Going through the git tags, this filter tells you if an entry is a marker version for cleaning.
     */
    fun filterMarkerVersion(project: Project, versionString: String): (String) -> Boolean = { _ -> false }

    /**
     * Whether this version manager requires git tags to work. When true, kmmbridge will perform git operations internally.
     */
    val needsGitTags: Boolean
}

class VersionException(val localDevOk: Boolean, message: String?) : Exception(message)
