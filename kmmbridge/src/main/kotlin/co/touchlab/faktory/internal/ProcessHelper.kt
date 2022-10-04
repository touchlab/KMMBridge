package co.touchlab.faktory.co.touchlab.faktory.internal

import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.InputStreamReader

internal fun procRun(vararg params: String, processLines: (String, Int) -> Unit): Unit {
    val process = ProcessBuilder(*params)
        .redirectErrorStream(true)
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
    if(returnValue != 0)
        throw GradleException("Process failed: ${params.joinToString(" ")}")
}

/**
 * Run a process. If it fails, write output to gradle error log and throw exception.
 */
internal fun Project.procRunFailLog(vararg params: String):List<String>{
    val output = mutableListOf<String>()
    try {
        procRun(*params){ line, _ -> output.add(line)}
    } catch (e: Exception) {
        output.forEach { logger.error("error: $it") }
        throw e
    }
    return output
}