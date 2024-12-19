package co.touchlab.kmmbridge

import co.touchlab.kmmbridge.artifactmanager.ArtifactManager
import co.touchlab.kmmbridge.artifactmanager.AwsS3PublicArtifactManager
import co.touchlab.kmmbridge.artifactmanager.MavenPublishArtifactManager
import co.touchlab.kmmbridge.dependencymanager.CocoapodsDependencyManager
import co.touchlab.kmmbridge.dependencymanager.DependencyManager
import co.touchlab.kmmbridge.dependencymanager.SpmDependencyManager
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.testfixtures.ProjectBuilder
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KmmBridgeExtensionTest {
    private lateinit var project: Project
    private lateinit var extension: TestKmmBridgeExtension

    @BeforeTest
    fun setup() {
        project = ProjectBuilder.builder().build()
        extension = TestKmmBridgeExtension(project)
    }

    @Test
    fun `test s3 artifact configuration`() {
        extension.apply {
            project.s3PublicArtifacts(
                region = "us-east-1",
                bucket = "test-bucket",
                accessKeyId = "test-key",
                secretAccessKey = "test-secret"
            )
        }

        val artifactManager = extension.artifactManager.get()
        assertTrue(artifactManager is AwsS3PublicArtifactManager)
    }

    @Test
    fun `test maven publish configuration`() {
        extension.apply {
            project.mavenPublishArtifacts(
                repository = "test-repo",
                publication = "test-pub",
                isMavenCentral = true
            )
        }

        val artifactManager = extension.artifactManager.get()
        assertTrue(artifactManager is MavenPublishArtifactManager)
    }

    @Test
    fun `test spm configuration`() {
        extension.apply {
            project.spm(
                spmDirectory = "test-dir",
                useCustomPackageFile = true
            )
        }

        val dependencyManager = extension.dependencyManagers.get().first()
        assertTrue(dependencyManager is SpmDependencyManager)
    }

    @Test
    @Ignore("CocoaPods plugin not loaded in test environment. Trunk specifically isn't important.")
    fun `test cocoapods trunk configuration`() {
        extension.apply {
            project.cocoapodsTrunk(
                allowWarnings = true,
                verboseErrors = true
            )
        }

        val dependencyManager = extension.dependencyManagers.get().first()
        assertTrue(dependencyManager is CocoapodsDependencyManager)
    }

    @Test
    fun `test property finalization`() {
        val testValue = "test-framework"
        extension.frameworkName.set(testValue)
        extension.frameworkName.finalizeValue()

        assertEquals(testValue, extension.frameworkName.get())
        assertTrue(extension.frameworkName.isPresent)
    }
}

private class TestKmmBridgeExtension(private val project: Project) : KmmBridgeExtension {
    override val frameworkName: Property<String> = project.objects.property(String::class.java)
    override val dependencyManagers = project.objects.listProperty(DependencyManager::class.java)
    override val artifactManager = project.objects.property(ArtifactManager::class.java)
    override val buildType: Property<NativeBuildType> = project.objects.property(NativeBuildType::class.java)
}
