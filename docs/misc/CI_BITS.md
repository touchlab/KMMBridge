# CI Bits

Some parts pulled from the cocoapods ci setup. We may want to add some back, but I don't think we need all of this.

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

You'll also need to change how you add the Podspec source in your Podfiles so it can change based on the local environment
to include authentication in CI.
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

### Running Locally
If you've set your build up to take the podspec URL as a property either for CI or to allow developers to use SSH or HTTPS,
you'll need to make sure that is available in your local environment.

To set up the gradle property, you can either set an environment variable or set it in `gradle.properties` in your
`GRADLE_USER_HOME` directory.
```
// gradle.properties
PODSPEC_URL=<https or ssh url>
// .bashrc/.zshrc/etc 
export ORG_GRADLE_PROJECT_PODSPEC_URL=<https or ssh url> 
```