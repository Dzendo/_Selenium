package dev.selenium.getting_started

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.ie.InternetExplorerOptions
import org.openqa.selenium.opera.OperaDriver
import org.openqa.selenium.opera.OperaOptions
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.safari.SafariOptions


class OpenBrowserTest {
    private lateinit var driver: WebDriver

    @Test
    fun chromeSession() {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
        driver = ChromeDriver(options)
        driver.quit()
    }

    @Test
    fun edgeSession() {
        WebDriverManager.edgedriver().setup()
        val options = EdgeOptions()
        driver = EdgeDriver(options)
        driver.quit()
    }

    @Test
    fun firefoxSession() {
        WebDriverManager.firefoxdriver().setup()
        val options = FirefoxOptions()
        driver = FirefoxDriver(options)
        driver.quit()
    }

    //@Disabled("Only runs on Windows")
    @Test
    fun internetExplorerSession() {
        WebDriverManager.iedriver().setup()
        val options = InternetExplorerOptions()
        driver = InternetExplorerDriver(options)
        driver.quit()
    }

    @Disabled("Only runs on Windows")
    @Test
    fun internetExplorerCompatibilitySession() {
        val options = InternetExplorerOptions()
        options.attachToEdgeChrome()
        options.withEdgeExecutablePath("/path/to/edge/browser")
        driver = InternetExplorerDriver(options)
        driver.quit()
    }

    //@Disabled("Requires non-standard browser")
    @Test
    fun operaSession() {
        WebDriverManager.operadriver().setup()
        val options = OperaOptions()
        options.setBinary("C:\\Users\\ASDze\\AppData\\Local\\Programs\\Opera\\launcher.exe")
        driver = OperaDriver(options)
        driver.quit()
        //WebDriverManager.chromiumdriver().setup().
    }

    @Disabled("Only runs on Mac")
    @Test
    fun safariSession() {
        WebDriverManager.safaridriver().setup()
        val options = SafariOptions()
        driver = SafariDriver(options)
        driver.quit()
    }
    //@Disabled("need webdriver option")
    @Test
    fun yandexSession() {
        //WebDriverManager.chromiumdriver().setup()

        // определение пути до драйвера и его настройка (берется из файла)
        System.setProperty("webdriver.chrome.driver", "N:\\_Selenium\\WebDrivers\\yandexdriver.exe")
        val options = ChromeOptions()
        //options.browserVersion = "96"
        //options.
        options.setBinary("C:\\Users\\ASDze\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe")
        driver = ChromeDriver(options)
        driver.quit()
    }
}