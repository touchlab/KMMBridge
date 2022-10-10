# CocoaPods Local Dev Flow

After you have [integrated CocoaPods into Xcode](01_IOS_COCOAPODS.md), you can run local dev builds by adding the Kotlin code as a local dev Cocoapod.

> For this example, we will assume you have cloned both the Xcode repo and the Kotln repo to the same directory, although you can clone them anywhere on your local drive. Just replace the path accordingly.

Modify your `Podfile` to check environment variables for a local dev path.

```ruby
platform :ios, '13'

source 'https://github.com/touchlab/PublicPodspecs.git'

target 'KMMBridgeSampleCocoaPods' do
  if ENV.include?("LOCAL_KOTLIN_PATH")
    pod 'shared', :path => ENV["LOCAL_KOTLIN_PATH"]
  else
    pod 'shared', '0.2.1'
  end
end
```

If you run `pod install` without any environment variable changes, you will get the prebuilt binary. However, you can use the local binary by running:

```shell
export LOCAL_KOTLIN_PATH=../KMMBridgeSampleKotlin/shared
pod install
```

In our example, the Kotlin project is called `KMMBridgeSampleKotlin` and the Kotlin code is in a module called `shared`. Replace with your project and module names.

After running `pod install`, close and reopen the `xcworkspace` file. You should now be in the standard Kotlin local CocoaPods build flow.

> Note: It is *highly* recommended that you run `linkPodDebugFrameworkIosX64` or `linkPodDebugFrameworkIosSimulatorArm64`, depending on your Mac architecture, before you run `pod install`, due to a minor issue with the Kotlin CocoaPods integration.

Once your changes are complete, push them to your repo and run the KMMBridge build process again. When complete, you should be able to remove the local dev flow by removing the environment variable and running `pod install` again.