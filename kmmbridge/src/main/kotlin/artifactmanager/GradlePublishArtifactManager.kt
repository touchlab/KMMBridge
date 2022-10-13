package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.publishingExtension
import co.touchlab.faktory.versionFile
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.attributes.Bundling
import org.gradle.api.attributes.Category
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.attributes.Usage
import org.gradle.api.attributes.java.TargetJvmVersion
import org.gradle.api.component.SoftwareComponentFactory
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.named
import java.io.File

class GradlePublishArtifactManager(
    val project: Project,
    private val publication: String?,
    private val repository: String?
) : ArtifactManager {

    val gradlePublishingTask: Task?
        get() = publishingTaskName()?.let { project.tasks.findByName(it) }

    private val group: String = project.group.toString().replace(".", "/")
    private val name: String = project.name
    private val artifactBasePath: String = "{{url}}/$group/$name/{{version}}/$name-{{version}}-kmmbridge.zip"

    override fun configure(
        project: Project,
        resolveVersionTask: Task,
        uploadTask: Task,
        softwareComponentFactory: SoftwareComponentFactory
    ) {
        val dependsTask = gradlePublishingTask
        if(dependsTask != null) {
            with(softwareComponentFactory.adhoc("kmmbridge")) {
                project.components.add(this)
                addVariantsFromConfiguration(project.createOutgoingConfiguration()) { mapToMavenScope("runtime") }
            }
            val publication = project.publishingExtension.publications.create("SharedFramework", MavenPublication::class.java) {
                from(project.components.getByName("kmmbridge"))
                artifact(project.tasks.getByName("zipXCFramework")) {
                    classifier = "kmmbridge"
                    extension = "zip"
                }
            }
            val lateApplyVersionToPublicationTask = project.tasks.create("lateApplyVersionToPublication"){
                dependsOn(resolveVersionTask)
                @Suppress("ObjectLiteralToLambda")
                doLast(object : Action<Task> {
                    override fun execute(t: Task) {
                        publication.version = project.versionFile.readText()
                    }
                })
            }
            gradlePublishingTask?.dependsOn(lateApplyVersionToPublicationTask)
            uploadTask.dependsOn(gradlePublishingTask)
        }
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

        // TODO Look at the gradle publishing side of things - does it need to be told the dynamic version rather than pulling the project.version?
        return artifactBasePath
            .replace("{{url}}", mavenArtifactRepository.url.toString())
            .replace("{{version}}", version)
    }

    private fun publishingTaskName(): String? {
        val publishing = project.extensions.getByType<PublishingExtension>()

        val publication = publication?.let {
            publishing.publications.findByName(it)
        } ?: publishing.publications.firstOrNull()
        val publicationName = publication?.name?.capitalize()

        val mavenArtifactRepository = (repository?.let {
            publishing.repositories.findByName(it)
        } ?: publishing.repositories.firstOrNull()) as MavenArtifactRepository?
        val repositoryName = mavenArtifactRepository?.name?.capitalize()

        return if (publicationName == null || repositoryName == null) {
            null
        } else {
            "publish${publicationName}PublicationTo${repositoryName}Repository"
        }
    }

    private fun Project.createOutgoingConfiguration(): Configuration {
        val configuration by configurations.creating {
            isCanBeConsumed = true
            isCanBeResolved = false
            attributes {
                attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
                attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.NATIVE_LINK))
                attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EMBEDDED))
                attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, JavaVersion.current().majorVersion.toInt())
                attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named("shared-xcframework"))
            }
        }

        return configuration
    }
}
