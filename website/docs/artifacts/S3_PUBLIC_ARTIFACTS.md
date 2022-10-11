---
sidebar_position: 3
---

# S3 Public Artifacts

You can publish artifacts to AWS S3. However, there is no easy way to make them private using auth options available out of the box for S3. Therefore, artifacts in S3 will have publicly readable URL's. Those URL's are generated with a random UUID, so they're essentially unguessable, but that's an important restriction.

Some environments have S3 access automatically secured thorugh VPN's, etc. If you have thoughts on how to implement the S3 Artifact Manager in private environments, please reach out.

## Configuration

```kotlin
kmmbridge {
    s3PublicArtifacts(
        "us‑east‑1",
        "my-kmm-artifacts",
        "[ACCESS_KEY]",
        "[SECRET_ACCESS_KEY]"
    )
}
```

Parameters:

* region: the AWS region
* bucket: the S3 bucket
* accessKeyId: IAM access key (should probably be a repo secret)
* secretAccessKey: IAM secret key (should *definitely* be a repo secret)
* makeArtifactsPublic: optional boolean. Defaults to true. Can keep URL's private, but out of the box, there is no way to authenticate clients for access.
* altBaseUrl: optional alternative base URL.

## Considerations

We generally wouldn't use this option unless we're publishing a public repo. However, if you have alternative ways of securing and authenticating an S3 bucket, this is a good option.