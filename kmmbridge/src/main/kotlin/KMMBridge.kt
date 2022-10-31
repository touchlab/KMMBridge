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

import org.gradle.api.*
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkConfig
import java.io.*
import java.net.URI
import java.util.*
import javax.inject.Inject

@Suppress("unused")
class KMMBridgePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        val extension = extensions.create<KmmBridgeExtension>(EXTENSION_NAME)

        extension.dependencyManagers.convention(emptyList())
        extension.buildType.convention(NativeBuildType.RELEASE)

        // Don't call `kotlin` directly as that'd create an order dependency on the Kotlin Multiplatform plugin
        val fallbackVersion = project.provider {
            kotlin.cocoapodsOrNull?.version ?: version.toString()
        }
        extension.versionPrefix.convention(fallbackVersion)

        afterEvaluate {
            if (!extension.artifactManager.isPresent) {
                error("You must apply an artifact manager! Call `artifactManager.set(...)` or a configuration function like `githubRelease()` in your `kmmbridge {}` block.")
            }

            configureXcFramework()
            configureArtifactManagerAndDeploy()
        }
    }

    private fun Project.configureZipTask(extension: KmmBridgeExtension): Pair<TaskProvider<Zip>, File> {
        val zipFile = zipFilePath()
        val zipTask = tasks.register<Zip>("zipXCFramework") {
            group = TASK_GROUP_NAME
            from("$buildDir/XCFrameworks/${extension.buildType.get().getName()}")
            destinationDirectory.set(zipFile.parentFile)
            archiveFileName.set(zipFile.name)
        }

        return Pair(zipTask, zipFile)
    }

    // Collect all declared frameworks in project and combine into xcframework
    private fun Project.configureXcFramework() {
        val extension = kmmBridgeExtension
        var xcFrameworkConfig: XCFrameworkConfig? = null

        val spmBuildTargets: Set<String> = project.spmBuildTargets?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }?.toSet() ?: emptySet()

        kotlin.targets
            .withType<KotlinNativeTarget>()
            .filter { it.konanTarget.family.isAppleFamily }
            .flatMap { it.binaries.filterIsInstance<Framework>() }
            .forEach { framework ->
                val theName = framework.baseName
                val currentName = extension.frameworkName.orNull
                if (currentName == null) {
                    extension.frameworkName.set(theName)
                } else {
                    if (currentName != theName) {
                        throw IllegalStateException("Only one framework name currently allowed. Found $currentName and $theName")
                    }
                }
                val shouldAddTarget = spmBuildTargets.isEmpty() || spmBuildTargets.contains(framework.target.konanTarget.name)
                if(shouldAddTarget) {
                    if (xcFrameworkConfig == null) {
                        xcFrameworkConfig = XCFramework(theName)
                    }
                    xcFrameworkConfig!!.add(framework)
                }
            }
    }

    private fun Project.configureArtifactManagerAndDeploy() {
        val extension = extensions.getByType<KmmBridgeExtension>()
        val (zipTask, zipFile)= configureZipTask(extension)
        val artifactManager = extension.artifactManager.get()
        val dependencyManagers = extension.dependencyManagers.get()
        val versionManager = extension.versionManager.orNull ?: throw GradleException("versionManager must be specified")
        val version = versionManager.getVersion(project, extension.versionPrefix.get())

        val uploadTask = tasks.register("uploadXCFramework") {
            group = TASK_GROUP_NAME

            dependsOn(zipTask)
            inputs.file(zipFile)
            outputs.files(urlFile, versionFile)
            outputs.upToDateWhen { false } // We want to always upload when this task is called

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    versionFile.writeText(version)
                    logger.info("Uploading XCFramework version $version")
                    val deployUrl = artifactManager.deployArtifact(project, zipFile, version)
                    urlFile.writeText(deployUrl)
                }
            })
        }

        val publishRemoteTask = tasks.register("kmmBridgePublish") {
            group = TASK_GROUP_NAME
            dependsOn(uploadTask)

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    extension.versionManager.get().recordVersion(project, versionFile.readText())
                }
            })
        }

        artifactManager.configure(this, version, uploadTask, publishRemoteTask)

        for (dependencyManager in dependencyManagers) {
            dependencyManager.configure(this, uploadTask, publishRemoteTask)
        }

        zipTask.configure {
            dependsOn(findXCFrameworkAssembleTask())
        }
    }
}