---
sidebar_position: 1
---

# Configuration Overview

## Workflow Configuration

For local development, KMMBridge configures XCFrameworks and, if you're using SPM, the SPM local dev flow. Publishing a build is really intended to happen from CI. To publish from your local machine or a custom CI flow, you'll need to be aware of some parameters that KMMBridge expects.

Generally speaking, you should refer to [the GitHub workflow](https://github.com/touchlab/KMMBridgeGithubWorkflow/blob/main/.github/workflows/faktorybuildbranches.yml) for an up-to-date example with everything you'll need.

These are some of the parameters you should be aware of:

`ENABLE_PUBLISHING` - Gradle parameter. For local dev, by default we avoid certain operations that are only necessary if you are publishing. Pass in

```shell
./gradelew -PENABLE_PUBLISHING=true [your tasks]
```

[See KMMBridgeGithubWorkflow for an example](https://github.com/touchlab/KMMBridgeGithubWorkflow/blob/b99bb8222c2c38980d18cedd175a0d0c5f88e2dc/.github/workflows/faktorybuildbranches.yml#L94)

`GITHUB_PUBLISH_TOKEN` - Gradle parameter. For GitHub releases, you'll need to pass in a GitHub token. It is available in GitHub actions.

`GITHUB_REPO` - Gradle parameter. For GitHub releases, the repo you want to point to.

## Artifact Managers

Artifact Managers handle uploading the binary and generate the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:

### [Maven Repository Artifacts](../artifacts/MAVEN_REPO_ARTIFACTS)

This is the simplest to configure and currently the recommended best way to publish your binaries. If you are using GitHub, you can publish to Github Packages easily with our integration tools. For a complete introduction and overview of how to configure your project with this manager, see [Default GitHub Flow](../DEFAULT_GITHUB_FLOW.md).

### [S3 Public Artifacts](../artifacts/S3_PUBLIC_ARTIFACTS.md)

This implementation will publish to your S3 bucket. By default it will set the access to public. You can also have access be private and controlled by AWS access, but there is no easy way to give Xcode access to your binaries. You'll need to configure machine access to these buckets (this is more common in larger enterprise environment).

The S3 artifact manager is really the starting point for teams that need a more custom implementation (Azure, Google Cloud, private hosting, etc).

:::note Github Release Artifacts
KMMBridge previously had a separate `githubReleaseArtifacts()` option, but it is no longer supported in versions 0.3.5+. The reason is it doesn't provide good enough benefits over using the [maven artifact manager](../artifacts/MAVEN_REPO_ARTIFACTS) to publish to Github packages. If using an old version of KMMBridge you can find the documentation [here](/github_release_artifacts)
:::

## Dependency Managers

Dependency managers handle integration with CocoaPods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:

* CocoapodsDependencyManager: [IOS_COCOAPODS](../cocoapods/01_IOS_COCOAPODS.md) 
* SpmDependencyManager: [IOS_SPM](../spm/01_IOS_SPM.md)

## Version Managers

KMMBridge is designed to allow you to publish updates to your iOS Kotlin code as development progresses. That often means several minor versions in between major ones. To support this, we've added an interface called `VersionManager` that handles this for you.

There are two basic options: automatically incrementing version managers, and exact Gradle version. The automatically incrementing versions exist for teams that will publish more frequent iOS builds while dev is ongoing. Each publish will automatically increment a minor version. If you plan to directly manage versions for the whole project, you can just tell KMMBridge to use the Gradle version.

>In the current version of KMMBridge the Android version is not automatically incremented while the iOS version is. If you need the versions to be aligned, you need to manage the versions manually (by using [ManualVersionManager](#ManualVersionManager)).

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

## Version Writer

You usually don't need to worry about the version writer. Depending on the version manager you're using, and if you're publishing to SPM, 
there are git operations that need to be run. If you're doing a lot of coordinated git operations in your CI workflow, you may want to control 
or disable KMMBridge git operations.

You can simply disable all git operations by calling the following in config:

```kotlin
kmmbridge { 
  noGitOperations()
}
```

Alternatively, you can set a custom version writer:

```kotlin
kmmbridge {
    versionWriter.set(myWriter)
}
```

:::caution

The git operations necessary to manage versions can be complex. Managing this on your own can introduce 
errors that are difficult to diagnose.

:::

## Naming

In Kotlin code you can set the name of your Framework as well as the name of your Podfile (when using cocoapods).

**Framework base name** controls the name that will eventually be used in the Swift `import` statement.

When using Cocoapods:

```kotlin
kotlin {
    cocoapods {
        framework {
            baseName = "FRAMEWORKNAME"
        }
    }
}
```

When using SPM you can set the framework name in the `kmmbridge` block:

```kotlin
kmmbridge {
    frameworkName.set("FRAMEWORKNAME")
}
```

Cocoapods only:
**Podfile name** controls the name that will eventually be used in the iOS Podfile, and is the name of the podspec file. This is written to the podspec in the `spec.name` field.

```kotlin
kotlin {
    cocoapods {
        name = "PODNAME"
    }
}
```

The podspec is uploaded to a folder in the podspec repo based on the KMMBridge version and the Podfile name. Therefore, the path looks like this:
```
<podspec-repo-url>/<podname>/<kmm-version>/<podname>.podspec
```

There is a danger of having naming conflicts. If two projects haven't configured their cocoapods naming, and are running with the default where they are named after the gradle module (eg "shared") then there's a possibility that both will upload a podspec file to the repository with the same name.
