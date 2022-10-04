# Publishing to a Custom Podspec Repo

Cocoapods supports hosting private podspec repos. These are git repos that only host release publishing info for 
Cocoapods podspecs, not their actual code. If you are planning to publish KMM Xcode Frameworks with Cocoapods, that 
means you'll need a separate git repo dedicated to publishing podspec versoins. See the 
[Cocoapods Documentation](https://guides.cocoapods.org/making/private-cocoapods.html) for more context.

## Note

We *highly* recommend publishing from CI rather than from your local machine, although if properly configured you
can do either. Our documentation generally assumes a CI configuration.

## Overview

When using the KMM Bridge Cocoapods configuration to publish to a private podspec repo, 
you'll need to do some extra setup in your CI action to give the runner access to the podspec repo. This doc will
give examples for GitHub actions but the same general steps should apply to other CI setups.

## Using Deploy Keys

You'll need a Deploy Key to publish podspec versions to your podspec repo.  For context on Deploy Keys, see
[GitHub](https://docs.github.com/en/developers/overview/managing-deploy-keys#deploy-keys) and [Gitlab](https://docs.gitlab.com/ee/user/project/deploy_keys/).
Deploy Keys are a built-in way to provide access to private repos through ssh keys made specifically for pushing with credentials
that aren't associated to an individual account. With these you can give your CI task write access to that repo.

You will need to create a podspec repo if you have not created one already, and you'll need admin access to it to configure
your Deploy Key.

### Create a Deploy Key

To set up a deploy key, create an ssh public/private key pair on your local machine using the following command

`ssh-keygen -t ed25519 -f deploykey -C "git@github.com:<ORG>/<PODSPEC REPO>"`

- `-f deploykey` gives a custom name `deploykey` to the generated keys and will put both keys in the current directory. If 
you run this command in your repo make sure you delete these files after finishing setup and do NOT commit them to your repo.

- `-C "git@github.com:<ORG>/<PODSPEC REPO>"` adds the podspec repo as a comment to the key that gives the ssh client a hint on when to 
use this key. This is optional but recommended. 

After running this command, your working directory will have two files: `deploykey`, the private key, and `deploykey.pub`, the public key. 
The public key, `deploykey.pub`, is what you will set up as a deploy key in your Podspec repo. The private key, `deploykey`, will need to be added in the 
ssh agent in your CI actions in order to pull/push from the podspec repo. 

### Adding the Deploy Key

To add the deploy key, go to settings in the podspec repo (again, you'll need admin access) and look for `Deploy Keys`. Click
Add New, give it a meaningful name like `deploykey`, and copy the contents of `deploykey.pub`. Make sure to allow write access so your 
build can push to it. Once you save these changes you can delete `deploykey.pub` from your machine. 

### Using Deploy Key in CI 

Generally speaking, you'll want to publish to the podspec repo from your Kotlin source code repo.  To use the private deploy 
key in CI, first you'll have to put the contents of `deploykey` into a CI secret. Samples will 
assume the secret is added to GitHub as `PODSPEC_SSH_KEY`. Once the private key is stored in a secret, you can delete `deploykey` from your machine.

In GitHub actions, we use the opensource action [webfactory/ssh-agent](https://github.com/marketplace/actions/webfactory-ssh-agent)
which will handle adding the private key to the ssh agent with `ssh-add` so that the runner can push to the private repo. 

```yaml
        steps:
            - actions/checkout@v2
            # Make sure the @v0.5.4 matches the current version of the
            # action 
            - uses: webfactory/ssh-agent@v0.5.4
              with:
                  ssh-private-key: ${{ secrets.PODSPEC_SSH_KEY }}
...
```

For this to work you'll need to make sure you use the ssh address of your repo in the KMM Bridge config block, not the `https` address.
If you need to access your podspec repo over HTTPS see [CocoapodsCIHttps.md](#CocoapodsCIHttps)

```kotlin
kmmbridge {
    cocoapods("git@github.com:<ORG>/<REPO>.git")
    // NOT THIS
    // cocoapods("https://github.com/ORG/REPO.git")
}
```