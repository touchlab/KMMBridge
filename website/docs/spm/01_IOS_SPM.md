# Using Swift Package Manager

Swift Package Manager is a newer dependency manager directly from Apple. In some ways it's more integrated into Xcode, but is also less flexible than CocoaPods. Much of that seems by design, as it's very difficult to introduce side effects into the `Package.swift` build scripts. While that is likely to result in more reliable builds for the average Xcode project, for Kotlin builds, that means some more manual processes at present.

Out of the box, the official Kotlin tools are far less integrated into SPM. We have some basic support for SPM development, but this is a work in progress. Feedback welcome.

## Kotlin Project Configuration

After setting up KMMBridge in your Kotlin project, you should configure SPM for library publishing. Generally speaking, SPM wants to have the `Package.swift` file in the root of the repo. Xcode and SPM use Git repos as an organizational and discovery unit. The `Package.swift` file goes in the root, and Xcode clones from GitHub (or others) to read info about the library and source code.

If you don't have a `Package.swift` file, or don't know how to set one up, that's OK. KMMBridge currently generates these files for you.

> Note: If you'd prefer, or need to, manage your own `Package.swift` file, please reach out. An earlier version of the plugin modified the file rather than replacing it. We may add that feature back after KMMBridge is more stable.

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
    githubReleaseArtifacts()
    githubReleaseVersions()
    versionPrefix.set("0.1")
    spm()
}
```

Once this is all set up, run a build so you have at least one version available.

## Artifact Authentication

For artifacts that are kept in private storage, you may need to add authentication information so your `~/.netrc` file or your Mac's Keychain Access. See [GITHUB_RELEASE_ARTIFACTS#private-repos](../artifacts/GITHUB_RELEASE_ARTIFACTS#private-repos) for a description of how to set up private file access.

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
