package com.example.kotlingradlejunit5selenuim4_01

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// https://www.jetbrains.com/
class MainPage(driver: WebDriver) {
    @FindBy(css = "a.wt-button_mode_primary")
    lateinit var seeAllToolsButton: WebElement

    @FindBy(xpath = "//div[contains(@class, 'menu-main__item') and text() = 'Developer Tools']")
    lateinit var toolsMenu: WebElement

    @FindBy(css = "[data-test='menu-main-icon-search']")
    lateinit var searchButton: WebElement

    @FindBy(css = "[data-test='search-input']")
    lateinit var searchField: WebElement

    init {
        PageFactory.initElements(driver, this)
    }
}
