package com.example.kotlingradlejunit5selenium4all_22

import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.element

// https://www.jetbrains.com/
class MainPage {
    val seeAllToolsButton = element("a.wt-button_mode_primary")
    val toolsMenu = element(byXpath("//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']"))
    val searchButton = element("[data-test='site-header-search-action']")
}
