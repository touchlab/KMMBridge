package co.touchlab.faktory

import org.gradle.api.Project

object TimestampVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String =
        "${versionPrefix}.${System.currentTimeMillis()}"
}
