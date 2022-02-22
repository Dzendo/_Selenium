package ru.cs.tdm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * При использовании Page Object элементы страниц,
 * а также методы непосредственного взаимодействия с ними,
 * выносятся в отдельный класс.
 *
 * Класс LoginPage, который будет содержать локацию элементов страницы логина
 * и методы для взаимодействия с этими элементами.
 */
public class LoginPage {
// HTML <input type="text" data-t="field:input-login" dir="ltr" autocorrect="off" autocapitalize="off"
// aria-invalid="false" autocomplete="username" class="Textinput-Control" id="passp-field-login" name="login"
// placeholder="Телефон или почта" value="afalinalv">
// рекомендую к прочтению статью «Не так страшен xpath как его незнание»
// Selector- #passp-field-login
// JS path-  #passp-field-login
// XPath- //*[@id="passp-field-login"]
// ful XPath- /html/body/div/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div/div/div[1]/form/div[1]/span/input
// статья- //*[@id="root"]/div/div/div[2]/div/div/div[3]/div[2]/div/div/div[1]/form/div[1]/div[1]/label
// статья- @FindBy(xpath = "//*[contains(@id, 'passp-field-login')]")

    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public WebDriver driver;
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver; }
    /**
     * определение локатора поля ввода логина
     * #passp-field-login  - selector
     * document.querySelector("#passp-field-login") - JS path
     * //*[@id="passp-field-login"] - XPath
     * /html/body/div/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div/div/div[1]/form/div[1]/span/input - Full Xpath
     * //div[@class=’buttons’ and contains(text(),’Save’)]
     */
   // @FindBy(xpath = "//*[contains(@id, 'passp-field-login')]")
    @FindBy(xpath = "//input [contains(@id, 'passp-field-login')]")
    private WebElement loginField;
    /**
     * определение локатора кнопки входа в аккаунт
     * #passp\:sign-in
     * document.querySelector("#passp\\:sign-in")
     * //*[@id="passp:sign-in"]
     */
    @FindBy(xpath = "//*[contains(text(), 'Войти')]/..")
    private WebElement loginBtn;
    /**
     * определение локатора поля ввода пароля
     */
    //@FindBy(xpath = "//*[contains(@id, 'passp-field-passwd')]")
    @FindBy(xpath = "//input[contains(@id, 'passp-field-passwd')]")
    private WebElement passwdField;
    /**
     * метод для ввода логина
     */
    public void inputLogin(String login) {
        loginField.sendKeys(login); }
    /**
     * метод для ввода пароля
     */
    public void inputPasswd(String passwd) {
        passwdField.sendKeys(passwd); }
    /**
     * метод для осуществления нажатия кнопки входа в аккаунт
     */
    public void clickLoginBtn() {
        loginBtn.click(); }

}
