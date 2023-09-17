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

`GITHUB_PUBLISH_TOKEN` - Gradle parameter. Used on CI with the default workflow to configure auth for validating packages.

`GITHUB_REPO` - Gradle parameter. Used on CI with the default workflow to configure auth for validating packages.

## Artifact Managers

Artifact Managers handle uploading the binary and generate the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:

### [Maven Repository Artifacts](../artifacts/MAVEN_REPO_ARTIFACTS)

This is the simplest to configure and currently the recommended best way to publish your binaries. If you are using GitHub, you can publish to Github Packages easily with our integration tools. For a complete introduction and overview of how to configure your project with this manager, see [Default GitHub Flow](../DEFAULT_GITHUB_FLOW.md).

### [S3 Public Artifacts](../artifacts/S3_PUBLIC_ARTIFACTS.md)

This implementation will publish to your S3 bucket. By default it will set the access to public. You can also have access be private and controlled by AWS access, but there is no easy way to give Xcode access to your binaries. You'll need to configure machine access to these buckets (this is more common in larger enterprise environment).

The S3 artifact manager is really the starting point for teams that need a more custom implementation (Azure, Google Cloud, private hosting, etc).

## Dependency Managers

Dependency managers handle integration with CocoaPods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:

* CocoapodsDependencyManager: [IOS_COCOAPODS](../cocoapods/01_IOS_COCOAPODS.md) 
* SpmDependencyManager: [IOS_SPM](../spm/01_IOS_SPM.md)

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
