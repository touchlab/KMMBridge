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
    alias(libs.plugins.allopen) apply false
    alias(libs.plugins.maven.publish) apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0" apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension>()?.apply {
        jvmToolchain(17)
    }
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.4.0")
        enableExperimentalRules.set(true)
        verbose.set(true)
        filter {
            exclude { it.file.path.contains("build/") }
        }
    }

    val GROUP: String by project
    val VERSION_NAME: String by project

    group = GROUP
    version = VERSION_NAME

    afterEvaluate {
        tasks.getByName<Test>("test") {
            useJUnitPlatform()
        }
        tasks.named("check") {
            dependsOn(tasks.getByName("ktlintCheck"))
        }
    }
}
