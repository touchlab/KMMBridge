---
title: Github Release Artifacts
---

:::danger NO LONGER SUPPORTED!
Github Release Artifact and Github Enterprise Release Artifact are no longer supported in versions 0.3.5+. The reason is it doesn't provide good enough benefits over using the maven artifact manager to publish to Github packages. Please, use [Maven Artifacts](docs/artifacts/MAVEN_REPO_ARTIFACTS) instead.
:::

# GitHub Release Artifacts

Publishing binary Xcode Frameworks requires a place to publish and host your artifacts. This really just needs to be a file bucket that you can read/write to. GitHub doesn't provide raw file hosting like AWS S3, but does provide various methods to host files. One simple method is to upload files to a GitHub release. The benefits are:

* You can publish as many files as you want to a release
* There is no (published) total limit on files, although each file has to be 2 gigabytes or less.
* There are no (published) restrictions on bandwidth.

Logically, it would make sense to publish your binary files with a versioned github release. However, we need a binary URL to be able to publish releases, at least for SPM, so you have a chicken/egg problem.

However, you can add binary files to an existing release. We're going to use special GitHub releases as "file buckets". They only exist to add binary files to for hosting. While this seems a bit hacky, there are benefits to this approach:

* It is simple
* You can use it for public or private deployments
* It works for SPM or CocoaPods
* Access is handled by GitHub, so you can add or remove developers as you normally would

![image-20221004164855858](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-04_16-48-image-20221004164855858.png)

## Note: Experimental

This is experimental. According to GitHub docs and policies, this will work fine, but it's not exactly the way they intend you to use releases. There are no published limits to uploads or bandwidth, but if you're using this a lot, there may be upper bounds we haven't encountered yet. However, for most standard use cases this should be fine.

## Config

### Gradle Build

If you are using our GitHub Actions CI [touchlab/KMMBridgeGithubWorkflow](https://github.com/touchlab/KMMBridgeGithubWorkflow), you don't need to pass any parameters. Just add this to your `kmmbridge` config in your Gradle build file.

```koltin
kmmbridge {
    githubReleaseArtifacts()
    githubReleaseVersions() // Highly recommended that you add this also...
}
```

You should also usually add a version manager, and if you're using GitHub artifacts, versioning with Github releases also makes sense, so you should add `githubReleaseVersions()`, unless you have a specific reason not to. See:  [CONFIGURATION_OVERVIEW](docs/general/CONFIGURATION_OVERVIEW) for more detail on version managers.

The CI script passes some arguments to Gradle directly that allow KMMBridge to publish zip files to releases.  If you aren't using our CI script, you'll need to add those parameters on your own. [Here is the relevant code from our CI script](https://github.com/touchlab/KMMBridgeGithubWorkflow/blob/f6075b60151caf15b8759c811b0d2458fbdd08a7/.github/workflows/faktorybuild.yml#L49):

```yaml
./gradlew kmmBridgePublish -PGITHUB_PUBLISH_TOKEN=${{ secrets.GITHUB_TOKEN }} -PGITHUB_REPO=${{ github.repository }}
```

`GITHUB_PUBLISH_TOKEN` is the access token we pass to GitHub. `GITHUB_REPO` is the repo we'll be publishing to.

### iOS Build

#### Public Repos

For public projects, you should not need to do any configuration. CocoaPods and SPM can access public release files directly with no auth.

#### Private Repos

For private builds, you'll need to tell the local machine how to access the private file. You can do this either by editing the `~/.netrc` file, or by adding the info to your local keychain.

First, get a personal access token from GitHub. Make sure it has at least `repo` permissions. You can add an expiration, but if you do, you'll need to remember to create a new one later...

![Screen Shot 2022-09-29 at 8.16.31 AM](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-09-29_08-17-Screen%20Shot%202022-09-29%20at%208.16.31%20AM.png)

Add the following to your `~/.netrc` file (create it if it doesn't exist):

```
machine api.github.com
  login [github username]
  password [your new personal access token]
```

Once that is set up, assuming you have access to the private repo where the artifacts are stored, you should be able to sync and build Xcode successfully.

Alternatively, you can use the Mac's keychain to manage access. See [this blog post for more detail](https://medium.com/geekculture/xcode-13-3-supports-spm-binary-dependency-in-private-github-release-8d60a47d5e45). Also, a big thanks to the author of that post for connecting a lot of the dots!
