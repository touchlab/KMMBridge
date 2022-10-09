package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.alwaysWriteGitTags
import co.touchlab.faktory.writeGitTagVersion
import org.gradle.api.Project

/**
 * Version manager that does nothing. User is responsible for incrementing version as needed.
 */
object ManualVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String = versionPrefix
    override fun recordVersion(project: Project, versionString: String) {
        if(project.alwaysWriteGitTags){
            writeGitTagVersion(project, versionString)
        }
    }
}
