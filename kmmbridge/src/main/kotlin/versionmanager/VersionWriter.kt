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

import org.gradle.api.Project

interface VersionWriter {
    fun initVersions(project: Project)
    fun scanVersions(project: Project, block:(Sequence<String>)->Unit)
    fun markerVersion(project: Project, version: String)
    fun cleanMarkerVersions(project: Project, filter:(String)->Boolean)
    fun finalVersion(project: Project, version: String)
    fun runGitStatement(project: Project, vararg params:String)
}

object NoOpVersionWriter: VersionWriter {
    override fun initVersions(project: Project) {

    }

    override fun scanVersions(project: Project, block: (Sequence<String>) -> Unit) {

    }

    override fun markerVersion(project: Project, version: String) {

    }

    override fun cleanMarkerVersions(project: Project, filter: (String) -> Boolean) {

    }

    override fun finalVersion(project: Project, version: String) {

    }

    override fun runGitStatement(project: Project, vararg params: String) {

    }
}