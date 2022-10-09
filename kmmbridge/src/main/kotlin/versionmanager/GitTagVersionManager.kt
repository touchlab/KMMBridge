package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.writeGitTagVersion
import org.gradle.api.Project

object GitTagVersionManager : GitTagBasedVersionManager() {
    override fun recordVersion(project: Project, versionString: String) {
        writeGitTagVersion(project, versionString)
    }
}