package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.githubRepo
import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Project

object GithubReleaseVersionManager : GitTagBasedVersionManager() {
    override fun recordVersion(project: Project, versionString: String) {
        val commitId = project.procRunFailLog("git", "rev-parse", "HEAD").first()
        GithubCalls.createRelease(project, project.githubRepo, versionString, commitId)
    }
}