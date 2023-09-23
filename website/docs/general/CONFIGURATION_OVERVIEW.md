---
sidebar_position: 1
---

# Configuration Overview

## Workflow Configuration

### KMMBridge Block

In the Gradle build file for your module that exports Xcode Frameworks, you'll apply the KMMBridge Gradle plugin, and add a `kmmbridge` config block.

```kotlin
id("co.touchlab.kmmbridge")

// Etc

kmmbridge {
    
}
```

All of the block config ultimately ends up in the [KMMBridgeExtension](https://github.com/touchlab/KMMBridge/blob/main/kmmbridge/src/main/kotlin/KmmBridgeExtension.kt#L40). 

Every config should have an [`ArtifactManager`](#artifact-managers) and at least one [`DependencyManager`](#dependency-managers).

You can control the [Framework name](#naming) here as well.

By default, the "build type" is Release. To change it to Debug, do the following:

```kotlin
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

// Etc

kmmbridge {
    buildType.set(NativeBuildType.DEBUG)
}
```

### VersionManager

Version 0.3.x of KMMBridge had the concept of `VersionManager`, which managed the version for published Xcode Frameworks. The responsibility for versioning has been moved out of KMMBridge, but you can still override the default by setting a custom `VersionManager`. There aren't many reasons you'd want to do this. The most likely scenario would be:

* You are only publishing iOS builds
* You don't want to set the Gradle version dynamically, or you don't want to use the Gradle version property at all

To use something other than the `version` property, see `ManualVersionManager` and write a custom implementation.

If you only want to publish iOS builds and would like KMMBridge to do simple version incrementing, we have a `TimestampVersionManager` instance that simply appends the current timestamp to Gradle's version property. This assumes the Gradle version property will be the major and minor values of a semantic version (ex. `2.3`). So, a build might have `2.3.1695492019324` for its version.

```kotlin
version = "2.3"

kmmbridge {
    timestampVersions()
}
```

### GitHub Packages

There is a special function that handles GitHub Packages repo setup automatically.

```kotlin
addGithubPackagesRepository()
```

This function reads values provided to CI from our [GitHub Actions Workflow](../DEFAULT_GITHUB_FLOW.md), and sets up access to publish to GitHub Packages automatically.

### Optional Gradle Parameters

For local development, KMMBridge configures XCFrameworks and, if you're using SPM, the SPM local dev flow. Publishing a build is really intended to happen from CI, using a predefined script. It can be manually or locally configured, but there are parameters you should be aware of.

Generally speaking, you should refer to the [Default GitHub Workflow](../DEFAULT_GITHUB_FLOW.md) for an up-to-date example with everything you'll need.

These are some of the parameters you should be aware of:

`GITHUB_PUBLISH_TOKEN` - Gradle parameter. Used on CI with the default workflow to configure auth for validating packages.

`GITHUB_REPO` - Gradle parameter. Used on CI with the default workflow to configure auth for validating packages.

`ENABLE_PUBLISHING` - Gradle parameter. KMMBridge does some extra setup that isn't necessary if you aren't publishing. This setup may cause warnings, so disabling that part of the Gradle setup may be useful. Add the following to `gradle.properties`

```
ENABLE_PUBLISHING=false
```

In CI, you can override that value with the following.

```shell
./gradelew -PENABLE_PUBLISHING=true [your tasks]
```

**Note:** Earlier versions of KMMBridge required this parameter, as we were doing git operations locally. The majority of those operations now live outside of the plugin. We do one call to get the repo folder root, and fall back with a warning if there is no git repo. Just FYI.

## Artifact Managers

Artifact Managers handle uploading the binary and generate the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:

### [Maven Repository Artifacts](../artifacts/MAVEN_REPO_ARTIFACTS)

This is the simplest to configure and currently the recommended best way to publish your binaries. If you are using GitHub, you can publish to Github Packages easily with our integration tools. For a complete introduction and overview of how to configure your project with this manager, see [Default GitHub WorkFlow](../DEFAULT_GITHUB_FLOW.md).

### [S3 Public Artifacts](../artifacts/S3_PUBLIC_ARTIFACTS.md)

This implementation will publish to your S3 bucket. By default it will set the access to public. You can also have access be private and controlled by AWS access, but there is no easy way to give Xcode access to your binaries. You'll need to configure machine access to these buckets (this is more common in larger enterprise environment).

The S3 artifact manager is really the starting point for teams that need a more custom implementation (Azure, Google Cloud, private hosting, etc).

## Dependency Managers

Dependency managers handle integration with CocoaPods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:

* SpmDependencyManager: [IOS_SPM](../spm/01_IOS_SPM.md)
* CocoapodsDependencyManager: [IOS_COCOAPODS](../cocoapods/01_IOS_COCOAPODS.md)

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
