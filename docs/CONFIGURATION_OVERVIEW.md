# Configuration Overview

## Components

### Artifact Managers

Artifact Manager handle uploading the binary and generating the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. There are currently implementations for Github Releases, S3, and Faktory (our server). Standard Gradle/Maven repo publishing is coming soon.

### Dependency Managers

Dependency managers handle integration with Cocoapods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations: CocoapodsDependencyManager and  SpmDependencyManager.

### Version Managers

Version managers take a version prefix and append a new version to it on every publish. Doing this makes publishing multiple dev versions easier, but you can also opt for explicit versioning. Currently implementations either append a timestamp, use git tags, or Github releases.
