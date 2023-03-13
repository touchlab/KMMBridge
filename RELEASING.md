# Releasing

Notes for releasing KMMBridge versions. There are some integration tests that we only run on releases, as they're 
rather long-running. Also, we need to make sure to review and update any supported samples.

Review when introducing **usage changes**:

* [KMMBridgeKickStart](https://github.com/touchlab/KMMBridgeKickStart) - Kick Start
* [KMMBridgeSampleKotlin](https://github.com/touchlab/KMMBridgeSampleKotlin) - Shared Kotlin code
* [KMMBridgeSampleSpm](https://github.com/touchlab/KMMBridgeSampleSpm) - Xcode example with SPM
* [KMMBridgeSampleCocoaPods](https://github.com/touchlab/KMMBridgeSampleCocoaPods) - Xcode example with CocoaPods
* [PublicPodspecs](https://github.com/touchlab/PublicPodspecs) - Public CocoaPods podspec repo
* [1 hour tutorial](https://touchlab.co/quick-start-with-kmmbridge-1-hour-tutorial/) - Tutorial for using KMMBridge
- [Samples of using blog post](https://touchlab.co/samples-of-using-kmmbridge/) - Blog post describing KMMBridge usage in Samples
- [Introducing KMMBRidge blog post](https://touchlab.co/introducing-kmmbridge-teams/) - Introduction of KMMBridge blog post
- [KMMBridge readme](README.md) - readme of this project
- [KMMBridge website](website/docs/index.md) - KMMBridge documentation

Update when releasing **new version number**:

* `libs.versions.toml` in [KMMBridgeKickStart](https://github.com/touchlab/KMMBridgeKickStart) - Kick Start
* `build.gradle.kts` in `shared` module in [KMMBridgeSampleKotlin](https://github.com/touchlab/KMMBridgeSampleKotlin) - Shared Kotlin code
