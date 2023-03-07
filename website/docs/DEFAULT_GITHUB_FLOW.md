---
sidebar_position: 3
title: Default GitHub Workflow
---

# Default GitHub Flow

If you are hosting your repos in GitHub and can use GitHub Actions for CI, the Default GitHub Flow is the simplest way to start publishing Kotlin builds for iOS.

:::note

This flow depends on calling our GitHub Actions workflow. Many of the features of KMMBridge assume operations that the GitHub Actions workflow is performing. SPM in particular uses git repo structure and tags to manage versions, so however you run publishing, you'll need to expect that KMMBridge is performing git operaitons automatically. If you intend to implement your own workflow, please be aware of what the plugin is doing inside git.

:::

## Overview

This flow allows you to do the following:

* Publish to public or private repos.
* Uses Maven artifacts with GitHub Packages. Standard tools!
* Upload Xcode Framework artifacts to GitHub Packages. No external storage or auth configuration is required. All auth is manages through GitHub.
* Can use either CocoaPods, SPM, or both.
* Publish iOS and (optionally) Android binaries.

## Kotlin Repo

You'll need to find or add the Kotlin Multiplatform module to publish. This module can be in the same project as your Android code (if any), or in a separate repo. In the Kotlin repo you'll add the Gradle config and CI to publish Xcode Frameworks.

## Spec Repo

If you are going to publish for CocoaPods, you'll also need a CocoaPods spec repo. This is a separate repo that CocoaPods uses to store published version information. This config is somewhat more complex, but still reasonably straightforward to configure.

## Configure The Kotlin Repo

### 1 Access the Gradle plugin

Make sure you have `mavenCental()` set up for Gradle plugins. That means adding it to the `pluginManagement` or `buildscript` blocks. In `settings.gradle.kts`:

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

### 2 Modify the Gradle Build

Find the `build.gradle.kts` file where you configure the multiplatform module you'd like to publish. Add the KMMBridge Gradle plugin:

```kotlin
plugins {
    kotlin("multiplatform")
    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME}}"
    `maven-publish`
}
```

Just FYI. You'll also need the `maven-publish` plugin.

Later in the same file, add the `kmmbridge` config block:

```kotlin
kmmbridge {
    mavenPublishArtifacts()
    githubReleaseVersions()
    spm()
    cocoapods("git@github.com:touchlab/PodSpecs.git")
    versionPrefix.set("0.3")
}
```

Finally, you'll need to add a Maven repository you can publish to, along with the necessary config. However, in our flow, assuming you're using our GitHub Actions scripts, just add this:

```kotlin
addGithubPackagesRepository()
```

When running on CI, that will add the GitHub Packages Maven repo, for this project, using the auth provided by GitHub automatically. No extra auth config!

`githubReleaseVersions()` is highly recommended. This will use GitHub releases for release tracking and incrementing. You can use a different version manager, but you need to configure one. See: [Version Managers](general/CONFIGURATION_OVERVIEW.md#version-managers) for more detail.

`spm()` only needs to be added if you want to support SPM. The parameter points at the root directory of your repo. In this case, we have the KMP module in a folder under the repo, so the repo root is one level up. This is where your `Package.swift` file should be stored.

Note: this config is only for SPM publishing. To understand how to integrate an SPM build into Xcode, and how to locally build and test Kotlin changes, see [IOS_SPM](spm/01_IOS_SPM.md).

`cocoapods("[some git repo].git")` is only needed if you plan to publish for CocoaPods. You will need the spec repo mentioned above, properly configured for deployment. See  [COCOAPODS_GITHUB_PODSPEC](cocoapods/03_COCOAPODS_GITHUB_PODSPEC.md) for details on getting the podspec repo configured.

`versionPrefix` is optional. By default, KMMBridge takes the version from Gradle and uses that as a prefix to generate the iOS published versions. Versioning strategy differs depending on how you intend to work and publish. See [Version Managers](general/CONFIGURATION_OVERVIEW#version-managers) for more detail.


### 3 Add the GitHub Actions workflow call

At the top of your project, if it does not already exist, add the folders `.github/workflows`. Add a file called `kmmbridgepnblish.yml` there, and copy the following into it.

```yaml
name: KMMBridge Publish Release
on: workflow_dispatch

jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@{{WORKFLOW_VERSION_NAME}}
```

Note: if you are using CocoaPods and a podspec repo, your file should look like the following:

```yaml
name: KMMBridge Publish Release
on: workflow_dispatch

jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@{{WORKFLOW_VERSION_NAME}}
    secrets:
      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}
```

You need to pass the ssh key configured earlier.

There are actually 2 versions of the workflow script. The other file, `faktorybuild.yml`, performs all operations in the same branch it's run on. `faktorybuildbranches.yml` creates a temporary branch to run builds. This is generally better, as the build process involves git operations which can overwrite files and possibly cause conflics.

### 4 Add and push your code

Push your changes to GitHub, and make sure they're in the default branch.

## Publish a Build!

Assuming your configuration is set up correctly, you should be able to publish your first build. In the Kotlin repo's GitHub home page, go to "Actions", select "KMMBridge Publish Release", and manually run it.

![runbuild](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-04_21-14-runbuild.png)

When that run is complete, you should see a green result. If not, please reach out :) This sample project is very small. A larger project may take considerably longer to build, so be prepared to wait...

![image-20221004211903511](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-04_21-19-image-20221004211903511.png)

## iOS Dev Machine Config

If you're publishing to a public repo, you won't need to configure auth for your machine. Private repos need to have authentication configured so the binaries can be accessed.

#### Private Repos

For private builds, you'll need to tell the local machine how to access the private file. You can do this either by editing the `~/.netrc` file, or by adding the info to your local keychain.

First, get a personal access token from GitHub. Make sure it has at least `repo` permissions. You can add an expiration, but if you do, you'll need to remember to create a new one later...

![Screen Shot 2022-09-29 at 8.16.31 AM](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-09-29_08-17-Screen%20Shot%202022-09-29%20at%208.16.31%20AM.png)

Add the following to your `~/.netrc` file (create the file if it doesn't exist):

```
machine maven.pkg.github.com
  login [github username]
  password [your new personal access token]
```

The `~/.netrc` file tells curl and other networking tools how to authenticate to servers matching each `machine` entry. If you use a different back end you'll need to have a different `~/.netrc` entry.

Alternatively, you can use the Mac's keychain to manage access. See [this blog post for more detail](https://medium.com/geekculture/xcode-13-3-supports-spm-binary-dependency-in-private-github-release-8d60a47d5e45).

## Next Steps

You'll want to pull this new build into Xcode. For more information on how to do that, see  [IOS_COCOAPODS](cocoapods/01_IOS_COCOAPODS.md) or [IOS_SPM](spm/01_IOS_SPM.md).

## See Also

[TROUBLESHOOTING](TROUBLESHOOTING.md)
