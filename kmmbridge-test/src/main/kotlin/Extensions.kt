import co.touchlab.kmmbridge.findStringProperty
import co.touchlab.kmmbridge.test.TestArtifactManager
import co.touchlab.kmmbridge.test.TestUploadArtifactManager
import co.touchlab.kmmbridge.test.kmmBridgeExtension
import org.gradle.api.GradleException
import org.gradle.api.Project

@Suppress("unused")
fun Project.testArtifacts() {
    val artifactManager = kmmBridgeExtension.artifactManager
    artifactManager.set(TestArtifactManager())
    artifactManager.finalizeValue()
}

/**
 * This is for Touchlab use. See the code for more details.
 */
@Suppress("unused")
fun Project.testUploadArtifacts() {
    val server = findStringProperty("TOUCHLAB_TEST_ARTIFACT_SERVER")
    val code = findStringProperty("TOUCHLAB_TEST_ARTIFACT_CODE")

    if (server == null || code == null) {
        throw GradleException("TODO: Figure out a way for forks to not fail builds. But not today...")
    }

    val am = TestUploadArtifactManager(server, code)

    val artifactManager = kmmBridgeExtension.artifactManager
    artifactManager.set(am)
    artifactManager.finalizeValue()
}