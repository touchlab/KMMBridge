---
title: Android Versioning
sidebar_position: 2
---

# Publishing Android Alongside KMMBridge
KMMBridge is primarily a tool for managing KMM publications for iOS. It does not implicitly do any Android publication, but it's reasonable for a team to desire to publish both iOS and Android simultaneously. Accomplishing that simply comes down to updating your CI workflows to also run the appropriate android publish tasks. 

Potential problems arise, though, when you start to consider versions. KMMBridge will manage incrementing versions automatically according to the [version manager that you choose in your configuration](CONFIGURATION_OVERVIEW.md#version-managers). 

## Using Different Versions
If you don't care about the iOS and Android versions matching, then simply make sure that you [set a versionPrefix](CONFIGURATION_OVERVIEW.md#incrementing-version-managers) for KMMBridge so that you can use gradle version for Android however you like. Make sure you increment Android versioning before running your Android publish CI, or it will likely fail due to the version already being published (which is a good thing)

## Aligning Versions
If you desire the Android and iOS builds of your library to have a singular version number, you will need to turn off KMMBridge's version incrementing by using [manualVersionManager](CONFIGURATION_OVERVIEW.md#manualversionmanager) and use another mechanism for incrementing

```kotlin title="Example config with manual version manager"
kmmbridge {
    mavenPublishArtifacts()
    manualVersions()
    spm()
}
```
### Auto Incrementing
There are many ways to accomplish automatic incrementing of versions utilizing tags, files, or environment variables. Once you are using `manualVersions()` it's outside the purview of KMMBridge but here's a very basic example using github actions workflow run number:

In your workflow, pass the run number into gradle as a property
```yaml
-PBUILD_NUMBER=${{github.run_number}}
```

In build.gradle.kts concat your build number onto the final version string:
```kotlin
    val GROUP: String by project
    val LIBRARY_VERSION: String by project
    val BUILD_NUMBER: String? by project

    group = GROUP
    version = BUILD_NUMBER?.let { "$LIBRARY_VERSION.$it"} ?: "0.0.0"
```

This is of course a very simplistic approach to incrementing versions that may have issues as you use the workflow. There are various plugins and prebuilt actions which you can use instead.