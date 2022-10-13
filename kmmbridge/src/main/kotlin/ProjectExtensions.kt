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

import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.File

internal val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByType()
internal val Project.kmmBridgeExtension get() = extensions.getByType<KmmBridgeExtension>()

internal val Project.urlFile get() = file("$buildDir/faktory/url")
internal val Project.versionFile get() = file("$buildDir/faktory/version")

// Cocoapods is an extension of KMP extension so you can't just do project.extensions.getByType<CocoapodsExtension>()
internal val KotlinMultiplatformExtension.cocoapodsOrNull get() = (this as ExtensionAware).extensions.findByType<CocoapodsExtension>()
internal val KotlinMultiplatformExtension.cocoapods get() = cocoapodsOrNull
    ?: error("You must apply the org.jetbrains.kotlin.native.cocoapods plugin to use cocoapods() configuration")

internal val Project.githubPublishToken
    get() = (project.property("GITHUB_PUBLISH_TOKEN")
        ?: throw IllegalArgumentException("KMMBridge Github operations need property GITHUB_PUBLISH_TOKEN")) as String

internal val Project.githubEnterpriseHost
    get() = (project.property("GITHUB_ENTERPRISE_HOST")
        ?: throw IllegalArgumentException("KMMBridge Github operations need property GITHUB_ENTERPRISE_HOST")) as String

internal val Project.githubEnterpriseRepoOwner
    get() = (project.property("GITHUB_REPO_OWNER")
        ?: throw IllegalArgumentException("KMMBridge Github operations need property GITHUB_REPO_OWNER")) as String

internal val Project.githubRepo
    get() = (project.findStringProperty("GITHUB_REPO")
        ?: throw IllegalArgumentException("KMMBridge Github operations need a repo param or property GITHUB_REPO"))

internal val Project.spmBuildTargets: String?
    get() = project.findStringProperty("spmBuildTargets")

internal val Project.alwaysWriteGitTags: Boolean
    get() = kmmBridgeExtension.dependencyManagers.get().any { it.needsGitTags }

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

/**
 * Write version to git tags
 */
internal fun writeGitTagVersion(project: Project, versionString: String) {
    project.procRunFailLog("git", "tag", "-a", versionString, "-m", "KMM release version $versionString")
    project.procRunFailLog("git", "push", "--follow-tags")
}

internal const val TASK_GROUP_NAME = "kmmbridge"
internal const val EXTENSION_NAME = "kmmbridge"

internal fun Project.findXCFrameworkAssembleTask(buildType: NativeBuildType? = null): Task {
    val extension = extensions.getByType<KmmBridgeExtension>()
    val name = extension.frameworkName.get()
    val buildTypeString = (buildType ?: extension.buildType.get()).getName().capitalize()
    val taskWithoutName = "assemble${buildTypeString}XCFramework"
    val taskWithName = "assemble${name.capitalize()}${buildTypeString}XCFramework"
    return try {
        tasks.findByName(taskWithName) ?: tasks.findByName(taskWithoutName)!!
    } catch (e: NullPointerException) {
        throw UnknownTaskException("Cannot find XCFramework assemble task. Tried ${taskWithName} and ${taskWithoutName}.", e)
    }
}
