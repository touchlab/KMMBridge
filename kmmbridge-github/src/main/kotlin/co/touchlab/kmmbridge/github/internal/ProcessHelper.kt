package co.touchlab.kmmbridge.github.internal

import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

internal fun procRun(vararg params: String, dir: File?, processLines: (String, Int) -> Unit) {
    val processBuilder = ProcessBuilder(*params)
        .redirectErrorStream(true)
    if (dir != null) {
        println("*** Running proc in ${dir.path}")
        processBuilder.directory(dir)
    }

    val process = processBuilder
        .start()

    val streamReader = InputStreamReader(process.inputStream)
    val bufferedReader = BufferedReader(streamReader)
    var lineCount = 1

    bufferedReader.forEachLine { line ->
        processLines(line, lineCount)
        lineCount++
    }

    bufferedReader.close()
    val returnValue = process.waitFor()
    if (returnValue != 0)
        throw GradleException("Process failed: ${params.joinToString(" ")}")
}

internal fun Project.procRunFailLog(vararg params: String, dir: File? = null): List<String> {
    val output = mutableListOf<String>()
    try {
        logger.info("Project.procRunFailLog: ${params.joinToString(" ")}")
        procRun(*params, dir = dir) { line, _ -> output.add(line) }
    } catch (e: Exception) {
        output.forEach { logger.error("error: $it") }
        throw e
    }
    return output
}