# Troubleshooting

### Error: "This fat framework already has a binary for architecture `x64` (common for target `ios_x64`)” (or similar for arm)

This is basically saying you have more than one framework defined for the same architecture. This most commonly happens
because the project has both explicit frameworks defined in the kotlin/targets area, and the Cocoapods plugin applied.

If you see `kotlin("native.cocoapods")` or `id("org.jetbrains.kotlin.native.cocoapods")` in the plugins:

```kotlin
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods") // <--- This
    id("co.touchlab.faktory.kmmbridge") version "x.y.z"
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

You have duplicate frameworks being declared. The Cocoapods plugin is adding frameworks automatically for all darwin/Apple
targets, so explicitly declaring them is redundant.