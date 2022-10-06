package co.touchlab.faktory

import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Project

object GitReleaseVersionManager : GitTagBasedVersionManager() {
    private var versionRecorded = false

    override fun recordVersion(project: Project, versionString: String) {
        if(!versionRecorded) {
            project.procRunFailLog("git", "push")
            val commitId = project.procRunFailLog("git", "rev-parse", "HEAD").first()
            GithubCalls.createRelease(project, project.githubRepo, versionString, commitId)
            versionRecorded = true
        }
    }
}