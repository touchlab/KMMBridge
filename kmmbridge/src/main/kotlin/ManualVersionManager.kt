package co.touchlab.faktory

import org.gradle.api.Project

/**
 * Version manager that does nothing. User is responsible for incrementing version as needed.
 */
object ManualVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String = versionPrefix
    override fun recordVersion(project: Project, versionString: String) {
        // Not needed
    }
}
