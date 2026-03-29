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

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

/**
 * Metadata for a single KMMBridge module that will be included in Package.swift.
 * Each module writes this metadata after uploading its XCFramework.
 */
data class SpmModuleMetadata(
    val frameworkName: String,
    val url: String,
    val checksum: String,
    val platforms: Map<String, String>, // e.g., {"iOS": "15", "macOS": "15"}
    val swiftToolsVersion: String,
) {
    companion object {
        private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

        const val METADATA_FILE_NAME = "kmmbridge-spm-metadata.json"

        fun fromJson(json: String): SpmModuleMetadata = gson.fromJson(json, SpmModuleMetadata::class.java)

        fun fromFile(file: File): SpmModuleMetadata = fromJson(file.readText())
    }

    fun toJson(): String = gson.toJson(this)

    fun writeToFile(file: File) {
        file.parentFile?.mkdirs()
        file.writeText(toJson())
    }
}
