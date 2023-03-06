---
sidebar_position: 1
title: KMMBridge Intro 
---

# KMMBridge for Teams

KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built KMM (Kotlin Multiplatform Mobile) Xcode Framework binaries.

The modules can be published to various back ends, public or private, and (currently) consumed by either CocoaPods or Swift Package Manager.

## Who is this for?

Different types of teams and different types of projects use Kotlin Multiplatform in different ways. Native mobile dev teams often want to start by including a prebuilt Xcode Framework in the iOS build rather than having every member of the team building Kotlin locally. This is especially true when adding KMM to an existing app, and/or when the teams are larger than a few developers.

Building and publishing binary Xcode Frameworks from Kotlin is certainly possible, but not easily supported "out of the box". Where those binaries are published, and how they are included in the iOS build, also varies. Most teams we have talked to go through the same process getting started. They first need to build some kind of publishing architecture, which is non-trivial, and make a lot of the same mistakes along the way.

For more context, see Nate Ebel’s talk from Droidcon NYC 2022: [Adopting Kotlin Multiplatform in Brownfield Applications](https://www.droidcon.com/2022/09/29/adopting-kotlin-multiplatform-in-brownfield-applications/). It's a very good overview of the startup issues teams face.

<genericCta message="We build solutions that get teams started smoothly with Kotlin Multiplatform Mobile and ensure their success in production. Join our community to learn how your peers are adopting KMM."
link="https://form.typeform.com/to/MJTpmm#hubspot_utk=xxxxx&hubspot_page_name=xxxxx&hubspot_page_url=xxxxx" buttonMessage="Subscribe!"/>

## Simple Getting Started Setup

If you are using GitHub for source control, and are OK with using GitHub Actions to build and GitHub releases for published artifacts, we have a simple setup flow you can use. It is the easiest default to start from.

See [DEFAULT_GITHUB_FLOW](DEFAULT_GITHUB_FLOW.md) for setup instructions.

## KMMBridge Kick Start

The quickest way to get up and running is to use our template "Kick Start" project. See [KMMBridgeKickStart](https://github.com/touchlab/KMMBridgeKickStart)

## Sample Projects

* [KMMBridgeSampleKotlin](https://github.com/touchlab/KMMBridgeSampleKotlin) - Shared Kotlin code
* [KMMBridgeSampleSpm](https://github.com/touchlab/KMMBridgeSampleSpm) - Xcode example with SPM
* [KMMBridgeSampleCocoaPods](https://github.com/touchlab/KMMBridgeSampleCocoaPods) - Xcode example with CocoaPods
* [PublicPodspecs](https://github.com/touchlab/PublicPodspecs) - Public CocoaPods podspec repo

## Basic Flow

The basic concept is that after making some changes to Kotlin code, you'll want to publish an updated iOS Framework that Xcode can grab and use. Most native mobile projects exist as 2 separate repos: one for Android and one for iOS. To add some shared Kotlin code, you can either add a KMM module to the Android project, or create a separte repo just for the shared Kotlin code. In either configuration, you publish the iOS Framework and integrate it into the Xcode project.

Changes are made and tested to the shared Kotlin, then pushed to source control. When that happens, you can run CI to publish a new build. Doing that will:

* Create a new version number
* Publish the Xcode Framework zip
* Generate `Package.swift` file and/or a CocoaPods podspec file

The iOS app can then include these frameworks through SPM or CocoaPods.

![kmmbridge_diagram2](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-07_09-13-kmmbridge_diagram2.png)

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
    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME}}"
}
```

At the top level in the same file, put the `kmmbridge` configuration:

```kotlin
kmmbridge {
    mavenPublishArtifacts()
    githubReleaseVersions()
    spm()
    cocoapods("git@github.com:touchlab/PublicPodSpecs.git")
    versionPrefix.set("0.3")
    //etc
}
```

## Detailed Configuration Documentation

For non-GitHub installations, other artifact locations, etc, see [CONFIGURATION_OVERVIEW](general/CONFIGURATION_OVERVIEW.md).

## Local Kotlin Testing

KMMBridge also provides some support for locally building and testing Kotlin-generated Frameworks directly in your Xcode project. You can "flip a switch" to run your Xcode project against Kotlin locally, to test your changes. This process differs depending on if you're using [CocoaPods](cocoapods/02_IOS_LOCAL_DEV_COCOAPODS.md) and [SPM](spm/02_IOS_LOCAL_DEV_SPM.md).

## Project Status

This project is new. The code was extracted from a longer running internal effort, which went through a lot of experimentation
and code written for specific use cases. Please let us know if you run into issues or find setup confusing.

### Some notes

* Groovy was not a focus during dev. All testing effort has been with Kotlin Gradle scripts. See [GROOVY_BUILD_SCRIPTS](general/GROOVY_BUILD_SCRIPTS.md) for suggestions and samples if needed.

## See Also

[TROUBLESHOOTING](TROUBLESHOOTING.md)


