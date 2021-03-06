package ru.cs.tdm.cases

import ru.cs.tdm.pages.MainViewHeaderPage
import org.openqa.selenium.WebDriver
import org.junit.jupiter.api.BeforeEach
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.cs.tdm.code.Login
import ru.cs.tdm.data.ConfProperties
import java.time.Duration

/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь удостоверяется в успешной аутентификации — об этом свидетельствует имя пользователя в верхнем правом углу окна;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
@DisplayName("Testing MainViewHeaderTest")
class MainViewHeaderTest {
    // переменная для драйвера
    private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
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
        mainViewHeaderPage = MainViewHeaderPage(driver)
        //окно разворачивается на полный экран
        driver.manage().window().maximize()
        // задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        val loginpageTDM = ConfProperties.getProperty("loginpageTDM")
        driver.get(loginpageTDM)
        val loginTDM = ConfProperties.getProperty("loginTDM")
        val passwordTDM = ConfProperties.getProperty("passwordTDM")

        Login(driver).loginIn(loginTDM, passwordTDM)
    }

    /**
     * тестовый метод для осуществления аутентификации
     */

    @Test
    @DisplayName("Testing each menu separately")
    fun menuTest() = repeat(1) {
            // mainViewHeaderPage.ClickTDMSWeb()
            assertTrue(mainViewHeaderPage.ClickTDMSWeb())
            assertEquals("TDM365", mainViewHeaderPage.title())

            assertTrue(mainViewHeaderPage.ClickDesktop())
            assertEquals("Рабочий", mainViewHeaderPage.title())
            assertTrue(mainViewHeaderPage.ClickObjects())
            assertEquals("TDM365", mainViewHeaderPage.title())
            assertTrue(mainViewHeaderPage.ClickMail())
            assertEquals("Почта", mainViewHeaderPage.title())
            assertTrue(mainViewHeaderPage.ClickChat())
            assertEquals("Совещания", mainViewHeaderPage.title())
            assertTrue(mainViewHeaderPage.ClickHelp())
            //assertEquals("undefined", mainViewHeaderPage.title())
            assertTrue(mainViewHeaderPage.ClickObjects())
            assertTrue(mainViewHeaderPage.InputSearch("Лебедев"))
            assertTrue(mainViewHeaderPage.ClickSearchEnter()) // КОСТЫЛЬ посылаю Enter вместо Лупы
            assertEquals("Результаты", mainViewHeaderPage.title())
            // mainViewHeaderPage.ClickMagnifier(); // не работает
            assertTrue(mainViewHeaderPage.ClickMessages())
            //assertEquals("Результаты", mainViewHeaderPage.title())
            assertTrue(mainViewHeaderPage.CloseMessages()) // КОСТЫЛЬ посылаю ESC вместо крестика закрытия окна

            // mainViewHeaderPage.entryMenu() // Халтура - button
            // mainViewHeaderPage.entryMenu() // Халтура - button

            //mainViewHeaderPage.userLogout()
            //получаем отображаемый логин
            //val user = mainViewHeaderPage.firstUserName // Халтура - button
            //и сравниваем его с логином из файла настроек
            //assertEquals(ConfProperties.getProperty("loginTDM"), user)
    }
    @AfterEach
        fun tearDown() {
        Login(driver).loginOut()
        driver.quit() //  закрытия окна браузера
        }
}