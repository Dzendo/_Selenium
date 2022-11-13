package ru.cs.tdm.code

import org.junit.platform.engine.discovery.DiscoverySelectors
import org.junit.platform.launcher.Launcher
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.TestPlan
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import org.junit.platform.launcher.listeners.TestExecutionSummary
import java.io.PrintWriter

// https://www.programcreek.com/java-api-examples/?api=org.junit.platform.launcher.Launcher
// https://www.javacodemonk.com/junit-5-platform-launcher-api-7dddb7ab

//@JvmStatic
class Runner {
    //val listener: TestExecutionListener = SummaryGeneratingListener()
    val listener: SummaryGeneratingListener = SummaryGeneratingListener()
    fun runTest(testClass: Class<*> ) {
        println("############### Стартовал Test ${testClass.canonicalName}! ################")
        // Discover and filter tests
        val request: LauncherDiscoveryRequest = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(DiscoverySelectors.selectClass(testClass))
            //.selectors(selectPackage("ru.cs.tdm.cases"))
            //.filters(includeClassNamePatterns(".*Test"))
            .build()
        val launcher: Launcher = LauncherFactory.create()
        //val plan: TestPlan = launcher.discover(request)

        // Executing tests

        launcher.registerTestExecutionListeners(listener)
        //launcher.execute(request, listener)
        launcher.execute(request)
        val summary: TestExecutionSummary = listener.summary
        summary.printTo(PrintWriter(System.out))
    }
}
