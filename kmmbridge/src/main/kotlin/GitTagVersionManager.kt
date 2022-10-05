package co.touchlab.faktory

import co.touchlab.faktory.co.touchlab.faktory.internal.procRun
import co.touchlab.faktory.co.touchlab.faktory.internal.procRunWarnLog
import org.gradle.api.Project

object GitTagVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String {
        project.logger.info("Running GitTagVersionManager.getVersion")
        val tagSet = mutableSetOf<String>()
        // Need to make sure we have all of the tags. This may need to be configurable in the future for
        // more complex git setups. If call fails, we'll get a warning but keep going.
        project.procRunWarnLog("git", "pull", "--tags")
        procRun("git", "tag") { line, _ ->
            project.logger.info("Found tag: $line")
            tagSet.add(line)
        }
        val nextVersion = findNextVersion(versionPrefix) {
            tagSet.contains(it)
        }
        project.logger.info("GitTagVersionManager nextVersion: $nextVersion")
        return nextVersion
    }
}