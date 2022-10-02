package co.touchlab.faktory

import co.touchlab.faktory.co.touchlab.faktory.internal.procRun
import org.gradle.api.Project

object GitTagVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String {
        val tagSet = mutableSetOf<String>()
        procRun("git", "tag") { line, _ ->
            tagSet.add(line)
        }
        return findNextVersion(versionPrefix) {
            tagSet.contains(it)
        }
    }
}