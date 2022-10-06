package co.touchlab.faktory

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import java.io.File

internal val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByType()
internal val Project.kmmBridgeExtension get() = extensions.getByType<KmmBridgeExtension>()

internal val Project.urlFile get() = file("$buildDir/faktory/url")
internal val Project.versionFile get() = file("$buildDir/faktory/version")

// Cocoapods is an extension of KMP extension so you can't just do project.extensions.getByType<CocoapodsExtension>()
internal val KotlinMultiplatformExtension.cocoapods get() = (this as ExtensionAware).extensions.findByType<CocoapodsExtension>()
    ?: error("You must apply the org.jetbrains.kotlin.native.cocoapods plugin to use cocoapods() configuration")

internal val Project.githubPublishToken
    get() = (project.property("GITHUB_PUBLISH_TOKEN")
        ?: throw IllegalArgumentException("KMM Bridge Github operations need property GITHUB_PUBLISH_TOKEN")) as String

internal val Project.githubRepo
    get() = (project.findStringProperty("GITHUB_REPO")
        ?: throw IllegalArgumentException("KMM Bridge Github operations need a repo param or property GITHUB_REPO"))

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
