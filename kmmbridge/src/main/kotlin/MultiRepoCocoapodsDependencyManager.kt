package co.touchlab.faktory

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.task
import java.io.File

sealed class SpecRepo {
    object Trunk : SpecRepo()
    class Private(val url: String) : SpecRepo()
}

class MultiRepoCocoapodsDependencyManager(
    private val specRepo: SpecRepo,
    private val allowWarnings: Boolean = true
) : DependencyManager {
    override fun configure(project: Project, uploadTask: Task, publishRemoteTask: Task) {

        val podSpecFile =
            "${project.buildDir}/XCFrameworks/${project.kmmBridgeExtension.buildType.get().name.toLowerCase()}/${project.kotlin.cocoapods.name}.podspec"

        val generatePodspecTask = project.task("generateReleasePodspec") {
            inputs.files(project.urlFile, project.versionFile)
            outputs.file(podSpecFile)
            dependsOn(uploadTask)
            doLast {
                project.generatePodspec(project.file(podSpecFile))
            }
        }

        val pushRemotePodspecTask = project.task<Exec>("pushRemotePodspec") {
            group = "faktory"
            inputs.files(podSpecFile)
            dependsOn(generatePodspecTask)

            val extraArgs = if (allowWarnings) arrayOf("--allow-warnings") else emptyArray()
            when (specRepo) {
                is SpecRepo.Trunk ->
                    commandLine("pod", "trunk", "push", podSpecFile, *extraArgs)
                is SpecRepo.Private ->
                    commandLine("pod", "repo", "push", specRepo.url, podSpecFile, *extraArgs)
            }

            standardOutput = System.out
        }

        publishRemoteTask.dependsOn(pushRemotePodspecTask)
    }
}

// Adapted from spec generation logic in the kotlin.cocoapods plugin, but we skip script phases and some other details,
// and we read straight from the project and cocoapods extension rather than task properties. We also ignore source and
// insert our deploy URL instead, and include our own version logic.
// TODO it might be nice to migrate this back to using the kotlin.cocoapods podspec task directly, but not worth the
//  effort to wire it up right now.
private fun Project.generatePodspec(outputFile: File) = with(kotlin.cocoapods) {
    val deploymentTargets = run {
        listOf(ios, osx, tvos, watchos).filter { it.deploymentTarget != null }.joinToString("\n") {
            if (extraSpecAttributes.containsKey("${it.name}.deployment_target")) "" else "|    spec.${it.name}.deployment_target = '${it.deploymentTarget}'"
        }
    }

    val dependencies = pods.map { pod ->
        val versionSuffix = if (pod.version != null) ", '${pod.version}'" else ""
        "|    spec.dependency '${pod.name}'$versionSuffix"
    }.joinToString(separator = "\n")

    val vendoredFramework = "${frameworkName}.xcframework"
    val vendoredFrameworks =
        if (extraSpecAttributes.containsKey("vendored_frameworks")) "" else "|    spec.vendored_frameworks      = '$vendoredFramework'"

    val libraries =
        if (extraSpecAttributes.containsKey("libraries")) "" else "|    spec.libraries                = 'c++'"

    val customSpec = extraSpecAttributes.map { "|    spec.${it.key} = ${it.value}" }.joinToString("\n")

    val url = urlFile.readText()
    val version = versionFile.readText()

    // 'Accept: application/octet-stream' needed for github release file downloads
    outputFile.writeText(
        """
            |Pod::Spec.new do |spec|
            |    spec.name                     = '$name'
            |    spec.version                  = '$version'
            |    spec.homepage                 = ${homepage.orEmpty().surroundWithSingleQuotesIfNeeded()}
            |    spec.source                   = { 
            |                                      :http => '${url}',
            |                                      :type => 'zip',
            |                                      :headers => ['Accept: application/octet-stream']
            |                                    }
            |    spec.authors                  = ${authors.orEmpty().surroundWithSingleQuotesIfNeeded()}
            |    spec.license                  = ${license.orEmpty().surroundWithSingleQuotesIfNeeded()}
            |    spec.summary                  = '${summary.orEmpty()}'
            $vendoredFrameworks
            $libraries
            $deploymentTargets
            $dependencies
            $customSpec
            |end
        """.trimMargin()
    )
}

private fun String.surroundWithSingleQuotesIfNeeded(): String =
    if (startsWith("{") || startsWith("<<-") || startsWith("'")) this else "'$this'"
