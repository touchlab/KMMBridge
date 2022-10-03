package co.touchlab.faktory

import okhttp3.OkHttpClient
import org.gradle.api.Project
import java.time.Duration

class GithubReleaseVersionManager(private val repoArg: String?) : VersionManager {
    private val okHttpClient =
        OkHttpClient.Builder().callTimeout(Duration.ofMinutes(5)).connectTimeout(Duration.ofMinutes(2))
            .writeTimeout(Duration.ofMinutes(5)).readTimeout(Duration.ofMinutes(2)).build()

    override fun getVersion(project: Project, versionPrefix: String): String {
        val repoName: String = repoArg ?: (project.findStringProperty("GITHUB_REPO")
            ?: throw IllegalArgumentException("GithubReleaseArtifactManager needs a repo param or property GITHUB_REPO"))

        val token = (project.property("GITHUB_PUBLISH_TOKEN")
            ?: throw IllegalArgumentException("GithubReleaseArtifactManager needs property GITHUB_PUBLISH_TOKEN")) as String

        return findNextVersion(versionPrefix) {
            releaseId(repoName, it, token, okHttpClient) != null
        }
    }
}