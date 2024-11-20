/*
 * Copyright (c) 2024 Touchlab.
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

import co.touchlab.faktory.dependencymanager.DependencyManager
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import java.io.File

abstract class ArtifactManager {
    /**
     * Do configuration specific to this `ArtifactManager`.
     */
    open fun configure(
        project: Project,
        version: String,
        uploadTask: TaskProvider<Task>,
        kmmPublishTask: TaskProvider<Task>
    ) {
    }

    /**
     * Send the thing, and return a link to the thing...
     */
    abstract fun Task.deployArtifact(zipFilePath: File, version: String): String

    internal fun deployArtifact(task: Task, zipFilePath: File, version: String): String{
        return task.deployArtifact(zipFilePath, version)
    }

    /**
     * Run after file written. This is essentially for GitHub releases.
     */
    open fun Task.finishArtifact(version: String, dependencyManagers: List<DependencyManager>) {}

    internal fun finishArtifact(task: Task, version: String, dependencyManagers: List<DependencyManager>) {
        task.finishArtifact(version, dependencyManagers)
    }
}