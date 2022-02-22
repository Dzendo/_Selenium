package com.example.javamavenjunitselenium3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// https://www.jetbrains.com/
public class MainPage {
    @FindBy(css = "a.wt-button_mode_primary")
    public WebElement seeAllToolsButton;

    @FindBy(xpath = "//div[contains(@class, 'menu-main__item') and text() = 'Developer Tools']")
    public WebElement toolsMenu;

    @FindBy(css = "[data-test='menu-main-icon-search']")
    public WebElement searchButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
