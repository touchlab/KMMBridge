plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("java-gradle-plugin")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("faktory-kmmbridge-plugin") {
            id = "co.touchlab.faktory.kmmbridge"
            implementationClass = "co.touchlab.faktory.KMMBridgePlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(gradleApi())
    implementation(kotlin("gradle-plugin"))
    implementation(kotlin("compiler-embeddable"))

    implementation("javax.json:javax.json-api:1.1.4")
    implementation("org.glassfish:javax.json:1.1.4")
    implementation("commons-codec:commons-codec:1.15")
    implementation("software.amazon.awssdk:s3:2.17.276")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.9.0")
    testImplementation(kotlin("test"))
}

val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = VERSION_NAME

apply(plugin = "com.vanniktech.maven.publish")
