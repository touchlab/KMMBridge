package co.touchlab.kmmbridge

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class SimplePluginTest : BasePluginTest() {
    override fun testProjectPath(): String = "test-projects/basic"

    @Test
    fun runBasicBuild() {
        val result = ProcessHelper.runSh("./gradlew linkDebugFrameworkIosSimulatorArm64", workingDir = testProjectDir)
        logExecResult(result)
        assertEquals(0, result.status)
    }

    @Test
    fun runSpmDevBuild() {
        val result = ProcessHelper.runSh("./gradlew spmDevBuild --stacktrace", workingDir = testProjectDir)
        logExecResult(result)
        assertEquals(0, result.status)
    }

    @Test
    fun runKmmBridgePublishNoPublishingEnabled() {
        val result = ProcessHelper.runSh("./gradlew kmmBridgePublish --stacktrace", workingDir = testProjectDir)
        logExecResult(result)
        assertEquals(1, result.status)
    }

    @Test
    fun runKmmBridgePublish() {
        val urlFile = File(testProjectDir, "allshared/build/kmmbridge/url")
        assertFalse(urlFile.exists())
        val result = ProcessHelper.runSh(
            "./gradlew kmmBridgePublish -PENABLE_PUBLISHING=true --stacktrace",
            workingDir = testProjectDir
        )
        logExecResult(result)
        assertTrue(urlFile.exists())
        assertEquals(urlFile.readText(), "test://${loadTestGradleProperties().get("LIBRARY_VERSION")}")
        assertEquals(0, result.status)
    }

    @Test
    fun runSpmDevBuildNoGit() {
        ProcessHelper.runSh("rm -rdf .git", workingDir = testProjectDir)
        val result = ProcessHelper.runSh("./gradlew spmDevBuild --stacktrace", workingDir = testProjectDir)
        logExecResult(result)
        assertEquals(0, result.status)
    }
}