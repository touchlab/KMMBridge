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

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.File

internal val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByType()
internal val Project.kmmBridgeExtension get() = extensions.getByType<KmmBridgeExtension>()
internal val Project.publishingExtension get() = extensions.getByType<PublishingExtension>()

internal val Project.urlFile get() = file("$buildDir/faktory/url")
internal val Project.versionFile get() = file("$buildDir/faktory/version")

// Cocoapods is an extension of KMP extension so you can't just do project.extensions.getByType<CocoapodsExtension>()
internal val KotlinMultiplatformExtension.cocoapodsOrNull get() = (this as ExtensionAware).extensions.findByType<CocoapodsExtension>()
internal val KotlinMultiplatformExtension.cocoapods
    get() = cocoapodsOrNull
        ?: error("You must apply the org.jetbrains.kotlin.native.cocoapods plugin to use cocoapods() configuration")

internal val Project.enablePublishing: Boolean
    get() = project.findStringProperty("ENABLE_PUBLISHING")?.toBoolean() ?: false

internal val Project.spmBuildTargets: String?
    get() = project.findStringProperty("spmBuildTargets")

internal fun Project.zipFilePath(): File {
    val tempDir = file("$buildDir/faktory/zip")
    val artifactName = "frameworkarchive.zip"
    return file("$tempDir/$artifactName")
}

internal fun Project.findStringProperty(name: String): String? {
    rootProject.extensions.getByType(ExtraPropertiesExtension::class.java).run {
        if (has(name))
            return get(name).toString()
    }
    return null
}


internal const val TASK_GROUP_NAME = "kmmbridge"
internal const val EXTENSION_NAME = "kmmbridge"
internal const val TEMP_PUBLISH_TAG_PREFIX = "kmmbridge-tmp-publishing-"

internal fun Project.findXCFrameworkAssembleTask(buildType: NativeBuildType? = null): TaskProvider<Task> {
    val extension = extensions.getByType<KmmBridgeExtension>()
    val name = extension.frameworkName.get()
    val buildTypeString = (buildType ?: extension.buildType.get()).getName().capitalize()
    val taskWithoutName = "assemble${buildTypeString}XCFramework"
    val taskWithName = "assemble${name.capitalize()}${buildTypeString}XCFramework"
    return runCatching {
        tasks.named(taskWithName)
    }.recoverCatching {
        tasks.named(taskWithoutName)
    }.getOrElse {
        throw UnknownTaskException(
            "Cannot find XCFramework assemble task. Tried ${taskWithName} and ${taskWithoutName}."
        )
    }
}
