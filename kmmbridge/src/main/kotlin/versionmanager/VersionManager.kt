package co.touchlab.faktory.versionmanager

import org.gradle.api.Project

interface VersionManager {
    /**
     * Compute a final version to use for publication, based on the plugin versionPrefix
     */
    fun getVersion(project: Project, versionPrefix: String): String

    /**
     * Versions that need to write somewhere need to do it after everything else is done.
     * Called after dependency managers are done.
     */
    fun recordVersion(project: Project, versionString: String)
}