package ru.cs.tdm.cases

import ru.cs.tdm.data.ConfProperties
import org.openqa.selenium.WebDriver
import org.junit.jupiter.api.BeforeEach
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.openqa.selenium.Point
import org.openqa.selenium.edge.EdgeDriver
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
        //WebDriverManager.chromedriver().setup()
        WebDriverManager.edgedriver().setup()
        //driver = ChromeDriver()
        driver = EdgeDriver()
        //driver = ChromeDriver(ChromeOptions().addArguments("--window-position=2000,0"))
        //окно разворачивается на полный второй экран-1500 1500 3000 2000,0
        driver.manage().window().position = Point(2000, 0)
        //окно разворачивается на полный экран
        driver.manage().window().maximize()
        // задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        //получение ссылки на страницу входа из файла настроек
        val loginpageTDM = ConfProperties.getProperty("loginpageTDM")
        // Драйверу командуем открыть эту страницу
        driver.get(loginpageTDM)
        // Берем ссылку на класс Логин, которую будем использовать ниже в тестах.
        login = Login(driver)
    }

    /**
     * тестовый метод для осуществления аутентификации
     */
   // Аннтотация Junit5 -Объявляем функцию тестом и повторяемым 3-раза.
    @RepeatedTest(3)
    // Аннтотация Junit5 - Отображать человеческое имя вместо имени функции
    // С рускими именами есть проблема  в настройках JB и наверное будет проблема дальше
    @DisplayName("Checking the input-output Login - Password")
    fun loginTest() {

        //значение login/password берутся из файла настроек по аналогии с chromedriver и loginpage
        val loginTDM = ConfProperties.getProperty("loginTDM")
        val passwordTDM = ConfProperties.getProperty("passwordTDM")

        login.loginIn(loginTDM, passwordTDM)

        //получаем отображаемый логин
        //val user = login.loginUserName()
        //и сравниваем его с логином из файла настроек
        //assertEquals(loginTDM, user)
        //assertTrue(login.loginUserNameWait(loginTDM))
        // assertTrue проверяет труе или фалс возвращает функция qtipLoginUserNameWait из класса login
        assertTrue(login.qtipLoginUserNameWait(loginTDM))
    }
    // После теста зовет login.loginOut(), а тот используя мой тулс через qtip нажимает на что надо, что бы выйти из логина
    // Потом закрывает окно драйвера
    @AfterEach
        fun tearDown() {
            login.loginOut()
            driver.quit() //  закрытия окна браузера
        }
}
// Мы перскачили в LoginPage через явное ожидание webDriverWait - следующий урок
// Перескачили как срабатывает мой тулс и его ожидания FluentWait упомянуты в JB test