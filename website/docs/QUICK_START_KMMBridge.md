# Quick Start with KMMBridge

One of the more difficult things to do when getting started with Kotlin Multiplatform is configuring projects and Gradle builds. This is difficult enough to do with new "Greenfield" projects. Trying to modify and integrate within an existing production system can be significantly more difficult.

Because production system config tends to be fairly complex, regardless of the technical ecosystem in question, there's a limit to how easy we can realistically make any particular situation. However, if the following are true:

* Your team uses GitHub for your code
* You're using CocoaPods or Swift Package Manager (SPM) for iOS dependencies
* You're OK using GitHub Actions for CI, at least for right now

If the above are true, you should be able to get a basic integration working in about an hour or less.

Yes, I said that! An hour ⏰!

You'll need to have admin access to your GitHub org, or find somebody that does. At a minimum you'll need to create another repo, and more detailed config will require other admin steps. Also, at least for this integration you'll need to be able to use GitHub Actions for builds and GitHub Packages for artifact hosting. Once you get this up and running, you can move builds and config to other CI/CD services and publish artifacts to other back ends (Artifactory, etc), but using GitHub for everything will let you get started very quickly.

:::info

If you are using other CI/CD tools and/or other artifact managers, please reach out in our Kotlin Slack channel [#touchlab-tools](https://kotlinlang.slack.com/archives/CTJB58X7X). Your feedback will help drive future development efforts.

:::

## Start The Clock!

### 1) Use Our Template Repo to Create Your Kotlin Repo

We have a starter template project here: [touchlab/KMMSharedLibrary](https://github.com/touchlab/KMMSharedLibrary). Click "Use Template", give your repo a name, and create it. It can be public or private.

### 2) Edit GROUP

**Very Important!!!** You'll need to add a group string to your repo before you can do anything with it. You can open `gradle.properties` and edit `GROUP`. The value for `GROUP` needs to be a valid maven coordinate string. Generally speaking, it should be reverse domain ("com.whatever") and a name for the project, all lower case.

### 3) Publish A Build

After the repo has been created and `GROUP` has been specified, go to "Actions" and run `KMM Bridge Publish Release`. This should build and publish a version of your new library.

## All Together...

Here is a video showing how to use the template and make a build:

<video src="usetemplate2.mp4"></video>

## Check In (~10 minutes)

Assuming no major surprises, you should have your first build processing. The template project generally takes a few minutes to build, so with a total of roughly 10 minutes, you have your first KMM build!

:::warning

If you did those 3 things and have an error, please reach out to our Kotlin Slack channel [#touchlab-tools](https://kotlinlang.slack.com/archives/CTJB58X7X), or create an issue in the template repo.

:::

### CocoaPods Considerations

While the build is publishing, it's a good time to talk about a CocoaPods configuration decision. CocoaPods allows you to host private podspec repos. That is, CocoaPods uses git to host it's version metadata (which is a `podspec`). That is usually a separate dedicated repo. To simplify config for *this sample*, the template project is configured to publish podspecs back into the Kotlin repo you created. That will work, but is not ideal, certainly if you intend to have ongoing development. To set up a separate podspec repo and publish to it, make sure to check out [COCOAPODS_GITHUB_PODSPEC](cocoapods/COCOAPODS_GITHUB_PODSPEC.md).

If you aren't using CocoaPods, you should remove the CocoaPods config from the repo you created in step one. Find the `kmmbridge` block in `<your repo>/allshared/build.gradle.kts`, line 56 ([link to the config line in the template repo](https://github.com/touchlab/KMMSharedLibrary/blob/main/allshared/build.gradle.kts#L56))

```kotlin
kmmbridge {
    mavenPublishArtifacts()
    githubReleaseVersions()
    spm()
    // cocoapods() <- remove this
}
```

## Configure Xcode Clients

If your repo is public, you should be able to access the binary that you just published without any auth configuration. If it is private, and in most cases it will be, you'll need to let Xcode access the binary. There are 2 ways to do that: netrc and Keychain Access.

**Note:** Every user accessing your builds with Xcode will need to do this configuration on their local machine.

You'll want to generate a Personal Access Token with `repo` access and `read:packages`:

![pataccess](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-17_16-33-pataccess.png)

That will give you a PAT string. Create or open `~/.netrc`, and add the following:

```shell
machine maven.pkg.github.com
  login [your username]
  password [your PAT]
```

Put your username in the `login` field, and the PAT you just created in the `password` field.

## Add the Framework to Xcode

### Swift Package Manager (SPM)

To add your Framework to Xcode with SPM, do the following:

1. Get the GitHub URL from your new repo (I generally copy the clone https URL)
2. In Xcode, go to `File > Add Packages` to open the SPM tool
3. Paste your GitHub URL in the "Search or Enter Package URL"
4. Select your repo and for "Dependency Rule", select "Up to Next Major Version". Xcode should auto-detect the version.

That's it! You should be able to import your Kotlin code now.

### CocoaPods

You'll need to add the podsepc source repo, then include the project. Assuming your local GitHub access is configured, you should just need to edit your `Podfile`.

See [this doc](https://touchlab.github.io/KMMBridge/cocoapods/IOS_COCOAPODS#add-podspec-repo) for more detail. 

```ruby
platform :ios, '13'

source 'https://github.com/[your org]/[your repo].git'

target '[Your iOS app target]' do
  pod 'allshared', '~> 0.1'
end
```

## Next Steps

When you're editing Kotlin, you'll probably want to locally test your build. See [IOS_LOCAL_DEV_COCOAPODS](cocoapods/IOS_LOCAL_DEV_COCOAPODS.md) or [IOS_LOCAL_DEV_SPM](spm/IOS_LOCAL_DEV_SPM.md), depending on which dependency manager you use.

⏰ *It'll probably take longer than an hour, but not a lot. Good luck!*