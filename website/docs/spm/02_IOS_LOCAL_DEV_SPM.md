# SPM Local Dev Flow

After you have [integrated your Kotlin module into Xcode using SPM](01_IOS_SPM.md), you may want to locally build and test your Kotlin code when making changes.

***Experimental:*** *While all of KMMBridge is relatively new, the SPM dev flow is very experimental. The most likely issues with be path resolutions.*

> As mentioned, SPM is not really integrated out of the box with Kotlin, and SPM itself does not make integrations easy (generally speaking). We have some support for local dev flow, but we are looking for feedback and improvements.

Since KMMBridge is generating your `Package.swift` files, the first step is to run a dev build step to build the local dev `Package.swift` and locally build a debug version of the Kotlin code.

```shell
./gradlew spmDevBuild
```

This should:

* Build a debug version of the XCFramework
* Write a local dev path to `Package.swift`

Next, you need to manually copy the whole Kotlin project into Xcode. That means, quite literally, drag the Kotlin project's folder into Xcode.

<video src="https://tl-navigator-images.s3.amazonaws.com/docimages/dragspm.mp4"></video>

In the sample above, the pacakge `allshared` is inside `KevsKmmTest`. When you drag it in, if Xcode properly recognizes it, you'll see `allshared` disappear, but when you build, things should work as expected.


When you run `spmDevBuild`, it will build all architectures, which you probably don't need for testing on a simulator. To restrict architectures when building, you can pass in a Gradle param.

```shell
./gradlew spmDevBuild -PspmBuildTargets=ios_x64
```

For Intel Macs, use `ios_x64`. For arm Macs, use `ios_simulator_arm64`. You can pass in multiple architectures by separating them with commas.


When you are done making and testing local changes, select the folder you dragged in, and remove it by right-clicking it and selecting "Delete". Make sure to select "Remove References" on the popup. Xcode should then reload the remote version you had previously.

<video src="https://tl-navigator-images.s3.amazonaws.com/docimages/removelocal.mp4"></video>