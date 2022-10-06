package co.touchlab.faktory

import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Project

class GithubReleaseVersionManager() : GitTagBasedVersionManager() {
    override fun recordVersion(project: Project, versionString: String) {
        val commitId = project.procRunFailLog("git", "rev-parse", "HEAD").first()
        GithubCalls.createRelease(project, project.githubRepo, versionString, commitId)
    }
}