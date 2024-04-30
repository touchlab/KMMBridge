---
sidebar_position: 1
title: KMMBridge Intro 
---

# KMMBridge for Teams

KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built Kotlin Multiplatform Xcode Framework binaries.

The modules can be published to various back ends, public or private, and (currently) consumed by either CocoaPods or Swift Package Manager.

## Feature Snapshot

* Creates XCFramework zip archives from your Kotlin Xcode Framework modules
* Publish those archives to various online storage locations
* Configure and publish versions for Swift Package Manager (SPM) and CocoaPods that can be used by other developers

In addition, KMMBridge provides a fairly basic local development SPM flow, along with it's SPM publishing functionality.

## Who is this for?

Anybody that needs to publish Xcode Frameworks from Kotlin for use by iOS developers. This can be for teams trying KMP that don't want to disrupt their build setups, larger teams that need to modularize, teams publishing SDKs to internal or external clients.

Anybody that needs to publish a Kotlin Xcode Framework.

<genericCta message="We build solutions that get teams started smoothly with Kotlin Multiplatform Mobile and ensure their success in production. Join our community to learn how your peers are adopting KMM."
link="https://touchlab.co/?s=shownewsletter" buttonMessage="Subscribe!"/>

## [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quick-start)

This is a post series that explains the basics, and provides a template GitHub project that you can start using right away. If you want to test out sharing KMP libraries with your team, this is the fastest way to get going.

## [What Are We Doing?](WHAT_ARE_WE_DOING.md)

It is a bit difficult to describe how to set up and deploy KMMBridge without going through the details of exactly what needs to happen to publish Xcode Framework binaries. This section walks through the parts you need to understand to figure out what KMMBridge is doing and why certains pieces work they way they do.

## [Default GitHub Workflow](DEFAULT_GITHUB_FLOW.md)

KMMBridge is designed to live in CI and your build pipeline. There is information that needs to be passed into KMMBridge, outside configuration and access needs to be granted, git operations that need to happen. There's a lot happening outside of KMMBridge itself, and the details of that configureation depend on your build environment, code and artifact hosting, etc.

Having off-the-shelf config for every possible scenario would be a challenge, and in real world production environments, we've found that there's always a lot of customization anyway.

We do provide a relatively off-the-shelf experience for repos in GitHub, using GitHub Actions and GitHub Packages. The [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quick-start) post and template project use this setup.

The [Default GitHub Workflow](DEFAULT_GITHUB_FLOW.md) will walk through the parts of the default GitHub flow. If you need to customize a KMMBridge build, this is where you should look.

:::info

The first version of KMMBridge, 0.3.x, attempted to put a lot of the CI logic inside the Gradle plugin itself, but this added to the complexity and reduced flexibility. Version 0.5+ is more streamlined. If you are porting your existing KMMBridge project, see [Porting from KMMBridge 0.3.x](PORTING_0.3.x) for an overview of what's changed and how to approach porting your build.

:::

:::caution

If you were using the original version of KMMBridge, the updated version has a different plugin id.

```kotlin
id("co.touchlab.kmmbridge")
```

:::

## Types of Kotlin Repos

Teams using KMP have a variety of repo configurations. They include

* Monorepo
* Android/Kotlin with a shared KMP module (or modules)
* Separate repo with the shared KMP that is published to both iOS and Android

KMMBridge can be used in any of these repo configurations, depending on your needs.

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

Then add the plugin to the module that is actually building the Xcode Framework. In the `build.gradle.kts` file:

```kotlin
plugins {
    kotlin("multiplatform")
    id("co.touchlab.kmmbridge") version "{{VERSION_NAME}}"
}
```

At the top level in the same file, put the `kmmbridge` configuration:

```kotlin
kmmbridge {
    mavenPublishArtifacts()
    spm()
    cocoapods("git@github.com:touchlab/PublicPodSpecs.git")
    //etc
}
```

## Detailed Configuration Documentation

For non-GitHub installations, other artifact locations, etc, see [CONFIGURATION_OVERVIEW](general/CONFIGURATION_OVERVIEW.md).

## Local Kotlin Testing

Many teams, especially for existing apps, introduce KMP to their environment by publishing an Xcode Framework that their iOS dev team consumes like any other dependency. That makes integration easy, but creates several problems with development, testing, and simply getting the iOS team to start editing the shared code.

While introducing KMP this way is often the best option, we feel strongly that being able to locally build and test the shared Kotlin directly in the target apps is a critical feature.

KMMBridge provides support for locally editing the published Kotlin. More info here: [CocoaPods](cocoapods/02_IOS_LOCAL_DEV_COCOAPODS.md) and [SPM](spm/02_IOS_LOCAL_DEV_SPM.md).

### Some notes

* Groovy was not a focus during dev. All testing effort has been with Kotlin Gradle scripts. See [GROOVY_BUILD_SCRIPTS](general/GROOVY_BUILD_SCRIPTS.md) for suggestions and samples if needed.

## See Also

[TROUBLESHOOTING](TROUBLESHOOTING.md)


