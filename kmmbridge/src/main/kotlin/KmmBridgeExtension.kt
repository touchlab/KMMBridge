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

import co.touchlab.faktory.artifactmanager.ArtifactManager
import co.touchlab.faktory.artifactmanager.AwsS3PublicArtifactManager
import co.touchlab.faktory.artifactmanager.FaktoryServerArtifactManager
import co.touchlab.faktory.artifactmanager.GithubEnterpriseReleaseArtifactManager
import co.touchlab.faktory.artifactmanager.GithubReleaseArtifactManager
import co.touchlab.faktory.dependencymanager.CocoapodsDependencyManager
import co.touchlab.faktory.dependencymanager.DependencyManager
import co.touchlab.faktory.dependencymanager.SpecRepo
import co.touchlab.faktory.dependencymanager.SpmDependencyManager
import co.touchlab.faktory.versionmanager.GitTagVersionManager
import co.touchlab.faktory.versionmanager.GithubEnterpriseReleaseVersionManager
import co.touchlab.faktory.versionmanager.GithubReleaseVersionManager
import co.touchlab.faktory.versionmanager.ManualVersionManager
import co.touchlab.faktory.versionmanager.TimestampVersionManager
import co.touchlab.faktory.versionmanager.VersionManager
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

interface KmmBridgeExtension {
    /**
     * The name of the kotlin framework, which will be wrapped into a cocoapod. May be the same or different from podName.
     * This should be the same as
     */
    val frameworkName: Property<String>

    val dependencyManagers: ListProperty<DependencyManager>

    val artifactManager: Property<ArtifactManager>

    val buildType: Property<NativeBuildType>

    val versionManager: Property<VersionManager>

    val versionPrefix: Property<String>

    fun s3PublicArtifacts(
        region: String,
        bucket: String,
        accessKeyId: String,
        secretAccessKey: String,
        makeArtifactsPublic: Boolean = true,
        altBaseUrl: String? = null,
    ) {
        artifactManager.set(
            AwsS3PublicArtifactManager(
                region,
                bucket,
                accessKeyId,
                secretAccessKey,
                makeArtifactsPublic,
                altBaseUrl
            )
        )
    }

    fun githubReleaseArtifacts(
        artifactRelease: String? = null
    ) {
        artifactManager.set(GithubReleaseArtifactManager(artifactRelease))
    }

    fun githubEnterpriseReleaseArtifacts(
        artifactRelease: String? = null
    ) {
        artifactManager.set(GithubEnterpriseReleaseArtifactManager(artifactRelease))
    }

    fun Project.faktoryServerArtifacts(faktoryReadKey: String? = null) {
        artifactManager.set(FaktoryServerArtifactManager(faktoryReadKey, this))
    }

    fun timestampVersions() {
        versionManager.set(TimestampVersionManager)
    }

    fun gitTagVersions() {
        versionManager.set(GitTagVersionManager)
    }

    fun githubReleaseVersions() {
        versionManager.set(GithubReleaseVersionManager)
    }

    fun githubEnterpriseReleaseVersions() {
        versionManager.set(GithubEnterpriseReleaseVersionManager)
    }

    fun manualVersions() {
        versionManager.set(ManualVersionManager)
    }

    fun Project.spm(
        spmDirectory: String? = null,
        packageName: String = project.name,
    ) {
        val dependencyManager = SpmDependencyManager(spmDirectory, packageName)
        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }

    fun Project.cocoapods(specRepoUrl: String?) {
        kotlin.cocoapods // This will throw error if we didn't apply cocoapods plugin

        val specRepo = if (specRepoUrl == null) SpecRepo.Trunk else SpecRepo.Private(specRepoUrl)

        val dependencyManager = CocoapodsDependencyManager(specRepo)
        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }
}