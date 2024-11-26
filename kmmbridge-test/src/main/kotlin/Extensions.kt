import co.touchlab.kmmbridge.test.TestArtifactManager
import co.touchlab.kmmbridge.test.kmmBridgeExtension
import org.gradle.api.Project

@Suppress("unused")
fun Project.testArtifacts() {
    val artifactManager = kmmBridgeExtension.artifactManager
    artifactManager.set(TestArtifactManager())
    artifactManager.finalizeValue()
}