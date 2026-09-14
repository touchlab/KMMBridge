package co.touchlab.kmmbridge

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class SpmLocalDevTest : BasePluginTest() {
    override fun testProjectPath(): String = "test-projects/basic"

    @Test
    fun runSpmDevBuild() {
        val result =
            ProcessHelper.runSh(
                "./gradlew spmDevBuild --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)
        assertEquals(0, result.status)
    }

    /**
     * Ensure that SPM local dev can load and run when there is no git repo set up.
     */
    @Test
    fun runSpmDevBuildNoGit() {
        ProcessHelper.runSh("rm -rdf .git", workingDir = testProjectDir)
        val result =
            ProcessHelper.runSh(
                "./gradlew spmDevBuild --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)
        assertEquals(0, result.status)
    }
}
