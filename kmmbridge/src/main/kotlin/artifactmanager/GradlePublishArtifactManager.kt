package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.publishingExtension
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.component.SoftwareComponentFactory
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.getByType
import java.io.File

class GradlePublishArtifactManager(
    val project: Project,
    private val publication: String?,
    private val repository: String?
) : ArtifactManager {

    private val group: String = project.group.toString().replace(".", "/")
    private val name: String = project.name
    private val artifactBasePath: String = "{{url}}/$group/$name/{{version}}/$name-{{version}}-kmmbridge.zip"

    override fun configure(
        project: Project,
        version: String,
        uploadTask: Task,
        softwareComponentFactory: SoftwareComponentFactory
    ) {
        project.publishingExtension.publications.create("SharedFramework", MavenPublication::class.java) {
            from(project.components.getByName("kmmbridge"))
            this.version = version
            artifact(project.tasks.getByName("zipXCFramework")) {
                classifier = "kmmbridge"
                extension = "zip"
            }
        }
        uploadTask.dependsOn(project.tasks.findByName(publishingTaskName()))
    }

    /**
     * The GradlePublishArtifactManager relies on the gradle publishing plugin to manage uploading
     * to maven repositories, and its presence as the artifact manager is a marker for the main
     * KMMBridge plugin to configure task level dependencies.  Since the gradle publishing plugin
     * doesn't tell you anything about the remote URLs that it's creating, it's inferred based on
     * maven's well known conventions.
     */
    override fun deployArtifact(project: Project, zipFilePath: File, version: String): String {
        val publishing = project.extensions.getByType<PublishingExtension>()

        val mavenArtifactRepository = (repository?.let {
            publishing.repositories.findByName(it)
        } ?: publishing.repositories.first()) as MavenArtifactRepository

        return artifactBasePath
            .replace("{{url}}", mavenArtifactRepository.url.toString())
            .replace("{{version}}", version)
    }

    private fun publishingTaskName(): String {
        val publishing = project.extensions.getByType<PublishingExtension>()

        val publication = publication?.let {
            publishing.publications.findByName(it)
        } ?: publishing.publications.first()
        val publicationName = publication.name.capitalize()

        val mavenArtifactRepository = (repository?.let {
            publishing.repositories.findByName(it)
        } ?: publishing.repositories.first()) as MavenArtifactRepository
        val repositoryName = mavenArtifactRepository.name.capitalize()

        return "publish${publicationName}PublicationTo${repositoryName}Repository"
    }
}
