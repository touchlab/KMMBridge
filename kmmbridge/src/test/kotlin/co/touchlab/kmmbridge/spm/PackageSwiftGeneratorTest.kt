/*
 * Copyright (c) 2024 Touchlab.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package co.touchlab.kmmbridge.spm

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PackageSwiftGeneratorTest {

    private val generator = PackageSwiftGenerator()

    @Test
    fun `generates Package swift for single module`() {
        val modules = listOf(
            SpmModuleMetadata(
                frameworkName = "MyFramework",
                url = "https://example.com/MyFramework.xcframework.zip",
                checksum = "abc123",
                platforms = mapOf("iOS" to "15"),
                swiftToolsVersion = "5.9",
            ),
        )

        val result = generator.generatePackageSwift("my-sdk", "5.9", modules)

        assertTrue(result.contains("// swift-tools-version:5.9"))
        assertTrue(result.contains("name: \"my-sdk\""))
        assertTrue(result.contains(".library(name: \"MyFramework\", targets: [\"MyFramework\"])"))
        assertTrue(result.contains("name: \"MyFramework\""))
        assertTrue(result.contains("url: \"https://example.com/MyFramework.xcframework.zip\""))
        assertTrue(result.contains("checksum: \"abc123\""))
        assertTrue(result.contains(".iOS(.v15)"))
    }

    @Test
    fun `generates Package swift for multiple modules`() {
        val modules = listOf(
            SpmModuleMetadata(
                frameworkName = "OpenAIClient",
                url = "https://example.com/OpenAIClient.xcframework.zip",
                checksum = "checksum1",
                platforms = mapOf("iOS" to "15", "macOS" to "12"),
                swiftToolsVersion = "5.9",
            ),
            SpmModuleMetadata(
                frameworkName = "AnthropicClient",
                url = "https://example.com/AnthropicClient.xcframework.zip",
                checksum = "checksum2",
                platforms = mapOf("iOS" to "15", "macOS" to "12"),
                swiftToolsVersion = "5.9",
            ),
        )

        val result = generator.generatePackageSwift("openai-kotlin", "5.9", modules)

        // Check both modules are included (sorted alphabetically)
        assertTrue(result.contains(".library(name: \"AnthropicClient\", targets: [\"AnthropicClient\"])"))
        assertTrue(result.contains(".library(name: \"OpenAIClient\", targets: [\"OpenAIClient\"])"))
        assertTrue(result.contains("name: \"AnthropicClient\""))
        assertTrue(result.contains("name: \"OpenAIClient\""))

        // Check platforms
        assertTrue(result.contains(".iOS(.v15)"))
        assertTrue(result.contains(".macOS(.v12)"))
    }

    @Test
    fun `resolves maximum swift tools version`() {
        val modules = listOf(
            SpmModuleMetadata("A", "", "", emptyMap(), "5.7"),
            SpmModuleMetadata("B", "", "", emptyMap(), "5.9"),
            SpmModuleMetadata("C", "", "", emptyMap(), "5.8"),
        )

        val version = generator.resolveSwiftToolsVersion(modules, "5.5")

        assertEquals("5.9", version)
    }

    @Test
    fun `uses default swift tools version when none specified`() {
        val modules = listOf(
            SpmModuleMetadata("A", "", "", emptyMap(), ""),
            SpmModuleMetadata("B", "", "", emptyMap(), ""),
        )

        val version = generator.resolveSwiftToolsVersion(modules, "5.9")

        assertEquals("5.9", version)
    }

    @Test
    fun `resolves maximum platform versions across modules`() {
        val modules = listOf(
            SpmModuleMetadata("A", "", "", mapOf("iOS" to "14", "macOS" to "11"), "5.9"),
            SpmModuleMetadata("B", "", "", mapOf("iOS" to "15", "macOS" to "12"), "5.9"),
            SpmModuleMetadata("C", "", "", mapOf("iOS" to "13", "macOS" to "13"), "5.9"),
        )

        val platforms = generator.resolvePlatforms(modules)

        assertEquals("15", platforms["iOS"])
        assertEquals("13", platforms["macOS"])
    }

    @Test
    fun `handles modules with different platforms`() {
        val modules = listOf(
            SpmModuleMetadata("A", "", "", mapOf("iOS" to "15"), "5.9"),
            SpmModuleMetadata("B", "", "", mapOf("macOS" to "12"), "5.9"),
            SpmModuleMetadata("C", "", "", mapOf("iOS" to "14", "tvOS" to "15"), "5.9"),
        )

        val platforms = generator.resolvePlatforms(modules)

        assertEquals("15", platforms["iOS"])
        assertEquals("12", platforms["macOS"])
        assertEquals("15", platforms["tvOS"])
    }

    @Test
    fun `version comparator handles different version formats`() {
        val modules = listOf(
            SpmModuleMetadata("A", "", "", emptyMap(), "5.7"),
            SpmModuleMetadata("B", "", "", emptyMap(), "5.10"),
            SpmModuleMetadata("C", "", "", emptyMap(), "5.9"),
        )

        val version = generator.resolveSwiftToolsVersion(modules, "5.5")

        assertEquals("5.10", version) // 5.10 > 5.9 > 5.7
    }

    @Test
    fun `generated Package swift has correct structure`() {
        val modules = listOf(
            SpmModuleMetadata(
                frameworkName = "TestModule",
                url = "https://test.com/TestModule.zip",
                checksum = "test123",
                platforms = mapOf("iOS" to "15"),
                swiftToolsVersion = "5.9",
            ),
        )

        val result = generator.generatePackageSwift("test-package", "5.9", modules)

        // Verify structure order
        val toolsVersionIndex = result.indexOf("swift-tools-version")
        val importIndex = result.indexOf("import PackageDescription")
        val packageIndex = result.indexOf("let package = Package")
        val platformsIndex = result.indexOf("platforms:")
        val productsIndex = result.indexOf("products:")
        val targetsIndex = result.indexOf("targets:")

        assertTrue(toolsVersionIndex < importIndex)
        assertTrue(importIndex < packageIndex)
        assertTrue(packageIndex < platformsIndex)
        assertTrue(platformsIndex < productsIndex)
        assertTrue(productsIndex < targetsIndex)
    }
}
