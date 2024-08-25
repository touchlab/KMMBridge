package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.dependencymanager.DependencyManager
import co.touchlab.faktory.dependencymanager.SpmDependencyManager
import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.githubRepo
import co.touchlab.faktory.internal.procRunFailLog
import co.touchlab.faktory.internal.skipGitHumReleaseSpmChecks
import co.touchlab.faktory.kmmBridgeExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File
import java.util.*

class GithubReleaseArtifactManager(
    private val repository: String?, private val releaseString: String?, private val useExistingRelease: Boolean
) : ArtifactManager {
    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val releaseVersion = releaseString ?: project.version.toString()
        val repoName: String = repository ?: project.githubRepo

        val existingReleaseId = GithubCalls.findReleaseId(
            project, repoName, releaseVersion
        )

        if (existingReleaseId != null && !useExistingRelease) {
            throw GradleException("Release for '$releaseVersion' exists. Set 'useExistingRelease = true' to update existing releases.")
        }

        val idReply: Int = existingReleaseId ?: GithubCalls.createRelease(
            project, repoName, releaseVersion, null
        )

        val fileName = artifactName(project, version, useExistingRelease)

        val uploadUrl = GithubCalls.uploadZipFile(project, zipFilePath, repoName, idReply, fileName)
        return "${uploadUrl}.zip"
    }

    override fun finishArtifact(project: Project, version: String, dependencyManagers: List<DependencyManager>) {
        val needsSpmReleaseTagUpdate = dependencyManagers.any { it is SpmDependencyManager }
        if (needsSpmReleaseTagUpdate) {

            val gitCommonDir = project.procRunFailLog("git", "rev-parse", "--git-common-dir").first().trim()
            project.logger.warn("gitCommonDir: $gitCommonDir")
            val trimmedPath = gitCommonDir.substring(0, gitCommonDir.length - ".git".length)
            val repoHomeDir = if (trimmedPath.isEmpty()) {
                null
            } else {
                File(trimmedPath)
            }

            project.logger.warn("Running git diff")
            val diffResult = project.procRunFailLog("git", "diff", "--name-only", dir = repoHomeDir)

            val skipCheck = project.skipGitHumReleaseSpmChecks
            if (!skipCheck) {
                // We *usually* expect one file in the diff, which is the Package.swift file. However, if the repo does
                // not already have a local-dev Package.swift file being tracked, git diff doesn't see the new file.
                // To be extra safe, at some point we should check that the Package.swift file was actually written,
                // but this should at least help pinpoint issues from GitHub Actions logs.
                val expectedResult = diffResult.size == 1 && diffResult.first().contains("Package.swift")
                if (!expectedResult) {
                    project.logger.warn("Normally we'd expect a single Package.swift in the git diff. If you do not have Package.swift in your main branch, ignore this warning.")

                    if (diffResult.size > 1) {
                        project.logger.error("Failing spm check. To disable this check add SKIP_GITHUB_RELEASE_SPM_CHECKS=true to your Gradle properties or pass the Gradle property in on the command line.")
                        throw GradleException(
                            "git diff should be one file. Multiple found: ${
                                diffResult.joinToString(
                                    "\n"
                                ) { " --|$it" }
                            }"
                        )
                    }
                }
            }

            val tempBranch = UUID.randomUUID().toString()

            // Get the tag created by the GitHub release
            project.logger.warn("Running git fetch")
            project.procRunFailLog("git", "fetch", "--tags", dir = repoHomeDir)


            // Create a local branch. We don't need to delete is as we just need the commit ref.
            project.logger.warn("Running git checkout new branch")
            project.procRunFailLog("git", "checkout", "-b", tempBranch, dir = repoHomeDir)

            // Add and commit
            project.logger.warn("Running git add")
            project.procRunFailLog("git", "add", ".", dir = repoHomeDir)

            project.logger.warn("Running git commit")
            project.procRunFailLog("git", "commit", "-m", "KMP SPM package release for $version", dir = repoHomeDir)

            // Force-update the tag created by the GitHub release
            project.logger.warn("Running git tag")
            project.procRunFailLog(
                "git",
                "tag",
                "-fa",
                version,
                "-m",
                "KMP release version $version", dir = repoHomeDir
            )

            // Force-push the tag reference. This will push the tag and commit.
            project.logger.warn("Running git push")
            project.procRunFailLog(
                "git",
                "push",
                "origin",
                "-f",
                "refs/tags/${version}", dir = repoHomeDir
            )
        }
    }
}

private fun artifactName(project: Project, versionString: String, useExistingRelease: Boolean): String {
    val frameworkName = project.kmmBridgeExtension.frameworkName.get()
    return if (useExistingRelease) {
        "$frameworkName-${versionString}-${(System.currentTimeMillis() / 1000)}.xcframework.zip"
    } else {
        "$frameworkName.xcframework.zip"
    }
}