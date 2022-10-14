package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.publishingExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.getByType
import java.io.File

private const val FRAMEWORK_PUBLICATION_NAME = "KMMBridgeFramework"
private const val KMMBRIDGE_ARTIFACT_SUFFIX = "kmmbridge"

class MavenPublishArtifactManager(
    val project: Project,
    private val publicationName: String?,
    private val repositoryName: String?
) : ArtifactManager {
    private val group: String = project.group.toString().replace(".", "/")
    private val kmmbridgeArtifactId = "${project.name}-$KMMBRIDGE_ARTIFACT_SUFFIX"

    override fun configure(
        project: Project,
        version: String,
        uploadTask: Task
    ) {
        project.publishingExtension.publications.create(FRAMEWORK_PUBLICATION_NAME, MavenPublication::class.java) {
            this.version = version
            artifact(project.tasks.getByName("zipXCFramework")) {
                extension = "zip"
            }
            artifactId = kmmbridgeArtifactId
        }

        publishingTasks().forEach { uploadTask.dependsOn(it) }
    }

    /**
     * The GradlePublishArtifactManager relies on the gradle publishing plugin to manage uploading
     * to maven repositories, and its presence as the artifact manager is a marker for the main
     * KMMBridge plugin to configure task level dependencies.  Since the gradle publishing plugin
     * doesn't tell you anything about the remote URLs that it's creating, it's inferred based on
     * maven's well known conventions.
     */
    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val publishingExtension = project.extensions.getByType<PublishingExtension>()

        val mavenArtifactRepository = findArtifactRepository(publishingExtension)!! // Previous code assumed not-null, so just hard not-nulling here, unless we want to deal with nulls

        return artifactPath(mavenArtifactRepository.url.toString(), version)
    }

    private fun publishingTasks(): List<Task> {
        val publishingExtension = project.extensions.getByType<PublishingExtension>()

        // Either the user has supplied a correct name, or we use the default. If neither is found, fail.
        val publicationNameCap = publishingExtension.publications.getByName(publicationName ?: FRAMEWORK_PUBLICATION_NAME).name.capitalize()

        return publishingExtension.repositories.filterIsInstance<MavenArtifactRepository>().map { repo ->
            val repositoryName = repo.name.capitalize()
            val publishTaskName = "publish${publicationNameCap}PublicationTo${repositoryName}Repository"
            // Verify that the "publish" task exists before collecting
            project.tasks.findByName(publishTaskName) ?: throw GradleException("Cannot find publish task $publishTaskName")
        }
    }

    private fun findArtifactRepository(publishingExtension: PublishingExtension): MavenArtifactRepository? {
        val mavenArtifactRepository = (repositoryName?.let {
            publishingExtension.repositories.findByName(it) as MavenArtifactRepository
        } ?: publishingExtension.repositories.filterIsInstance<MavenArtifactRepository>().firstOrNull())
        return mavenArtifactRepository
    }

    private fun artifactPath(url: String, version: String) =
        "$url/$group/$kmmbridgeArtifactId/$version/$kmmbridgeArtifactId-$version.zip"
}
