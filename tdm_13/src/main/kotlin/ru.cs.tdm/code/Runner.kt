package ru.cs.tdm.code

import org.junit.platform.engine.discovery.DiscoverySelectors
import org.junit.platform.launcher.Launcher
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.TestPlan
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import org.junit.platform.launcher.listeners.TestExecutionSummary
import ru.cs.tdm.ui.TestsProperties.debugPrintNomber
import java.io.PrintWriter
import java.time.LocalDateTime

// https://www.programcreek.com/java-api-examples/?api=org.junit.platform.launcher.Launcher
// https://www.javacodemonk.com/junit-5-platform-launcher-api-7dddb7ab

//@JvmStatic
class Runner(private val povtor : Int) {
    //val listener: TestExecutionListener = SummaryGeneratingListener()
    val listener: SummaryGeneratingListener = SummaryGeneratingListener()
    val DT = debugPrintNomber
    fun runTest(testClass: Class<*> ) : Long {

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
        if (DT > 4) println("======= run $povtor Test ${testClass.canonicalName}! ======= ${LocalDateTime.now().withNano(0)} ============")
        launcher.registerTestExecutionListeners(listener)
        //launcher.execute(request, listener)
        launcher.execute(request)

        // Этот оператор берет от слушателя потока теста итоговые результаты (launcher Junit)
        val summary: TestExecutionSummary = listener.summary
        val failed: Long = summary.testsFailedCount
        if ((DT > 7) or (failed > 0L)) summary.printTo(PrintWriter(System.out))
        if (failed >0) summary.printFailuresTo(PrintWriter(System.out))

        // полученные результаты на печать - дает перечень результатов
        if (DT > 4) println("======= end $povtor Test ${testClass.canonicalName}! ====== Errors $failed =============")
        return failed
    }
}
