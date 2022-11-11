KMMBridge was written and tested with Kotlin Gradle scripts. It's possible to use from Groovy, but most syntax is less
convenient due to our usage of default parameter values and extension functions in Kotlin. If you are using Groovy,
here's some snippets that might help.

```groovy
kmmbridge {
    /* Dependency managers */
    cocoapods(project, "git@github.com:MyOrg/MyPodspecs.git")
    // and/or
    spm(project, "../", project.name)

    /* Artifact managers */
    githubReleaseArtifacts("release-")
    // or
    s3PublicArtifacts(
            "us-east-2",
            "my-s3-bucket",
            "0123456789ABCDEF",
            "0123456789ABCDEF",
            true,
            null
    )
}
```
