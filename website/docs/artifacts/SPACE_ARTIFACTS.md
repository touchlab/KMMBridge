---
sidebar_position: 3
---

# JetBrains Space Artifacts
If you're using JetBrains Space to store artifacts, you just have to set up a repo on Space and add your repo url and credentials in the
`publishing` block [see this example](MAVEN_REPO_ARTIFACTS.md#1-configure-push-access), as well as [configure client read access](MAVEN_REPO_ARTIFACTS.md#2-configure-client-read-access)

When publishing in a CI action, if you're using CocoaPods you need to add the credentials to `~/.netrc` before running publish
to validate the podspec. To do this simply use the custom netrc params in our GitHub Workflow.

You'll also need to add the username and password gradle params through the `gradle_params` secret in our workflow or
```yaml
jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@{{WORKFLOW_VERSION_NAME}}
    with: 
      netrcMachine: maven.pkg.jetbrains.space 
    secrets:
      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}
      netrcUsername: ${{ secrets.SPACE_USERNAME }} 
      netrcPassword: ${{ secrets.SPACE_PASSWORD }} 
      gradle_params: -PUSERNAME=${{ secrets.SPACE_USERNAME}} -PPASSWORD=${{ secrets.SPACE_PASSWORD }}
```

