import org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage
import org.junit.platform.launcher.Launcher
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestPlan
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.SummaryGeneratingListener

// https://www.programcreek.com/java-api-examples/?api=org.junit.platform.launcher.Launcher

//@JvmStatic
fun main(args: Array<String>) {
    // Discover and filter tests
    val request: LauncherDiscoveryRequest = LauncherDiscoveryRequestBuilder
        .request()
        .selectors(
            selectPackage("io.github.bonigarcia"),
            //selectClass(HeadRef::class.java)
        )
        .filters(includeClassNamePatterns(".*Test")).build()
    val launcher: Launcher = LauncherFactory.create()
    val plan: TestPlan = launcher.discover(request)

    // Executing tests
    val listener: TestExecutionListener = SummaryGeneratingListener()
    launcher.registerTestExecutionListeners(listener)
    launcher.execute(request, listener)
}