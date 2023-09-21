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
link="https://form.typeform.com/to/MJTpmm#hubspot_utk=xxxxx&hubspot_page_name=xxxxx&hubspot_page_url=xxxxx" buttonMessage="Subscribe!"/>

## [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quickstart-updates)

This is a post series that explains the basics, and provides a template GitHub project that you can start using right away. If you want to test out sharing KMP libraries with your team, this is the fastest way to get going.

## [Default GitHub Workflow](DEFAULT_GITHUB_FLOW.md)

KMMBridge is designed to publish binary builds. That involves setting up repos, access, git versioning, etc. There's a lot to set up if you do it from scratch. We support a configuration that uses GitHub as the repo, GitHub Actions to build, and GitHub Packages as the publication target. The setup is fairly opinionated, but easy to use if you're OK with that stack.

For most teams, if you're using GitHub, this is a good place to start.

If you need to customize your builds, the Default GitHub Workflow is a great reference for how you should set up your build.

:::info

The first version of KMMBridge that was released last year had a significant amount of logic included that could arguably live in CI and other configuration. This current version has removed a lot of that logic, so that KMMBridge can focus on it's core features. If you are porting your existing KMMBridge project, see [Porting from KMMBridge 0.3.x](PORTING_0.3.x) for an overview of what's changed and how to approach porting your build.

:::

## Basic Flow

### Build

Your shared Kotlin code exists in some git repo, and after you've made and tested some changes, you want to publish an Xcode Framework build. Assuming your KMMBridge configuration is set up and you're running on some form of CI, you run a publish operation, and then your new Xcode Framework is available!

### Deploy

When that build runs, KMMBridge will create a zip file with release-build XCFramework instances for each configured architecture in your module. That zip file will be pushed to whatever backend(s) you configure.

By default, KMMBridge provides a maven repo adapter that lets you publish to many maven repositories using a custom build target. Working, tested maven repos include GitHub Packages, Artifactory, JetBrains space. Any "standard" maven repo should work.

KMMBridge also provides a basic AWS S3 target which will push to an S3 bucket. Custom implementations can be written as an alternative.

### SPM and/or CocoaPods

Almost all iOS teams use SPM or CocoaPods for dependency management. KMMBridge lets you use one or both.

SPM relies on a `Package.swift` file living in your repo root to point at the binary, and git tags for versions. While your config needs to provide the version, KMMBridge generates the `Package.swift` for the published XCFramework zip archive. We provide supporting tools to manage version incrementing and git operations for teams with fairly standard needs.

CocoaPods also uses git for dependency versions, but uses a separate repo and custom process. KMMBridge can publish to both private CocoaPods repo and the "official" trunk repo for the CocoaPods main registry.

You can publish to either SPK or CocoaPods, or both.

### After Publishing

The iOS app can then include these frameworks through SPM or CocoaPods.

![kmmbridge_diagram2](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-07_09-13-kmmbridge_diagram2.png)

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
    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME}}"
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


