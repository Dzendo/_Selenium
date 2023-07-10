package ru.cs.tdm.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
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
 * ancestor - предок
 * following-sibling - следующий брат-сестра
 * descendant - потомок = дети и их внуки и правнуки и т.д.
 */
class LoginPage(val driver: WebDriver) {
    private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(10))

    /**
     * Конструктор класса, занимающийся инициализацией полей класса
     */
    init {
        PageFactory.initElements(driver, this)
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000L))  // Неявное ожидание
    }


    // <span class="Authorization_headerName__Ge1zs">Войти в TDMS</span>
    @FindBy(xpath = "//div[@id='root']//div[@data-reference='title-authorization']//span[starts-with(@class, 'Authorization_headerName_')]")
    private lateinit var  authorizationHeaderName: WebElement
    /**
     * Определение локатора поля ввода логина
     */

    @FindBy(xpath = "//input[@data-reference='login-authorization']")
    private lateinit var  loginAuthorization: WebElement

    /**
     * Определение локатора поля ввода пароля
     */

    @FindBy(xpath = "//input[@data-reference='password-authorization']") // Тема 1,2
    private lateinit var passwordAuthorization: WebElement

    /**
     * Определение кнопки входа Войти
     */

    @FindBy(xpath = "//button [@id='authorization-button']")
    private lateinit var authorizationButton: WebElement

    /**
     * Определение локатора поля нажатия на  Пользователь
     */

    //*[@id="current-user"]  title="Пользователь"
    @FindBy(xpath = "//div[@id='current-user']")
    private lateinit var currentUser: WebElement

    /**
     * Определение локатора поля имени Пользователя
     */
    // Имя пользователя - text
    @FindBy(xpath = "//div[@id='current-user']//span")
    private lateinit var currentUserName: WebElement

    /**
     * Определение локатора пункта меню Сменить пароль
     */
    //div[@id="user-change-password"]  title="Надежный пароль предотвращает несанкционированный доступ к вашей учетной записи." <span>Сменить пароль</span>
    @FindBy(xpath = "//div[@data-reference='user-change-password']")
    private lateinit var userChangePassword: WebElement

    /**
     * Определение локатора пункта меню Настройки
     */
    //*[@id="user-settings"] title="Настройки" <span>Настройки</span>
    @FindBy(xpath = "//div [@id='user-settings']")
    private lateinit var userSettings: WebElement

    /**
     * Определение локатора пункта меню Выход
     */

    //div [@id="user-logout"]  title="Выход из приложения"  <span>Выход</span>
    @FindBy(xpath = "//div [@data-reference='user-logout']")
    private lateinit var userLogout: WebElement

    /**
     * Определение локаторов ЗАГОЛОВКА текущего окна попап окна подтаерждения выхода
     * //div[@id="modalRoot"]/div[@data-modal-window='current']
     */
    // <span class="Header_headerTitle__1PFLZ">Подтверждение</span>
    // //div[@id="modalRoot"]/div[@data-modal-window='current']//span [starts-with(@class, 'Header_headerTitle')]
    @FindBy(xpath = "//span [starts-with(@class, 'Header_headerTitle')]")
    private lateinit var headerConfirmation: WebElement

    // Да
    //button [@id="accept-logout-btn"] data-reference="accept-logout-btn"  <span class="Buttons_selectionsButtonDescription__-QPev">Да</span>
    @FindBy(xpath = "//button [@data-reference='accept-logout-btn']")
    private lateinit var acceptLogoutBtn: WebElement

    // Нет
    //button [@id="deny-logout-btn"]  data-reference="deny-logout-btn" <span class="Buttons_selectionsButtonDescription__-QPev">Нет</span>
    @FindBy(xpath = "//button [@data-reference='deny-logout-btn']")
    private lateinit var denyLogoutBtn: WebElement

    // Крестик - закрыть окно
    //<button class="Header_headerButtonSelection__b26pw Header_headerButtonSelectionClose__jnL9i"
    // title="Закрыть" style="background-image: url(&quot;resources/images/tools/tool-sprites.gif&quot;);"></button>
    @FindBy(xpath = "//button [@title='Закрыть']")
    private lateinit var xClose: WebElement

    // заголовок окна ввода пароля
    fun authorizationHeaderName(): String = authorizationHeaderName.text



    /**
     * Метод для ввода логина
     */
    fun inputLogin(login: String) = webDriverWait.until(elementToBeClickable(loginAuthorization)).sendKeys(login)

    /**
     * Метод для ввода пароля
     */
    fun inputPasswd(passwd: String) = webDriverWait.until(elementToBeClickable(passwordAuthorization)).sendKeys(passwd)

    /**
     * Метод для осуществления нажатия кнопки входа в аккаунт
     */
    // Мы получаем веб элемент и даем ему команду нажать
    fun clickAuthorizationButton() = webDriverWait.until(elementToBeClickable(authorizationButton)).click()

    /**
     * Метод для осуществления выхода из TDMS
     */

    fun clickCurrentUser() = webDriverWait.until(elementToBeClickable(currentUser)).click()

    fun clickUserLogout() = webDriverWait.until(elementToBeClickable(userLogout)).click()

    fun currentUserName(): String = currentUserName.text

    /**
     * Метод для осуществления нажатия кнопки подтверждения выхода из TDMS
     */
    fun clickYesBtn() = webDriverWait.until(elementToBeClickable(acceptLogoutBtn)).click()

    fun titleContain(title: String): Boolean = webDriverWait.until(ExpectedConditions.titleContains(title))
    /**
     * Процедура, которая получает имя пользователя, обращается в tools оттуда получает имя пользователя
     * Потом сравнивает имена, если равно - то труе, если не равно - фалсе.
     * И возвращает что определила.
     */
    fun titleLoginUserNameWait(): String = currentUserName().trim().split(" ")[0]
        //.also { println("ожидание $it пользователя из меню пользователя")}
    fun getBrowserUrl(): String = driver.currentUrl

}