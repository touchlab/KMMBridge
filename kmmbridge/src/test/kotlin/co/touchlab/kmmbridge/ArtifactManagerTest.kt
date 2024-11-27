package co.touchlab.kmmbridge

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArtifactManagerTest : BasePluginTest() {
    override fun testProjectPath(): String = "test-projects/basic"

    @Test
    fun runKmmBridgePublishNoPublishingEnabled() {
        val result = ProcessHelper.runSh(
            "./gradlew kmmBridgePublish " +
                    "-PTOUCHLAB_TEST_ARTIFACT_SERVER=api.touchlab.dev " +
                    "-PTOUCHLAB_TEST_ARTIFACT_CODE=${TOUCHLAB_TEST_ARTIFACT_CODE} " +
                    "--stacktrace", workingDir = testProjectDir
        )
        logExecResult(result)
        assertEquals(1, result.status)
    }

    @Test
    fun runKmmBridgePublish() {
        val urlFile = File(testProjectDir, "allshared/build/kmmbridge/url")
        assertFalse(urlFile.exists())
        val result = ProcessHelper.runSh(
            "./gradlew clean kmmBridgePublish " +
                    "-PENABLE_PUBLISHING=true " +
                    "-PTOUCHLAB_TEST_ARTIFACT_SERVER=api.touchlab.dev " +
                    "-PTOUCHLAB_TEST_ARTIFACT_CODE=${TOUCHLAB_TEST_ARTIFACT_CODE} " +
                    "--stacktrace",
            workingDir = testProjectDir
        )
        logExecResult(result)

        assertTrue(urlFile.exists())
        val urlValue = urlFile.readText()
        assertTrue(urlValue.startsWith("https://api.touchlab.dev/infoadmin/streamTestZip"))
        assertEquals(0, result.status)
    }
}