@file:Suppress("PropertyName")

/*
* Copyright (c) 2023 Touchlab.
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
    `kotlin-dsl`
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.vanniktech.maven.publish.base")
    id("com.gradle.plugin-publish") version "1.2.1"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    website = "https://github.com/touchlab/KMMBridge"
    vcsUrl = "https://github.com/touchlab/KMMBridge.git"
    description = "KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built KMM (Kotlin Multiplatform Mobile) Xcode Framework binaries."
    plugins {
        register("faktory-kmmbridge-plugin") {
            id = "co.touchlab.kmmbridge"
            implementationClass = "co.touchlab.faktory.KMMBridgePlugin"
            displayName = "KMMBridge for Teams"
            tags = listOf("kmm", "kotlin", "multiplatform", "mobile", "ios", "xcode", "framework", "binary", "publish", "consume")
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly(kotlin("gradle-plugin"))

    implementation("commons-codec:commons-codec:1.15")
    implementation("software.amazon.awssdk:s3:2.20.17")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(11)
}

val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = VERSION_NAME

mavenPublishing {
    publishToMavenCentral()
    val releaseSigningEnabled =
        project.properties["RELEASE_SIGNING_ENABLED"]?.toString()?.equals("false", ignoreCase = true) != true
    if (releaseSigningEnabled) signAllPublications()
    pomFromGradleProperties()
}
