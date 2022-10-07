package co.touchlab.faktory

import co.touchlab.faktory.internal.procRunFailLog
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkConfig
import java.io.*
import java.util.*

interface KmmBridgeExtension {
    /**
     * The name of the kotlin framework, which will be wrapped into a cocoapod. May be the same or different from podName.
     * This should be the same as
     */
    val frameworkName: Property<String>

    val dependencyManagers: ListProperty<DependencyManager>

    val artifactManager: Property<ArtifactManager>

    val buildType: Property<NativeBuildType>

    val versionManager: Property<VersionManager>

    val versionPrefix: Property<String>

    fun s3PublicArtifacts(
        region: String,
        bucket: String,
        accessKeyId: String,
        secretAccessKey: String,
        makeArtifactsPublic: Boolean = true,
        altBaseUrl: String? = null,
    ) {
        artifactManager.set(
            AwsS3PublicArtifactManager(
                region,
                bucket,
                accessKeyId,
                secretAccessKey,
                makeArtifactsPublic,
                altBaseUrl
            )
        )
    }

    fun githubReleaseArtifacts(
        artifactRelease: String? = null
    ) {
        artifactManager.set(GithubReleaseArtifactManager(artifactRelease))
    }

    fun Project.faktoryServerArtifacts(faktoryReadKey: String? = null) {
        artifactManager.set(FaktoryServerArtifactManager(faktoryReadKey, this))
    }

    fun timestampVersions() {
        versionManager.set(TimestampVersionManager)
    }

    fun gitTagVersions() {
        versionManager.set(GitTagVersionManager)
    }

    fun githubReleaseVersions() {
        versionManager.set(GithubReleaseVersionManager)
    }

    fun manualVersions() {
        versionManager.set(ManualVersionManager)
    }

    fun Project.spm(
        spmDirectory: String? = null,
        packageName: String = project.name,
    ) {
        val swiftPackageFolder = spmDirectory ?: projectDir.path
        val dependencyManager = SpmDependencyManager(swiftPackageFolder, packageName)
        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }

    fun Project.cocoapods(specRepoUrl: String?) {
        kotlin.cocoapods // This will throw error if we didn't apply cocoapods plugin

        val specRepo = if (specRepoUrl == null) SpecRepo.Trunk else SpecRepo.Private(specRepoUrl)

        val dependencyManager = CocoapodsDependencyManager(specRepo)
        dependencyManagers.set(dependencyManagers.getOrElse(emptyList()) + dependencyManager)
    }
}

interface DependencyManager {
    /**
     * Do configuration specific to this `DependencyManager`. Generally this involves creating tasks that depend on
     * [uploadTask] and are dependencies of [publishRemoteTask].
     */
    fun configure(project: Project, uploadTask: Task, publishRemoteTask: Task) {}

    /**
     * True if this type of dependency needs git tags to function properly (currently SPM true, Cocoapods false)
     */
    val needsGitTags: Boolean
}

interface ArtifactManager {
    /**
     * Send the thing, and return a link to the thing...
     */
    fun deployArtifact(project: Project, zipFilePath: File, version: String): String
}

interface VersionManager {
    /**
     * Compute a final version to use for publication, based on the plugin versionPrefix
     */
    fun getVersion(project: Project, versionPrefix: String): String

    /**
     * Versions that need to write somewhere need to do it after everything else is done.
     * Called after dependency managers are done.
     */
    fun recordVersion(project: Project, versionString: String)
}

/**
 * Write version to git tags
 */
internal fun writeGitTagVersion(project: Project, versionString: String) {
    project.procRunFailLog("git", "tag", "-a", versionString, "-m", "KMM release version $versionString")
    project.procRunFailLog("git", "push", "--follow-tags")
}

internal const val TASK_GROUP_NAME = "kmmbridge"
private const val EXTENSION_NAME = "kmmbridge"

