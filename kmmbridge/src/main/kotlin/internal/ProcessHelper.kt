/*
 * Copyright (c) 2023 Touchlab.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package co.touchlab.faktory.internal

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

internal fun procRunSequence(vararg params: String, block:(Sequence<String>)->Unit) {
    val process = ProcessBuilder(*params)
        .redirectErrorStream(true)
        .start()

    val streamReader = InputStreamReader(process.inputStream)
    val bufferedReader = BufferedReader(streamReader)

    var thrown:Throwable? = null

    try {
        block(bufferedReader.lineSequence())
    } catch (e: Throwable) {
        thrown = e
    }

    bufferedReader.close()
    val returnValue = process.waitFor()
    if(returnValue != 0)
        throw GradleException("Process failed: ${params.joinToString(" ")}", thrown)

    if(thrown != null){
        throw thrown
    }
}

/**
 * Run a process. If it fails, write output to gradle error log and throw exception.
 */
internal fun Project.procRunFailLog(vararg params: String):List<String>{
    val output = mutableListOf<String>()
    try {
        logger.info("Project.procRunFailLog: ${params.joinToString(" ")}")
        procRun(*params){ line, _ -> output.add(line)}
    } catch (e: Exception) {
        output.forEach { logger.error("error: $it") }
        throw e
    }
    return output
}

/**
 * Run a process. If it fails, write output to gradle warn log and return an empty list.
 */
internal fun Project.procRunWarnLog(vararg params: String):List<String>{
    val output = mutableListOf<String>()
    try {
        logger.info("Project.procRunFailLog: ${params.joinToString(" ")}")
        procRun(*params){ line, _ -> output.add(line)}
    } catch (e: Exception) {
        output.forEach { logger.warn("warn: $it") }
        return emptyList()
    }
    return output
}

/**
 * Run a process. If it fails, write output to gradle error log and throw exception.
 */
internal fun Project.procRunFailThrow(vararg params: String):List<String>{
    val output = mutableListOf<String>()
    try {
        logger.info("Project.procRunFailLog: ${params.joinToString(" ")}")
        procRun(*params){ line, _ -> output.add(line)}
    } catch (e: Exception) {
        throw ProcOutputException("Project.procRunFailLog [failed]: ${params.joinToString(" ")}", output)
    }
    return output
}

class ProcOutputException(message: String?, val output: List<String>) : Exception(message)