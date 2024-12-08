package co.touchlab.kmmbridge.github

import co.touchlab.kmmbridge.KmmBridgeExtension
import co.touchlab.kmmbridge.artifactmanager.ArtifactManager
import co.touchlab.kmmbridge.github.internal.GithubCalls
import co.touchlab.kmmbridge.github.internal.githubArtifactIdentifierName
import co.touchlab.kmmbridge.github.internal.githubArtifactReleaseId
import co.touchlab.kmmbridge.github.internal.githubPublishToken
import co.touchlab.kmmbridge.github.internal.githubRepo
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.getByType
import java.io.File
import kotlin.properties.Delegates

open class GithubReleaseArtifactManager(
    private val repository: String?,
    private val releaseString: String?,
    @Deprecated("Releases should be created externally. This parameter controls the flow for releases created " +
            "by this class, which will eventually be unsupported.")
    private val useExistingRelease: Boolean
) : ArtifactManager {

    @get:Input
    lateinit var releaseVersion: String

    @get:Input
    lateinit var repoName: String

    @get:Input
    lateinit var frameworkName: String

    @get:Input
    lateinit var artifactIdentifierName: String

    @get:Input
    var artifactReleaseId by Delegates.notNull<Int>()

    // TODO: This value is stored in the config cache. It is encrypted, but this still feels insecure. Review alternatives.
    // https://docs.gradle.org/current/userguide/configuration_cache.html#config_cache:secrets
    lateinit var githubPublishToken: String

    override fun configure(
        project: Project, version: String, uploadTask: TaskProvider<Task>, kmmPublishTask: TaskProvider<Task>
    ) {
        this.releaseVersion = releaseString ?: project.version.toString()
        this.repoName = this.repository ?: project.githubRepo
        this.githubPublishToken = project.githubPublishToken
        this.frameworkName = project.kmmBridgeExtension.frameworkName.get()
        artifactReleaseId = project.githubArtifactReleaseId?.toInt() ?: 0
        this.artifactIdentifierName = project.githubArtifactIdentifierName ?: ""
    }

    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String {
        val uploadReleaseId = if (artifactReleaseId == 0) {
            val existingReleaseId = GithubCalls.findReleaseId(
                githubPublishToken, repoName, releaseVersion
            )

            task.logger.info("existingReleaseId: $existingReleaseId")

            if (existingReleaseId != null && !useExistingRelease) {
                throw GradleException("Release for '$releaseVersion' exists. Set 'useExistingRelease = true' to update existing releases.")
            }

            val idReply: Int = existingReleaseId ?: GithubCalls.createRelease(
                githubPublishToken, repoName, releaseVersion, null
            )

            task.logger.info("GitHub Release created with id: $idReply")

            idReply
        } else {
            artifactReleaseId
        }

        val fileName = artifactName(version, useExistingRelease)

        val uploadUrl = GithubCalls.uploadZipFile(githubPublishToken, zipFilePath, repoName, uploadReleaseId, fileName)
        return "${uploadUrl}.zip"
    }

    private fun artifactName(versionString: String, useExistingRelease: Boolean): String {
        return if (useExistingRelease) {
            "$frameworkName-${versionString}-${(System.currentTimeMillis() / 1000)}.xcframework.zip"
        } else {
            uploadZipFileName(versionString)
        }
    }

    open fun uploadZipFileName(versionString: String) = if (artifactIdentifierName.isNotEmpty()) {
        "$frameworkName-${artifactIdentifierName}.xcframework.zip"
    } else {
        "$frameworkName.xcframework.zip"
    }
}

internal val Project.kmmBridgeExtension get() = extensions.getByType<KmmBridgeExtension>()