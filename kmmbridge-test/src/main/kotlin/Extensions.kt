import co.touchlab.kmmbridge.test.TestArtifactManager
import co.touchlab.kmmbridge.test.TestUploadArtifactManager
import co.touchlab.kmmbridge.test.kmmBridgeExtension
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
    val artifactManager = kmmBridgeExtension.artifactManager
    artifactManager.set(TestUploadArtifactManager())
    artifactManager.finalizeValue()
}
