package co.touchlab.faktory

import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Project

object GitTagVersionManager : GitTagBasedVersionManager() {
    override fun recordVersion(project: Project, versionString: String) {
        project.procRunFailLog("git", "tag", "-a", versionString, "-m", "KMM release version $versionString")
        project.procRunFailLog("git", "push", "--follow-tags")
    }
}