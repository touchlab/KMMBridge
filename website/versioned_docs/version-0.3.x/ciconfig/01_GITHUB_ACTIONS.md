# Calling our GitHub Actions Workflow

You can easily configure GitHub Actions to run and publish builds. We have a set of external workflows that your project
can use to do this.

## Workflow Options

There are currently two workflow options available. One builds and publishes from the branch you run it in, while the other creates a temporary branch, publishes from there, then deletes the branch.

Same branch: [faktorybuild.yml](https://github.com/touchlab/KMMBridgeGithubWorkflow/blob/main/.github/workflows/faktorybuild.yml)

Build branch: [faktorybuildbranches.yml](https://github.com/touchlab/KMMBridgeGithubWorkflow/blob/main/.github/workflows/faktorybuildbranches.yml)

:::tip

Generally speaking, if you're using SPM, we'd suggest the version that creates a new branch. Building in the same branch will require you to pull locally before you can push, and occasionally you'll have `Package.swift` conflicts. They're generally not actual conflicts. `Package.swift` is automatically generated when publishing and when doing local dev for SPM, but git will complain.

:::

## Setup

In your Kotlin project, find or create the `.github/workflows` folder. Create a `yml` file in it. Call it something descriptive, like `kmmbridgepublish.yml`. From there, add workflow triggers, and call our workflow:

```yaml
name: KMM Bridge Publish Release
on:
  workflow_dispatch:
  push:
    branches:
      - "main"
jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@{{WORKFLOW_VERSION_NAME}}
    secrets:
      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}
```

Notice the "on" section. In our example, you can run the workflow manually with `workflow_dispatch:`, or have it run whenever you push to the main branch:

```yaml
  push:
    branches:
      - "main"
```

Then call our workflow:

```yaml
jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@{{WORKFLOW_VERSION_NAME}}
    secrets:
      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}
```

If you are publishing to Cocoapods, you'll likely need the `PODSPEC_SSH_KEY` secret. See [COCOAPODS_GITHUB_PODSPEC](../cocoapods/COCOAPODS_GITHUB_PODSPEC) for more detail.

## Params

There are 4 optional parameters:

* gradle_params - If your Gradle build needs custom params, like properties, pass them here.
* PODSPEC_SSH_KEY - The SSH key for publishing.
* module - the module to run the publish task in, used when you have multiple publishable modules
* publishTask - the task to run if not `kmmBridgePublish`. If using this make sure the task you give will also run `kmmBridgePublish`

When publishing in a CI action you may need to add the credentials to `~/.netrc` before running publish, for example to validate the podspec or to authenticate to your artifact hosting. To do this simply pass the custom `netrc` params in our GitHub Workflow.

You'll also need to add the username and password gradle params through the `gradle_params` secret in our workflow:

```yaml
jobs:
  call-kmmbridge-publish:
    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@{{WORKFLOW_VERSION_NAME}}
    with: 
      netrcMachine: touchlabartifactory.jfrog.io
    secrets:
      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}
      gradle_params: -PUSERNAME=${{ secrets.ARTIFACTORY_USERNAME}} -PPASSWORD=${{ secrets.ARTIFACTORY_PASSWORD }}
```

or set them separately like this:

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
```
