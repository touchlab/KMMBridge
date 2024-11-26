package co.touchlab.kmmbridge.internal

import co.touchlab.kmmbridge.KmmBridgeExtension
import co.touchlab.kmmbridge.findStringProperty
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.File

internal val Project.layoutBuildDir get() = layout.buildDirectory.get().asFile

internal val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByType()
internal val Project.kmmBridgeExtension get() = extensions.getByType<KmmBridgeExtension>()

internal val Project.urlFile get() = file("$layoutBuildDir/kmmbridge/url")

// Cocoapods is an extension of KMP extension, so you can't just do project.extensions.getByType<CocoapodsExtension>()
internal val KotlinMultiplatformExtension.cocoapodsOrNull get() = (this as ExtensionAware).extensions.findByType<CocoapodsExtension>()
internal val KotlinMultiplatformExtension.cocoapods
    get() = cocoapodsOrNull
        ?: error("You must apply the org.jetbrains.kotlin.native.cocoapods plugin to use cocoapods() configuration")

// This previously defaulted to 'false', but now you can disable it if needed, but otherwise ignore
internal val Project.enablePublishing: Boolean
    get() = project.findStringProperty("ENABLE_PUBLISHING")?.toBoolean() ?: false

internal val Project.spmBuildTargets: String?
    get() = project.findStringProperty("spmBuildTargets")

@Suppress("SpellCheckingInspection")
internal fun Project.zipFilePath(): File {
    val tempDir = file("$layoutBuildDir/kmmbridge/zip")
    val artifactName = "frameworkarchive.zip"
    return file("$tempDir/$artifactName")
}

internal fun Project.findXCFrameworkAssembleTask(buildType: NativeBuildType? = null): TaskProvider<Task> {
    val extension = extensions.getByType<KmmBridgeExtension>()
    val name = extension.frameworkName.get()
    val buildTypeString = (buildType ?: extension.buildType.get()).getName().capitalized()
    val taskWithoutName = "assemble${buildTypeString}XCFramework"
    val taskWithName = "assemble${name.capitalized()}${buildTypeString}XCFramework"
    return runCatching {
        tasks.named(taskWithName)
    }.recoverCatching {
        tasks.named(taskWithoutName)
    }.getOrElse {
        throw UnknownTaskException(
            "Cannot find XCFramework assemble task. Tried $taskWithName and ${taskWithoutName}."
        )
    }
}