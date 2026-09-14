# KMMBridge Project Tests

Gradle guidance suggests using include builds to test Gradle plugins. However, after a significant effort to avoid JVM classpath and other related issues, we decided to run tests by starting external Gradle builds.

To do that, we need to locally publish KMMBridge, then point tests projects at that new version. For each test:

* A temp folder is constructed
* A sample app project is copied to that folder
* A command line process is run, generally running Gradle to perform whatever task we intend to test

## Publishing KMMBridge locally

We want to publish KMMBridge as version `9.9.9`. This is a fake local version, just for testing. To do that, run the following on the root folder of KMMBridge:

```shell
./gradlew publishToMavenLocal -PVERSION_NAME=9.9.9 
```

## Editing the test project

Our simple test project lives at `test-projects/basic`. It points at KMMBridge version `9.9.9`. You should be able to open it directly with IJ or AS.

## Tests

See class `co.touchlab.kmmbridge.SimplePluginTest`. `fun setup()` copies the test project an initializes the test project.

Here is a sample test:

```kotlin
@Test
fun runSpmDevBuild() {
    val result = ProcessHelper.runSh("./gradlew spmDevBuild --stacktrace", workingDir = testProjectDir)
    logExecResult(result)
    assertEquals(0, result.status)
}
```