@Suppress("unused")
class KMMBridgePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        val extension = extensions.create<KmmBridgeExtension>(EXTENSION_NAME)
        extension.dependencyManagers.convention(emptyList())
        extension.buildType.convention(NativeBuildType.RELEASE)

        // Don't call `kotlin` directly as that'd create an order dependency on the Kotlin Multiplatform plugin
        val fallbackVersion = project.provider {
            kotlin.cocoapodsOrNull?.version ?: version.toString()
        }
        extension.versionPrefix.convention(fallbackVersion)

        afterEvaluate {
            if (!extension.artifactManager.isPresent) {
                error("You must apply an artifact manager! Call `artifactManager.set(...)` or a configuration function like `githubRelease()` in your `kmmbridge {}` block.")
            }

            configureXcFramework()
            configureDeploy()
        }
    }

    // Collect all declared frameworks in project and combine into xcframework
    private fun Project.configureXcFramework() {
        val extension = extensions.getByType<KmmBridgeExtension>()
        var xcFrameworkConfig: XCFrameworkConfig? = null

        val spmBuildTargets: Set<String> = project.spmBuildTargets?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }?.toSet() ?: emptySet()

        kotlin.targets
            .withType<KotlinNativeTarget>()
            .filter { it.konanTarget.family.isAppleFamily }
            .flatMap { it.binaries.filterIsInstance<Framework>() }
            .forEach { framework ->
                val theName = framework.baseName
                val currentName = extension.frameworkName.orNull
                if (currentName == null) {
                    extension.frameworkName.set(theName)
                } else {
                    if (currentName != theName) {
                        throw IllegalStateException("Only one framework name currently allowed. Found $currentName and $theName")
                    }
                }
                val shouldAddTarget = spmBuildTargets.isEmpty() || spmBuildTargets.contains(framework.target.konanTarget.name)
                if(shouldAddTarget) {
                    if (xcFrameworkConfig == null) {
                        xcFrameworkConfig = XCFramework(theName)
                    }
                    xcFrameworkConfig!!.add(framework)
                }
            }
    }

    private fun Project.configureDeploy() {
        val extension = extensions.getByType<KmmBridgeExtension>()

        val xcFrameworkPath = "$buildDir/XCFrameworks/${extension.buildType.get().getName()}"
        val artifactManager = extension.artifactManager.get()
        val zipFile = zipFilePath()

        val zipTask = task<Zip>("zipXCFramework") {
            group = TASK_GROUP_NAME
            dependsOn(findXCFrameworkAssembleTask())

            from(xcFrameworkPath)
            destinationDirectory.set(zipFile.parentFile)
            archiveFileName.set(zipFile.name)
        }

        val dependencyManagers = extension.dependencyManagers.get()
        val uploadTask = task("uploadXCFramework") {
            group = TASK_GROUP_NAME

            dependsOn(zipTask)
            inputs.file(zipFile)
            outputs.files(urlFile, versionFile)
            outputs.upToDateWhen { false } // We want to always upload when this task is called

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    val versionManager = extension.versionManager.orNull ?: throw GradleException("versionManager must be specified")
                    val version = versionManager.getVersion(project, extension.versionPrefix.get())
                    versionFile.writeText(version)
                    logger.info("Uploading XCFramework version $version")
                    val deployUrl = artifactManager.deployArtifact(project, zipFile, version)
                    urlFile.writeText(deployUrl)
                }
            })
        }

        val publishRemoteTask = task("kmmBridgePublish") {
            group = TASK_GROUP_NAME
            dependsOn(uploadTask)

            @Suppress("ObjectLiteralToLambda")
            doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    extension.versionManager.get().recordVersion(project, versionFile.readText())
                }
            })
        }

        for (dependencyManager in dependencyManagers) {
            dependencyManager.configure(this, uploadTask, publishRemoteTask)
        }
    }
}

internal fun Project.findXCFrameworkAssembleTask(buildType: NativeBuildType? = null): Task {
    val extension = extensions.getByType<KmmBridgeExtension>()
    val name = extension.frameworkName.get()
    val buildTypeString = (buildType ?: extension.buildType.get()).getName().capitalize()
    val taskWithoutName = "assemble${buildTypeString}XCFramework"
    val taskWithName = "assemble${name.capitalize()}${buildTypeString}XCFramework"
    return try {
        tasks.findByName(taskWithName) ?: tasks.findByName(taskWithoutName)!!
    } catch (e: NullPointerException) {
        throw UnknownTaskException("Cannot find XCFramework assemble task. Tried ${taskWithName} and ${taskWithoutName}.", e)
    }
}
