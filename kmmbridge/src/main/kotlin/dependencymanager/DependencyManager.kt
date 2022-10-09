package co.touchlab.faktory.dependencymanager

import org.gradle.api.Project
import org.gradle.api.Task

interface DependencyManager {
    /**
     * Do configuration specific to this `DependencyManager`. Generally this involves creating tasks that depend on
     * [uploadTask] and are dependencies of [publishRemoteTask].
     */
    fun configure(project: Project, uploadTask: Task, publishRemoteTask: Task) {}

    /**
     * True if this type of dependency needs git tags to function properly (currently SPM true, Cocoapods false)
     */
    val needsGitTags: Boolean
}