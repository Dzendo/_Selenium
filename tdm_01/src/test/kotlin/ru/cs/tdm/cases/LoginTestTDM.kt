package ru.cs.tdm.cases

import ru.cs.tdm.data.ConfProperties
import ru.cs.tdm.pages.LoginPage
import ru.cs.tdm.pages.MainViewHeaderPage
import org.openqa.selenium.WebDriver
import org.junit.jupiter.api.BeforeEach
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь удостоверяется в успешной аутентификации — об этом свидетельствует имя пользователя в верхнем правом углу окна;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
class LoginTestTDM {
    // переменная для драйвера
    private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var loginPage: LoginPage
    lateinit var mainViewHeaderPage: MainViewHeaderPage

    /**
     * осуществление первоначальной настройки
     */
    @BeforeEach
    fun setup() {
        // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()

        // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
        // В качестве параметра указываем созданный перед этим объект driver,
        loginPage = LoginPage(driver)
        mainViewHeaderPage = MainViewHeaderPage(driver)
        //окно разворачивается на полный экран
        driver.manage().window().maximize()
        // задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpageTDM"))
    }

    /**
     * тестовый метод для осуществления аутентификации
     */
    @Test
    fun loginTest() {
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //значение login/password берутся из файла настроек по аналогии с chromedriver и loginpage
        //вводим логин  ХАЛТУРА - только тема 1
        loginPage.inputLogin(ConfProperties.getProperty("loginTDM"))
        //вводим пароль
        loginPage.inputPasswd(ConfProperties.getProperty("passwordTDM"))
        //нажимаем кнопку входа
        loginPage.clickLoginBtn()

        //получаем отображаемый логин
        val user = mainViewHeaderPage.firstUserName // Халтура - button
        //и сравниваем его с логином из файла настроек
        assertEquals(ConfProperties.getProperty("loginTDM"), user)
    }

    @AfterEach
        fun tearDown() {
            mainViewHeaderPage.ClickTDMSWeb()
            mainViewHeaderPage.ClickDesktop()
            mainViewHeaderPage.ClickObjects()
            mainViewHeaderPage.ClickMail()
            mainViewHeaderPage.ClickChat()
            mainViewHeaderPage.ClickHelp()
            mainViewHeaderPage.ClickObjects()
            mainViewHeaderPage.InputSearch("Лебедев")
            mainViewHeaderPage.ClickSearchEnter() // КОСТЫЛЬ посылаю Enter вместо Лупы
            // mainViewHeaderPage.ClickMagnifier(); // не работает
            mainViewHeaderPage.ClickMessages()
            mainViewHeaderPage.CloseMessages() // КОСТЫЛЬ посылаю ESC вместо крестика закрытия окна
            mainViewHeaderPage.entryMenu() // Халтура - button
            mainViewHeaderPage.userLogout()
            mainViewHeaderPage.logoutOKBtn()
            driver.quit() //  закрытия окна браузера
        }
}