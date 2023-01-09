package examples
// https://www.programcreek.com/java-api-examples/?api=org.junit.platform.launcher.Launcher

import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory

fun main() {
    LauncherFactory.create().execute(
    LauncherDiscoveryRequestBuilder.request().selectors(selectClass(JetBrainsTest2::class.java)).build()
    )
}