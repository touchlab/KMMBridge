# Using Swift Package Manager

Swift Package Manager is a newer dependency manager directly from Apple. In some ways it's more integrated into Xcode, but is also less flexible than CocoaPods. Much of that seems by design, as it's very difficult to introduce side effects into the `Package.swift` build scripts. While that is likely to result in more reliable builds for the average Xcode project, for Kotlin builds, that means some more manual processes at present.

Out of the box, the official Kotlin tools are far less integrated into SPM. We have some basic support for SPM development, but this is a work in progress. Feedback welcome.

## Kotlin Project Configuration

After setting up KMMBridge in your Kotlin project, you should configure SPM for library publishing. Generally speaking, SPM wants to have the `Package.swift` file in the root of the repo. Xcode and SPM use Git repos as an organizational and discovery unit. The `Package.swift` file goes in the root, and Xcode clones from GitHub (or others) to read info about the library and source code.

If you don't have a `Package.swift` file, or don't know how to set one up, that's OK. KMMBridge currently generates these files for you.

In the `kmmbridge` block, add `spm()`. If you call it without parameters, KMMBridge assumes you want the `Package.swift` file at the root of your repo (we also assume you're using Git. We're not sure you could use SPM to deploy *without* Git).

```kotlin
kmmbridge {
    spm()
    // Other config...
}
```

In the example above, the Kotlin module is one folder down. The `spm()` setup detects that with git automatically.

![spmfolder](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-43-spmfolder.png)

SPM uses Git for versioning. Your publication process will need to add tags to your repo as builds are published so that SPM can find those builds. Our [Default GitHub Flow](../DEFAULT_GITHUB_FLOW) handles versioning automatically.

**If you are configuring KMMBridge on your own**, be aware that you need to set the Gradle `version` property correctly, or provide a different way for KMMBridge's SPM support to get a version for publishing (see [Configuration Overview - VersionManager](../general/CONFIGURATION_OVERVIEW.md#versionmanager))

### Specifying platform versions

You can specify platform versions in the spm config:

```kotlin
kmmbridge {
    spm {
        iOS("17.1")
        tvOS("10")
    }
}
```

Platforms that you can specify:

* iOS - default "16"
* macOS - default "13"
* tvOS - default "16.1"
* watchOS - default "9"

### Specifying swift tools version
You can use the `swiftToolsVersion` parameter to set the swift tools version that will be written to the Package.swift header. If no value is provided, version 5.3 will be used by default:

```kotlin
kmmbridge {
    ...
    spm(swiftToolVersion = "5.8")
}
```

### Specifying target platforms and versions
You can use the `targetPlatforms` lambda to add a targets and versions. Currently, only iOS target is supported:
```kotlin
kmmbridge {
    ...
    spm(targetPlatforms = {
        iOS { v("14") }
    })
}
```

### Using a custom package file

By default, KMMBridge fully manages your Package.swift file. This might not be what you want, if your published library needs to include more than just your Kotlin framework. If you need to customize your package file, pass the `useCustomPackageFile` flag when configuring SPM in KMMBridge:

```kotlin
kmmbridge {
    ...
    spm(useCustomPackageFile = true)
}
```

When this flag is set, rather than regenerating your entire package file during publication, KMMBridge will only update the variables it sets at the top of the package file. You are now responsible for using them correctly when making changes.

This works by replacing a block of code that begins with the comment `// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)` and ends with the comment `// END KMMBRIDGE BLOCK`.

:::caution
The custom package file mode is new and experimental. The local dev flow using the `spmDevBuild` gradle task is disabled when `useCustomPackageFile` is true.
:::

## Artifact Authentication

For artifacts that are kept in private storage, you may need to add authentication information so your `~/.netrc` file or your Mac's Keychain Access. See [the section here](../DEFAULT_GITHUB_FLOW.md#private-repos) for a description of how to set up private file access.

:::caution

When publishing to GitHub Packages, be aware of a few issues. First, GitHub Packages requires authentication for accessing files in **all** repos, public or private. You need to add `~/.netrc` or Mac Keychain Access authentication info for any user accessing these repos. Also, if your repo is private, you'll need to authenticate to GitHub in Xcode to add it (see below), but you still also need to set up `~/.netrc` or Mac Keychain Access for SPM to grab the binary.

:::

## Xcode Configuration

Open or create an Xcode project. To add an SPM package, go to `File > Add Packages` in the Xcode menu. Add your source control account (presumably GitHub). You can usually browse for the package at that point, but depending on how many repos you have, it may be easier to copy/paste the repo URL in the top/right search bar. After finding the package, you should generally add the pacakge by version ("Up to Next Major Version" suggested).

![addpackages](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-57-addpackages.png)

:::warning

The Xcode configuration can be confusing here. You need to authenticate to GitHub through the Xcode UI if you have a private GitHub repo. You will *still* need to set up authentication for SPM to access the actual packages with `~/.netrc`. This has been the *primary* source of issues when people set up KMMBridge!!!

:::

Once added, you should be able to import the Kotlin module into Swift/Objc files and build!

![import](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-00-import.png)

## Updating Builds

Run the KMMBridge build again. It should automatically create another build version and publish that to the GitHub repo. In Xcode, you can update your imported version by right-clicking on the module and selecting "Update Package":

![updatepackage](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-04-updatepackage.png)

![updatepackagedone](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-17-updatepackagedone.png)

## Local Kotlin Dev

If you are going to locally build and test Kotlin with SPM, see  [IOS_LOCAL_DEV_SPM](02_IOS_LOCAL_DEV_SPM.md).
