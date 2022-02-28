package ru.cs.tdm.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.PageFactory

/**
 * При использовании Page Object элементы страниц,
 * а также методы непосредственного взаимодействия с ними,
 * выносятся в отдельный класс.
 */
/**
 * Класс LoginPage, который будет содержать локацию элементов страницы логина
 * и методы для взаимодействия с этими элементами.
 */
class ToolsPage(val driver: WebDriver) {
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    init {
        PageFactory.initElements(driver, this)
    }

    /**
     * определение локатора кнопки Показать/скрыть дерево  Tree.png
     * отмена - повторное нажатие
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Показать/скрыть дерево')]")
    lateinit var treeBtn: WebElement

    /**
     * определение локатора кнопки Создать фильтр
     * Отмена Esc
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Создать фильтр')]")
    lateinit var filterBtn: WebElement

    /**
     * определение локатора кнопки <--  back.png
     * Alt <-  отмена - Alt -> (Chrome)
     */
    // @FindBy(xpath = "//a[contains(@data-qtip, 'Назад')]") // ждем Омск
    @FindBy(xpath = "//span[contains(@style, 'back.png')]/ancestor::a")
    lateinit var backBtn: WebElement

    /**
     * определение локатора кнопки Обновить
     * отмена не требуется
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Обновить')]")
    lateinit var updateBtn: WebElement

    /**
     * определение локатора кнопки Системные вкладки с подменю будет расписано
     * отмена - повторное нажатие
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Системные вкладки')]")
    lateinit var systemTabsBtn: WebElement

    /**
     * определение локатора кнопки Показать/скрыть панель предварительного просмотра Info.png
     * * отмена - не требуется - повторное нажатие
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Показать/скрыть панель предварительного просмотра')]")
    lateinit var previewPanelBtn: WebElement


    /***********************************************************************/
    /**
     * определение локатора кнопки Просмотреть
     * Отмена Esc
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Просмотреть')]")
    lateinit var viewBtn: WebElement

    /**
     * определение локатора кнопки Администрирование групп
     * Отмена Esc
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Администрирование групп')]")
    lateinit var groupAdministrationBtn: WebElement

    /**
     * определение локатора кнопки Настройка Camunda
     * Отмена Esc
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Настройка Camunda')]")
    lateinit var settingCamundaBtn: WebElement

    /**
     * определение локатора кнопки Удалить структуру объектов
     * Отмена Esc
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Удалить структуру объектов')]")
    lateinit var deleteObjectStructureBtn: WebElement

    /**
     * определение локатора кнопки Создать объект разработки
     * Отмена Esc
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Создать объект разработки')]")
    lateinit var createObjectBtn: WebElement

    /**
     * определение локатора кнопки Параметры системы
     * Отмена Esc
     */
    @FindBy(xpath = "//a[contains(@data-qtip, 'Параметры системы')]")
    lateinit var systemParametersBtn: WebElement



    /**
     * метод для осуществления нажатия кнопки входа в Tools
     */
    fun toolBtn(qtip: String): WebElement?{
       val listElements =  driver.findElements(By.xpath("//a[contains(@data-qtip, \'$qtip\')]"))
        if (listElements == null) return null
        println ("найдено ${listElements.size} элементов $qtip")
        return listElements.last()
    }
    fun CloseEsc(): Boolean {
        println("закрыть Окно ")
        val action = Actions(driver)
        action.sendKeys(Keys.ESCAPE).perform()
        return true
    }
}