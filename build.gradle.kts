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
plugins {
    alias(libs.plugins.kotlin) apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0" apply false
    alias(libs.plugins.maven.publish) apply false
}

subprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension>()?.apply {
        jvmToolchain(17)
    }

    val GROUP: String by project
    val VERSION_NAME: String by project

    group = GROUP
    version = VERSION_NAME

    afterEvaluate {
        tasks.getByName<Test>("test") {
            useJUnitPlatform()
        }
    }
}