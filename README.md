# Faktory: KMM Bridge

KMM Bridge is a set of Gradle tooling that facilitates publishing and consuming pre-built KMM (Kotlin Multiplatform Mobile) Xcode Framework binaries.

The modules can be published to various back ends, public or private, and (currently) consumed by either Cocoapods or Swift Package Manager.

## Who is this for?

Different types of teams and different types of projects use Kotlin Multiplatform in different ways. Native mobile dev teams often want to start by including a prebuilt Xcode Framework in the iOS build rather than having every member of the team building Kotlin locally. This is especially true when adding KMM to an existing app, and/or when the teams are larger than 2-3 developers.

Building and publishing binary Xcode Frameworks from Kotlin is certainly possible, but not easily supported "out of the box". Where those binaries are published, and how they are included in the iOS build, also varies. Most teams we have talked to go through the same process getting started. They first need to build some kind of publishing architecture, which is non-trivial, and make a lot of the same mistakes along the way.

For more context, see Nate Ebel’s talk from Droidcon NYC 2022: [Adopting Kotlin Multiplatform in Brownfield Applications](https://www.droidcon.com/2022/09/29/adopting-kotlin-multiplatform-in-brownfield-applications/). It's a very good overview of the startup issues teams face.

## Configuration

The plugin is currently published to the maven central repo. If needed, makes sure to add the `mavenCentral()` repo to `pluginManagement` or the `buildscript` block:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
```

Note: If you're using a SNAPSHOT version of the plugin, add the SNAPSHOT repo as well:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}
```

Then add the plugin to the module that is actually building the Xcode Framework. In the `build.gradle.kts` file:

```kotlin
plugins {
    kotlin("multiplatform")
    id("co.touchlab.faktory.kmmbridge") version "0.1.XX"
}
```

At the top level in the same file, put the `kmmbridge` configuration:

```kotlin
kmmbridge {
    githubReleaseArtifacts()
    githubReleaseVersions()
    spm("..")
    cocoapods("git@github.com:touchlab/PodSpecs.git")
    versionPrefix.set("0.3")
    //etc
}
```

## Basic Flow

The basic concept is that after making some changes to Kotlin code, you'll want to publish an updated iOS Framework that Xcode can grab and use. Most native mobile projects exist as 2 separate repos: one for Android and one for iOS. To add some shared Kotlin code, you can either add a KMM module to the Android project, or create a separte repo just for the shared Kotlin code. In either configuration, you publish the iOS Framework and integrate it into the Xcode project.

Changes are made and tested to the shared Kotlin, then pushed to source control. When that happens, you can run CI to publish a new build. Doing that will:

* Create a new version number
* Publish the Xcode Framework zip
* Generate `Package.swift` file and/or a Cocoapods podspec file

## Simple Getting Started Setup

If you are using Github for source control, and are OK with using Github Actions to build and Github releases for published artifacts, we have a very simple setup flow you can use. It is the easiest default to start from.

See [DEFAULT_GITHUB_FLOW](docs/DEFAULT_GITHUB_FLOW.md) for setup instructions.

## Detailed Configuration Documentation

For non-Github installations, other artifact locations, etc, see [CONFIGURATION_OVERVIEW](docs/CONFIGURATION_OVERVIEW.md).

## See Also

[TROUBLESHOOTING](docs/TROUBLESHOOTING.md)