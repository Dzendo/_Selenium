package ru.cs.tdm.examples

/**
 * Selenium - это система управления браузером  - открыть, локация, считать, нажать, вкладки и т.д.
 * jupiter - это система тестирования чего угодно старты тестов, ассерты, отчеты, сетки, и более сложные вещи
 * bonigarcia - левая приблуда, котоая подсовывает актуальный chrome driver
 */
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
/**
 * Классический пример теста переданный JB и подшаманенный, т.к. был не рабочий
 */

class JetBrainsTest {
    // Лениво объявленные переменные для WebDriver и модуля который рядом лежит
    // Они объявлены в классе, т.к. присваиваются в BeforeEach, а используются в тестах ниже
    private lateinit var driver: WebDriver
    private lateinit var brainsPage: JetBrainsPage
    // Для разных типов ожиданий; см Wait
    private lateinit var webDriverWait: WebDriverWait
    private lateinit var fluentWait: FluentWait<WebDriver>

    /**
     * Функция которая выполняется перед каждым тестом, в т.ч. перед повторными
     */
    @BeforeEach
    fun setUp() {
        println("Начало функции BeforeEach")
        // Подтягивает сюда последний chromedriver из WebDriverManager
        // io.github.bonigarcia:webdrivermanager  - дает не геммороиться с драйвером
        // Chrome дложен быть обновлен до последней версии
        WebDriverManager.chromedriver().setup()
        // Переменной driver присваиваем ссылку на драйвер браузера, который стартован
        // через эту ссылку мы будем обращаться к браузеру, давать команды и получать ответы
        // Это и есть самая важная вещь из пакета Selenium
        driver = ChromeDriver()
        // устанавливает координаты верхнего левого угла окна браузера
        driver.manage().window().position = Point(2000,-1000)
        // Развернуть полностью экран, но можно и указать размера
        driver.manage().window().maximize()
        // В Selenium есть три типа ожиданий, что очень важно т.к. браузер и сервер тормозят.
        // Явная, Не явная, Сложная - здесь стоит самая простая и используемая
        // На все команды по усмотрению Selenium ждать 10 сек. и только потом давать тайм-аут и тест упал
        // Одна команда действует на весь модуль и на весь сеанс этого драйвера
        // Она не идеальна, поэтому и существуют две другие более сложные
        //https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
        webDriverWait = WebDriverWait(driver, Duration.ofSeconds(10))     // Явное ожидание
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))  // Неявное ожидание
        fluentWait = FluentWait<WebDriver>(driver)                              // Беглое ожидание (сложное)

        // Через драйвер=ChromeDriver() мы говорим браузеру Открой эту страницу
        driver.get("https://www.jetbrains.com/")

        // Даем ссылку на класс JetBrainsPage передавая ему ссылку на драйвер=ChromeDriver()
        // а класс этот лежит в модуле рядышком в этом же пакете
        // Ссылку эту мы будем использовать в тестах по правилам Котлина
        brainsPage = JetBrainsPage(driver)
        println("Конец функции BeforeEach")
    }

//    @RepeatedTest(3)
    @Test
    fun search() {
        println("Начало функции search")
    // Мы обращаемся к классу brainsPage = JetBrainsPage(driver)
    // Зовем из него переменную searchButton и говорим ему клик
        brainsPage.searchButton.click()
    // Мы обращаемся к классу brainsPage = JetBrainsPage(driver)
    // Зовем из него переменную searchField и посылаем ему строку
        brainsPage.searchField.sendKeys("Selenium")
    // пример явного ожидания ждем до 10 сек пока кнопка не будет Clickable
    // Теоретически нельзя применять с включенным неявным ожиданием
    webDriverWait.until(ExpectedConditions.elementToBeClickable(brainsPage.submitButton))
    // Мы обращаемся к классу brainsPage = JetBrainsPage(driver)
    // Зовем из него переменную submitButton и говорим ему клик
        brainsPage.submitButton.click()
    /**
     * assertEquals - это уже Junit и никакого отношения к selenium не имеет
     * Junit о Selenium не знает и наоборот то же
     * Можно управлять браузером и ничего не спрашивать Junit 5 (jupiter)
     */
    // brainsPage.searchPageField.getAttribute("value") - берем поле и вынимаем его значени
        assertEquals("Selenium", brainsPage.searchPageField.getAttribute("value"))
        println("Конец функции search")

    }

    @RepeatedTest(3)
    fun toolsMenu() {
        Actions(driver)
            .moveToElement(brainsPage.toolsMenu)
            .perform()
        brainsPage.toolsMenu.click()
        assertTrue(brainsPage.menuPopup.isDisplayed)
    }

    @RepeatedTest(3)
    fun navigationToAllTools() {
        brainsPage.seeAllToolsButton.click()
        assertTrue(brainsPage.productsList.isDisplayed)
        assertEquals("All Developer Tools and Products by JetBrains", driver.title)
    }
    /**
     * Функция которая выполняется после каждого теста, в т.ч. после повторного
     */
    @AfterEach
    fun tearDown() {
        println("Начало функции AfterEach")
        driver.quit()
        println("Конец функции AfterEach")
    }
}