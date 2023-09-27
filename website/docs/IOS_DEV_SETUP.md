---
sidebar_position: 5
title: iOS Dev Setup
---

# iOS Dev Setup

To use the published Xcode Framework, you'll need to integrate it into your Xcode project. You'll also need to understand how to add authentication information, if required by your dependency configuration and artifact storage.

## [Using SPM](spm/01_IOS_SPM.md)

## [Using CocoaPods](cocoapods/01_IOS_COCOAPODS.md)

To see everything working together in one place, please see [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quick-start). It will walk through configuring publication and Xcode access for the sample project.

## Private Artifacts

If you are hosting artifacts somewhere that requires authentication, you'll need to add auth info for that to work. See [the section here](DEFAULT_GITHUB_FLOW.md#ios-dev-machine-config).

*You must do this before attempting to integrate dependency managers!!!*

:::caution GitHub Packages

You can publish artifacts from public repos to GitHub Packages, but GitHub still requires a valid GitHub user to access those artifacts. If you are intending to publish a public library and do not want to force your users to configure GitHub auth, you'll need to host your library's binaries somewhere else.

:::

## Local Kotlin Editing

For developers editing Kotlin, you will want to test locally-built Kotlin code directly in your Xcode project from time to time. How that works differs depending on which dependency manager you use. For CocoaPods see  [IOS_LOCAL_DEV_COCOAPODS](cocoapods/02_IOS_LOCAL_DEV_COCOAPODS.md). For SPM see  [IOS_LOCAL_DEV_SPM](spm/02_IOS_LOCAL_DEV_SPM.md).