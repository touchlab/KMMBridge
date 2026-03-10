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

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SpmModuleMetadataTest {

    @Test
    fun `toJson creates valid JSON`() {
        val metadata = SpmModuleMetadata(
            frameworkName = "TestFramework",
            url = "https://example.com/TestFramework.xcframework.zip",
            checksum = "abc123def456",
            platforms = mapOf("iOS" to "15", "macOS" to "12"),
            swiftToolsVersion = "5.9",
        )

        val json = metadata.toJson()

        assertTrue(json.contains("\"frameworkName\": \"TestFramework\""))
        assertTrue(json.contains("\"url\": \"https://example.com/TestFramework.xcframework.zip\""))
        assertTrue(json.contains("\"checksum\": \"abc123def456\""))
        assertTrue(json.contains("\"swiftToolsVersion\": \"5.9\""))
        assertTrue(json.contains("\"iOS\": \"15\""))
        assertTrue(json.contains("\"macOS\": \"12\""))
    }

    @Test
    fun `fromJson parses JSON correctly`() {
        val json = """
            {
                "frameworkName": "MyFramework",
                "url": "https://github.com/example/repo/releases/download/1.0.0/MyFramework.xcframework.zip",
                "checksum": "sha256checksum123",
                "platforms": {
                    "iOS": "14",
                    "macOS": "11"
                },
                "swiftToolsVersion": "5.7"
            }
        """.trimIndent()

        val metadata = SpmModuleMetadata.fromJson(json)

        assertEquals("MyFramework", metadata.frameworkName)
        assertEquals("https://github.com/example/repo/releases/download/1.0.0/MyFramework.xcframework.zip", metadata.url)
        assertEquals("sha256checksum123", metadata.checksum)
        assertEquals(mapOf("iOS" to "14", "macOS" to "11"), metadata.platforms)
        assertEquals("5.7", metadata.swiftToolsVersion)
    }

    @Test
    fun `roundtrip serialization works`() {
        val original = SpmModuleMetadata(
            frameworkName = "RoundtripTest",
            url = "https://example.com/test.zip",
            checksum = "checksum123",
            platforms = mapOf("iOS" to "15", "macOS" to "12", "tvOS" to "15"),
            swiftToolsVersion = "5.9",
        )

        val json = original.toJson()
        val restored = SpmModuleMetadata.fromJson(json)

        assertEquals(original, restored)
    }

    @Test
    fun `writeToFile and fromFile work correctly`() {
        val tempFile = File.createTempFile("test-metadata", ".json")
        tempFile.deleteOnExit()

        val metadata = SpmModuleMetadata(
            frameworkName = "FileTest",
            url = "https://example.com/file-test.zip",
            checksum = "file-checksum",
            platforms = mapOf("iOS" to "16"),
            swiftToolsVersion = "5.8",
        )

        metadata.writeToFile(tempFile)

        assertTrue(tempFile.exists())
        assertTrue(tempFile.length() > 0)

        val restored = SpmModuleMetadata.fromFile(tempFile)
        assertEquals(metadata, restored)
    }

    @Test
    fun `empty platforms map is handled`() {
        val metadata = SpmModuleMetadata(
            frameworkName = "NoPlatforms",
            url = "https://example.com/test.zip",
            checksum = "checksum",
            platforms = emptyMap(),
            swiftToolsVersion = "5.9",
        )

        val json = metadata.toJson()
        val restored = SpmModuleMetadata.fromJson(json)

        assertEquals(metadata, restored)
        assertTrue(restored.platforms.isEmpty())
    }
}
