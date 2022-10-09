package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.alwaysWriteGitTags
import co.touchlab.faktory.writeGitTagVersion
import org.gradle.api.Project

object TimestampVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String =
        "${versionPrefix}.${System.currentTimeMillis()}"

    override fun recordVersion(project: Project, versionString: String) {
        if(project.alwaysWriteGitTags){
            writeGitTagVersion(project, versionString)
        }
    }
}
