# Quick Start with KMMBridge

One of the more difficult things to do when getting started with Kotlin Multiplatform is configuring projects and Gradle builds. This is difficult enough to do with new "Greenfield" projects. Trying to modify and integrate within an existing production system can be significantly more difficult.

There's only so much we can do to make production build configuration simpler, but if you're using GitHub for your repos and either CocoaPods or Swift Package Manager for iOS dependencies, you should be able to get a basic integration working in about an hour or less.

Yes, I said that! An hour ⏰!

You'll need to have admin access to your GitHub org, or find somebody that is. Also, at least for this integration you'll need to be able to use GitHub Actions for builds and GitHub Packages for artifact hosting. Once you get this up and running, you can move builds and config to other CI/CD services and publish artifacts to other back ends (Artifactory, etc), but getting started with these defaults will let you get started very quickly.

:::info

If you are using other CI/CD tools and/or other artifact managers, please reach out in our Kotlin Slack channel [#touchlab-tools](https://kotlinlang.slack.com/archives/CTJB58X7X). Your feedback will help drive future development efforts.

:::

## Use Our Template Repo

We have a starter template project here: [touchlab/KMMSharedLibrary](https://github.com/touchlab/KMMSharedLibrary). Click "Use Template", give your repo a name, and create it. It can be public or private.

**Very Important!!!** You'll need to add a group string to your repo before you can do anything with it. You can open `gradle.properties` and edit `GROUP`. The value for `GROUP` needs to be a valid maven coordinate string. Generally speaking, it should be reverse domain ("com.whatever") and a name for the project, all lower case.

If you're using CocoaPods to publish, you'll have a couple extra steps today. A private podspec repo is used to publish CocoaPods metadata files. We are looking into alternatives, but for now, you'll need to do the following.

1. Create a new podspec repo
2. Add a README, or anything, to get at least one commit in the history
3. Create an ssh key pair 
4. Add the public part of the key to your podspec repo
5. Add the private part of the key to your Kotlin repo

See [COCOAPODS_GITHUB_PODSPEC](cocoapods/COCOAPODS_GITHUB_PODSPEC.md) for more detail on configuring your podspec repo.



After the repo has been created and `GROUP` has been specified, go to "Actions" and run `KMM Bridge Publish Release`. This should build and publish a version of your new library.

<video src="usetemplate2.mp4"></video>


⏰ *It'll probably take longer than an hour, but not a lot. Good luck!*