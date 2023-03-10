# Using Swift Package Manager

Swift Package Manager is a newer dependency manager directly from Apple. In some ways it's more integrated into Xcode, but is also less flexible than CocoaPods. Much of that seems by design, as it's very difficult to introduce side effects into the `Package.swift` build scripts. While that is likely to result in more reliable builds for the average Xcode project, for Kotlin builds, that means some more manual processes at present.

Out of the box, the official Kotlin tools are far less integrated into SPM. We have some basic support for SPM development, but this is a work in progress. Feedback welcome.

## Kotlin Project Configuration

After setting up KMMBridge in your Kotlin project, you should configure SPM for library publishing. Generally speaking, SPM wants to have the `Package.swift` file in the root of the repo. Xcode and SPM use Git repos as an organizational and discovery unit. The `Package.swift` file goes in the root, and Xcode clones from GitHub (or others) to read info about the library and source code.

If you don't have a `Package.swift` file, or don't know how to set one up, that's OK. KMMBridge currently generates these files for you.

In the `kmmbridge` block, add `spm()`. If you call it without parameters, KMMBridge assumes you want the `Package.swift` file at the root of your repo (we also assume you're using Git).

```kotlin
kmmbridge {
    spm()
    // Other config...
}
```

In the example above, the Kotlin module is one folder down. The `spm()` setup detects that with git automatically.

![spmfolder](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-43-spmfolder.png)

SPM uses Git for versioning, so you'll probably want to use either Git tag or GitHub release version managers, and at least at launch, likely want to use GitHub artifacts.

Here is the suggested config for SPM:

```kotlin
kmmbridge {
    mavenPublishArtifacts()
    githubReleaseVersions()
    versionPrefix.set("0.1")
    spm()
}
```

### Commit manually

There is an option to disable git operations and commit the generated `Package.swift` by yourself.
Typical usage would be when using `manualVersions()` or `timestampVersions()` to avoid all automatic git interaction.
Using this flow is somewhat dangerous, as the publication state is inconsistent at the end of the kmmBridgePublish task.
The user is responsible for manually committing the updated package file, or else the new version will not be available to downstream consumers.
When using `gitTagVersions()` if you don't commit and tag the newest version, the next publishing might fail because the version is not incremented correctly.

To do so, call `noGitOperations()` in your `kmmbridge` block.

```kotlin
kmmbridge {
    spm()
    noGitOperations()
    // Other config...
}
```

Once this is all set up, run a build so you have at least one version available.

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

### Setting up SPM without using the Cocoapods plugin

You can use KMMBridge with only SPM, without Cocoapods plugin, but there are some differences in setting things up.

If you want to set the framework to be static or dynamic, you will need to access binaries of each target.
You can also set the framework name in the parameter of the `framework` function.

```kotlin
kotlin {
    iosX64 { // can be set for any of your tagets
        binaries {
            framework("FRAMEWORK_NAME") {
                isStatic = true // or false for dynamic framework
            }
        }
    }
}
```

Or you can set it for all the targets at once, just be careful not to have other kotlin native targets that you don't want to set this for (you can use filter if needed):

```kotlin
targets.withType<KotlinNativeTarget> {
    binaries {
        framework("FRAMEWORK_NAME") {
            isStatic = true // or false for dynamic framework
        }
    }
}
```

You can check out [build.gradle in this test](https://github.com/touchlab/KmmBridgeIntegrationTest-SPMWithoutCommit/blob/main/shared/build.gradle.kts) as an example.

## Artifact Authentication

For artifacts that are kept in private storage, you may need to add authentication information so your `~/.netrc` file or your Mac's Keychain Access. See [the section here](../DEFAULT_GITHUB_FLOW.md#private-repos) for a description of how to set up private file access.

:::caution

When you acces repos in GitHub with Xcode, you need to authenticate to GitHub. That isn't enough to access private GitHub release artifacts. You *also* need to add `~/.netrc` or Mac Keychain Access authentication info.

:::

## Xcode Configuration

Open or create an Xcode project. To add an SPM package, go to `File > Add Packages` in the Xcode menu. Add your source control account (presumably GitHub). You can usually browse for the package at that point, but depending on how many repos you have, it may be easier to copy/paste the repo URL in the top/right search bar. After finding the package, you should generally add the pacakge by version ("Up to Next Major Version" suggested).

![addpackages](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-57-addpackages.png)

Once added, you should be able to import the Kotlin module into Swift/Objc files and build!

![import](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-00-import.png)

## Updating Builds

Run the KMMBridge build again. It should automatically create another build version and publish that to the GitHub repo. In Xcode, you can update your imported version by right-clicking on the module and selecting "Update Package":

![updatepackage](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-04-updatepackage.png)

![updatepackagedone](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-17-updatepackagedone.png)

## Local Kotlin Dev

If you are going to locally build and test Kotlin with SPM, see  [IOS_LOCAL_DEV_SPM](02_IOS_LOCAL_DEV_SPM.md).
