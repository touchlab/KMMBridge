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

package co.touchlab.faktory.artifactmanager

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.component.SoftwareComponentFactory
import java.io.File

interface ArtifactManager {
    /**
     * Do configuration specific to this `ArtifactManager`.
     */
    fun configure(project: Project, resolveVersionTask: Task, uploadTask: Task, softwareComponentFactory: SoftwareComponentFactory) {}

    /**
     * Send the thing, and return a link to the thing...
     */
    fun deployArtifact(project: Project, zipFilePath: File, version: String): String
}