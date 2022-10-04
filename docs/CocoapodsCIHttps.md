# Cocoapods CI Publishing with HTTPS

SSH git access for publishing to the podspec repo is generally recommended. However, you can use HTTPS to publish.

## Note

Publishing with HTTPS is not recommended. Consuming/reading with HTTPS is fine, and will depend on how your team 
generally likes to access git.

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