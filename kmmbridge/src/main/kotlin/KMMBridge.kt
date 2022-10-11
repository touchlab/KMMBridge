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

import co.touchlab.faktory.artifactmanager.GradlePublishArtifactManager
import org.gradle.api.*
import org.gradle.api.artifacts.Configuration
import org.gradle.api.attributes.Bundling
import org.gradle.api.attributes.Category
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.attributes.Usage
import org.gradle.api.attributes.java.TargetJvmVersion
import org.gradle.api.component.SoftwareComponentFactory
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkConfig
import java.io.*
import java.util.*
import javax.inject.Inject

@Suppress("unused")
class KMMBridgePlugin @Inject constructor(
    private val softwareComponentFactory: SoftwareComponentFactory
) : Plugin<Project> {

    private lateinit var zipTask: Zip
    private lateinit var zipFile: File

    override fun apply(project: Project): Unit = with(project) {
        val extension = extensions.create<KmmBridgeExtension>(EXTENSION_NAME)

        with(softwareComponentFactory.adhoc("kmmbridge")) {
            components.add(this)
            addVariantsFromConfiguration(createOutgoingConfiguration()) { mapToMavenScope("runtime") }
        }

        extension.dependencyManagers.convention(emptyList())
        extension.buildType.convention(NativeBuildType.RELEASE)
        configureZipTask(extension)

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
            configureDeploy()

            zipTask.dependsOn(findXCFrameworkAssembleTask())
        }
    }

    private fun Project.configureZipTask(extension: KmmBridgeExtension) {
        zipFile = zipFilePath()
        zipTask = task<Zip>("zipXCFramework") {
            group = TASK_GROUP_NAME
            from("$buildDir/XCFrameworks/${extension.buildType.get().getName()}")
            destinationDirectory.set(zipFile.parentFile)
            archiveFileName.set(zipFile.name)
        }
    }

    // Collect all declared frameworks in project and combine into xcframework
    private fun Project.configureXcFramework() {
        val extension = extensions.getByType<KmmBridgeExtension>()
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

    private fun Project.configureDeploy() {
        val extension = extensions.getByType<KmmBridgeExtension>()
        val artifactManager = extension.artifactManager.get()
        val dependencyManagers = extension.dependencyManagers.get()

        val uploadTask = task("uploadXCFramework") {
            group = TASK_GROUP_NAME

            dependsOn(zipTask)
            inputs.file(zipFile)
            outputs.files(urlFile, versionFile)
            outputs.upToDateWhen { false } // We want to always upload when this task is called

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val versionManager = extension.versionManager.orNull ?: throw GradleException("versionManager must be specified")
                    val version = versionManager.getVersion(project, extension.versionPrefix.get())
                    versionFile.writeText(version)
                    logger.info("Uploading XCFramework version $version")
                    val deployUrl = artifactManager.deployArtifact(project, zipFile, version)
                    urlFile.writeText(deployUrl)
                }
            })
        }

        val publishRemoteTask = task("kmmBridgePublish") {
            group = TASK_GROUP_NAME
            dependsOn(uploadTask)

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    extension.versionManager.get().recordVersion(project, versionFile.readText())
                }
            })
        }

        if (artifactManager is GradlePublishArtifactManager) {
            uploadTask.dependsOn(artifactManager.gradlePublishingTask)
        }

        for (dependencyManager in dependencyManagers) {
            dependencyManager.configure(this, uploadTask, publishRemoteTask)
        }
    }

    private fun Project.createOutgoingConfiguration(): Configuration {
        val configuration by configurations.creating {
            isCanBeConsumed = true
            isCanBeResolved = false
            attributes {
                attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
                attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
                attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
                attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, JavaVersion.current().majorVersion.toInt())
                attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named("shared-xcframework"))
            }
        }

        return configuration
    }
}
