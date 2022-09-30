# Faktory in GitHub Actions - Cocoapods
When using the Faktory cocoapods configuration to publish to a private podspec repo, 
you'll need to do some extra setup in your CI action to give the runner access to the podspec repo. This doc will
give examples for GitHub actions but the same general steps should apply to other CI setups.

## Using Deploy Keys
### Create a Deploy Key
[GitHub](https://docs.github.com/en/developers/overview/managing-deploy-keys#deploy-keys) and [Gitlab](https://docs.gitlab.com/ee/user/project/deploy_keys/)
have a built-in way to provide access to private repos through ssh keys made specifically for pushing with credentials 
that aren't associated to an individual account. With these you can give your CI task access to only that repo. 

To set up a deploy key, create an ssh public/private key pair on your local machine using the following command

`ssh-keygen -t ed25519 -f deploykey -C "git@github.com:<ORG>/<PODSPEC REPO>"`

- `-f deploykey` gives a custom name `deploykey` to the generated keys and will put both keys in the current directory. If 
you run this command in your repo make sure you delete these files after finishing setup and do NOT commit them to your repo.

- `-C "git@github.com:<ORG>/<PODSPEC REPO>"` adds the repo as a comment to the key that gives the ssh client a hint on when to 
use this key. This is optional but recommended. 

After running this command your working directory will have two files `deploykey`, the generated private key, and `deploykey.pub`, the generated private key. 
`deploykey.pub` is what you will set up as a deploy key in your Podspec repo, `deploykey` will need to be added in the 
ssh agent in your CI actions in order to pull/push from the podspec repo. 

To add the deploy key, go to settings in the podspec repo (you'll need admin access) and look for `Deploy Keys`. Click
Add New, give it a meaningful name like, and copy the contents of `deploykey.pub`. Make sure to allow write access so your 
build can push to it. Once you save these changes you can delete `deploykey.pub` from your machine. 

### Using Deploy Key in CI 
To use the private deploy key in CI, first you'll have to put the contents of `deploykey` into a CI secret. Samples will 
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
            - run: ./gradlew publishRemoteFramework -PFAKTORY_SECRET_KEY=${{ secrets.FAKTORY_KEY }}
...
```

For this to work you'll need to make sure you use the ssh address of your repo in the Faktory config block, not the `https` address.
If you need to access your podspec repo over HTTPS see [Using HTTPS](#using-htps)
```kotlin
kmmbridge {
    faktoryReadKey.set("READKEY")
    cocoapods("git@github.com:<ORG>/<REPO>.git")
    // NOT THIS
    // cocoapods("https://github.com/ORG/REPO.git")
}
```
### Reading from iOS Repo
If you have a multirepo setup, and you want your separate iOS repo to be able to pull the remote podspec from a private repo 
for CI, you'll need to repeat the steps for creating, adding, and using a deploy key above. When you add a deploy key for 
a consumer only you should leave write access off to limit the permissions as much as possible.

See [the accompanying iOS repo](https://github.com/touchlab/FaktoryMultirepoDemoXcode) for an example. 

## Using HTTPS
If you need to be able to access the Podspec repo via HTTPS in CI you'll need to do a bit of extra configuration in your 
`build.gradle` and `Podfile`. GitHub recommends Deploy Keys to access private repos from CI, but these changes can also
be helpful if your team is split between using HTTPS and ssh to clone git repos. 

### Taking Podspec URL as a Property
Accessing a private GitHub repo from CI requires us to change the Podspec repo URL to include a username and Personal Access Token which 
we don't want to check in to source control, so we need to be able to take the URL as a gradle property. 

```kotlin
kmmbridge {
    // You can also make this default to an empty string if your developers don't need to run the publish task locally
    val PODSPEC_URL: String = project.property("PODSPEC_URL") as String
    faktoryReadKey.set("READKEY")
    cocoapods(PODSPEC_URL)
}
```

You'll also need to change how you add the Podspec source in your Podfiles so it can change based on the local environment. 
To do that we'll add the repo globally and have the Podfile refer to the local reference rather than a URL. 

```ruby
// This handles users who have a custom cocoapods home directory
if ENV.include?("CP_HOME_DIR")
  source ENV["CP_HOME_DIR"] + '/repos/touchlab'
else
  source ENV["HOME"] + '/.cocoapods/repos/touchlab'
end
```
In the snippet above `touchlab` is an alias for the private podspec repo. Make sure to use the same alias when you add the repo.

### HTTPS In CI
In order to access your private Podspec repo via HTTPS, first you'll need to [generate a Personal Access Token(PAT)](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token). 
In order to limit the permission available to the CI runner, it is recommended you do this on a service account separate from 
any individual user, so you can control repo access for the runner. 

Once you have a PAT, you need to add it to your repos action secrets, we'll call it `HTTPS_AUTH` and give the value 
`USERNAME:PAT` where `USERNAME` is the name of the runner user you made, and `PAT` is the PAT you created.  

Once that's added you can pass the url with the authentication as a command line property to the publish task 
```yaml
run: |
  ./gradlew publishRemoteFramework -PFAKTORY_SECRET_KEY=${{ secrets.FAKTORY_WRITE_KEY }} -PPODSPEC_URL=https://${{ secrets.HTTPS_AUTH }}@github.com/touchlab/Podspecs.git
```

For your iOS builds to have access to the podspec repo, you have to first add it using the url with authentication. Make sure 
to add it to the secrets of the iOS repo as well if you have a multirepo project.
```yaml
run: |
  pod repo add touchlab https://${{ secrets.HTTPS_AUTH }}@github.com/touchlab/Podspecs.git
  pod install
  ...
```

### HTTPS Locally
If you've set your build up to take the podspec URL as a property either for CI or to allow developers to use SSH or HTTPS, 
you'll need to make sure that is available in your local environment. 

To set up the gradle property, you can either set an environment variable or set it in `gradle.properties` in your 
`GRADLE_USER_HOME` directory. 
```
// gradle.properties
PODSPEC_URL=<https or ssh url>
// .bashrc/.zshrc/etc 
export ORG_GRADLE_PROJECT_PODSPEC_URL=https\://github.com/touchlab/PodSpecs.git
```