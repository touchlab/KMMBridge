# Local publish test

To test publishing from your local machine, use the following command:

```shell
./gradlew kmmBridgePublish -PENABLE_PUBLISHING=true -PGITHUB_PUBLISH_TOKEN=[GitHub PAT] -PGITHUB_REPO=[org]/[repo] --no-daemon --stacktrace
```