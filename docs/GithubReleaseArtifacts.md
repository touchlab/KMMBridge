# Github Release Artifacts

Publishing binary Xcode Frameworks requires a place to publish and host your artifacts. Github doesn't provide raw file hosting like AWS S3, but does provide various methods to host files. One simple method is to upload files to a Github release. The benefits are:

* You can publish as many files as you want to a release
* There is no (published) total limit on files, although each file has to be 2 gigabytes or less.
* There are no (published) restrictions on bandwidth.

When we publish a build from Kotlin, the ideal solution would be to push the zip binary along with the release. However, that is not so simple. For Cocoapods, we need an external repo for podspecs, so that would work.

SPM uses git tags to manage versioning. SPM's metadata file, `Package.swift`, needs the url of the binary file, which isn't available until you publish the release. It's a chicken/egg problem. You need the url before you publish the release, but you need to publish the release before you can get the URL.

You can't change the commit of a tag/release, but in Github, you can *add* files to a release after it's been created. As a simple workaround, we will create a special release that only exists to host binaries. When publishing a new build, the binary is added to that special release, which gives us the URL necessary to complete the rest of the release process.

## Note: Experimental

This is experimental. Github has no published limits on release file uploads, but we don't yet know what happens if you have very large numbers of assets in a single release. Github also makes no mention of total file hosting limits, but it seems reasonable to assume there will be a time when they start to notice huge additional file sizes. For example, if you published 2 releases per day of a fairly large project build, you could easily have 25 to 50 gigs of binary uploads in a year. Gihub also says there are no badwidth limits, and that seems like a less likely problem, but it's also not hard to imagine there are unpublished limits to wide distribution.

## Config

### Gradle Build

The artifact manager needs a few parameters:

* Repo owner
* Repo name
* Release tag

The repo owner and name will usually be the repo you are currently in. We may be able to auto-detect that eventurally, but for now you should specify. You can also push to a different repo in cases where that makes sense.

The release tag is important. It is the release that we will upload to. At major releases, it may make sense to update this, if only to avoid releases with huge numbers of artifacts (we may add some kind of auto-incrementing to handle this).

You will also need to supply the token used to access the repo, as a Gradle property called `GITHUB_PUBLISH_TOKEN`. This is usually passed in from a Github Actions workflow secret `${{ secrets.GITHUB_TOKEN }}`. For local builds, or to publish to a different repo, you'll need to create and store this value (TODO).

### iOS Build

#### Public Repos

For public projects, you should not need to do any configuration. SPM can access public release files.

#### Private Repos

For private builds, you'll need to tell the local machine how to access the private file. You can do this either by editing the `~/.netrc` file, or by adding the info to your local keychain.

First, get a personal access token from GIthub. Make sure it has at least `repo` permissions. You can add an expiration, but you'll need to remember to create a new one later :)

![Screen Shot 2022-09-29 at 8.16.31 AM](https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-09-29_08-17-Screen%20Shot%202022-09-29%20at%208.16.31%20AM.png)

Add the following to your `~/.netrc` file (create it if it doesn't exist):

```
machine api.github.com
  login [github username]
  password [your new personal access token]
```

Once that is set up, assuming you have access to the private repo where the artifacts are stored, you should be able to sync and build Xcode successfully.

Alternatively, to set up access with your local keychain TODO