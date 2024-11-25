package co.touchlab.kmmbridge

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Tests to ensure KMMBridge doesn't impact non-KMMBridge Gradle operation in any significant way.
 */
class NonKmmBridgeTasksTest : BasePluginTest() {
    override fun testProjectPath(): String = "test-projects/basic"

    @Test
    fun runBasicBuild() {
        val result = ProcessHelper.runSh("./gradlew linkDebugFrameworkIosSimulatorArm64", workingDir = testProjectDir)
        logExecResult(result)
        assertEquals(0, result.status)
    }
}