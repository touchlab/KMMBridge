package co.touchlab.kmmbridge.artifactmanager

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import java.io.File

class TestArtifactManager:ArtifactManager {
    private lateinit var url:String

    override fun configure(
        project: Project,
        version: String,
        uploadTask: TaskProvider<Task>,
        kmmPublishTask: TaskProvider<Task>
    ) {
        url = "test://$version"
    }

    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String = url
}