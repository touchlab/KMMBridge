---
sidebar_position: 4
---

# Artifactory Artifacts
If you're using Artifactory to store artifacts, you just have to set up a repo on Artifactory and add your repo url and credentials in the
`publishing` block [see this example](MAVEN_REPO_ARTIFACTS.md#1-configure-push-access), as well as [configure client read access](MAVEN_REPO_ARTIFACTS.md#2-configure-client-read-access)

NOTE: If you add the Artifactory repo with the `artifactory` gradle plugin KMMBridge won't be able to find the
repo. For now, you'll have to also add the repo in the maven `publishing` block.

When publishing in a CI action, if you're using CocoaPods you need to add the credentials to `~/.netrc` before running publish
to validate the podspec. To do this simply use the custom netrc params in our GitHub Workflow.

You'll also need to add the username and password gradle params through the `gradle_params` secret in our workflow or
```yaml
jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@{{WORKFLOW_VERSION_NAME}}
    with: 
      netrcMachine: touchlabartifactory.jfrog.io
    secrets:
      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}
      netrcUsername: ${{ secrets.ARTIFACTORY_USERNAME }} 
      netrcPassword: ${{ secrets.ARTIFACTORY_PASSWORD }} 
      gradle_params: -PUSERNAME=${{ secrets.ARTIFACTORY_USERNAME}} -PPASSWORD=${{ secrets.ARTIFACTORY_PASSWORD }}

```

