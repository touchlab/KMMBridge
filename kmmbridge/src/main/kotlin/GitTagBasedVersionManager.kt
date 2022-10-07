package co.touchlab.faktory

import co.touchlab.faktory.internal.procRunSequence
import co.touchlab.faktory.internal.procRunWarnLog
import org.gradle.api.Project

abstract class GitTagBasedVersionManager : VersionManager {
    override fun getVersion(project: Project, versionPrefix: String): String {
        val versionPrefixTrimmed = prepVersionString(versionPrefix)

        // Need to make sure we have all the tags. This may need to be configurable in the future for
        // more complex git setups. If call fails, we'll get a warning but keep going.
        project.procRunWarnLog("git", "pull", "--tags")

        var maxCount = 0

        procRunSequence("git", "tag") { sequence ->
            maxCount = maxVersion(versionPrefixTrimmed, sequence)
        }

        return "${versionPrefixTrimmed}${maxCount + 1}"
    }
}


