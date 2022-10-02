package co.touchlab.faktory.co.touchlab.faktory.internal

import java.io.BufferedReader
import java.io.InputStreamReader

internal fun <R> procRunMap(vararg params: String, processLines: (String, Int) -> R): List<R> {
    val process = ProcessBuilder(*params)
        .redirectErrorStream(true)
        .start()

    val streamReader = InputStreamReader(process.inputStream)
    val bufferedReader = BufferedReader(streamReader)
    var lineCount = 1
    val results = mutableListOf<R>()

    bufferedReader.forEachLine { line ->
        results.add(processLines(line, lineCount))
        lineCount++
    }

    bufferedReader.close()
    process.waitFor()

    return results
}

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
    process.waitFor()
}