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

package co.touchlab.kmmbridge

import co.touchlab.kmmbridge.artifactmanager.ArtifactManager
import co.touchlab.kmmbridge.artifactmanager.AwsS3PublicArtifactManager
import co.touchlab.kmmbridge.artifactmanager.MavenPublishArtifactManager
import co.touchlab.kmmbridge.dependencymanager.CocoapodsDependencyManager
import co.touchlab.kmmbridge.dependencymanager.DependencyManager
import co.touchlab.kmmbridge.dependencymanager.SpecRepo
import co.touchlab.kmmbridge.dependencymanager.SpmDependencyManager
import co.touchlab.kmmbridge.dsl.TargetPlatformDsl
import co.touchlab.kmmbridge.internal.cocoapods
import co.touchlab.kmmbridge.internal.domain.SwiftToolVersion
import co.touchlab.kmmbridge.internal.kotlin
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

interface KmmBridgeExtension {
    /**
     * The name of the kotlin framework, which will be wrapped into a cocoapod. The name may be the same or different from podName.
     * This should be the same as
     */
    val frameworkName: Property<String>

    val dependencyManagers: ListProperty<DependencyManager>

    val artifactManager: Property<ArtifactManager>

    val buildType: Property<NativeBuildType>


    @Suppress("unused")
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

    /**
     * If using multiple repos, you can specify which one the `Package.swift` and/or podspec point to, bypassing
     * the name in here.
     */
    @Suppress("unused")
    fun Project.mavenPublishArtifacts(
        repository: String? = null,
        publication: String? = null,
        artifactSuffix: String? = null,
        isMavenCentral: Boolean = false
    ) {
        artifactManager.setAndFinalize(
            MavenPublishArtifactManager(
                publication,
                artifactSuffix,
                repository,
                isMavenCentral
            )
        )
    }

    /**
     * Enable Swift Package Manager publication
     *
     * @param spmDirectory Folder where the Package.swift file lives
     * @param useCustomPackageFile Allow to use custom Package.swift file
     * @param perModuleVariablesBlock Allow the same Package.swift file to host multiple kotlin frameworks
     * @param swiftToolVersion Specifies swift-tools-version in Package.swift. Default: [SwiftToolVersion.Default]
     */
    @Suppress("unused")
    fun Project.spm(
        spmDirectory: String? = null,
        useCustomPackageFile: Boolean = false,
        perModuleVariablesBlock: Boolean = false,
        swiftToolVersion: String = SwiftToolVersion.Default,
        targetPlatforms: TargetPlatformDsl.() -> Unit = { iOS { v("13") } },
    ) {
        val dependencyManager = SpmDependencyManager(
            spmDirectory,
            useCustomPackageFile,
            perModuleVariablesBlock,
            swiftToolVersion,
            targetPlatforms
        )
        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }

    /**
     * Enable CocoaPods publication
     *
     * @param specRepoUrl Url to repo that holds specs.
     * @param allowWarnings Allow publishing with warnings. Defaults to true.
     * @param verboseErrors Output extra error info. Generally used if publishing fails. Defaults to false.
     */
    @Suppress("unused")
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
    @Suppress("unused")
    fun Project.cocoapodsTrunk(
        allowWarnings: Boolean = true,
        verboseErrors: Boolean = false,
    ) {
        kotlin.cocoapods // This will throw error if we didn't apply cocoapods plugin

        val dependencyManager = CocoapodsDependencyManager({ SpecRepo.Trunk }, allowWarnings, verboseErrors)

        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }

    fun <T> Property<T>.setAndFinalize(value: T) {
        this.set(value)
        this.finalizeValue()
    }
}
