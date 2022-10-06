# Using Cocoapods

You'll need an Xcode project with Cocoapods set up. That means you'll have a `Podfile` that you can edit with the necessary code to integrate your Kotlin module.

You'll need to add the podspec repo to Cocoapods and to your `Podfile`. First, tell Cocoapods about it. In our case, we're using `https://github.com/touchlab/PublicPodspecs.git`

> Note: You can use either ssh or https, depending on how your git/Github access is configured.

```shell
pod repo add touchlabpublic https://github.com/touchlab/PublicPodspecs.git
```

Replace "touchlabpublic" with whatever you want to call your repo.

Then, in your `Podfile`, add the module and the source. An example `Podfile` might look like this:

```ruby
platform :ios, '13'

source 'https://github.com/touchlab/PublicPodspecs.git'

target 'KMMBridgeSampleCocoaPods' do
  pod 'shared', '0.2.1'
end

```

Then, to initialize Cocoapods, run:

```shell
pod install
```

Assuming that all worked, you should be able to open the project and build it.

***VERY IMPORTANT!!!***

After you run `pod install`, Cocoapods generates an `xcworkspace` file. There is usually both an `xcodeproj` and an `xcworkspace`. Make sure you open the `xcworkspace` file!!!

![xcworkspacefile](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_09-11-xcworkspacefile.png)

## Local Kotlin Dev

If you are editing Kotlin, you will probably want to test it locally. To do that, see  [IOS_LOCAL_DEV_COCOAPODS](IOS_LOCAL_DEV_COCOAPODS.md).