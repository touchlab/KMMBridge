---
sidebar_position: 100
title: Porting from KMMBridge 0.3.x 
---

## Overview

The basic operation of KMMBridge remains the same, but how the plugin is run has changed somewhat. Here's the list of important changes:

### Plugin Id

The plugin id has changed, so replace:

```kotlin
id("co.touchlab.faktory.kmmbridge")
```

with

```kotlin
id("co.touchlab.kmmbridge")
```

### Removed Features

The removed features focus largely on version management. Versions were previously managed inside the plugin, but that was problematic [see Design Changes](#design-changes).

### versionPrefix

An explicit setting of `versionPrefix` in the plugin config should be removed. By default, `version` is read from Gradle, and that is the version published. There is no concept of `versionPrefix` in the current build.

### VersionWriter and VersionManager

The original KMMBridge had `VersionManager` and `VersionWriter`. These interfaces were responsible for finding and incrementing versions, and for "writing" them. Since KMMBridge is no longer managing versions, these are redundant. As mentioned above, KMMBridge was managing a lot of git commands internally, and all of that was driven by version management. Moving that to CI makes KMMBridge much simpler, and is much easier to reason about and customize.

As a result, the following functions no longer exist in the KMMBridge config block:

* `gitTagVersions()`
* `githubReleaseVersions()`
* `githubEnterpriseReleaseVersions()`
* `noGitOperations()`
* `timestampVersions()`

`VersionManager` still exists, but it's role has been reduced. KMMBridge will take the `version` property from Gradle and use that for it's version in publishing. If you would like some other method of calculating a version string, implement `VersionManager` and set it with the following:

```kotlin
object MyVersionManager : VersionManager {
    override fun getVersion(project: Project): String = "0.1.${System.currentTimeMillis()}"
}

kmmbridge {
    // Etc
    versionManager.set(MyVersionManager)
}
```

## Migrating

If you depend on the removed features and need help migrating, please [reach out](https://touchlab.co/keepintouch).

---

## Design Changes

The original KMMBridge plugin design was focused on externally publishing an iOS Xcode Framework from a Kotlin project. The idea was that a team would likely carve out a shared module from their Android app project, and want to make that available to iOS devs. We wanted to support automatic versioning of artifacts for iOS, and assumed that this would be necessarily separate from the Android project versioning.

For teams that wanted to control all versioning, we allowed that through plugins, but otherwise, the default behavior of KMMBridge was to manage dev build versions in the plugin itself.

We've had a year of feedback and time to think through this approach. Here are the issues:

* On a very basic level, it's just confusing. You have a version in your Gradle project, but the iOS build "version" is that with a suffix. There are various mechanisms to auto-calculate the next version (arguably too many options). The scheme took a lot of explaining.
* For teams that want a new, separate repo to publish builds, the default behavior of KMMBridge was broken for the Android builds. Our versioning mechanisms only afftect iOS, which is external to Gradle versioning. For Android to publish, you need to properly set the Gradle version.
* SPM depends on git project structure and various conventions to function properly. Notably, git tags. To properly work with SPM, KMMBridge needed to read and manage git tags. This meant that KMMBridge was reading, and updating, git. That also requires pushing changes. Even early on, the team was conflicted about this state of affairs. This new version completely gets out of git management and lets CI handle it, which works perfectly fine, and makes much more sense.

Essentially, KMMBridge was doing too much. The changes make KMMBridge much simpler, and understanding what KMMBridge is doing should be a lot easier when looking at the GitHub Actions samples and shared workflows.

One negative is with other CI solutions. Moving some logic out of Gradle means that logic now lives in our default CI: GitHub Actions. That logic will need to be replicated in those CI tools. However, out GitHub Actions logic is all publicly available, and assuming your CI of choice has reasonable equivalents, porting should be straightforward.



