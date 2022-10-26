package artifactmanager

import org.gradle.api.Project
import java.io.File

interface GithubApi {
    fun findReleaseId(project: Project, repoName: String, artifactReleaseTag: String): Int?

    fun createRelease(project: Project, repo: String, tag: String, commitId: String?): Int

    fun uploadZipFile(project: Project, zipFilePath: File, repo: String, releaseId: Int, fileName: String): String
}