package com.example.selenida

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.WebDriverRunner
import org.openqa.selenium.chrome.ChromeOptions
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

// https://ru.selenide.org/documentation/selenide-vs-selenium.html
class MainPageTest {
    private val mainPage = MainPage()

    companion object {
        @BeforeAll
        fun setUpAll() {
           // Configuration.browserSize = "1920x1080" //"1280x800"
           // WebDriverRunner.getWebDriver().manage().window().maximize()
            SelenideLogger.addListener("allure", AllureSelenide())
        }
    }

    @BeforeEach
    fun setUp() {
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        //Configuration.browserCapabilities = ChromeOptions().addArguments("--remote-allow-origins=*")
       // Configuration.browserCapabilities = ChromeOptions()  //.addArguments("--remote-allow-origins=*")
        open("https://www.jetbrains.com/")
        WebDriverRunner.getWebDriver().manage().window().maximize()
    }

    @Test
    fun search() {
        mainPage.searchButton.click()

        element("[data-test='search-input']").setValue("Selenium")  // .sendKeys("Selenium")
        //element("[data-test='search-input']").setValue("Selenium")  // .sendKeys("Selenium")
        element("button[data-test='full-search-button']").click()

        element("input[data-test='search-input']").shouldHave(attribute("value", "Selenium"))
    }

    @Test
    fun toolsMenu() {
        mainPage.toolsMenu.click()

        element("div[data-test='main-submenu']").shouldBe(visible)
    }

    @Test
    fun navigationToAllTools() {
        mainPage.seeDeveloperToolsButton.click()
        mainPage.findYourToolsButton.click()

        element("#products-page").shouldBe(visible)

        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title())
    }
}
