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

package co.touchlab.faktory.dependencymanager

import co.touchlab.faktory.*
import co.touchlab.faktory.internal.procRunFailLog
import co.touchlab.faktory.kotlin
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.plugin.cocoapods.KotlinCocoapodsPlugin
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.io.File

sealed class SpecRepo {
    object Trunk : SpecRepo()
    class Private(val url: String) : SpecRepo()
}

class CocoapodsDependencyManager(
    private val specRepoDeferred: () -> SpecRepo,
    private val allowWarnings: Boolean,
    private val verboseErrors: Boolean
) : DependencyManager {
    override fun configure(project: Project, uploadTask: TaskProvider<Task>, publishRemoteTask: TaskProvider<Task>) {

        val podSpecFile =
            "${project.buildDir}/faktory/podspec/${project.kmmBridgeExtension.buildType.get().name.toLowerCase()}/${project.kotlin.cocoapods.name}.podspec"

        val generatePodspecTask = project.tasks.register("generateReleasePodspec") {
            inputs.files(project.urlFile, project.versionFile)
            outputs.file(podSpecFile)
            dependsOn(uploadTask)
            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    project.generatePodspec(project.file(podSpecFile))
                }
            })
        }

        val pushRemotePodspecTask = project.tasks.register("pushRemotePodspec") {
            group = TASK_GROUP_NAME
            inputs.files(podSpecFile)
            dependsOn(generatePodspecTask)

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task>{
                override fun execute(t: Task) {
                    val extras = mutableListOf<String>()

                    if (allowWarnings) {
                        extras.add("--allow-warnings")
                    }

                    if (verboseErrors) {
                        extras.add("--verbose")
                    }

                    when (val specRepo = specRepoDeferred()) {
                        is SpecRepo.Trunk ->
                            project.procRunFailLog("pod", "trunk", "push", podSpecFile, *extras.toTypedArray())
                        is SpecRepo.Private ->
                            project.procRunFailLog("pod", "repo", "push", specRepo.url, podSpecFile, *extras.toTypedArray())
                    }
                }
            })
        }

        publishRemoteTask.configure {
            dependsOn(pushRemotePodspecTask)
        }
    }

    override val needsGitTags: Boolean = false
}

// Adapted from spec generation logic in the kotlin.cocoapods plugin, but we skip script phases and some other details,
// and we read straight from the project and cocoapods extension rather than task properties. We also ignore source and
// insert our deploy URL instead, and include our own version logic.
// TODO it might be nice to migrate this back to using the kotlin.cocoapods podspec task directly, but not worth the
//  effort to wire it up right now.
private fun Project.generatePodspec(outputFile: File) = with(kotlin.cocoapods) {
    val deploymentTargets = run {
        listOf(ios, osx, tvos, watchos).filter { it.deploymentTarget != null }.joinToString("\n") {
            if (extraSpecAttributes.containsKey("${it.name}.deployment_target")) "" else "|    spec.${it.name}.deployment_target = '${it.deploymentTarget}'"
        }
    }

    val dependencies = pods.map { pod ->
        val versionSuffix = if (pod.version != null) ", '${pod.version}'" else ""
        "|    spec.dependency '${pod.name}'$versionSuffix"
    }.joinToString(separator = "\n")

    // Logic for frameworkName pulled from various pieces of PodspecTask, CocoapodsExtension, and KotlinCocoapodsPlugin
    val anyPodFramework = project.provider {
        val anyTarget = project.kotlin.targets
            .withType(KotlinNativeTarget::class.java)
            .matching { it.konanTarget.family.isAppleFamily }.first()
        val anyFramework = anyTarget.binaries
            .matching { it.name.startsWith(KotlinCocoapodsPlugin.POD_FRAMEWORK_PREFIX) }
            .withType(Framework::class.java)
            .first()
        anyFramework
    }
    val frameworkName = anyPodFramework.map { it.baseName }

    val vendoredFramework = "${frameworkName.get()}.xcframework"
    val vendoredFrameworks =
        if (extraSpecAttributes.containsKey("vendored_frameworks")) "" else "|    spec.vendored_frameworks      = '$vendoredFramework'"

    val libraries =
        if (extraSpecAttributes.containsKey("libraries")) "" else "|    spec.libraries                = 'c++'"

    val customSpec = extraSpecAttributes.map { "|    spec.${it.key} = ${it.value}" }.joinToString("\n")

    val url = urlFile.readText()
    val version = versionFile.readText()

    // 'Accept: application/octet-stream' needed for github release file downloads
    outputFile.writeText(
        """
            |Pod::Spec.new do |spec|
            |    spec.name                     = '$name'
            |    spec.version                  = '$version'
            |    spec.homepage                 = ${homepage.orEmpty().surroundWithSingleQuotesIfNeeded()}
            |    spec.source                   = { 
            |                                      :http => '${url}',
            |                                      :type => 'zip',
            |                                      :headers => ['Accept: application/octet-stream']
            |                                    }
            |    spec.authors                  = ${authors.orEmpty().surroundWithSingleQuotesIfNeeded()}
            |    spec.license                  = ${license.orEmpty().surroundWithSingleQuotesIfNeeded()}
            |    spec.summary                  = '${summary.orEmpty()}'
            $vendoredFrameworks
            $libraries
            $deploymentTargets
            $dependencies
            $customSpec
            |end
        """.trimMargin()
    )
}

private fun String.surroundWithSingleQuotesIfNeeded(): String =
    if (startsWith("{") || startsWith("<<-") || startsWith("'")) this else "'$this'"
