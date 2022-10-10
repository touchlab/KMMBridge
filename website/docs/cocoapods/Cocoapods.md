## Basic Configuration

Add the sonatype snapshots repo where the plugin currently lives

```kotlin
// settings.gradle.kts
pluginManagement {
    repositories {
        ...
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}
```

Add the Faktory plugin to your project, in the same module where the Kotlin CocoaPods plugin is configured.

```kotlin
// build.gradle.kts
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME}}"
}
```

Now configure Faktory for cocoapods

```kotlin
kmmbridge {
    cocoapods("git@github.com:your-org/Podspecs.git")
}
```

You'll need to provide a url to your own spec repo to host the podspec. For more on spec repos, see
the [CocoaPods documentation](https://guides.cocoapods.org/making/private-cocoapods.html).

## Publishing a new version

To publish a new version, use the `kmmBridgePublish` gradle task.
This will take care of uploading a new binary to your artifact storage, as well as uploading the podspec to the designated
spec repo so the library can be consumed from non-Kotlin-aware iOS projects.

The Podspec will be generated based on the same configuration used by the kotlin.cocoapods plugin, except that
the `source` and `version` will be overridden by the KMMBridge plugin.

### Version numbering

The KMMBridge plugin does some automated version management in order to ensure that new library versions are published
with a consistently increasing version number. A base version is set either in the `cocoapods` block or directly on the
gradle project, as managed by the kotlin.cocoapods plugin.

```kotlin
version = "1.2" // This will be used second, if not set in cocoapods block

kotlin {
    cocoapods {
        version = "1.2" // This will be used first, if present
    }
}

```

When publishing a new cocoapod, Faktory will append a timestamp to this version to ensure a unique and increasing
version number. This will result in a published version like `1.2.1663779589077`. To maintain semver compatibility, you
should set the base version using only a major and minor version (1.2, rather than 1.2.3) so that the timestamp piece
becomes the patch version.

To consume this from your iOS project without needing to manually update versions, you can use
CocoaPods [optimistic version operator](https://guides.cocoapods.org/using/the-podfile.html#specifying-pod-versions) as
follows:

```ruby
pod 'MyShared', '~> 1.2'
```

When you first run a `pod install` with this line in your `Podfile`, CocoaPods will use the most recent version
matching `1.2.x` and generate a `Podfile.lock` file. The next time you run `pod install` it will read the version from
that lock file until the version in the `Podfile` changes. When you publish a new `1.2.x` version, you can update the
lock file by running `pod update`.

Configuring things in this way makes it easier to manage integrating changes from shared code into the iOS project. If a
breaking change happens in the Kotlin code, you can publish it without impacting the iOS project until you
run `pod update` when you're ready to incorporate the iOS-side changes.

If you prefer instead to always set an exact version, you can check the spec repo for the most recent published version
and set it manually.

```ruby
pod 'MyShared', '1.2.1663779589077'
```

## Local Development

While Faktory allows fast and easy builds on iOS using pre-built binaries, if you are doing work in the shared code, you
will want to be able to test your changes on iOS rather than using the pre-built binaries. To support this without
making `Podfile` changes which might accidentally get committed to source control, we recommend using an environment
variable to point to your local version.

You can use the variable in your `Podfile` as follows:

```ruby
target 'MyApp' do
  if ENV.include?("LOCAL_KOTLIN_PATH")
    pod 'MyShared', :path => ENV["LOCAL_KOTLIN_PATH"]
  else
    pod 'MyShared', '~> 1.2'
  end
end
```

and setting it before running `pod install`

```sh
export LOCAL_KOTLIN_PATH="path/to/cloned/MyShared"
pod install
```

and revert to using the remote:

```sh
unset LOCAL_KOTLIN_PATH
pod install
```

When pointing to a local version like this you will, of course, need the shared code cloned in the location specified
and you will need the appropriate environment set up for building KMP

## Uploading to a custom S3 bucket

By default, the Faktory plugin uploads binaries through Touchlab's servers. As an alternative, you can provide your own
Amazon S3 bucket to use instead.

<!-- TODO clean up this configuration -->

```kotlin
faktory {
    // faktoryReadKey.set("0123456789ABCDEF") // This is no longer necessary
    ...
    s3Public(
        region = "us-east-2",
        bucket = "my-s3-bucket",
        accessKeyId = "0123456789ABCDEF",
        secretAccessKey = "0123456789ABCDEF",
        makeArtifactsPublic = true, // Set this always to true
        altBaseUrl = null // Set this always to null
    )
}
```

In the current version of the plugin, the bucket must be configured for public access. This may change in the future, so
if private S3 access is important to you, let us know! <!-- TODO contact link -->

To configure an access key, go to
the [AWS IAM management console](https://console.aws.amazon.com/iam/home#/security_credentials) and click "Create access
key". The "Access key ID" and "Secret access key" fields correspond to `accessKeyId` and `secretAccessKey` in the
Faktory configuration.

These keys are only used when uploading a new binary, so you may want to read them from environment variables or some
other source that's not checked into the project rather than committing them into the gradle file.
