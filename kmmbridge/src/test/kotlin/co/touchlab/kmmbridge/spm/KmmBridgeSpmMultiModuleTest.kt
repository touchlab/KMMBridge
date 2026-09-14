package co.touchlab.kmmbridge.spm

import co.touchlab.kmmbridge.BasePluginTest
import co.touchlab.kmmbridge.ProcessHelper
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

/**
 * Exercises the root-level [KmmBridgeSpmPlugin] aggregator against a real multi-module build
 * (module-a, module-b, module-excluded), verifying module discovery/exclusion and generated
 * Package.swift content flow entirely through [KmmBridgeSpmRegistry] rather than the root project
 * touching subprojects' live `Project`/`Task` objects.
 */
class KmmBridgeSpmMultiModuleTest : BasePluginTest() {
    override fun testProjectPath(): String = "test-projects/multi-module"

    private fun packageSwiftFile(): File = File(testProjectDir, "Package.swift")

    @Test
    fun generatePackageSwift() {
        val result =
            ProcessHelper.runSh(
                "./gradlew generatePackageSwift -PENABLE_PUBLISHING=true --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)
        assertEquals(0, result.status)

        val packageSwift = packageSwiftFile().readText()
        assertTrue(packageSwift.contains("ModuleA"), "Package.swift should include module-a")
        assertTrue(packageSwift.contains("ModuleB"), "Package.swift should include module-b")
        assertFalse(packageSwift.contains("ModuleExcluded"), "Package.swift should not include the excluded module")
    }

    @Test
    fun spmDevBuildAll() {
        val result =
            ProcessHelper.runSh(
                "./gradlew spmDevBuildAll --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)
        assertEquals(0, result.status)

        val packageSwift = packageSwiftFile().readText()
        assertTrue(packageSwift.contains("module-a/build/XCFrameworks/debug/ModuleA.xcframework"))
        assertTrue(packageSwift.contains("module-b/build/XCFrameworks/debug/ModuleB.xcframework"))
        assertFalse(packageSwift.contains("ModuleExcluded"))
    }

    @Test
    fun kmmBridgePublishAll() {
        val result =
            ProcessHelper.runSh(
                "./gradlew kmmBridgePublishAll -PENABLE_PUBLISHING=true --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)
        assertEquals(0, result.status)

        // The root aggregator is applied, so each module's own updatePackageSwift must defer to it
        // rather than racing to write its own single-module Package.swift to the same file.
        assertTrue((result.output + result.error).contains("Skipping updatePackageSwift"))

        val packageSwift = packageSwiftFile().readText()
        assertTrue(packageSwift.contains("ModuleA"))
        assertTrue(packageSwift.contains("ModuleB"))
        assertFalse(packageSwift.contains("ModuleExcluded"))
    }

    /**
     * Regression test: if a module is registered but its metadata was never actually written (e.g.
     * it hasn't been published in this run), `generatePackageSwift` must fail loudly instead of
     * silently emitting an incomplete Package.swift missing that module.
     */
    @Test
    fun generatePackageSwiftFailsOnPartialMetadata() {
        val result =
            ProcessHelper.runSh(
                "./gradlew generatePackageSwift -PENABLE_PUBLISHING=true -x :module-b:writeSpmMetadata --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(result)
        assertEquals(1, result.status)
        assertTrue((result.output + result.error).contains("Missing or unreadable SPM metadata for module(s): ModuleB"))
    }

    /**
     * Regression test: once the root aggregator is applied, a module's own `spmDevBuild` must
     * defer to `spmDevBuildAll` rather than overwriting the aggregated Package.swift. This exercises
     * [KmmBridgeSpmRegistry.isRootApplied], read at task-execution time rather than by the module
     * reaching into the root project's live extension.
     */
    @Test
    fun perModuleSpmDevBuildSkipsWhenRootAggregatorApplied() {
        val aggregate =
            ProcessHelper.runSh(
                "./gradlew spmDevBuildAll --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(aggregate)
        assertEquals(0, aggregate.status)
        val aggregatedContent = packageSwiftFile().readText()

        val perModule =
            ProcessHelper.runSh(
                "./gradlew :module-a:spmDevBuild --stacktrace",
                workingDir = testProjectDir,
            )
        logExecResult(perModule)
        assertEquals(0, perModule.status)
        assertTrue(perModule.output.contains("Skipping spmDevBuild"))

        assertEquals(
            aggregatedContent,
            packageSwiftFile().readText(),
            "Per-module spmDevBuild must not overwrite the aggregated Package.swift",
        )
    }
}
