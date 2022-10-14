package co.touchlab.faktory.artifactmanager

import co.touchlab.faktory.publishingExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.ArtifactRepository
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

private const val FRAMEWORK_PUBLICATION_NAME = "KMMBridgeFramework"

class MavenPublishArtifactManager(
    val project: Project,
    private val publicationName: String?,
    private val repositoryName: String?
) : ArtifactManager {

    private val gradlePublishingTask: Task?
        get() = publishingTaskName()?.let { project.tasks.findByName(it) }

    private val group: String = project.group.toString().replace(".", "/")
    private val name: String = project.name

    override fun configure(
        project: Project,
        version: String,
        uploadTask: Task,
        softwareComponentFactory: SoftwareComponentFactory
    ) {
        with(softwareComponentFactory.adhoc("kmmbridge")) {
            project.components.add(this)
            addVariantsFromConfiguration(project.createOutgoingConfiguration()) { mapToMavenScope("runtime") }
        }

        project.publishingExtension.publications.create(FRAMEWORK_PUBLICATION_NAME, MavenPublication::class.java) {
            from(project.components.getByName("kmmbridge"))
            this.version = version
            artifact(project.tasks.getByName("zipXCFramework")) {
                classifier = "kmmbridge"
                extension = "zip"
            }
        }

        val dependsTask = gradlePublishingTask
        // If we're not in CI, this can be null
        if(dependsTask != null) {
            uploadTask.dependsOn(dependsTask)
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
        val publishingExtension = project.extensions.getByType<PublishingExtension>()

        val mavenArtifactRepository = findArtifactRepository(publishingExtension)!! // Previous code assumed not-null, so just hard not-nulling here, unless we want to deal with nulls

        return artifactPath(mavenArtifactRepository.url.toString(), version)
    }

    private fun publishingTaskName(): String? {
        val publishingExtension = project.extensions.getByType<PublishingExtension>()

        // Either the user has supplied a correct name, or we use the default. If neither is found, fail.
        val publicationNameCap = publishingExtension.publications.getByName(publicationName ?: FRAMEWORK_PUBLICATION_NAME).name.capitalize()

        val mavenArtifactRepository = findArtifactRepository(publishingExtension)

        val repositoryName = mavenArtifactRepository?.name?.capitalize()

        return if (repositoryName == null) {
            null
        } else {
            "publish${publicationNameCap}PublicationTo${repositoryName}Repository"
        }
    }

    private fun findArtifactRepository(publishingExtension: PublishingExtension): MavenArtifactRepository? {
        val mavenArtifactRepository = (repositoryName?.let {
            publishingExtension.repositories.findByName(it) as MavenArtifactRepository
        } ?: publishingExtension.repositories.filterIsInstance<MavenArtifactRepository>().firstOrNull())
        return mavenArtifactRepository
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

    private fun artifactPath(url: String, version: String) =
        "$url/$group/$name/$version/$name-$version-kmmbridge.zip"
}
