package co.touchlab.kmmbridge.artifactmanager

import co.touchlab.kmmbridge.internal.capitalized
import co.touchlab.kmmbridge.publishingExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.getByType
import java.io.File

private const val FRAMEWORK_PUBLICATION_NAME = "KMMBridgeFramework"
private const val KMMBRIDGE_ARTIFACT_SUFFIX = "kmmbridge"

internal class MavenPublishArtifactManager(
    private val publicationName: String?,
    private val artifactSuffix: String?,
    private val repositoryName: String?,
    private val isMavenCentral: Boolean = false,
) : ArtifactManager {
    lateinit var group: String
    lateinit var kmmbridgeArtifactId: String
    lateinit var mavenArtifactRepositoryUrl: String

    override fun configure(
        project: Project,
        version: String,
        uploadTask: TaskProvider<Task>,
        kmmPublishTask: TaskProvider<Task>
    ) {
        this.group = project.group.toString().replace(".", "/")
        this.kmmbridgeArtifactId = "${project.name}-${artifactSuffix ?: KMMBRIDGE_ARTIFACT_SUFFIX}"
        this.mavenArtifactRepositoryUrl = project.evaluateRepoUrl()

        project.publishingExtension.publications.create(
            publicationName ?: FRAMEWORK_PUBLICATION_NAME,
            MavenPublication::class.java
        ) {
            this.version = version
            val archiveProvider = project.tasks.named("zipXCFramework", Zip::class.java).flatMap {
                it.archiveFile
            }
            artifact(archiveProvider) {
                extension = "zip"
            }
            artifactId = kmmbridgeArtifactId
        }

        publishingTasks(project).forEach {
            uploadTask.configure {
                dependsOn(it)
            }
        }
        try {
            project.tasks.named("publish").also { task ->
                task.configure {
                    dependsOn(kmmPublishTask)
                }
            }
        } catch (_: UnknownTaskException) {
            project.logger.warn("Gradle publish task not found")
        }
    }

    /**
     * The GradlePublishArtifactManager relies on the gradle publishing plugin to manage uploading
     * to maven repositories, and its presence as the artifact manager is a marker for the main
     * KMMBridge plugin to configure task level dependencies.  Since the gradle publishing plugin
     * doesn't tell you anything about the remote URLs that it's creating, it's inferred based on
     * maven's well known conventions.
     */
    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String {
        return artifactPath(mavenArtifactRepositoryUrl, version)
    }

    private fun Project.evaluateRepoUrl(): String {
        val publishingExtension = project.extensions.getByType<PublishingExtension>()

        // There may be more than one repo, but it's also possible we get none. This will allow us to continue and trying
        // to use the dependency should fail.
        // If there are multiple repos, the repo name needs to be specified.
        return if (!isMavenCentral) {
            findArtifactRepository(publishingExtension).url.toString()
        } else {
            "https://repo.maven.apache.org/maven2"
        }
    }

    private fun publishingTasks(project: Project): List<TaskProvider<Task>> {
        val publishingExtension = project.extensions.getByType<PublishingExtension>()

        // Either the user has supplied a correct name, or we use the default. If neither is found, fail.
        val publicationNameCap =
            publishingExtension.publications.getByName(publicationName ?: FRAMEWORK_PUBLICATION_NAME).name.capitalized()

        return publishingExtension.repositories.filterIsInstance<MavenArtifactRepository>().map { repo ->
            val repositoryName = repo.name.capitalized()
            val publishTaskName = "publish${publicationNameCap}PublicationTo${repositoryName}Repository"
            // Verify that the "publish" task exists before collecting
            project.tasks.named(publishTaskName)
        }
    }

    private fun findArtifactRepository(publishingExtension: PublishingExtension): MavenArtifactRepository =
        repositoryName?.let {
            publishingExtension.repositories.findByName(it) as MavenArtifactRepository
        } ?: publishingExtension.repositories.filterIsInstance<MavenArtifactRepository>().firstOrNull()
        ?: throw GradleException(
            "Artifact repository not found, please, specify maven repository\n" +
                    "publishing {\n" +
                    "    repositories {\n" +
                    "        maven {\n" +
                    "            url = uri(\"https://someservice/path/to/repo\")\n" +
                    "            credentials {\n" +
                    "                username = publishUsername\n" +
                    "                password = publishPassword\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}"
        )

    private fun artifactPath(url: String, version: String) =
        "$url/$group/$kmmbridgeArtifactId/$version/$kmmbridgeArtifactId-$version.zip"
}
