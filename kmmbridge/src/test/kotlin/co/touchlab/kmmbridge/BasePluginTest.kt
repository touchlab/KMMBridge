package co.touchlab.kmmbridge

import java.io.File
import java.io.FileInputStream
import java.util.Properties
import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir

abstract class BasePluginTest {
    @TempDir
    lateinit var testProjectDir: File
    internal val assumedRootProjectDir = File(File("..").absolutePath)
    private val testProjectSource = File(assumedRootProjectDir, testProjectPath())

    internal lateinit var settingsFile: File
    internal lateinit var buildFile: File

    abstract fun testProjectPath(): String

    @BeforeEach
    fun setup() {
        FileUtils.copyDirectory(testProjectSource, testProjectDir)
        ProcessHelper.runSh("git init;git add .;git commit -m 'arst'", workingDir = testProjectDir)
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        buildFile = File(testProjectDir, "build.gradle.kts")
    }

    internal fun loadTestGradleProperties(): Properties {
        val properties = Properties()
        FileInputStream(File(testProjectDir, "gradle.properties")).use { stream ->
            properties.load(stream)
        }
        return properties
    }

    internal fun logExecResult(result: ExecutionResult) {
        println("***********START***********")
        println("Params: ${result.params.joinToString(" ")}")
        println("Working dir: ${result.workingDir.absolutePath}")

        if (result.output.isNotEmpty()) {
            println(result.output)
        }
        if (result.error.isNotEmpty()) {
            System.err.println(result.error)
        }
        println("***********END***********")
    }
}
