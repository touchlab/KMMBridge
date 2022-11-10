# Configuration Overview

## Artifact Managers

Artifact Managers handle uploading the binary and generate the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:

### [Maven Repository Artifacts](../artifacts/MAVEN_REPO_ARTIFACTS)

This is the simplest to configure and currently the recommended best way to publish your binaries. If you are using GitHub, you can publish to Github Packages easily with our integration tools. For a complete introduction and overview of how to configure your project with this manager, see [Default GitHub Flow](../DEFAULT_GITHUB_FLOW.md).

### [S3 Public Artifacts](../artifacts/S3_PUBLIC_ARTIFACTS.md)

This implementation will publish to your S3 bucket. By default it will set the access to public. You can also have access be private and controlled by AWS access, but there is no easy way to give Xcode access to your binaries. You'll need to configure machine access to these buckets (this is more common in larger enterprise environment).

The S3 artifact manager is really the starting point for teams that need a more custom implementation (Azure, Google Cloud, private hosting, etc).

###  [GitHub Release Artifacts](../artifacts/GITHUB_RELEASE_ARTIFACTS.md)

This implementation will create a release in GitHub that exists only to upload artifacts. It was the first GitHub implementation that we had working while finishing the maven publishing. This artifact manager will likely be deprecated in future releases, unless we get sufficient feedback that suggests we keep it.

## Dependency Managers

Dependency managers handle integration with CocoaPods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:

* CocoapodsDependencyManager: [IOS_COCOAPODS](../cocoapods/01_IOS_COCOAPODS.md) 
* SpmDependencyManager: [IOS_SPM](../spm/01_IOS_SPM.md)

## Version Managers

KMMBridge is designed to allow you to publish updates to your iOS Kotlin code as development progresses. That often means several minor versions in between major ones. To support this, we've added an interface called `VersionManager` that handles this for you.

There are two basic options: automatically incrementing version managers, and exact Gradle version. The automatically incrementing versions exist for teams that will publish more frequent iOS builds while dev is ongoing. Each publish will automatically increment a minor version. If you plan to directly manage versions for the whole project, you can just tell KMMBridge to use the Gradle version.

### Incrementing Version Managers

All incrementing version managers need a base version, which should be the first two numbers of a three number semantic versioning scheme. CocoaPods, and especially SPM, do not work well with less structured version strings.

You can either set your Gradle version to something like "0.3", or explicitly set `versionPrefix` in the `kmmbridge` config block. `versionPrefix` will override whatever your Gradle version is set to.

When you publish new iOS builds, you get release a history like the following:

* 0.3.0
* 0.3.1
* 0.3.2
* etc...

We don't set a version manager by default, so you'll need to select something. The version managers provided with KMMBridge are the following:

### GitTagVersionManager

Versions with Git tags. Increments by one each time.

```kotlin
kmmbridge {
  gitTagVersions()
}
```

### GithubReleaseVersionManager

Similar to GitTagVersionManager, but calls the GitHub api to create a Git release. Only usable with GitHub, but prefferred to GitTagVersionManager if you are using GitHub.

```kotlin
kmmbridge {
  githubReleaseVersions()
}
```

### TimestampVersionManager

Use the current timestamp, which should mean increasing values (unless various server timestamps are extremenly off).

```kotlin
kmmbridge {
  timestampVersions()
}
```

Generally speaking, you should use GitTagVersionManager over TimestampVersionManager, but both will work fine.

### ManualVersionManager

No automatic versioning. This version manager will get the version from Gradle (or the Jetbrains CocoaPods config, if set separately there). You'll need to make sure to increment this version if you're publishing a new version.

(Note: [KMMBridge/issues/58](https://github.com/touchlab/KMMBridge/issues/58) is pending. CocoaPods may overwrite rather than fail when publishing)

```kotlin
kmmbridge {
  manualVersions()
}
```