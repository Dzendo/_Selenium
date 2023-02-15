package examples

import org.junit.platform.engine.TestExecutionResult
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestIdentifier
import kotlin.system.exitProcess

class CustomTestExecutionListener : TestExecutionListener {
    private var shouldPause = false
    private var shouldStop = false
    private var shouldExit = false
    private var shouldSkip = false
    override fun executionStarted(testIdentifier: TestIdentifier) {
        println("Launcher Started test:  ${testIdentifier.displayName} ${testIdentifier.source} ")

        if (shouldStop) {
            //is it possible to stop launcher and return ???
            // maybe to reset the test plan??
            // Maybe you can cancel Launcher.cancel(true) outside ?
        }
    }
    override fun executionFinished(testIdentifier: TestIdentifier, testExecutionResult: TestExecutionResult) {
        println("Launcher Finished test: ${testIdentifier.displayName}  ${testExecutionResult.status}")
        if (shouldPause) {
            println("Launcher Pausing for Resume")
                while(shouldPause) Thread.sleep(1_000L)
            println("Launcher  Resume")
        }
        if (shouldExit) {
            println("Launcher Exiting test execution")
            exitProcess(0)
        }
    }

    /**
     * @param shouldPause must be:
     * true - Pause
     * false - Resume - run
     */
    fun setShouldPause(shouldPause: Boolean) {
        this.shouldPause = shouldPause
    }
    fun setShouldStop(shouldStop: Boolean) {
        this.shouldStop = shouldStop
    }
    fun setShouldExit(shouldExit: Boolean) {
        this.shouldExit = shouldExit
    }
    fun setShouldSkip(shouldSkip: Boolean) {
        this.shouldSkip = shouldSkip
    }
}