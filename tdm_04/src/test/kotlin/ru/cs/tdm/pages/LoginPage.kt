package ru.cs.tdm.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

/**
 * При использовании Page Object элементы страниц,
 * а также методы непосредственного взаимодействия с ними,
 * выносятся в отдельный класс.
 */
/**
 * Класс LoginPage, который будет содержать локацию элементов страницы логина
 * и методы для взаимодействия с этими элементами.
 */
class LoginPage(driver: WebDriver) {
    val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(20))
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    init {
        PageFactory.initElements(driver, this)
    }

    /**
     * определение локатора поля ввода логина
     */
    //@FindBy(xpath = "//input [contains(@id, 'textfield-1054-inputEl')]")
    // @FindBy(xpath = "//input[contains(@placeholder,'Пользователь')]")  // Тема 2
    //@FindBy(xpath = "//span[contains(text(),'Пользователь:')]/../../..//input")
    //@FindBy(xpath = "//input [contains(@type, 'text') and contains(@class, 'x-form-field') and contains(@class, 'x-form-required-field')]")
    @FindBy(xpath = "//span[contains(text(),'Пользователь:')]/ancestor::label/following-sibling::div//input")
    private lateinit var  loginField: WebElement

    /**
     * определение локатора поля ввода пароля
     */
    //@FindBy(xpath = "//input [contains(@id,'textfield-1055-inputEl')]")
    // @FindBy(xpath = "//input[contains(@placeholder,'Пароль')]")  // Тема 2
    @FindBy(xpath = "//input[contains(@type, 'password')]") // Тема 1,2
    private lateinit var passwdField: WebElement

    /**
     * определение кнопки входа
     */
    //@FindBy(xpath = "//*[@id="button-1060"]")
    //@FindBy(xpath = "//span [contains(@id,'button-1060-btnInnerEl')]/ancestor::a")
    //@FindBy(xpath = "//span [contains(text(), 'Войти')]/../../..")
    @FindBy(xpath = "//span [contains(text(), 'Войти')]/ancestor::a")
    private lateinit var loginBtn: WebElement

    @FindBy(xpath ="//span[text()='Да']/ancestor::a")
    private lateinit var yesBtn: WebElement

    /**
     * метод для ввода логина
     */
    fun inputLogin(login: String) = webDriverWait.until(elementToBeClickable(loginField)).sendKeys(login)

    /**
     * метод для ввода пароля
     */
    fun inputPasswd(passwd: String) = webDriverWait.until(elementToBeClickable(passwdField)).sendKeys(passwd)

    /**
     * метод для осуществления нажатия кнопки входа в аккаунт
     */
    fun clickLoginBtn() = webDriverWait.until(elementToBeClickable(loginBtn)).click()

    /**
     * метод для осуществления нажатия кнопки подтверждения выхода из TDMS
     */
    fun ClickYesBtn() = webDriverWait.until(elementToBeClickable(yesBtn)).click()
}