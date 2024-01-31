---
sidebar_position: 3
title: Default GitHub Workflow
---

# GitHub Actions Workflow

We publish a reference CI implementation that works with GitHub Actions and, by default, GitHub Packages. This
implementation is the simplest way to get a new project set up if your team uses GitHUb. It is also a great reference to
review if you are setting up KMMBridge with a different CI or artifact host.

The main workflow is here:

<github org="touchlab" repo="KMMBridgeGithubWorkflow"/>

There are also a set of GitHub Action steps used in the workflow above that can be used in custom workflows. You can
find these listed below.

:::note

This flow depends on calling our GitHub Actions workflow. Many of the features of KMMBridge assume operations that the
GitHub Actions workflow is performing. SPM in particular uses git repo structure and tags to manage versions. Our GitHub
actions and workflow will modify your repo by adding tags and creating build branches. If you want complete control over
this, you'll need to fork and modify our processes.

:::

You can see our GitHub Workflow in action by checking
out [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quick-start).

## Overview

This flow allows you to do the following:

* Publish to public or private repos.
* Uses Maven artifacts with GitHub Packages. Standard tools!
* Upload Xcode Framework artifacts to GitHub Packages. No external storage or auth configuration is required. All auth
  is manages through GitHub.
* Can use either CocoaPods, SPM, or both.
* Publish iOS and (optionally) Android binaries.

## Workflow Reference

### Inputs

| Key                 | Type    | Required? | Default                | Description                                                                                                                                                                          |
|---------------------|---------|-----------|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| module              | string  | No        | N/A                    | The module name to run the task on if you have multiple kmp modules. Leave this blank to have all KMMBridge modules build                                                            |
| publishTask         | string  | No        | 'kmmBridgePublish'     | If you need to call something other than 'kmmBridgePublish', pass it in here.                                                                                                        |
| netrcMachine        | string  | No        | 'maven.pkg.github.com' | The domain name of the maching for netrc config.                                                                                                                                     |
| jvmVersion          | string  | No        | '11'                   | JVM Version to use. You may want to use '17' for current Android compatibility.                                                                                                      |
| runsOn              | string  | No        | 'macos-12'             | GitHub Actions host name. The default will likely change as GitHub Actions changes versions.                                                                                         |
| versionBaseProperty | string  | Yes       | N/A                    | The GitHub Workflow calculates versions automatically using a base (ex. `2.3`) and incrementing sequentially. The Workflow reads the base from a named value in `gradle.properties`. |
| retainBuildBranch   | boolean | No        | false                  | The Workflow needs to add and commit values to the repo. It does this in a build branch, which is deleted at the end. If set to 'true', this branch is retained.                     |

### Secrets

| Key             | Required? | Default              | Description                                            |
|-----------------|-----------|----------------------|--------------------------------------------------------|
| gradle_params   | No        | N/A                  | Extra parameters you can send to the main Gradle call. |
| PODSPEC_SSH_KEY | No        | N/A                  | For publishing to CocoaPod Podspec repos.              |
| netrcUsername   | No        | 'cirunner'           | Username for netrc config.                             |
| netrcPassword   | No        | secrets.GITHUB_TOKEN | Password for netrc config.                             |

### Steps

#### Checkout the repo with tags

Checkout the repo, make sure we get all tags (needed for SPM versioning).

#### Read versionBaseProperty

Uses a GitHub Action [touchlab/read-property](https://github.com/touchlab/read-property) to read a property
from `gradle.properties`.

#### Print versionBaseProperty

You can see the value read in the GitHub Actions log (for debugging purposes).

#### Calculate Next Version

GitHub Action [touchlab/autoversion-nextversion](https://github.com/touchlab/autoversion-nextversion)
uses `versionBase`, reads git tags from the repo, then calculates the next incremental build version to use.

#### Print Next Version

Prints the output of the last call (for debugging purposes). This is the version that the workflow will attempt to
publish.

#### Build Version Tag Marker

GitHub Action [touchlab/autoversion-tagmarker](https://github.com/touchlab/autoversion-tagmarker). During setup, this
will apply a temporary git tag and push to the repo remote. The purpose is to mark that version string as "in progress",
but it uses a prefix for the tag so SPM clients don't try to update the version before it's ready.

For example, if the next version should be `2.3.14`, a marker tag of `autoversion-tmp-publishing-2.3.14` will be added.

The reason for this marker tag is, for maven repos, including GitHub Packages, we need to "publish" the XCFramework zip
bundle before we can publish to the dependencies (CocoaPods specifically). If the publishing process fails after the zip
file is put into GitHub Packages, the artifacts will still exist. If the next publish attempt uses `2.3.14` again, it
will fail when it attempts to push the artifacts because they already exist.

We avoid that with the marker tag. If `2.3.14` fails, and another publish attempt is made, it will use `2.3.15` instead.

#### Create Build Branch

GitHub Action [touchlab/autoversion-buildbranch](https://github.com/touchlab/autoversion-buildbranch). Very simple.
Creates a build branch for the publication process.

#### Apply SSH Key

If you are publishing to CocoaPods, and have provided a `PODSPEC_SSH_KEY` secret, it will be applied to the CI machine
to allow publishing and access.

#### Apply netrc values

For local machine access to the published artifacts. Necessary for CocoaPods publishing. Defaults configured for GitHub Packages.

#### Setup Java

For Gradle build. Defaults to 11 currently, but will probably bump to 17 soon as current Android requires.

#### Validate Gradle Wrapper/Cache build tooling

Gradle and KMP build stuff.

#### Build Main

Runs `gradlew` to do the main build and publishing work.

The optional `module` parameter is used to narrow the build. `publishTask` is the Gradle task that's run. This can be multiple. See the reference app [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quick-start). The secret `gradle_params` is also applied here, in case your build needs something custom added.

Of note, the Gradle property `AUTO_VERSION` is created here and is available inside your build file. The value is the calculated version from the "Calculate Next Version" step. Inside your build script, `AUTO_VERSION` should be assigned to Gradle `version` is it is available. See the template project from [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quick-start) for an example.

:::caution

If publishing is going to fail, it is almost certainly in this step. If you get past here, we're essentially just wrapping up.

:::

#### Finish Release

GitHub Action [touchlab/autoversion-finishrelease](https://github.com/touchlab/autoversion-finishrelease). Adds and commits `Package.swift`, adds the final version tag, and pushes everything. After this step, any SPM client can get the new version. 

#### Build Version Tag Marker Cleanup

GitHub Action [touchlab/autoversion-tagmarker](https://github.com/touchlab/autoversion-tagmarker). Run again, but to clean up the tag markers created previously. If there were failed builds previously with the same version prefix, this action will remove all of them.

#### Delete branch

Deletes the build branch if `retainBuildBranch` is false.

## Next Steps

There are a number of setup steps required to use this workflow. See [KMMBridge Quick Start Updates](https://touchlab.co/kmmbridge-quick-start) for a walkthrough of setting up a project.