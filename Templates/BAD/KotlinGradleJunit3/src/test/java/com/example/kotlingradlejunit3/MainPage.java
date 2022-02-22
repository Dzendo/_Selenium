package com.example.kotlingradlejunit3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// https://www.jetbrains.com/
public class MainPage {
    //@FindBy(css = "a.wt-button_mode_primary")
    //@FindBy(xpath = "//svg[contains(@class, 'wt-icon wt-icon_size_m site-header-action__icon _wt-button__icon_1df2673_609')]")
    @FindBy(xpath = "//*[@id=\"js-site-header\"]/div/div/div[2]/div[2]/div/div[2]/div/div[1]/button")
    public WebElement seeAllToolsButton;

    @FindBy(xpath = "//div[contains(@class, 'menu-main__item') and text() = 'Developer Tools']")
    public WebElement toolsMenu;

    // //*[@id="js-site-header"]/div/div/div[2]/div[2]/div/div[2]/div/div[1]/button
    // data-test="site-header-mobile-search-action"
    //@FindBy(css = "[data-test='menu-main-icon-search']")
    @FindBy(css = "[data-test='site-header-mobile-search-action']")
    public WebElement searchButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
