import co.touchlab.kmmbridge.KmmBridgeExtension
import co.touchlab.kmmbridge.github.GithubReleaseArtifactManager
import co.touchlab.kmmbridge.github.internal.githubPublishTokenOrNull
import co.touchlab.kmmbridge.github.internal.githubPublishUser
import co.touchlab.kmmbridge.github.internal.githubRepoOrNull
import co.touchlab.kmmbridge.github.kmmBridgeExtension
import co.touchlab.kmmbridge.publishingExtension
import org.gradle.api.Project
import java.net.URI

@Suppress("unused")
fun Project.gitHubReleaseArtifacts(
    repository: String? = null,
    releasString: String? = null,
    useExistingRelease: Boolean = false
) {
    kmmBridgeExtension.setupGitHubReleaseArtifacts(
        GithubReleaseArtifactManager(
            repository,
            releasString,
            useExistingRelease
        )
    )
}

private fun KmmBridgeExtension.setupGitHubReleaseArtifacts(
    githubReleaseArtifactManager: GithubReleaseArtifactManager
) {
    artifactManager.setAndFinalize(githubReleaseArtifactManager)
}

/**
 * Helper function to support GitHub Packages publishing. Use with https://github.com/touchlab/KMMBridgeGithubWorkflow
 * or pass in a valid GitHub token with GITHUB_PUBLISH_TOKEN. Defaults user to "cirunner", which can be overridden with
 * GITHUB_PUBLISH_USER.
 *
 * Generally, just add the following in the Gradle build file.
 *
 * addGithubPackagesRepository()
 */
@Suppress("unused")
fun Project.addGithubPackagesRepository() {
    publishingExtension.apply {
        val githubPublishUser = project.githubPublishUser ?: "cirunner"
        val githubRepo = project.githubRepoOrNull ?: return
        val githubPublishToken = project.githubPublishTokenOrNull ?: return
        repositories.maven {
            name = "GitHubPackages"
            url = URI.create("https://maven.pkg.github.com/$githubRepo")
            credentials {
                username = githubPublishUser
                password = githubPublishToken
            }
        }
    }
}