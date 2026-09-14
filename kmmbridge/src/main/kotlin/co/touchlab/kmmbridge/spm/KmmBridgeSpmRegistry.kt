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
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters

/**
 * Build-scoped registry that lets KMMBridge SPM modules and the root aggregator plugin exchange
 * data without ever touching each other's live [Project]/[org.gradle.api.Task] objects, so the
 * plugin stays compatible with Gradle's Project Isolation feature.
 *
 * Modules write their own [ModuleRegistration] about themselves at their own configuration time
 * (always safe). The root plugin only reads this data from task-execution-time actions, never at
 * configuration time - task execution is always ordered after every project has finished
 * configuring, so there is no dependency on project evaluation order.
 */
internal abstract class KmmBridgeSpmRegistry : BuildService<BuildServiceParameters.None> {
    data class ModuleRegistration(
        val path: String,
        val frameworkName: String,
        val metadataFile: File,
        val debugXCFrameworkDir: File,
        val debugAssembleTaskName: String,
        val platforms: Map<String, String>,
        val swiftToolsVersion: String,
    )

    private val registrations = ConcurrentHashMap<String, ModuleRegistration>()
    private val rootApplied = AtomicBoolean(false)

    fun registerModule(registration: ModuleRegistration) {
        registrations[registration.path] = registration
    }

    fun markRootApplied() {
        rootApplied.set(true)
    }

    fun isRootApplied(): Boolean = rootApplied.get()

    fun modules(): List<ModuleRegistration> = registrations.values.toList()
}

internal fun Project.kmmBridgeSpmRegistry(): Provider<KmmBridgeSpmRegistry> =
    gradle.sharedServices.registerIfAbsent("kmmBridgeSpmRegistry", KmmBridgeSpmRegistry::class.java) {}
