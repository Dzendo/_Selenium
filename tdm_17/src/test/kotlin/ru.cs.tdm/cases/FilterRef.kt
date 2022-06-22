package ru.cs.tdm.cases

import io.github.bonigarcia.wdm.WebDriverManager
import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

/**
 * data-reference="CMD_CREATE_USER_QUERY" - кнопка создать фмльтр
 * data-reference="CMD_GET_REPORT" - Получить отчет
 * data-reference="CMD_VIEW_ONLY" Посмотреть
 * data-reference="CMD_EDIT_ATTRS" - редактировать
 * data-reference="CMD_DELETE_USER_QUERY" - удалить
 * data-reference="tabbar-FORM_USER_QUERY" закладка фильтр
 * data-reference="tabbar-FORM_USER_QUERY_RESULT" - закладка Результаты фильтрации
 * data-reference="FORM_USER_QUERY" поле закладки фильтр
 * data-reference="FORM_USER_QUERY_RESULT" поле закладки Результаты фильтрации
 * data-reference="QUERY_USER_QUERY" ЕЩЕ поле закладки Результаты фильтрации
 *
 * Засады:
 * Дерево: Встать, открыть, выделить в Дереве
 * Определить таблицу "Результаты фильтрации" - кол-во итд
 * Выбор из раскрывающегося справочника (выделить, ок)
 * Движки-ползунки (дерево, список результатов + горизонтальный, справочники)
 * Выбор даты
 * Системные вкладки - много
 * Получить результаты: Промаргивает вкладкой браузера и в загрузках файл
 * Определение красного в журнале сервера (GMT)
 *
 * Определение "когда тест больше не ждать"
 * Что далее если срыв теста где-то
 * Вызов тестов из Main
 * Вывод результатов тестов
 */
/*
Результат: 6,1,135 Не найден объект с идентификатором
100 новый - 33,74,78- раб стол - нет титла 365
100 удалить - 2 - нет титла, 4 - нет мессадж удалить, 33,74,78 - есст нечего удалять
 */
