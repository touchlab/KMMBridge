package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.alwaysWriteGitTags
import co.touchlab.faktory.githubRepo
import co.touchlab.faktory.internal.GithubEnterpriseCalls
import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Project

object GithubEnterpriseReleaseVersionManager : GitTagBasedVersionManager() {
    override fun recordVersion(project: Project, versionString: String) {
        val commitId = project.procRunFailLog("git", "rev-parse", "HEAD").first()
        if (project.alwaysWriteGitTags){
            GithubEnterpriseCalls.createRelease(project, project.githubRepo, versionString, commitId)
        } else {
            GithubEnterpriseCalls.createRelease(project, project.githubRepo, null, commitId)
        }
    }
}