---
sidebar_position: 1
---

# Maven Repository Artifacts

You can use a "standard" Maven repository to store your KMMBridge zip artifact. This will push the XCFramework zip archive to a standard maven/gradle endpoint. Using this artifact storage method allows for a wider range of publication possibilities, and utilizes standard Gradle publishing config.

Using Maven/Gradle repos to store your KMMBridge artifacts requires a few setup steps, and has some unique considerations. Setup requires handling the following:

1. Configure push access to your repository.
2. Configure artifact read access for private repos (generally the `~/.netrc` or Mac Keychain Access config).
3. (Possibly) consolidate your publishing versions and operations.

## 1) Configure Push Access

Each repo type has it's own unique setup, but essentially we need:

1. A repo url
2. A username that can publish to the repo
3. A password/token for that user

See specific guidance for Github Packages, JetBrains Space, and Artifactory.

The standard Gradle publishing config looks like the following.

```kotlin
plugins {
    `maven-publish`
    // etc
}

// ...

publishing {
    // Externally set these values. Generally ~/.gradle/gradle.properties or CI Secrets
    val publishUsername: String by project
    val publishPassword: String by project
    repositories {
        maven {
            url = uri("https://someservice/path/to/repo")
            credentials {
                username = publishUsername
                password = publishPassword
            }
        }
    }
}
```

With publishing configured, you can publish everything with `./gradlew publish`, or just publish the KMMBridge artifact with `./gradlew kmmBridgePublish`.

## 2) Configure Client Read Access

To access your published Xcode Framework package, you'll need to make sure Xcode has access to the package metadata and the binary itself. The package metadata differs depending on if you use Cocoapods or Swift Pacakge Manager. Access to the binary is the same with either.

### Package Metadata

Cocoapods publishes pacakge metadata to a git repo. See [CocoaPods setup](../cocoapods/IOS_COCOAPODS).

SPM uses git for publishing and git tags for versioning. See [SPM setup](../spm/IOS_SPM).

### Binary Access

If the binaries are stored in a private server, you'll need to configure auth access to them. You can do that with `~/.netrc` or Mac Keychain Access. We'll use `~/.netrc` here.

Open or create `~/.netrc`. Add the server and credentials to access your files.

```shell
machine repo.example.com
  login <user>
  password <pass>
```

If you're using GitHub Packages, you'll need your GitHub username and a Personal Access Token that can read repo and read packages.

```shell
machine maven.pkg.github.com
  login <Github User>
  password <PAT>
```

For different repos you'll need to change the machine to point at the server where your files are kept.

## 3) Publish Versions and Operations

The Maven publishing adds a special artifact to the "standard" Gradle publishing config, specically for KMMBridge. We're publishing the Xcode Framework XCFramework zip file as it's own Gradle/Maven artifact. If you run `./gradlew publish`, it'll attempt to publish KMMBridge along with any other configured publish targets.

If you do intend to publish everything, you'll likely want to disable the automatically incrementing verison support that KMMBridge provides, and use the standard Gradle publishing version management.

If you want to use the version from Gradle, use the following configuration:

```kotlin
kmmbridge {
    mavenPublishArtifacts()
    manualVersions()
    // etc
}
```

`manualVersions()` means we're just going to use whatever version string is available and not try to auto-increment. If you don't provide a `versionPrefix` value, KMMBridge will just use the version provided to Gradle.

## Github Packages

If you are publishing to Github Packages and you are using our [helper workflow](https://github.com/touchlab/KMMBridgeGithubWorkflow) with GitHub Actions, you can turn on publishing to GitHub Packages very easily with the following function at the root of your Gradle build file.

Pretty much everything you'd need to configure to publish to GitHub Packages is listed here.

```kotlin
import co.touchlab.faktory.addGithubPackagesRepository

plugins {
    kotlin("multiplatform")
    `maven-publish`
    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME}}"
}

group = "co.touchlab.somegroup"
version = "0.2"

kotlin {
    ios {
        binaries.framework()
    }
}

kmmbridge {
    mavenPublishArtifacts() // <- Publish using a Maven repo
    gitTagVersions()
    spm()
}

addGithubPackagesRepository() // <- Add the GitHub Packages repo
```

The `plugins` section, `group/version`, and `kotlin` blocks should looks pretty familiar to anybody who has done KMP/KMM work. You'll probably have more targets in `kotlin`, and probably more entries in `plugins`, but this setup would be complete and functional as presented.

In `kmmbridge`, notice `mavenPublishArtifacts()` tells the plugin to push KMMBridge artifacts to a Maven repo. You then need to define a repo. Rather than do everything manually, you can just call `addGithubPackagesRepository()`, which will add the correct repo given parameters that are passed in from GitHub Actions.
