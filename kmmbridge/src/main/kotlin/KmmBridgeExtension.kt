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

package co.touchlab.faktory

import co.touchlab.faktory.artifactmanager.ArtifactManager
import co.touchlab.faktory.artifactmanager.AwsS3PublicArtifactManager
import co.touchlab.faktory.artifactmanager.FaktoryServerArtifactManager
import co.touchlab.faktory.artifactmanager.MavenPublishArtifactManager
import co.touchlab.faktory.dependencymanager.CocoapodsDependencyManager
import co.touchlab.faktory.dependencymanager.DependencyManager
import co.touchlab.faktory.dependencymanager.SpecRepo
import co.touchlab.faktory.dependencymanager.SpmDependencyManager
import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.GithubEnterpriseCalls
import co.touchlab.faktory.versionmanager.*
import localdevmanager.LocalDevManager
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import co.touchlab.faktory.versionmanager.GithubReleaseVersionWriter

interface KmmBridgeExtension {
    /**
     * The name of the kotlin framework, which will be wrapped into a cocoapod. May be the same or different from podName.
     * This should be the same as
     */
    val frameworkName: Property<String>

    val dependencyManagers: ListProperty<DependencyManager>

    val artifactManager: Property<ArtifactManager>

    val localDevManager: Property<LocalDevManager>

    val buildType: Property<NativeBuildType>

    val versionManager: Property<VersionManager>

    val versionWriter: Property<VersionWriter>

    val versionPrefix: Property<String>

    fun Project.s3PublicArtifacts(
        region: String,
        bucket: String,
        accessKeyId: String,
        secretAccessKey: String,
        makeArtifactsPublic: Boolean = true,
        altBaseUrl: String? = null,
    ) {
        artifactManager.setAndFinalize(
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

    fun Project.faktoryServerArtifacts(faktoryReadKey: String? = null) {
        artifactManager.setAndFinalize(FaktoryServerArtifactManager(faktoryReadKey, this))
    }

    /**
     * If using multiple repos, you can specify which one the `Package.swift` and/or podspec point to by
     * passing the name in here.
     */
    fun Project.mavenPublishArtifacts(repository: String? = null, publication: String? = null) {
        artifactManager.setAndFinalize(MavenPublishArtifactManager(this, publication, repository))
    }

    fun timestampVersions() {
        versionManager.setAndFinalize(TimestampVersionManager)
    }

    private fun Property<VersionWriter>.setIfNull(versionWriter: VersionWriter){
        if(!isPresent){
            set(versionWriter)
        }
    }

    fun gitTagVersions() {
        versionManager.setAndFinalize(GitTagVersionManager)
        versionWriter.setIfNull(GitRemoteVersionWriter())
    }

    fun githubReleaseVersions() {
        versionManager.setAndFinalize(GitTagVersionManager)
        versionWriter.setIfNull(GithubReleaseVersionWriter(GithubCalls))
    }

    fun githubEnterpriseReleaseVersions() {
        versionManager.setAndFinalize(GitTagVersionManager)
        versionWriter.setIfNull(GithubReleaseVersionWriter(GithubEnterpriseCalls))
    }

    /**
     * This is advanced. You *really* need to know what you're doing.
     */
    fun noGitOperations(){
        versionWriter.set(NoOpVersionWriter)
    }

    fun manualVersions() {
        versionManager.setAndFinalize(ManualVersionManager)
    }

    fun Project.spm(
        spmDirectory: String? = null,
    ) {
        val dependencyManager = SpmDependencyManager(spmDirectory)
        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
        localDevManager.setAndFinalize(dependencyManager)
    }

    /**
     * Enable CocoaPods publication
     *
     * @param specRepoUrl Url to repo that holds specs.
     * @param allowWarnings Allow publishing with warnings. Defaults to true.
     * @param verboseErrors Output extra error info. Generally used if publishing fails. Defaults to false.
     */
    fun Project.cocoapods(
        specRepoUrl: String,
        allowWarnings: Boolean = true,
        verboseErrors: Boolean = false,
    ) {
        kotlin.cocoapods // This will throw error if we didn't apply cocoapods plugin

        val dependencyManager = CocoapodsDependencyManager({
            SpecRepo.Private(specRepoUrl)
        }, allowWarnings, verboseErrors)

        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }

    /**
     * Enable CocoaPods publication using the [Trunk](https://github.com/CocoaPods/Specs) as a spec repo
     *
     * @param allowWarnings Allow publishing with warnings. Defaults to true.
     * @param verboseErrors Output extra error info. Generally used if publishing fails. Defaults to false.
     */
    fun Project.cocoapodsTrunk(
        allowWarnings: Boolean = true,
        verboseErrors: Boolean = false,
    ) {
        kotlin.cocoapods // This will throw error if we didn't apply cocoapods plugin

        val dependencyManager = CocoapodsDependencyManager({ SpecRepo.Trunk }, allowWarnings, verboseErrors)

        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }

    private fun <T> Property<T>.setAndFinalize(value: T) {
        this.set(value)
        this.finalizeValue()
    }
}
