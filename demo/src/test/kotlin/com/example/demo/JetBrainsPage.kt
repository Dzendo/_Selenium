package com.example.demo

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// https://www.jetbrains.com/
class JetBrainsPage(driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }
    //@FindBy(css = "[data-test='search-input']")
    @FindBy(xpath = "//input[@data-test='search-input']")
    lateinit var searchField : WebElement

    /**
     * Нажатие на Advanced search Ctrl+K в выпадающем списке под поиском
     */
    //@FindBy(css = "button[data-test='full-search-button']")
    @FindBy(xpath = "//button[@data-test='full-search-button']")
    lateinit var submitButton: WebElement

    /**
     * Поле поиск в новом отрывшемся окне
     */
    //@FindBy(css ="input[data-test='search-input']")
    @FindBy(xpath ="//input[@data-test='search-input']")
    lateinit var searchPageField: WebElement

    @FindBy(css = "a.wt-button_mode_primary")
    lateinit var seeAllToolsButton: WebElement

    @FindBy(xpath = "//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']")
    lateinit var toolsMenu: WebElement

    @FindBy(css ="div[data-test='main-submenu']")
    lateinit var menuPopup: WebElement

    //*[@id="js-site-header"]/div/div/div[2]/div[1]/div/div/div/div[1]/button
    //*[@data-test='site-header-search-action']

    //@FindBy(css = "[data-test='site-header-search-action']")
    @FindBy(xpath = "//button[@data-test='site-header-search-action']")
    lateinit var searchButton: WebElement

    @FindBy(id = "products-page" )
    lateinit var productsList: WebElement
}

