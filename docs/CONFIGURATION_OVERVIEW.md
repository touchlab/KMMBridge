# Configuration Overview

## Components

### Artifact Managers

Artifact Manager handle uploading the binary and generating the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:

* GithubReleaseArtifactManager:  [GITHUB_RELEASE_ARTIFACTS](GITHUB_RELEASE_ARTIFACTS.md) (recommended if using Github)
* AwsS3PublicArtifactManager:  [S3_PUBLIC_ARTIFACTS](S3_PUBLIC_ARTIFACTS.md)
* FaktoryServerArtifactManager:  [FAKTORY_ARTIFACTS](FAKTORY_ARTIFACTS.md) (private testing currently)

Standard Gradle/Maven repo publishing is coming soon. This should allow a wider range of existing backend repository hosts.

### Dependency Managers

Dependency managers handle integration with Cocoapods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:

* CocoapodsDependencyManager: [IOS_COCOAPODS.md](IOS_COCOAPODS.md) 
* SpmDependencyManager: [IOS_SPM](IOS_SPM.md)

### Version Managers

By default, when you publish a build with KMM Bridge, it'll get the version from Gradle, so you'll need to update that version manually every time you want to publish a new version. However, for ongoing development, incrementing minor versions automatically can be more productive.

The basic process involves setting the `versionPrefix` property to, say, "0.3", and setting the version manager to something that increments automatically, then as you publish you get release history like the following:

* 0.3.0
* 0.3.1
* 0.3.2
* etc...

The version managers provided with KMM Bridge are the following:

#### ManualVersionManager

 Default. No automatic versioning. Just use Gradle's version.

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