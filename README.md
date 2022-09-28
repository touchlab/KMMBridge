# Faktory: KMM Bridge

Different types of teams and different types of projects use Kotlin Multiplatform in different ways. Native mobile dev teams often want to start by including a prebuilt Xcode Framework in the iOS build rather than having every member of the team building Kotlin locally. This is especially true when adding KMM to an existing app, and/or when the teams are larger than 2-3 developers.

Building and publishing binary Xcode Frameworks from Kotlin is certainly possible, but not easily supported "out of the box". Where those binaries are published, and how they are included in the iOS build, also varies. Most teams we have talked to go through the same process getting started. They build this piece of infrastructure, and often go through the same stages (and make the same mistakes).

KMM Bridge is a set of tooling that facilitates publishing and consuming pre-built KMM Xcode Framework binaries.

## Configuration

The plugin is currently published to the maven central snapshots repo. To include it, add the snapshots repo to `pluginManagement` or the `buildscript` block:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}
```

Then add the plugin to the module, in the `build.gradle.kts` file, that is building the Xcode Framework:

```kotlin
plugins {
    kotlin("multiplatform")
    id("co.touchlab.faktory.kmmbridge") version "0.1.2-SNAPSHOT"
}
```

The various configuration arguments go in the `kmmbridge` extension block:

```kotlin
kmmbridge {
    faktoryReadKey.set("1EE4B4A7CFEF438A8C0DAF8981")
    spm("..")
    cocoapods("git@github.com:touchlab/PodSpecs.git")
    //etc
}
```

## Local Development

For faktory server hosting, there's a local dev doc that needs an update...
