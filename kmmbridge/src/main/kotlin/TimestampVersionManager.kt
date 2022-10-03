package co.touchlab.faktory

import org.gradle.api.Project

internal class TimestampVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String =
        "${versionPrefix}.${System.currentTimeMillis()}"
}
