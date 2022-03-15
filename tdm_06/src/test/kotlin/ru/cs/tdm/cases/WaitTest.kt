package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import ru.cs.tdm.pages.ToolsPage
import java.time.Duration


/**
 *
 */
@DisplayName("Testing Wait Test")
class WaitTest {
    // переменная для драйвера
    private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var toolsPage: ToolsPage
    lateinit var tools: Tools

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
        toolsPage = ToolsPage(driver)
        tools = Tools(driver)
        //окно разворачивается на полный экран
        driver.manage().window().maximize()
        // задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))  // Неявное ожидание
        val waitDriver = WebDriverWait(driver, Duration.ofSeconds(10))     // Явное ожидание
        val waitFluen = FluentWait<WebDriver>(driver)                             // Беглое ожидание
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

    @RepeatedTest(3)
    @DisplayName("Testing each menu separately")
    fun headMenuTest()  {
        val sleepSec = 500L

        tools.qtipClickLast("Главное меню")
        Thread.sleep(sleepSec)
        assertEquals("TDM365", driver.title.split(" ").toTypedArray()[0])
        assertTrue(tools.qtipLast("Объекты")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Рабочий стол")
        Thread.sleep(sleepSec)
        assertEquals("Рабочий", driver.title.split(" ").toTypedArray()[0])
        assertTrue(tools.qtipLast("Рабочий стол")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Объекты")
        Thread.sleep(sleepSec)
        assertEquals("TDM365", driver.title.split(" ").toTypedArray()[0])
        assertTrue(tools.qtipLast("Объекты")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Почта")
        Thread.sleep(sleepSec)
        assertEquals("Почта", driver.title.split(" ").toTypedArray()[0])
        assertTrue(tools.qtipLast("Почта")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Совещания")
        Thread.sleep(sleepSec)
        assertEquals("Совещания", driver.title.split(" ").toTypedArray()[0])
        assertTrue(tools.qtipLast("Совещания")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Справка")
        Thread.sleep(sleepSec)
       // assertEquals("Справка", driver.title.split(" ").toTypedArray()[0])
        assertTrue(tools.qtipLast("Справка")?.getAttribute("aria-pressed") =="true")

        tools.qtipClickLast("Объекты")
        Thread.sleep(sleepSec)
        // @FindBy(xpath = "//input[contains(@placeholder,'Найти')]")
        tools.qtipClickLast("Справка")
        Thread.sleep(sleepSec)
        // assertEquals("Справка", driver.title.split(" ").toTypedArray()[0])
        assertTrue(tools.qtipLast("Справка")?.getAttribute("aria-pressed") =="true")

        // //input [@data-qtip = "Введите текст"] //input [contains(@data-qtip , "Введите текст")]
        tools.qtipClickLast("Объекты")
        Thread.sleep(sleepSec)
        tools.qtipLast("Введите текст")?.sendKeys("Лебедев")
        tools.qtipClickLast("Искать")
        Thread.sleep(sleepSec)
        assertEquals("Результаты", driver.title.split(" ").toTypedArray()[0])
        //assertTrue(tools.qtipLast("Введите текст")?.getAttribute("aria-pressed") =="true")  // Здесь не работает
        //println ( "Строка поиска = ${ tools.qtipLast("Введите текст")?.text} ")  // Пусто
        tools.qtipClickLast("Очистить")

        /**
         * <div id="window-1241_header-title-textEl" data-ref="textEl"
         * class="x-title-text x-title-text-default x-title-item"
         * unselectable="on" role="presentation"
         * >Окно сообщений</div>
         * <div id="window-1241-ghost_header-title-textEl" data-ref="textEl"
         * class="x-title-text x-title-text-default x-title-item"
         * unselectable="on" role="presentation"
         * >Окно сообщений</div>
         * //div[starts-with(@id, "window") and contains(@id, "title") and contains(@id, "textEl") and not(contains(@id, "ghost"))]
         */
        tools.qtipClickLast("Уведомления")
        Thread.sleep(sleepSec)
        val windowNext = tools.xpathLast("//div[starts-with(@id, 'window') and contains(@id, 'title') and contains(@id, 'textEl') and not(contains(@id, 'ghost'))]")?.text?:""
        assertEquals("Окно сообщений", windowNext)
        tools.closeXLast()
    }
    @RepeatedTest(3)
    @DisplayName("Testing Tools Box")
    fun toolsTestTDM()  {
        val sleepSec = 3000L
    
        Thread.sleep(sleepSec)
        tools.qtipClickLast("Главное меню")

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Показать/скрыть дерево")
        Thread.sleep(sleepSec)
        tools.qtipClickLast("Показать/скрыть дерево")

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")
        Thread.sleep(sleepSec)
        tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Создать фильтр")
        Thread.sleep(sleepSec)
        tools.closeXLast()

        Thread.sleep(sleepSec)
        toolsPage.toolBtn("Обновить")?.click()

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Администрирование групп")
        Thread.sleep(sleepSec)
        tools.closeXLast()

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Настройка Camunda")
        Thread.sleep(sleepSec)
        tools.closeXLast()

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Удалить структуру объектов")
        Thread.sleep(sleepSec)
        tools.closeXLast()

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Создать объект разработки")
        Thread.sleep(sleepSec)
        tools.closeXLast()

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Параметры системы")
        Thread.sleep(sleepSec)
        tools.closeXLast()

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Поток - Проверка статуса проекта")
        repeat(3) {
            Thread.sleep(sleepSec)
            tools.closeXLast()
        }

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Поток 0 - Отправка проекта")
        Thread.sleep(sleepSec)
        tools.closeXLast()

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Поток 1 - Отправка передаточного документа")
        repeat(2) {
            Thread.sleep(sleepSec)
            tools.closeXLast()
        }

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Поток 2.1 - Ответ о результате передаче РЗ")
        repeat(3) {
            Thread.sleep(sleepSec)
            tools.closeXLast()
        }

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Поток 3 - Отправка ответов на замечания")
        repeat(2) {
            Thread.sleep(sleepSec)
            tools.closeXLast()
        }

        Thread.sleep(sleepSec)
        tools.qtipClickLast("Настройка шаблона уведомлений")
        Thread.sleep(sleepSec)
        tools.closeXLast()
        repeat(3) {
            Thread.sleep(sleepSec)
        }
    }

    @AfterEach
    fun tearDown() {
        Login(driver).loginOut()
        driver.quit() //  закрытия окна браузера
    }
}