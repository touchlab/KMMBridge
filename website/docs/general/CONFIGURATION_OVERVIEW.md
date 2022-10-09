# Configuration Overview

## Components

### Artifact Managers

Artifact Manager handle uploading the binary and generating the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:

* GithubReleaseArtifactManager:  [GITHUB_RELEASE_ARTIFACTS](../artifacts/GITHUB_RELEASE_ARTIFACTS.md) (recommended if using Github)
* AwsS3PublicArtifactManager:  [S3_PUBLIC_ARTIFACTS](../artifacts/S3_PUBLIC_ARTIFACTS.md)
* FaktoryServerArtifactManager:  [FAKTORY_ARTIFACTS](../artifacts/FAKTORY_ARTIFACTS.md) (private testing currently)

Standard Gradle/Maven repo publishing is coming soon. This should allow a wider range of existing backend repository hosts.

### Dependency Managers

Dependency managers handle integration with Cocoapods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:

* CocoapodsDependencyManager: [IOS_COCOAPODS.md](../cocoapods/01_IOS_COCOAPODS.md) 
* SpmDependencyManager: [IOS_SPM](../spm/01_IOS_SPM.md)

### Version Managers

KMMBridge is designed to allow you to publish updates to your iOS Kotlin code as development progresses. That often means several minor versions in between major ones. To support this, we've added an interface called `VersionManager` that handles this for you.

The basic process involves setting the `versionPrefix` property to, say, "0.3", and setting the version manager to something that increments automatically, then as you publish you get release history like the following:

* 0.3.0
* 0.3.1
* 0.3.2
* etc...

We (currently) don't set a version manager by default, so you'll need to select something. The version managers provided with KMMBridge are the following:

#### GitTagVersionManager

Versions with git tags. Increments by one each time.

```kotlin
kmmbridge {
  gitTagVersions()
}
```

#### GithubReleaseVersionManager

Similar to GitTagVersionManager, but calls the Github api to create a git release. Only usable with Github, but prefferred to GitTagVersionManager if you are using Github.

```kotlin
kmmbridge {
  githubReleaseVersions()
}
```

#### TimestampVersionManager

Use the current timestamp, which should mean increasing values (unless various server timestamps are extremenly off).

```kotlin
kmmbridge {
  timestampVersions()
}
```

Generally speaking, you should use GitTagVersionManager over TimestampVersionManager, but both will work fine.

#### ManualVersionManager

No automatic versioning. This version manager will get the version from Gradle (or the Jetbrains Cocoapods config, if set separately there). You'll need to make sure to increment this version if you're publishing a new version.

(Note: KMMBridge/issues/58 is pending. Cocoapods may overwrite rather than fail when publishing)

```kotlin
kmmbridge {
  manualVersions()
}
```