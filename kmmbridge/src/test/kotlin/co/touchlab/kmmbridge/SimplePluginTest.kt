package co.touchlab.kmmbridge

import org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.FAILED
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals


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
    fun runBasicBuild(){
        val result = ProcessHelper.runSh("./gradlew linkDebugFrameworkIosSimulatorArm64", workingDir = testProjectDir)
        assertEquals(result.status, 0)
    }

    @Test
    fun runSpmDevBuild(){
        val result = ProcessHelper.runSh("./gradlew spmDevBuild --stacktrace", workingDir = testProjectDir)
        println(result.output)
        println(result.error)
        assertEquals(result.status, 0)
    }

    @Test
    fun runSpmDevBuildNoGit(){
        ProcessHelper.runSh("rm -rdf .git", workingDir = testProjectDir)
        val result = ProcessHelper.runSh("./gradlew spmDevBuild --stacktrace", workingDir = testProjectDir)
        println(result.output)
        println(result.error)
        assertEquals(result.status, 0)
    }
/*

 */
//    @Test
    fun buildBasicSample() {
//        val buildFileContent = """
//         plugins {
//            id("org.example.hello-world")
//         }
//         configure<org.example.HelloWorldExtension>{
//            message = "Hello World!"
//         }
//      """.trimIndent()

//        buildFile.writeText(buildFileContent)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("linkDebugFrameworkIosSimulatorArm64")
            .withPluginClasspath()
            .forwardOutput()
            .build()

//        assertTrue(result.output.contains("Hello World!"))
        assertEquals(SUCCESS, result.task(":allshared:linkDebugFrameworkIosSimulatorArm64")?.outcome ?: FAILED)
    }
}