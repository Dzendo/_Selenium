package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.not
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import java.time.Duration


/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 *
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
@DisplayName("Testing Tools Menu-Icons Test")
class HeadTest {
    // переменная для драйвера
    private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var tools: Tools
    lateinit var webDriverWait: WebDriverWait
    lateinit var fluentWait: FluentWait<WebDriver>

    /**
     * осуществление первоначальной настройки
     * Предупреждение: Не смешивайте неявные и явные ожидания.
     * Это может привести к непредсказуемому времени ожидания.
     * Например, установка неявного ожидания 10 секунд и явного ожидания 15 секунд
     * может привести к таймауту через 20 секунд.
     */

    @BeforeEach
    fun setup() {
        // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()

        // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
        // В качестве параметра указываем созданный перед этим объект driver,
        tools = Tools(driver)
        //окно разворачивается на полный экран
        driver.manage().window().maximize()
        // задержка на выполнение теста = 10 сек.
        webDriverWait = WebDriverWait( driver, Duration.ofSeconds(10))     // Явное ожидание
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))  // Неявное ожидание
        fluentWait = FluentWait<WebDriver>(driver)                             // Беглое ожидание
        val loginpageTDM = ConfProperties.getProperty("loginpageTDM")
        driver.get(loginpageTDM)
        val loginTDM = ConfProperties.getProperty("loginTDM")
        val passwordTDM = ConfProperties.getProperty("passwordTDM")

        Login(driver).loginIn(loginTDM, passwordTDM)
    }

    /**
     * тестовый метод нажатия на кнопки
     *
     */

    @RepeatedTest(25)
    @DisplayName("Testing each menu separately")
    fun headMenuTest()  {

        tools.qtipClickLast("Главное меню")
        assertTrue(webDriverWait.until(ExpectedConditions.titleContains("TDM365")))
        assertTrue(tools.qtipLast("Объекты")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Рабочий стол")
        assertTrue(webDriverWait.until(ExpectedConditions.titleContains("Рабочий")))
        assertTrue(tools.qtipLast("Рабочий стол")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Объекты")
        assertTrue(webDriverWait.until(ExpectedConditions.titleContains("TDM365")))
        assertTrue(tools.qtipLast("Объекты")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Почта")
        assertTrue(webDriverWait.until(ExpectedConditions.titleContains("Почта")))
        assertTrue(tools.qtipLast("Почта")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Совещания")
        assertTrue(webDriverWait.until(ExpectedConditions.titleContains("Совещания")))
        assertTrue(tools.qtipLast("Совещания")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Справка")
        assertTrue(webDriverWait.until(not(ExpectedConditions.titleContains("Справка"))))       // Исправить!!! NOT
        assertTrue(tools.qtipLast("Справка")?.getAttribute("aria-pressed") =="true")

        //input [@data-qtip = "Введите текст"] //input [contains(@data-qtip , "Введите текст")]
        tools.qtipClickLast("Объекты")
        tools.qtipLast("Введите текст")?.sendKeys("Лебедев")
        tools.qtipClickLast("Искать")
        assertTrue(webDriverWait.until(ExpectedConditions.titleContains("Результаты")))
        tools.qtipClickLast("Очистить")
        tools.qtipClickLast("Объекты")

        tools.qtipClickLast("Уведомления")
        assertEquals("Окно сообщений", tools.windowTitle())
        tools.closeXLast()
    }
    @RepeatedTest(15)
    @DisplayName("Testing Tools Box")
    fun toolsTest()  {
        tools.qtipClickLast("Главное меню")
        tools.qtipClickLast("Показать/скрыть дерево")
        tools.qtipClickLast("Показать/скрыть дерево")
        tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")
        tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")
        tools.qtipClickLast("Создать фильтр")
        tools.closeXLast()
        tools.qtipClickLast("Обновить")
        tools.qtipClickLast("Администрирование групп")
        tools.closeXLast()
        tools.qtipClickLast("Настройка Camunda")
        tools.closeXLast()
        tools.qtipClickLast("Удалить структуру объектов")
        tools.closeXLast()
        tools.qtipClickLast("Создать объект разработки")
        tools.closeXLast()
        tools.qtipClickLast("Параметры системы")
        tools.closeXLast()
        tools.qtipClickLast("Поток - Проверка статуса проекта")
        repeat(3) {
            tools.closeXLast()
        }
        tools.qtipClickLast("Поток 0 - Отправка проекта")
        tools.closeXLast()
        tools.qtipClickLast("Поток 1 - Отправка передаточного документа")
        repeat(2) {
            tools.closeXLast()
        }
        tools.qtipClickLast("Поток 2.1 - Ответ о результате передаче РЗ")
        repeat(3) {
            tools.closeXLast()
        }
        tools.qtipClickLast("Поток 3 - Отправка ответов на замечания")
        repeat(2) {
            tools.closeXLast()
        }
        tools.qtipClickLast("Настройка шаблона уведомлений")
        tools.closeXLast()
        repeat(3) {
        }
    }

    @AfterEach
    fun tearDown() {
        Login(driver).loginOut()
        driver.quit() //  закрытия окна браузера
    }
}