@DisplayName("Filter Test")
@TestMethodOrder(MethodOrderer.MethodName::class)
class FilterRef {
    val localDateTimeNow = LocalDate.now()  //LocalDateTime.now()
    companion object {
    const val DT: Int = 9
    const val NN:Int = 1
    // переменная для драйвера
    lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var tools: Tools

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
        if (DT>7) println("Вызов BeforeAll AdminUserTest")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()
        //окно разворачивается на полный второй экран-1500 1500 3000 2000,0
        driver.manage().window().position = Point(2000,-1000)
        //driver.manage().window().position = Point(0,-1000)
        driver.manage().window().maximize()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = ConfProperties.getProperty("loginpageTDM")
            if (DT>8) println("Открытие страницы $loginpage")
            driver.get(loginpage)
            assertTrue(driver.title == "Tdms")
            val login = ConfProperties.getProperty("loginTDM")
            val password = ConfProperties.getProperty("passwordTDM")
            if (DT>8) println("login= $login   password= $password")
            Login(driver).loginIn(login, password)
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            if (DT>7) println("Вызов AfterAll AdminUserTest")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера

        }
    }   // конец companion object

    @BeforeEach
    fun beforeEach(){

        if (DT>7) println("Начало BeforeEach FilterTest")
        val mainMenu = "Объекты"
        if (DT>8) println("Test нажатия на $mainMenu TDMS Web")
        tools.qtipClickLast(mainMenu)
//        assertTrue(tools.titleContain("TDM365"))  // сбоит нечасто заголовок страницы на создании
        assertTrue(tools.qtipPressedLast("Объекты"))

        val workTable = "Рабочий стол"
        if (DT>8) println("Test нажатия на $workTable")
        tools.qtipClickLast(workTable)
        //Thread.sleep(1000)
        tools.xpathClickLast("//span[text()= '$workTable (SYSADMIN)']") // встать в дереве на Рабочий стол (SYSADMIN)
//        assertTrue(tools.titleContain(workTable))  // сбоит 1 раз на 100
        assertTrue(tools.qtipPressedLast(workTable))
        // проверить что справа Рабочий стол (SYSADMIN)
        // Здесь проверка дерева и отображения
        tools.xpathClickLast("//span[contains(text(), 'Фильтры')]")
        Thread.sleep(1000)

        if (DT>7) println("Конец BeforeEach FilterTest")
    }
    @AfterEach
    fun afterEach(){
        if (DT>7) println("Вызов AfterEach FilterTest")
        //screenShot()
        tools.closeEsc5()
        Thread.sleep(1000)
        driver.navigate().refresh()
    }
    fun screenShot(name: String = "image") {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val sdf = SimpleDateFormat("ddMMyyyyhhmmss")
        copyFile(scrFile, File("./$name${sdf.format(Date())}.png"))
    }

     /**
     * Общий длинный тест пока : создание, редактирование, получение отчета, удаление
      */
    /**
     *  тест создание нового фильтра
     */
    @RepeatedTest(NN)
    @DisplayName("Создать фильтр")
    fun n04_CreateUserQuery(repetitionInfo: RepetitionInfo) {
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val createUser = "Создать фильтр"
        if (DT>8) println("Test нажатия на $createUser")

        tools.referenceClickLast("CMD_CREATE_USER_QUERY")
        assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"))
        tools.referenceClickLast("tabbar-FORM_USER_QUERY")
//        assertTrue(tools.referenceLast("tabbar-FORM_USER_QUERY")?.getAttribute("aria-selected") == "true")
        assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра"))
        tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
            ?.sendKeys(" $nomberFilter $localDateTimeNow")
        tools.clickOK()
        // проверка что фильтр создан
        tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateTimeNow')]")

    }
        /**
         *  тест заполнение и сохранение фильтра
         */
    @RepeatedTest(NN)
    @DisplayName("Заполнение фильтра")
    fun n05_fillingFilterTest(repetitionInfo: RepetitionInfo) {
            val nomberFilter = "${repetitionInfo.currentRepetition}"

            val fillingUser = "Редактирование объекта"
            if (DT > 8) println("Test нажатия на $fillingUser")
            tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateTimeNow')]")
            tools.referenceClickLast("CMD_EDIT_ATTRS")
            assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"))

            tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
                ?.sendKeys(" @")
            tools.xpathLast("// *[@data-reference='ATTR_QUERY_TechDoc_Num']/descendant::input")  // Обозначение
                ?.sendKeys("Обозначение $nomberFilter $localDateTimeNow")
            tools.xpathLast("// *[@data-reference='ATTR_QUERY_TechDoc_RevNum']/descendant::input")  // Изм. №
                ?.sendKeys("77")
            tools.xpathLast("// *[@data-reference='ATTR_QUERY_TechDoc_Name']/descendant::input")  // Наименование
                ?.sendKeys("Наименование $nomberFilter $localDateTimeNow")
            tools.xpathLast("// *[@data-reference='ATTR_DESCRIPTION']/descendant::textarea")  // Описание
                ?.sendKeys("Описание $nomberFilter $localDateTimeNow")

            /* tools.xpathLast("// *[@data-reference='ATTR_USER_LAST_NAME']/descendant::input")  // Фамилия
            ?.sendKeys("Фамилия")
        tools.xpathLast("// *[@data-reference='ATTR_USER_PHONE']/descendant::input")  // Телефон
            ?.sendKeys("9291234567")
        tools.xpathLast("// *[@data-reference='ATTR_USER_EMAIL']/descendant::input")  // E-mail
            ?.sendKeys("ya@ya")
       */

            //tools.clickOK()
            tools.clickOK()
        }

        /**
         *  тест редактирование пользователя
         */
        @Test
        @Disabled
        @DisplayName(" фильтра")
        fun n07_fillingFilterTest(repetitionInfo: RepetitionInfo) {
            val nomberFilter = "${repetitionInfo.currentRepetition}"
        val editUser = "Редактировать пользователя"
        if (DT>8) println("Test нажатия на $editUser")

        //val testUpFIO = "Тестовая Фамилия"

        if (DT>8) println("Редактирование $nomberFilter")
        tools.xpathLast("//div[contains(text(), '$nomberFilter')]")?.click()

        //  Редактировать пользователя data-reference="BUTTON_USER_EDIT"
        tools. referenceClickLast("BUTTON_USER_EDIT")
        assertTrue(tools.titleWait("window", "Редактирование пользователя"))

        val description = tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание

        //.sendKeys("Тестовая Фамилия $nomberFilter")
        assertTrue(description?.getAttribute("value") == "Тестовая Фамилия $nomberFilter")
        description?.sendKeys(" @")
        assertTrue(description?.getAttribute("value") == "Тестовая Фамилия $nomberFilter @")

        /**
         *  тест добавление роли пользователю
         */
        //  кнопка Добавить профиль data-reference="BUTTON_PROFILE_ADD"
        tools.referenceClickLast("BUTTON_PROFILE_ADD")
        assertTrue(tools.titleWait("tdmsSelectObjectGridDialog","Выбор профиля"))

        val profileUser = "Руководитель"
        if (DT>8) println("Test нажатия на $profileUser")

        tools.xpathLast("//span[text()= '$profileUser']/ancestor::td")?.click()
        tools.clickOK()  // закрыть выбор профиля с выбором руководителя

        assertTrue(tools.titleWait("window", "Редактирование пользователя"))
        // проверка что есть профиль руководитель


        val  description_new= tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")
        assertTrue(description_new?.getAttribute("value") == "Тестовая Фамилия $nomberFilter @")
        tools.clickOK()
        tools.clickOK()
    }

    /**
     *  тест Удаление фильтра
     */
   @RepeatedTest(NN)
   @DisplayName("Удалить фильтр")
   fun n09_DeleteUserQuery(repetitionInfo: RepetitionInfo) {
       var errors = 0
       val nomberFilter = "${repetitionInfo.currentRepetition}"
       val deleteFilter = "Удалить фильтр"
       if (DT>8) println("Test нажатия на $deleteFilter")
       tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateTimeNow')]")
       val ffff = tools.xpathLast("//div[starts-with(@id,'panel-') and contains(@id,'header')]/descendant::span[contains(text(), 'Фильтр $nomberFilter $localDateTimeNow')]")
           ?.text ?: ""
       //assertTrue(ffff == "(Все проекты) Фильтр $nomberFilter $localDateTimeNow")
       if (ffff.startsWith("(Все проекты) Фильтр $nomberFilter $localDateTimeNow").not()) {
           println("СРЫВ - нет инструментов $ffff")
           errors += 1
           tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateTimeNow')]")
       }
       //   tools.xpathLast("//div[starts-with(@id,'panel-') and contains(@id,'header')]/descendant::span[contains(text(), 'Фильтр')]")
       //       ?.getAttribute("value") ==
       //   "Фильтр $nomberFilter $localDateTimeNow")
       tools.referenceClickLast("CMD_DELETE_USER_QUERY")
       assertTrue(tools.titleWait("messagebox", "TDM365"))
       // Вы действительно хотите удалить объект "(Все проекты) Фильтр" из системы?
       //assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
       //assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра"))
       //tools.referenceClickLast("tabbar-FORM_USER_QUERY")
       // Проверить вкладку assertTrue(tools.windowTitleWait("Редактирование объекта"))
       //assertEquals("Фильтр $nomberFilter $localDateTimeNow",
       //    tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
       //        ?.getAttribute("value"))
       //sendKeys(" $nomberFilter $localDateTimeNow")
       tools.clickButton("Да")
       if (errors > 0) assertTrue(false)
   }
}

