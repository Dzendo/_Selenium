package com.example.demo

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.interactions.Actions
import java.time.Duration

class JetBrainsTest {
    private lateinit var driver: WebDriver
    private lateinit var brainsPage: JetBrainsPage

    @BeforeEach
    fun setUp() {
        WebDriverManager.chromedriver().setup()
        //driver = ChromeDriver()   //  Версия 100.0.4896.60 (Официальная сборка), (64 бит)
        driver = ChromeDriver(ChromeOptions().addArguments("--window-position=2000,0"))
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get("https://www.jetbrains.com/")

        brainsPage = JetBrainsPage(driver)
    }

    @RepeatedTest(3)
    fun search() {
        brainsPage.searchButton.click()
        brainsPage.searchField.sendKeys("Selenium")
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