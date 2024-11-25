package co.touchlab.kmmbridge

import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class SimplePluginTest {
    @TempDir
    lateinit var testProjectDir: File
    private val assumedRootProjectDir = File(File("..").absolutePath)
    private val testProjectSource = File(assumedRootProjectDir, "test-projects/basic")

    private lateinit var settingsFile: File
    private lateinit var buildFile: File

    @BeforeEach
    fun setup() {
        FileUtils.copyDirectory(testProjectSource, testProjectDir)
        ProcessHelper.runSh("git init;git add .;git commit -m 'arst'", workingDir = testProjectDir)
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        buildFile = File(testProjectDir, "build.gradle.kts")
    }

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

    private fun loadTestGradleProperties(): Properties {
        val properties = Properties()
        FileInputStream(File(testProjectDir, "gradle.properties")).use { stream ->
            properties.load(stream)
        }
        return properties
    }

    private fun logExecResult(result: ExecutionResult) {
        if (result.output.isNotEmpty())
            println(result.output)
        if (result.error.isNotEmpty())
            System.err.println(result.error)
    }
}