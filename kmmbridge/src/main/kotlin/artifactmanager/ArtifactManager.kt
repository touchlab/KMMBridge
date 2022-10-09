package co.touchlab.faktory.artifactmanager

import org.gradle.api.Project
import java.io.File

interface ArtifactManager {
    /**
     * Send the thing, and return a link to the thing...
     */
    fun deployArtifact(project: Project, zipFilePath: File, version: String): String
}