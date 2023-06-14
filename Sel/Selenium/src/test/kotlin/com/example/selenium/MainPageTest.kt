package com.example.selenium

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration

class MainPageTest {
    private lateinit var driver: WebDriver
    private lateinit var mainPage: MainPage

    @BeforeEach
    fun setUp() {
        val options = ChromeOptions()
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*")
        driver = ChromeDriver(options)
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get("https://www.jetbrains.com/")

        mainPage = MainPage(driver)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun search() {
        mainPage.searchButton.click()

        val searchField = driver.findElement(By.cssSelector("[data-test='search-input']"))
        searchField.sendKeys("Selenium")
        val submitButton = driver.findElement(By.cssSelector("button[data-test='full-search-button']"))
        submitButton.click()

        val searchPageField = driver.findElement(By.cssSelector("input[data-test='search-input']"))
        assertEquals("Selenium", searchPageField.getAttribute("value"))
    }

    @Test
    fun toolsMenu() {
        mainPage.toolsMenu.click()

        val menuPopup = driver.findElement(By.cssSelector("div[data-test='main-submenu']"))
        assertTrue(menuPopup.isDisplayed)
    }

    @Test
    fun navigationToAllTools() {
        mainPage.seeDeveloperToolsButton.click()
        mainPage.findYourToolsButton.click()

        val productsList = driver.findElement(By.id("products-page"))
        assertTrue(productsList.isDisplayed)
        assertEquals("All Developer Tools and Products by JetBrains", driver.title)
    }
}
