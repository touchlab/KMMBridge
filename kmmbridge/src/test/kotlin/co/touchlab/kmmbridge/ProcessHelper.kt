package co.touchlab.kmmbridge

import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.atomic.AtomicReference

object ProcessHelper {
    fun runSh(
        command: String,
        envVars: Map<String, String> = emptyMap(),
        workingDir: File = File(".")
    ): ExecutionResult {
        return runParams("/bin/sh", "-c", command, envVars = envVars, workingDir = workingDir)
    }

    fun runParams(
        vararg params: String,
        envVars: Map<String, String> = emptyMap(),
        workingDir: File = File(".")
    ): ExecutionResult {
        val processBuilder = ProcessBuilder(*params)
        processBuilder.environment().putAll(envVars)
        processBuilder.directory(workingDir)
        val process = processBuilder
            .start()

        val stdOut = readProcStream(process.inputStream)
        val errOut = readProcStream(process.errorStream)

        val returnValue = process.waitFor()

        while (!stdOut.isDone && !errOut.isDone) {
            Thread.sleep(1000)
        }

        return ExecutionResult(
            params = params.toList(),
            workingDir = workingDir,
            status = returnValue,
            output = stdOut.result,
            error = errOut.result
        )
    }

    private fun readProcStream(iStream: InputStream): StreamCatcher {
        val atom = AtomicReference<String>("")
        val t = Thread {
            val bufferedReader = BufferedReader(InputStreamReader(iStream))
            val allOut = bufferedReader.readText()

            bufferedReader.close()
            atom.set(allOut)
        }

        t.start()

        return StreamCatcher(t, atom)
    }

    private class StreamCatcher(val t: Thread, val atom: AtomicReference<String>) {
        val isDone: Boolean
            get() = !t.isAlive
        val result: String
            get() = atom.get()
    }
}

data class ExecutionResult(
    val params: List<String>,
    val workingDir: File,
    val status: Int,
    val output: String,
    val error: String
)