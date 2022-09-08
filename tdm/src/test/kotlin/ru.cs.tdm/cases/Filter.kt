package ru.cs.tdm.cases

import io.github.bonigarcia.wdm.WebDriverManager
import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.assertContains

/**
 * Халтура - недоделки:
 * 1. Поле ответственный открываю нештатно - ждем ОМСК
 * 2. Выпадающие списки (Статус, Отв лицо) не раскрываю, а вношу в поле имеющееся в списке значение
 * 3. Дату не раскрываю + Сегодня, а вношу сразу сегодня
 * 4. Жестко привязался к омшаннику, а если у И.В.
 * 5. Поиск в таблице примитивный и безграмотный
 * 6. Не выпонен кейс от слова совсем
 * 7. Не проверок на то что все правильно внеслось, проверок на рез фильтрации итд
 */

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

@DisplayName("Filter Test")
@TestMethodOrder(MethodOrderer.MethodName::class)
class Filter {
    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDateNow = LocalDate.now().format(formatter)  //LocalDateTime.now()

    companion object {
    const val threadSleep = 3000L
    const val DT: Int = 9
    const val NN:Int = 20
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
            //tools.idList()
            if (DT>7) println("Вызов AfterAll AdminUserTest")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера

        }
    }   // конец companion object

    @BeforeEach
    fun beforeEach() {

        if (DT > 7) println("Начало BeforeEach FilterTest")
        val mainMenu = "Объекты"
        if (DT > 8) println("Test нажатия на $mainMenu TDMS Web")
        tools.qtipClickLast(mainMenu)
//        assertTrue(tools.titleContain("TDM365"))  // сбоит нечасто заголовок страницы на создании
        assertTrue(tools.qtipPressedLast("Объекты"))
        if (DT>7) println("Конец BeforeEach FilterTest")
    }
    fun workTable() {
        val workTable = "Рабочий стол"
        if (DT>8) println("Test нажатия на $workTable")
        tools.qtipClickLast(workTable)
        //Thread.sleep(threadSleep)
        tools.xpathClickLast("//span[text()= '$workTable (SYSADMIN)']") // встать в дереве на Рабочий стол (SYSADMIN)
//        assertTrue(tools.titleContain(workTable))  // сбоит 1 раз на 100
        assertTrue(tools.qtipPressedLast(workTable))
        // проверить что справа Рабочий стол (SYSADMIN)
        // Здесь проверка дерева и отображения
        tools.xpathClickLast("//span[contains(text(), 'Фильтры')]")
        Thread.sleep(threadSleep)
    }
    @AfterEach
    fun afterEach(){
        if (DT>7) println("Вызов AfterEach FilterTest")
        //screenShot()
        tools.closeEsc5()
        Thread.sleep(1000)
        //driver.navigate().refresh()
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
    @DisplayName("Создать фильтры")
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
            ?.sendKeys(" $nomberFilter $localDateNow")
        tools.clickOK()
        // проверка что фильтр создан
        //tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")

    }
        /**
         *  тест заполнение и сохранение фильтра
         */
    @RepeatedTest(NN)
    @DisplayName("Заполнение текстовых полей фильтра")
    fun n05_fillingFilterTest(repetitionInfo: RepetitionInfo) {
            workTable()
            val nomberFilter = "${repetitionInfo.currentRepetition}"
            Thread.sleep(threadSleep)
            val fillingUser = "Заполнение текстовых полей фильтра"
            if (DT > 8) println("Test нажатия на $fillingUser")
            tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
            tools.referenceClickLast("CMD_EDIT_ATTRS")
            assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"))
            Thread.sleep(threadSleep)
            tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
                ?.sendKeys(" #")
            Thread.sleep(threadSleep)
            tools.xpathLast("// *[@data-reference='ATTR_QUERY_TechDoc_Num']/descendant::input")  // Обозначение
                ?.sendKeys("Обозначение $nomberFilter $localDateNow")
            Thread.sleep(threadSleep)
            tools.xpathLast("// *[@data-reference='ATTR_QUERY_TechDoc_RevNum']/descendant::input")  // Изм. №
                ?.sendKeys("77")
            Thread.sleep(threadSleep)
            tools.xpathLast("// *[@data-reference='ATTR_QUERY_TechDoc_Name']/descendant::input")  // Наименование
                ?.sendKeys("Наименование $nomberFilter $localDateNow")
            Thread.sleep(threadSleep)
            tools.xpathLast("// *[@data-reference='ATTR_DESCRIPTION']/descendant::textarea")  // Описание
                ?.sendKeys("Описание $nomberFilter $localDateNow")
            Thread.sleep(threadSleep)

            tools.clickOK()
        }

        /**
         *  тест редактирование фильтра
         */
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Заполнение ссылочных полей фильтра")
        fun n06_EditFilterTest(repetitionInfo: RepetitionInfo) {
            workTable()
            val nomberFilter = "${repetitionInfo.currentRepetition}"
            val editFilter = "Заполнение ссылочных полей фильтра"
            if (DT > 8) println("Редактирование $nomberFilter")
            tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
            if (DT > 8) println("Test нажатия на $editFilter")

            tools.referenceClickLast("CMD_EDIT_ATTRS")
            assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"))
            Thread.sleep(threadSleep)
            val description =
                tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Описание
            assertTrue(description?.getAttribute("value")!!.contains("Фильтр $nomberFilter $localDateNow"))
            description.sendKeys(" @")
            assertTrue(description.getAttribute("value").contains("@"))

            val BUTTON_OBJDEV_SEL = {
                tools.referenceClickLast("BUTTON_OBJDEV_SEL")
                assertTrue(tools.titleWait("tdmsSelectObjectGridDialog", "Выбор объекта структуры"))
                tools.xpathLast("//div[contains(text(),'Ферма_омшанник')]/ancestor::td")?.click()
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_OBJDEV_SEL()
            Thread.sleep(threadSleep)
            val attrObjectDev = tools.xpathLast("//*[@data-reference='ATTR_OBJECT_DEV']/descendant::input")
            assertContains(attrObjectDev?.getAttribute("value") ?: "NONE", "Ферма_омшанник")

            tools.referenceClickLast("BUTTON_OBJDEV_ERASE")
            Thread.sleep(threadSleep)
            assertTrue(((attrObjectDev?.getAttribute("value") ?: "NONE").length) == 0)

            BUTTON_OBJDEV_SEL()  // Всавляем еще раз омшанник

            val BUTTON_PROJECT_SEL = {
                tools.referenceClickLast("BUTTON_PROJECT_SEL")
                assertTrue(tools.titleWait("tdmsSelectObjectGridDialog", "Выбор проекта"))
                tools.xpathLast("//div[contains(text(),'Разработка проекта электроснабжения Омшанника')]/ancestor::td")
                    ?.click()                   //tr  tbody  table
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_PROJECT_SEL()
            Thread.sleep(threadSleep)
            val ATTR_RefToProject = tools.xpathLast("//*[@data-reference='ATTR_RefToProject']/descendant::input")
            assertContains(
                ATTR_RefToProject?.getAttribute("value") ?: "NONE",
                "Разработка проекта электроснабжения Омшанника"
            )
            tools.referenceClickLast("BUTTON_ERASE_PROJECT")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_RefToProject?.getAttribute("value") ?: "NONE").length) == 0)
            BUTTON_PROJECT_SEL()  // Всавляем еще раз

            val BUTTON_TYPE_DOC = {
                tools.referenceClickLast("BUTTON_TYPE_DOC")
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Типы технической документации"))
                tools.xpathLast("//a[contains(text(),'Документация на коммуникации')]/ancestor::tr")?.click()
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_TYPE_DOC()
            Thread.sleep(threadSleep)
            val ATTR_TechDoc_Sort = tools.xpathLast("//*[@data-reference='ATTR_TechDoc_Sort']/descendant::input")
            assertContains(ATTR_TechDoc_Sort?.getAttribute("value") ?: "NONE", "Документация на коммуникации")
            tools.referenceClickLast("BUTTON_ERASE_TTD")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_TechDoc_Sort?.getAttribute("value") ?: "NONE").length) == 0)
            BUTTON_TYPE_DOC()  // Всавляем еще раз

            val BUTTON_OBJ_STR = {
                tools.referenceClickLast("BUTTON_OBJ_STR")
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Объекты структуры"))
                tools.xpathLast("//a[contains(text(),'Проект сооружения омшанника')]/ancestor::tr")?.click()
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_OBJ_STR()
            Thread.sleep(threadSleep)
            val ATTR_OCC = tools.xpathLast("//*[@data-reference='ATTR_OCC']/descendant::input")
            assertContains(ATTR_OCC?.getAttribute("value") ?: "NONE", "Проект сооружения омшанника")
            tools.referenceClickLast("BUTTON_ERASE_OS")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_OCC?.getAttribute("value") ?: "NONE").length) == 0)
            BUTTON_OBJ_STR()  // Всавляем еще раз

            val BUTTON_ORG_SEL = {
                tools.referenceClickLast("BUTTON_ORG_SEL")
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Организации/Подразделения"))
                tools.xpathLast("//a[contains(text(),'СМУ')]/ancestor::tr")?.click()
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_ORG_SEL()
            Thread.sleep(threadSleep*3)
            val ATTR_ORGANIZATION_LINK =
                tools.xpathLast("//*[@data-reference='ATTR_ORGANIZATION_LINK']/descendant::input")
            assertContains(ATTR_ORGANIZATION_LINK?.getAttribute("value") ?: "NONE", "СМУ")
            tools.referenceClickLast("BUTTON_ERASE_ORG")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_ORGANIZATION_LINK?.getAttribute("value") ?: "NONE").length) == 0)
            BUTTON_ORG_SEL()  // Всавляем еще раз

            tools.clickOK()
        }

    @RepeatedTest(NN)
    //@Disabled
    @DisplayName("Заполнение дат и выпадающих фильтра")
    fun n07_EditFilterTest(repetitionInfo: RepetitionInfo) {
        workTable()
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val editFilter = "Заполнение дат и выпадающих фильтра"
        if (DT > 8) println("Редактирование $nomberFilter")
        tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
        if (DT > 8) println("Test нажатия на $editFilter")

        tools.referenceClickLast("CMD_EDIT_ATTRS")
        assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"))
        Thread.sleep(threadSleep)
        val description =
            tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Описание
        assertTrue(description?.getAttribute("value")!!.contains("Фильтр $nomberFilter $localDateNow"))
        description.sendKeys(" @@")
        assertTrue(description.getAttribute("value").contains("@@"))


            tools.xpathLast("// *[@data-reference='ATTR_DATE_START']/descendant::input")
                ?.sendKeys(localDateNow)
            tools.xpathLast("// *[@data-reference='ATTR_DATE_END']/descendant::input")
                ?.sendKeys(localDateNow)

            tools.xpathLast("// *[@data-reference='ATTR_DATE_RELEASE_DOCUMENT']/descendant::div[contains(@id, 'picker')]")?.click()
            Thread.sleep(threadSleep)
            tools.xpathLast("//span[text()='Сегодня']")?.click()
            Thread.sleep(threadSleep)
            val ATTR_DATE_RELEASE_DOCUMENT = tools.xpathLast("//*[@data-reference='ATTR_DATE_RELEASE_DOCUMENT']/descendant::input")
            assertEquals(ATTR_DATE_RELEASE_DOCUMENT?.getAttribute("value") ?:"NONE", localDateNow)


            tools.xpathLast("// *[@data-reference='ATTR_USER_QUERY_STATUS']/descendant::input")
                ?.sendKeys("В разработке")
            tools.xpathLast("// *[@data-reference='ATTR_DOC_AUTHOR']/descendant::input")
                ?.sendKeys("SYSADMIN")

            //tools. referenceClickLast("ATTR_RESPONSIBLE_USER")   ОШИБОЧНОЕ ПОЛЕ
            tools. qtipClickLast("Выбрать объект")
            assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Выбор объектов"))
            tools.xpathLast("//a[contains(text(),'SYSADMIN')]/ancestor::tr")?.click()
            Thread.sleep(threadSleep)
            tools.clickOK("Ок")

            tools.clickOK()
    }
    @RepeatedTest(NN)
    @Disabled
    @DisplayName("Очистка фильтров")
    fun n08_clearUserQuery(repetitionInfo: RepetitionInfo) {
        workTable()
        var errors = 0
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val deleteFilter = "Удалить фильтр"
        if (DT > 8) println("Test нажатия на $deleteFilter")
        tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
        val ffff =
            tools.xpathLast("//div[starts-with(@id,'panel-') and contains(@id,'header')]/descendant::span[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
                ?.text ?: ""
        //assertTrue(ffff == "(Все проекты) Фильтр $nomberFilter $localDateNow")
        if (ffff.startsWith("(Все проекты) Фильтр $nomberFilter $localDateNow").not()) {
            println("СРЫВ - нет инструментов $ffff")
            errors += 1
            tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
        }

        tools.referenceClickLast("CMD_DELETE_USER_QUERY")
        Thread.sleep(threadSleep)
        assertTrue(tools.titleWait("messagebox", "TDM365"))
    }

    /**
     *  тест Удаление фильтра
     */
   @RepeatedTest(NN)
   @DisplayName("Удалить фильтр")
   fun n09_DeleteUserQuery(repetitionInfo: RepetitionInfo) {
        workTable()
       var errors = 0
       val nomberFilter = "${repetitionInfo.currentRepetition}"
       val deleteFilter = "Удалить фильтр"
       if (DT>8) println("Test нажатия на $deleteFilter")
       tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
       val ffff = tools.xpathLast("//div[starts-with(@id,'panel-') and contains(@id,'header')]/descendant::span[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
           ?.text ?: ""
       //assertTrue(ffff == "(Все проекты) Фильтр $nomberFilter $localDateNow")
       if (ffff.contains("Фильтр $nomberFilter $localDateNow").not()) {
           println("СРЫВ - нет инструментов $ffff")
           errors += 1
           tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
       }

       tools.referenceClickLast("CMD_DELETE_USER_QUERY")
        Thread.sleep(threadSleep)
       assertTrue(tools.titleWait("messagebox", "TDM365"))
       // Вы действительно хотите удалить объект "(Все проекты) Фильтр" из системы?
       //assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
       //assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра"))
       //tools.referenceClickLast("tabbar-FORM_USER_QUERY")
       // Проверить вкладку assertTrue(tools.windowTitleWait("Редактирование объекта"))
       //assertEquals("Фильтр $nomberFilter $localDateNow",
       //    tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
       //        ?.getAttribute("value"))
       //sendKeys(" $nomberFilter $localDateNow")
       tools.clickOK("Да")
       if (errors > 0) assertTrue(false)
   }
}

