package ru.cs.tdm.cases

import ru.cs.tdm.data.ConfProperties
import org.openqa.selenium.WebDriver
import org.junit.jupiter.api.BeforeEach
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.cs.tdm.code.Login
import java.time.Duration

/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь удостоверяется в успешной аутентификации — об этом свидетельствует имя пользователя в верхнем правом углу окна;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
class LoginTest {
    // переменная для драйвера
    private lateinit var driver: WebDriver
    private lateinit var login: Login

    /**
     * осуществление первоначальной настройки
     */
    @BeforeEach
    fun setup() {
        // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()
        //окно разворачивается на полный экран
        driver.manage().window().maximize()
        // задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        //получение ссылки на страницу входа из файла настроек
        val loginpageTDM = ConfProperties.getProperty("loginpageTDM")
        driver.get(loginpageTDM)
        login = Login(driver)

    }

    /**
     * тестовый метод для осуществления аутентификации
     */
    @Test
    fun loginTest() {

        //значение login/password берутся из файла настроек по аналогии с chromedriver и loginpage
        val loginTDM = ConfProperties.getProperty("loginTDM")
        val passwordTDM = ConfProperties.getProperty("passwordTDM")

        login.loginIn(loginTDM, passwordTDM)

        //получаем отображаемый логин
        val user = login.loginUserName() // Халтура - button
        //и сравниваем его с логином из файла настроек
        assertEquals(loginTDM, user)
    }

    @AfterEach
        fun tearDown() {
            login.loginOut()
            driver.quit() //  закрытия окна браузера
        }
}