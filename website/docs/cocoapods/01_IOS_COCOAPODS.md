# Using CocoaPods

You'll need an Xcode project with CocoaPods set up. That means you'll have a `Podfile` that you can edit with the necessary code to integrate your Kotlin module.

## Artifact Authentication

For artifacts that are kept in private storage, you may need to add authentication information so your `~/.netrc` file or your Mac's Keychain Access. See [GITHUB_RELEASE_ARTIFACTS#private-repos](../artifacts/GITHUB_RELEASE_ARTIFACTS#private-repos) for a description of how to set up private file access.

## Add Podspec Repo

In your `Podfile`, add the module and the source. An example `Podfile` might look like this:

```ruby
platform :ios, '13'

source 'https://github.com/touchlab/PublicPodspecs.git'

target 'KMMBridgeSampleCocoaPods' do
  pod 'shared', '0.2.1'
end

```

Then, to initialize CocoaPods, run:

```shell
pod install
```

As you publish new versions of the library, you will need to update the local podspec repo copy. Either run:

```shell
pod repo update
```

Or update the podspec when you're updating your CocoaPods project:

```shell
pod install --repo-update
# Or...
pod update --repo-update
```

Assuming that all worked, you should be able to open the project and build it.

If you are using a private podspec repo, your setup should work if you've added the authentication above. If the files can't be synced, make sure to double-check the auth setup.

***VERY IMPORTANT!!!***

After you run `pod install`, CocoaPods generates an `xcworkspace` file. There is usually both an `xcodeproj` and an `xcworkspace`. Make sure you open the `xcworkspace` file!!!

![xcworkspacefile](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_09-11-xcworkspacefile.png)

## Local Kotlin Dev

If you are editing Kotlin, you will probably want to test it locally. To do that, see  [IOS_LOCAL_DEV_COCOAPODS](02_IOS_LOCAL_DEV_COCOAPODS.md).