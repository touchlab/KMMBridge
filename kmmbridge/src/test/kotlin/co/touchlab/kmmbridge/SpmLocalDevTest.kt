package co.touchlab.kmmbridge

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SpmLocalDevTest : BasePluginTest() {
    override fun testProjectPath(): String = "test-projects/basic"

    @Test
    fun runSpmDevBuild() {
        val result = ProcessHelper.runSh(
            "./gradlew spmDevBuild --stacktrace " +
                    "-PTOUCHLAB_TEST_ARTIFACT_SERVER=api.touchlab.dev " +
                    "-PTOUCHLAB_TEST_ARTIFACT_CODE=${TOUCHLAB_TEST_ARTIFACT_CODE}", workingDir = testProjectDir
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
        val result = ProcessHelper.runSh(
            "./gradlew spmDevBuild --stacktrace " +
                    "-PTOUCHLAB_TEST_ARTIFACT_SERVER=api.touchlab.dev " +
                    "-PTOUCHLAB_TEST_ARTIFACT_CODE=${TOUCHLAB_TEST_ARTIFACT_CODE}", workingDir = testProjectDir
        )
        logExecResult(result)
        assertEquals(0, result.status)
    }
}