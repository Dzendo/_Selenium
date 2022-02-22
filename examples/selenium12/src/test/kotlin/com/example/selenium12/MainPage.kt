package com.example.selenium12

import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.element

// https://www.jetbrains.com/
class MainPage {
    val seeAllToolsButton = element("a.wt-button_mode_primary")
    val toolsMenu = element(byXpath("//div[contains(@class, 'menu-main__item') and text() = 'Developer Tools']"))
    val searchButton = element("[data-test='menu-main-icon-search']")
}
