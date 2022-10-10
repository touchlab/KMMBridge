---
sidebar_position: 5
title: iOS Dev Setup
---

# iOS Dev Setup

To use the published Xcode Framework, you'll need to integrate it into your Xcode project. You'll also need to understand how to add authentication information, if required by your artifact storage.

For developers editing Kotlin, you will want to test locally-built Kotlin code directly in your Xcode project from time to time. How that works differs depending on which dependency manager you use. For CocoaPods see  [IOS_LOCAL_DEV_COCOAPODS](cocoapods/02_IOS_LOCAL_DEV_COCOAPODS.md). For SPM see  [IOS_LOCAL_DEV_SPM](spm/02_IOS_LOCAL_DEV_SPM.md).

## Private GitHub Releases

If you are using private GitHub artifacts, you'll need to add auth info for that to work. See [GITHUB_RELEASE_ARTIFACTS](artifacts/GITHUB_RELEASE_ARTIFACTS.md#private-repos).

*You must do this before attempting to integrate dependency managers!!!*

## Using CocoaPods

See:  [IOS_COCOAPODS](cocoapods/01_IOS_COCOAPODS.md)

## Using SPM

See: [IOS_SPM](spm/01_IOS_SPM.md)

