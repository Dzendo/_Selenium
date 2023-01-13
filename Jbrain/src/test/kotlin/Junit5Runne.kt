//https://www.javacodemonk.com/junit-5-platform-launcher-api-7dddb7ab

import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.launcher.Launcher
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.LoggingListener
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import org.junit.platform.launcher.listeners.TestExecutionSummary
import java.io.PrintWriter


class Junit5Runner {
    private val listener: SummaryGeneratingListener = SummaryGeneratingListener()
    //private val listenerLog : LoggingListener = LoggingListener()
    private val listenerLog : LoggingListener
        get() {
            TODO()
        }

    fun runOne(testClass: Class<*> ) {
        val request: LauncherDiscoveryRequest = LauncherDiscoveryRequestBuilder.request()
            .selectors(selectClass(testClass))
            //.selectors(selectPackage("com.shunya"))
            //.selectors(selectPackage(""))
            //.filters(TagFilter.includeTags("security"))
            .build()
        val launcher: Launcher = LauncherFactory.create()
        //val testPlan: TestPlan = launcher.discover(request)
        launcher.registerTestExecutionListeners(listener)
        launcher.execute(request)
        val summary: TestExecutionSummary = listener.summary
        //val sumsum = listener.
        summary.printTo(PrintWriter(System.out))
        //println(summary)
        println("testsSucceededCount = ${summary.testsSucceededCount}")
        println("testsFailedCount = ${listener.summary.testsFailedCount}")
        println("failures = ${listener.summary.failures[0].toString()}  size =${listener.summary.failures.size} ")
        summary.printFailuresTo(PrintWriter(System.out))

    }
}
  //  companion object {
       // @JvmStatic
        fun main() {
           Junit5Runner().runOne(JetBrainsTest0::class.java)
        }
  //  }
