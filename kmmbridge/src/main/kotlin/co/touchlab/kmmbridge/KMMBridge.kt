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
import co.touchlab.kmmbridge.dependencymanager.SpmDependencyManager
import co.touchlab.kmmbridge.internal.enablePublishing
import co.touchlab.kmmbridge.internal.findXCFrameworkAssembleTask
import co.touchlab.kmmbridge.internal.kotlin
import co.touchlab.kmmbridge.internal.layoutBuildDir
import co.touchlab.kmmbridge.internal.spmBuildTargets
import co.touchlab.kmmbridge.internal.urlFile
import co.touchlab.kmmbridge.internal.zipFilePath
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkConfig
import java.io.File
import kotlin.collections.filter
import kotlin.collections.flatMap
import kotlin.collections.forEach

@Suppress("unused")
open class KMMBridgePlugin : BaseKMMBridgePlugin() {
}

abstract class BaseKMMBridgePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        val extension = extensions.create<KmmBridgeExtension>(EXTENSION_NAME)

        extension.dependencyManagers.convention(emptyList())

        val defaultNativeBuildType = if (project.findStringProperty("NATIVE_BUILD_TYPE") == "DEBUG") {
            NativeBuildType.DEBUG
        } else {
            NativeBuildType.RELEASE
        }

        extension.buildType.convention(defaultNativeBuildType)

        afterEvaluate {
            val kmmBridgeExtension = extensions.getByType<KmmBridgeExtension>()

            configureXcFramework(kmmBridgeExtension)
            configureLocalDev(kmmBridgeExtension)
            if (enablePublishing) {
                configureArtifactManagerAndDeploy(kmmBridgeExtension)
            }
        }
    }

    // Collect all declared frameworks in project and combine into xcframework
    private fun Project.configureXcFramework(kmmBridgeExtension: KmmBridgeExtension) {
        var xcFrameworkConfig: XCFrameworkConfig? = null

        val spmBuildTargets: Set<String> =
            project.spmBuildTargets?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }?.toSet() ?: emptySet()

        kotlin.targets
            .withType<KotlinNativeTarget>()
            .filter { it.konanTarget.family.isAppleFamily }
            .flatMap { it.binaries.filterIsInstance<Framework>() }
            .forEach { framework ->
                val theName = framework.baseName
                val currentName = kmmBridgeExtension.frameworkName.orNull
                if (currentName == null) {
                    kmmBridgeExtension.frameworkName.set(theName)
                } else {
                    if (currentName != theName) {
                        throw IllegalStateException("Only one framework name currently allowed. Found $currentName and $theName")
                    }
                }
                val shouldAddTarget =
                    spmBuildTargets.isEmpty() || spmBuildTargets.contains(framework.target.konanTarget.name)
                if (shouldAddTarget) {
                    if (xcFrameworkConfig == null) {
                        xcFrameworkConfig = XCFramework(theName)
                    }
                    xcFrameworkConfig!!.add(framework)
                }
            }
    }

    private fun Project.configureLocalDev(kmmBridgeExtension: KmmBridgeExtension) {
        (kmmBridgeExtension.dependencyManagers.get()
            .find { it is SpmDependencyManager } as? SpmDependencyManager)?.configureLocalDev(
            this
        )
    }

    private fun Project.configureArtifactManagerAndDeploy(kmmBridgeExtension: KmmBridgeExtension) {
        // Early-out with a warning if user hasn't added required config yet, to ensure project still syncs
        val artifactManager = kmmBridgeExtension.artifactManager.orNull ?: run {
            project.logger.warn("You must apply an artifact manager! Call `artifactManager.set(...)` or a configuration function like `mavenPublishArtifacts()` in your `kmmbridge` block.")
            return
        }

        val (zipTask, zipFile) = configureZipTask(kmmBridgeExtension, project.layoutBuildDir)

        // Zip task depends on the XCFramework assemble task
        zipTask.configure {
            dependsOn(findXCFrameworkAssembleTask())
        }

        // Upload task depends on the zip task
        val uploadTask = configureUploadTask(zipTask, zipFile, artifactManager)

        val dependencyManagers = kmmBridgeExtension.dependencyManagers.get()

        // Publish task depends on the upload task
        val publishRemoteTask = tasks.register("kmmBridgePublish") {
            description = "Publishes your framework. Uses your KMMBridge block configured in the build gradle to determine details."
            group = TASK_GROUP_NAME
            dependsOn(uploadTask)
        }

        // MavenPublishArtifactManager is somewhat complex because we have to hook into maven publishing
        // If you are exploring the task dependencies, be aware of that code
        artifactManager.configure(this, version.toString(), uploadTask, publishRemoteTask)

        for (dependencyManager in dependencyManagers) {
            dependencyManager.configure(providers, this, version.toString(), uploadTask, publishRemoteTask)
        }
    }

    private fun Project.configureUploadTask(
        zipTask: TaskProvider<Zip>,
        zipFile: File,
        artifactManager: ArtifactManager
    ) = tasks.register("uploadXCFramework") {
        group = TASK_GROUP_NAME

        dependsOn(zipTask)
        inputs.file(zipFile)
        outputs.files(urlFile)
        outputs.upToDateWhen { false } // We want to always upload when this task is called
        val versionLocal = version
        val urlFileLocal = urlFile

        @Suppress("ObjectLiteralToLambda")
        doLast(object : Action<Task> {
            override fun execute(t: Task) {
                logger.info("Uploading XCFramework version $versionLocal")
                val deployUrl = artifactManager.deployArtifact(this@register, zipFile, versionLocal.toString())
                urlFileLocal.writeText(deployUrl)
            }
        })
    }

    private fun Project.configureZipTask(
        kmmBridgeExtension: KmmBridgeExtension,
        buildDir: File
    ): Pair<TaskProvider<Zip>, File> {
        val zipFile = zipFilePath()
        val zipTask = tasks.register<Zip>("zipXCFramework") {
            group = TASK_GROUP_NAME
            from("$buildDir/XCFrameworks/${kmmBridgeExtension.buildType.get().getName()}")
            destinationDirectory.set(zipFile.parentFile)
            archiveFileName.set(zipFile.name)
        }

        return Pair(zipTask, zipFile)
    }
}
