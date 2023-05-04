package ru.cs.tdm.cases

import ru.cs.tdm.data.ConfProperties
import org.openqa.selenium.WebDriver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import ru.cs.tdm.code.Login
import ru.cs.tdm.data.startDriver
import ru.cs.tdm.data.TestsProperties
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
    private lateinit var loginClass: Login
    private val threadSleep = TestsProperties.threadSleepNomber        // задержки где они есть
    private val DT: Int = TestsProperties.debugPrintNomber            // глубина отладочной информации 0 - ничего не печатать, 9 - все
    //private val NN:Int = TestsProperties.repeateTestsNomber        // количество повторений тестов
    companion object {private const val NN:Int = 3 }                   // количество повторений тестов

    /**
     * осуществление первоначальной настройки
     */
    @BeforeEach
    fun setup() {
        // Создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        driver = startDriver()
        // задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        // Берем ссылку на класс Логин, которую будем использовать ниже в тестах.
        loginClass = Login(driver)

        //получение ссылки на страницу входа из файла настроек
        val loginpageTDM = ConfProperties.getProperty("loginpageTDM")
        // Драйверу командуем открыть эту страницу
        driver.get(loginpageTDM)
        val loginpage = TestsProperties.loginpage
        if (DT > 8) println("Открытие страницы $loginpage")
        //val login = TestsProperties.login
        //val password = TestsProperties.password
        driver.get(loginpage)
        assertTrue(driver.title == "Tdms", "Браузер не имеет вкладку с заголовком Tdms ")

    }

    /**
     * Тестовый метод для осуществления аутентификации
     */
   // Аннтотация Junit5 -Объявляем функцию тестом и повторяемым 3-раза.
    @RepeatedTest(NN)
    @DisplayName("Checking the input-output Login - Password")
    fun loginTest() {

        //значение login/password берутся из файла настроек по аналогии с chromedriver и loginpage
        val login = TestsProperties.login
        val password = TestsProperties.password
        if (DT > 8) println("login= $login   password= $password")
        loginClass.loginIn(login, password)
       // loginClass.loginOut()  Ошибка Title - жду исправления
       // loginClass.loginIn(login, password)
        println(" Вошли под login= $login   password= $password")

        //получаем отображаемый логин и сравниваем его с логином из файла настроек
        assertTrue(loginClass  .titleLoginUserNameWait()=="SYSADMIN",
            "@@@@ Не вошли под пользователем $login @@")
        println(" Проверили под login= $login   password= $password")
    }
    // После теста зовет login.loginOut(), а тот нажимает на что надо, что бы выйти из логина
    // Потом закрывает окно драйвера
    @AfterEach
        fun tearDown() {
            loginClass.loginOut()
            driver.quit() //  закрытия окна браузера
        }
}
