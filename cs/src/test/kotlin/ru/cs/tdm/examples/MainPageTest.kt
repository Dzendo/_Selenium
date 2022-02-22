package ru.cs.tdm.examples

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import java.time.Duration

class MainPageTest {
    private lateinit var driver: WebDriver
    private lateinit var mainPage: MainPage

    @BeforeEach
    fun setUp() {
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()   //  98.0.4758.102  98.0.4758.102
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get("https://www.jetbrains.com/")

        mainPage = MainPage(driver)
    }

    @Test
    fun search() {
        mainPage.searchButton.click()
        mainPage.searchField.sendKeys("Selenium")
        mainPage.submitButton.click()
        assertEquals("Selenium", mainPage.searchPageField.getAttribute("value"))
    }

    @Test
    fun toolsMenu() {
        Actions(driver)
            .moveToElement(mainPage.toolsMenu)
            .perform()
        mainPage.toolsMenu.click()
        assertTrue(mainPage.menuPopup.isDisplayed)
    }

    @Test
    fun navigationToAllTools() {
        mainPage.seeAllToolsButton.click()
        assertTrue(mainPage.productsList.isDisplayed)
        assertEquals("All Developer Tools and Products by JetBrains", driver.title)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}