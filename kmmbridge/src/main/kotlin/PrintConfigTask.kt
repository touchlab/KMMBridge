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
package co.touchlab.faktory

import co.touchlab.faktory.artifactmanager.*
import co.touchlab.faktory.dependencymanager.CocoapodsDependencyManager
import co.touchlab.faktory.dependencymanager.SpmDependencyManager
import co.touchlab.faktory.versionmanager.*
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class PrintConfigTask: DefaultTask() {

    @get:Input
    abstract val extension: Property<KmmBridgeExtension>

    @TaskAction
    fun printConfig() {
        println("________________________________")
        println("KMMBridge configuration:")
        val extension = this.extension.get()
        val versionManager = checkNotNull(extension.versionManager.orNull) {
            "You must apply an version manager! Call `versionManager.set(...)` or a configuration function like `githubReleaseVersions()` in your `kmmbridge` block."
        }

        val versionManagerName = when (versionManager) {
            is GitTagVersionManager -> "Git Tag"
            is GithubEnterpriseReleaseVersionManager -> "Github Enterprise Release"
            is GithubReleaseVersionManager -> "Github Release"
            is ManualVersionManager -> "Manual"
            is TimestampVersionManager -> "Timestamp"
            else -> "Unknown"
        }
        println("   Version manager: $versionManagerName")

        print("   Artifact manager: ")
        when (val artifactManager = extension.artifactManager.get()) {
            is AwsS3PublicArtifactManager -> println("Aws 3 Public")
            is FaktoryServerArtifactManager -> {
                println("Faktory Server")
                checkNotNull(artifactManager.faktorySecretKey) { "No Faktory secret key provided!" }
            }
            is GithubEnterpriseReleaseArtifactManager -> println("Github Enterprise Release")
            is GithubReleaseArtifactManager -> println("Github Release")
            is MavenPublishArtifactManager -> println("Maven Publish")
            else -> println("Unknown")
        }

        extension.dependencyManagers.get().forEach { dependencyManager ->
            print("   Dependency manager: ")
            when (dependencyManager) {
                is CocoapodsDependencyManager -> println("Cocoapods")
                is SpmDependencyManager -> println("SPM")
            }
        }

        val version = try {
            versionManager.getVersion(project, extension.versionPrefix.get())
        } catch (e: VersionException) {
            if (e.localDevOk) {
                project.logger.info("(KMMBridge) ${e.message}")
            } else {
                project.logger.warn("(KMMBridge) ${e.message}")
            }
            return
        }
        val deployUrl = extension.artifactManager.get().deployUrl(project, version)
        println("   Deploy URL: ${deployUrl.url}")
        println("   Version: $version")
        println("________________________________")
    }
}