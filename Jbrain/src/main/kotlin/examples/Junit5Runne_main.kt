package examples
//https://www.javacodemonk.com/junit-5-platform-launcher-api-7dddb7ab

import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.launcher.Launcher
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import org.junit.platform.launcher.listeners.TestExecutionSummary
import java.io.PrintWriter


class Junit5Runner {
    private val summaryListener: SummaryGeneratingListener = SummaryGeneratingListener()
    var executionListener = CustomTestExecutionListener()
    fun runOne(testClass: Class<*> ) {
        val request: LauncherDiscoveryRequest = LauncherDiscoveryRequestBuilder.request()
            .selectors(selectClass(testClass))
            //.selectors(selectPackage("com.shunya"))
            //.selectors(selectPackage(""))
            //.filters(TagFilter.includeTags("security"))
            .build()
        val launcher: Launcher = LauncherFactory.create()
        //val testPlan: TestPlan = launcher.discover(request)
        launcher.registerTestExecutionListeners(summaryListener)
        launcher.registerTestExecutionListeners( executionListener)
        launcher.execute(request)
        val summary: TestExecutionSummary = summaryListener.summary
        summary.printTo(PrintWriter(System.out))
    }
}

        fun main() {
           Junit5Runner().runOne(JetBrainsTest2::class.java)
        }
