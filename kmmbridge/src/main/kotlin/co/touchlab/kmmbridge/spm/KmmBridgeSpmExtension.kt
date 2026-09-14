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
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty

/**
 * Extension for configuring SPM Package.swift generation at the root project level.
 *
 * Example usage:
 * ```kotlin
 * // Root build.gradle.kts
 * plugins {
 *     id("co.touchlab.kmmbridge.spm")
 * }
 *
 * kmmBridgeSpm {
 *     packageName = "MyAwesomeSDK"  // Optional: defaults to rootProject.name
 * }
 * ```
 */
interface KmmBridgeSpmExtension {
    /**
     * The name of the SPM package. Defaults to the root project name.
     */
    val packageName: Property<String>

    /**
     * The Swift tools version for Package.swift. Defaults to the maximum version
     * specified by any module, or "5.9" if none specified.
     */
    val swiftToolsVersion: Property<String>

    /**
     * Output path for Package.swift. Defaults to the root project directory.
     */
    val outputDirectory: Property<File>

    /**
     * Explicitly include only these module paths. If empty, all KMMBridge modules are included.
     * Example: setOf(":openai-client:openai-client-darwin", ":anthropic-client:anthropic-client-darwin")
     */
    val includeModules: SetProperty<String>

    /**
     * Exclude these module paths from Package.swift generation.
     * Example: setOf(":legacy-module")
     */
    val excludeModules: SetProperty<String>
}
