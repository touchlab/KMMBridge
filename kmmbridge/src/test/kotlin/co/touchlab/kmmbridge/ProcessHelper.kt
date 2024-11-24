package co.touchlab.kmmbridge

import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicReference

object ProcessHelper {
    private val executorService = Executors.newFixedThreadPool(10)
    fun runSh(command: String, envVars: Map<String, String> = emptyMap(), workingDir: File = File(".")): ExecutionResult {
        return runParams("/bin/sh", "-c", command, envVars = envVars, workingDir = workingDir)
    }

    fun runParams(vararg params: String, envVars: Map<String, String> = emptyMap(), workingDir: File = File(".")): ExecutionResult {
        val processBuilder = ProcessBuilder(*params)
        processBuilder.environment().putAll(envVars)
        processBuilder.directory(workingDir)
        val process = processBuilder
            .start()

        val stdOut = readProcStream(process.inputStream)
        val errOut = readProcStream(process.errorStream)

        val returnValue = process.waitFor()

        while (!stdOut.isDone && !errOut.isDone){
            Thread.sleep(1000)
        }

        return ExecutionResult(
            status = returnValue,
            output = stdOut.result,
            error = errOut.result
        )
    }

    private fun readProcStream(iStream: InputStream): StreamCatcher {
        val atom = AtomicReference<String>("")
        val futureString = executorService.submit {
            val bufferedReader = BufferedReader(InputStreamReader(iStream))
            val allOut = bufferedReader.readText()

            bufferedReader.close()
            atom.set(allOut)
        }

        return StreamCatcher(futureString, atom)
    }

    private class StreamCatcher(val future:Future<*>, val atom:AtomicReference<String>){
        val isDone:Boolean
            get() = future.get() == null
        val result:String
            get() = atom.get()
    }
}

data class ExecutionResult(
    val status:Int,
    val output: String,
    val error: String
)