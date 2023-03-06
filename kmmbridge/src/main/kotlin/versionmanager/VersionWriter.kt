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

/**
 * Interacts with underlying version management. The only active implementation is git-based, so
 * the API very much leans towards what that needs. Custom implementations can be created to manage
 * git operations outside of the plugin, but using git tags can get tricky, so pay attention to all the steps involved.
 */
interface VersionWriter {
    /**
     * Do any prep work. Specifically for running `git tags` if needed.
     */
    fun initVersions(project: Project)

    /**
     * Scan sequence of versions. Used for running through git tags.
     */
    fun scanVersions(project: Project, block:(Sequence<String>)->Unit)

    /**
     * Apply marker version. Writes and commits a tag.i
     */
    fun writeMarkerVersion(project: Project, version: String)

    /**
     * After successful release, remove all temp marker versions.
     */
    fun cleanMarkerVersions(project: Project, filter:(String)->Boolean)

    /**
     * If running a git-required version manager, write final tag, or if using Github releases, call the Github API.
     */
    fun writeFinalVersion(project: Project, version: String)

    /**
     * Run arbitrary git statement. Currently used for SPM to write the Package.swift file.
     */
    fun runGitStatement(project: Project, vararg params:String)
}

/**
 * Implementation of VersionWrite that safely does nothing.
 */
object NoOpVersionWriter: VersionWriter {
    override fun initVersions(project: Project) {

    }

    override fun scanVersions(project: Project, block: (Sequence<String>) -> Unit) {

    }

    override fun writeMarkerVersion(project: Project, version: String) {

    }

    override fun cleanMarkerVersions(project: Project, filter: (String) -> Boolean) {

    }

    override fun writeFinalVersion(project: Project, version: String) {

    }

    override fun runGitStatement(project: Project, vararg params: String) {

    }
}