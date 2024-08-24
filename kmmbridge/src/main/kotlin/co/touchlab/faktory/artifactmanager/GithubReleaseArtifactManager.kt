package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.dependencymanager.DependencyManager
import co.touchlab.faktory.dependencymanager.SpmDependencyManager
import co.touchlab.faktory.internal.GithubCalls
import co.touchlab.faktory.internal.githubRepo
import co.touchlab.faktory.internal.skipGitHumReleaseSpmChecks
import co.touchlab.faktory.kmmBridgeExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset
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
            val os = ByteArrayOutputStream()
            project.logger.info("Running git diff")
            project.exec {
                commandLine(
                    "git",
                    "diff",
                    "--name-only"
                )
                standardOutput = os
            }.assertNormalExitValue()

            val diffResult = os.toByteArray().toString(Charset.defaultCharset()).trim().lines()

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
            project.logger.info("Running git fetch")
            project.exec {
                commandLine(
                    "git",
                    "fetch",
                    "--tags"
                )
            }.assertNormalExitValue()

            // Create a local branch. We don't need to delete is as we just need the commit ref.
            project.logger.info("Running git checkout new branch")
            project.exec {
                commandLine(
                    "git",
                    "checkout",
                    "-b",
                    tempBranch
                )
            }.assertNormalExitValue()

            // Force-update the tag created by the GitHub release
            project.logger.info("Running git tag")
            project.exec {
                commandLine(
                    "git",
                    "tag",
                    "-fa",
                    version,
                    "-m",
                    "KMP release version $version"
                )
            }.assertNormalExitValue()

            // Force-push the tag reference. This will push the tag and commit.
            project.logger.info("Running git push")
            project.exec {
                commandLine(
                    "git",
                    "push",
                    "origin",
                    "-f",
                    "refs/tags/${version}",
                )
            }.assertNormalExitValue()
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