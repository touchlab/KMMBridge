# Default Github Flow

If you are hosting your repos in Github and can use Github Actions for CI, the Default Github Flow is the simplest way to start publishing Kotlin builds for iOS.

## Overview

This flow allows you to do the following:

* Publish to public or private repos
* Upload Xcode Framework artifacts to Github releases. No external storage or auth configuration is required.
* Can use either Cocoapods, SPM, or both

## Kotlin Repo

You'll need find or add the Kotlin Multiplatform module to publish. This module can be in the same project as your Android code (if any), or in a separate repo. In the Kotlin repo you'll add the Gradle config and CI to publish Xcode Frameworks.

## Spec Repo

If you are going to publish for Cocoapods, you'll also need a Cocoapods spec repo. This is a separate repo that Cocoapods uses to store published version information. This config is somewhat more complex, but still reasonably straightforward to configure.

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
    id("co.touchlab.faktory.kmmbridge") version "0.1.XX"
}
```

Later in the same file, add the `kmmbridge` config block:

```kotlin
kmmbridge {
    githubReleaseArtifacts()
    githubReleaseVersions()
    spm("..")
    cocoapods("git@github.com:touchlab/PodSpecs.git")
    versionPrefix.set("0.3")
}
```

`githubReleaseArtifacts()` is mandatory for this flow. Without that, files will not be published anywhere (there are other publishing options available).

`githubReleaseVersions()` is highly recommended. This will use Github releases for release tracking and incrementing. You can use a different version manager, but you need to configure one. See: [Version Managers](CONFIGURATION_OVERVIEW.md#version-managers) for more detail.

`spm("..")` only needs to be added if you want to support SPM. The parameter points at the root directory of your repo. In this case, we have the KMP module in a folder under the repo, so the repo root is one level up. This is where your `Package.swift` file should be stored.

Note: this config is only for SPM publishing. To understand how to integrate an SPM build into Xcode, and how to locally build and test Kotlin changes, see [IOS_SPM](IOS_SPM.md).

`cocoapods("[some git repo].git")` is only needed if you plan to publish for Cocoapods. You will need the spec repo mentioned above, properly configured for deployment. See  [COCOAPODS_GITHUB_PODSPEC](COCOAPODS_GITHUB_PODSPEC.md) for details on getting the podspec repo configured.

`versionPrefix` is not technically necessary but highly encouraged. As you publish builds, the semantic version number will be incremented and appended onto the prefix. So, in the example above, the first version would be `0.3.0`, next `0.3.1`, and so on.

### 3 Add the Github Actions workflow call

At the top of your project, if it does not already exist, add the folders `.github/workflows`. Add a file called `kmmbridgepnblish.yml` there, and copy the following into it.

```yaml
name: KMMBridge Publish Release
on: workflow_dispatch

jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuild.yml@main
```

Note: if you are using Cocoapods and a podspec repo, your file should look like the following:

```yaml
name: KMMBridge Publish Release
on: workflow_dispatch

jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuild.yml@main
    secrets:
      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}
```

You need to pass the ssh key configured earlier.

### 4 Add and push your code

Push your changes to Github, and make sure they're in the default branch.

## Publish a Build!

Assuming your configuration is set up correctly, you should be able to publish your first build. In the Kotlin repo's Github home page, go to "Actions", select "KMMBridge Publish Release", and manually run it.

![runbuild](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-04_21-14-runbuild.png)

When that run is complete, you should see a green result. If not, please reach out :) This sample project is very small. A larger project may take considerably longer to build, so be prepared to wait...

![image-20221004211903511](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-04_21-19-image-20221004211903511.png)

## Next Steps

You'll want to pull this new build into Xcode. For more information on how to do that, see  [IOS_DEV_SETUP](IOS_DEV_SETUP.md).

## See Also

[TROUBLESHOOTING](TROUBLESHOOTING.md)