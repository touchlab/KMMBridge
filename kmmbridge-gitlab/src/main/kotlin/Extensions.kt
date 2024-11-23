import co.touchlab.kmmbridge.gitlab.internal.gitLabDomain
import co.touchlab.kmmbridge.gitlab.internal.gitLabPublishTokenOrNull
import co.touchlab.kmmbridge.gitlab.internal.gitLabPublishUser
import co.touchlab.kmmbridge.gitlab.internal.gitLabRepoOrNull
import co.touchlab.kmmbridge.publishingExtension
import org.gradle.api.Project
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication


/**
 * Helper function to support GitLab Packages publishing.
 * Pass in a valid GitLab token type with GITLAB_PUBLISH_USER. Options include; "Private-Token", "Deploy-Token" & "Job-Token" (default).
 * Pass in a valid GitLab token for the specified type with GITLAB_PUBLISH_TOKEN. Defaults to CI_JOB_TOKEN environment variable.
 * Pass in a custom GitLab domain. Useful for self-managed instances. Defaults to "gitlab.com".
 *
 * Generally, just add the following in the Gradle build file.
 *
 * addGitlabPackagesRepository()
 */
@Suppress("unused")
fun Project.addGitlabPackagesRepository() {
    publishingExtension.apply {
        try {
            val gitLabPublishUser = project.gitLabPublishUser ?: "Job-Token"
            val gitLabPublishToken = project.gitLabPublishTokenOrNull ?: System.getenv("CI_JOB_TOKEN") ?: return
            val gitLabRepo = project.gitLabRepoOrNull ?: return
            val gitLabDomain = project.gitLabDomain ?: "gitlab.com"
            repositories.maven {
                name = "GitLabPackages"
                url = uri("https://$gitLabDomain/api/v4/projects/$gitLabRepo/packages/maven")
                credentials(HttpHeaderCredentials::class.java) {
                    name = gitLabPublishUser
                    value = gitLabPublishToken
                }
                authentication {
                    create("header", HttpHeaderAuthentication::class.java)
                }
            }
        } catch (e: Exception) {
            logger.warn("Could not configure GitLabPackagesRepository! - $e")
        }
    }
}
