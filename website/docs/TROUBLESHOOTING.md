---
sidebar_position: 8
---

# Troubleshooting

### Error: "This fat framework already has a binary for architecture `x64` (common for target `ios_x64`) (or similar for arm)"

This is basically saying you have more than one framework defined for the same architecture. This most commonly happens
because the project has both explicit frameworks defined in the kotlin/targets area, and the CocoaPods plugin applied.

If you see `kotlin("native.cocoapods")` or `id("org.jetbrains.kotlin.native.cocoapods")` in the plugins:

```kotlin
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods") // <--- This
    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME}}"
}
```

and you see framework declarations for your targets:

```kotlin
kotlin {
    iosX64 {
        binaries.framework()
    }
}
```

You have duplicate frameworks being declared. The CocoaPods plugin is adding frameworks automatically for all darwin/Apple
targets, so explicitly declaring them is redundant.

### Error: "Received status code 422 from server: Unprocessable Entity" when using GitHub Packages"

If you have multiple repos publishing to the same group and artifact, you'll get this error. Changing the 
artifact name fixes it. See below.

https://github.com/orgs/community/discussions/23474

### Error: "Cannot add task 'assembleSdk_sharedReleaseXCFramework' as a task with that name already exists."

In your Gradle script, if you're creating your own `XCFramework` like `XCFramework("<name>") then you should remove it as `KMMBridge` creates the `XCFramework` definitions automatically.

We might add ability in future to automatically detect this and handle smoothly.

### Error: "Could not PUT 'https://maven.pkg.github.com/MyOrg/MyRepo/MyRepo/shared-kmmbridge/0.0.1/shared-kmmbridge-0.0.1.zip'. Received status code 403 from server: Forbidden"

You're publishing to github packages, but the workflow doesn't have the right permissions. You can add a permissions block like the following to fix this:

```yaml
permissions:
  contents: write
  packages: write
```
That block can be at the top-level or inside the publish task. [See here](https://github.com/touchlab/KMMBridgeSampleKotlin/blob/main/.github/workflows/main.yml) for an example.

### Error: Task 'kmmBridgePublish' not found in root project 'MyProject'.

KMMBridge won't configure it's publication tasks unless it knows you want it to. You must set the `ENABLE_PUBLISHING` gradle property to true (usually only in CI or hwen troubleshooting), and you must have publication fully configured, including an `artifactManager` and `dependencyManager`.
