package co.touchlab.kmmbridge

import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class ArtifactManagerTest : BasePluginTest() {
    override fun testProjectPath(): String = "test-projects/basic"

    @Test
    fun runKmmBridgePublishNoPublishingEnabled() {
        val result =
            ProcessHelper.runSh(
                "./gradlew kmmBridgePublish " +
                    "--stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)
        assertEquals(1, result.status)
    }

    @Test
    fun runKmmBridgePublish() {
        val urlFile = File(testProjectDir, "allshared/build/kmmbridge/url")
        assertFalse(urlFile.exists())
        val result =
            ProcessHelper.runSh(
                "./gradlew clean kmmBridgePublish " +
                    "-PENABLE_PUBLISHING=true " +
                    "--stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)

        assertTrue(urlFile.exists())
        val urlValue = urlFile.readText()
        assertTrue(urlValue.startsWith("http://127.0.0.1:8089/file"))
        assertEquals(0, result.status)
    }
}
