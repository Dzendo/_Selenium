package com.example.demo

import com.example.demo.JetBrainsPage
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class JetBrainsTestWait {
    private lateinit var driver: WebDriver
    private lateinit var brainsPage: JetBrainsPage
    private lateinit var webDriverWait: WebDriverWait
    private lateinit var fluentWait: FluentWait<WebDriver>

    @BeforeEach
    fun setUp() {
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()   //  98.0.4758.102  98.0.4758.102
        driver.manage().window().maximize()
        //https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
        webDriverWait = WebDriverWait(driver, Duration.ofSeconds(10))     // Явное ожидание
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))  // Неявное ожидание
        fluentWait = FluentWait<WebDriver>(driver)                              // Беглое ожидание
        driver.get("https://www.jetbrains.com/")


        brainsPage = JetBrainsPage(driver)
    }

    @RepeatedTest(3)
    fun search() {
        brainsPage.searchButton.click()
        brainsPage.searchField.sendKeys("Selenium")
        //val submitButton =
        // webDriverWait.until(ExpectedConditions.elementToBeClickable(brainsPage.submitButton))
        // webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test='full-search-button']")))
        WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(brainsPage.submitButton))
        //submitButton.click()
        brainsPage.submitButton.click()
        assertEquals("Selenium", brainsPage.searchPageField.getAttribute("value"))
    }

    @RepeatedTest(3)
    fun toolsMenu() {
        Actions(driver)
            .moveToElement(brainsPage.toolsMenu)
            .perform()
        brainsPage.toolsMenu.click()
        assertTrue(brainsPage.menuPopup.isDisplayed)
    }

    @RepeatedTest(3)
    fun navigationToAllTools() {
        brainsPage.seeAllToolsButton.click()
        assertTrue(brainsPage.productsList.isDisplayed)
        assertEquals("All Developer Tools and Products by JetBrains", driver.title)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}