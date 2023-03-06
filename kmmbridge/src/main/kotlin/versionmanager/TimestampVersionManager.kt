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

object TimestampVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String, versionWriter: VersionWriter): String =
        "${versionPrefix}.${System.currentTimeMillis()}"

    override val needsGitTags: Boolean = false
}
