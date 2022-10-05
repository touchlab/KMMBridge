package co.touchlab.faktory

import co.touchlab.faktory.co.touchlab.faktory.internal.GithubCalls
import org.gradle.api.Project

class GithubReleaseVersionManager() : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String {
        return findNextVersion(versionPrefix) {
            GithubCalls.findReleaseId(project, project.githubRepo, it) != null
        }
    }
}