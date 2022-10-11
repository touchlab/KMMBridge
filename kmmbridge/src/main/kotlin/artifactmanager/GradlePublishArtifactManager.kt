package co.touchlab.faktory.artifactmanager

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByType
import java.io.File

class GradlePublishArtifactManager(
    val project: Project,
    private val publication: String?,
    private val repository: String?
) : ArtifactManager {

    val gradlePublishingTask: Task?
        get() = project.tasks.findByName(publishingTaskName())

    private val group: String = project.group.toString().replace(".", "/")
    private val name: String = project.name
    private val artifactBasePath: String = "{{url}}$group/$name/{{version}}/$name-{{version}}.zip"

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

        // TODO Look at the gradle publishing side of things - does it need to be told the dynamic version rather than pulling the project.version?
        return artifactBasePath
            .replace("{{url}}", mavenArtifactRepository.url.toString())
            .replace("{{version}}", project.version.toString())
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
