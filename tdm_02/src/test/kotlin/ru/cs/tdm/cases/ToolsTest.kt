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
import org.openqa.selenium.By
import ru.cs.tdm.code.Login
import ru.cs.tdm.data.ConfProperties
import ru.cs.tdm.pages.ToolsPage
import java.time.Duration
import kotlin.concurrent.thread

/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь удостоверяется в успешной аутентификации — об этом свидетельствует имя пользователя в верхнем правом углу окна;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
@DisplayName("Testing Tools Menu-Icons Test")
class ToolsTest {
    // переменная для драйвера
    private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var mainViewHeaderPage: MainViewHeaderPage
    lateinit var toolsPage: ToolsPage

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
        toolsPage = ToolsPage(driver)
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
    @DisplayName("Testing Tools Menu-Icons")
    fun toolsTestTDM() = repeat(1) {
        println("********************Проход $it *******************************")
        Thread.sleep(3000)
        mainViewHeaderPage.ClickTDMSWeb()

        Thread.sleep(3000)
        toolsPage.toolBtn("Показать/скрыть дерево")?.click()
        Thread.sleep(3000)
        toolsPage.toolBtn("Показать/скрыть дерево")?.click()   //  toolsPage.treeBtn.click()

        Thread.sleep(3000)
        toolsPage.toolBtn("Показать/скрыть панель предварительного просмотра")?.click()
        Thread.sleep(3000)
        toolsPage.toolBtn("Показать/скрыть панель предварительного просмотра")?.click()   //  toolsPage.treeBtn.click()

        Thread.sleep(3000)
        toolsPage.toolBtn("Создать фильтр")?.click()   //toolsPage.filterBtn.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Обновить")?.click()

        Thread.sleep(3000)
        toolsPage.toolBtn("Администрирование групп")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Настройка Camunda")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Удалить структуру объектов")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Создать объект разработки")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Параметры системы")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Поток - Проверка статуса проекта")?.click()
        repeat(3) {
        Thread.sleep(3000)
        toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Поток 0 - Отправка проекта")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Поток 1 - Отправка передаточного документа")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Поток 2.1 - Ответ о результате передаче РЗ")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Поток 3 - Отправка ответов на замечания")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }

        Thread.sleep(3000)
        toolsPage.toolBtn("Настройка шаблона уведомлений")?.click()
        repeat(3) {
            Thread.sleep(3000)
            toolsPage.CloseEsc()
        }



            //assertEquals("Результаты", mainViewHeaderPage.title())
            // mainViewHeaderPage.ClickMagnifier(); // не работает
            //assertTrue(mainViewHeaderPage.ClickMessages())
            //assertEquals("Результаты", mainViewHeaderPage.title())
            //assertTrue(mainViewHeaderPage.CloseMessages()) // КОСТЫЛЬ посылаю ESC вместо крестика закрытия окна

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