package co.touchlab.faktory.internal

import co.touchlab.faktory.kmmBridgeExtension
import org.gradle.api.Project
import java.util.*

/**
 * Generate a file name that isn't guessable. Some artifact managers don't have auth guarding the urls.
 */
internal fun obscureFileName(project: Project, versionString: String): String {
    val randomId = UUID.randomUUID().toString()
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return "${frameworkName}-${versionString}-${randomId}.xcframework.zip"
}