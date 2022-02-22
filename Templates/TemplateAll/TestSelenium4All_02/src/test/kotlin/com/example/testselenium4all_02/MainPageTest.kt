package com.example.testselenium4all_02

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

class MainPageTest {
    private val mainPage = MainPage()

    @BeforeEach
    fun setUpAll() {
        Configuration.browserSize = "1280x800"
        SelenideLogger.addListener("allure", AllureSelenide())
    }

    @BeforeEach
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

        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title())
    }
}
