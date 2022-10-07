package co.touchlab.faktory

import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Project

object GitTagVersionManager : GitTagBasedVersionManager() {
    override fun recordVersion(project: Project, versionString: String) {
        writeGitTagVersion(project, versionString)
    }
}