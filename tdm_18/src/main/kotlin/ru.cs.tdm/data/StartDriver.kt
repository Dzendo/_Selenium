package ru.cs.tdm.data

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

//окно разворачивается на полный второй экран-1500 1500 3000 2000,0
fun startDriver(indexBrowser: Int = TestsProperties.browserIndex, xPoint:Int = 2000, yPoint: Int = -1000): WebDriver {
    lateinit var driver: WebDriver
    //      0        1        2         3         4        5         6           7        8
    //  "Chrome", "Edge", "Firefox", "Opera", "Yandex", "Brave", "CCleaner", "IntExp", "Safari"
    when(indexBrowser) {
        0 -> {
            driver = WebDriverManager.chromedriver().create()
            //WebDriverManager.chromedriver().setup()
            //driver = ChromeDriver()
        }
        1 -> {
            driver = WebDriverManager.edgedriver().create()
           // driver = EdgeDriver()
        }
        2 -> {
            driver = WebDriverManager.firefoxdriver().create()
            //driver = FirefoxDriver()
        }
        3 -> {
            driver = WebDriverManager.operadriver().create()
            //driver = OperaD FirefoxDriver()
        }
        4 -> {
            driver = WebDriverManager.chromiumdriver().create()
        }
        5 -> {
            val option: ChromeOptions  = ChromeOptions()
                option.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe")
            WebDriverManager.chromiumdriver().setup()
            driver = ChromeDriver(option)
        }
        6 -> {
            driver = WebDriverManager.chromiumdriver().create()
        }
        7 -> {
            driver = WebDriverManager.iedriver().create()
            //driver = InternetExplorerDriver()
        }
        8 -> {
            driver = WebDriverManager.safaridriver().create()
            //driver = SafariDriver()
        }
        else -> {}
    }

    driver.manage().window().position = Point(xPoint, yPoint)
    driver.manage().window().maximize()
    return driver
}