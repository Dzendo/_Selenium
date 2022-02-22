package com.example.templateselenium4alltestng_01

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.testng.annotations.*
import org.testng.Assert.*

class MainPageTest {
    private val mainPage = MainPage()

    @BeforeMethod
    fun setUpAll() {
        Configuration.browserSize = "1280x800"
        SelenideLogger.addListener("allure", AllureSelenide())
    }

    @BeforeMethod
    fun setUp() {
        open("https://www.jetbrains.com/")
    }

    @Test
    fun search() {
        mainPage.searchButton.click()

        element("[data-test='search-input']").sendKeys("Selenium")
        element("button[data-test='full-search-button']").click()

        element("input[data-test='search-input']").shouldHave(attribute("value", "Selenium"))
    }

    @Test
    fun toolsMenu() {
        mainPage.toolsMenu.hover()

        element("div[data-test='menu-main-popup-content']").shouldBe(visible)
    }

    @Test
    fun navigationToAllTools() {
        mainPage.seeAllToolsButton.click()

        element("#products-page").shouldBe(visible)

        assertEquals(Selenide.title(), "All Developer Tools and Products by JetBrains")
    }
}
