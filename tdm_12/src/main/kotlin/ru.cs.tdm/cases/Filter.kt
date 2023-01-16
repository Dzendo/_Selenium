package ru.cs.tdm.cases

import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.startDriver
import ru.cs.tdm.ui.TestsProperties
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
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDateNow = LocalDate.now().format(formatter)  //LocalDateTime.now()

    companion object {
        private val threadSleep = TestsProperties.threadSleepNomber     // задержки где они есть
        private val DT: Int = TestsProperties.debugPrintNomber          // глубина отладочной информации 0 - ничего не печатать, 9 - все
        //private val NN:Int = TestsProperties.repeateTestsNomber       // количество повторений тестов
        private const val NN:Int = 1                                    // количество повторений тестов

    // переменная для драйвера
    lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var tools: Tools

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
        if (DT >7) println("Вызов BeforeAll FilterTest")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
            driver = startDriver()
            /*
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()
        //окно разворачивается на полный второй экран-1500 1500 3000 2000,0
        driver.manage().window().position = Point(2000,-1000)
        //driver.manage().window().position = Point(0,-1000)
        driver.manage().window().maximize()
            */
            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = TestsProperties.loginpage
            if (DT > 7) println("Открытие страницы $loginpage")
            val login = TestsProperties.login
            val password = TestsProperties.password
            if (DT > 7) println("login= $login   password= $password")
            driver.get(loginpage)
            assertTrue(driver.title == "Tdms", "@@@@ Не открылась страница ${loginpage} - нет заголовка вкладки Tdms @@")
            Login(driver).loginIn(login, password)
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            //tools.idList()
            if (DT >7) println("Вызов AfterAll FilterTest")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера

        }
    }   // конец companion object

    @BeforeEach
    fun beforeEach() {

        if (DT > 7) println("Начало BeforeEach FilterTest")
        val mainMenu = "Объекты"
        if (DT > 7) println("Test нажатия на $mainMenu TDMS Web")
        tools.qtipClickLast(mainMenu)
        assertTrue(tools.titleContain("TDM365"), "@@@@ После нажатия $mainMenu - нет заголовка вкладки TDM365 @@")  // сбоит нечасто заголовок страницы на создании
        assertTrue(tools.qtipPressedLast("Объекты"), "@@@@ После нажатия $mainMenu - кнопка Объекты нет утоплена @@")
        if (DT >7) println("Конец BeforeEach FilterTest")
    }
    // пришлось ввести т.к. при рабочем столе два значка "создать фильтр"
    fun workTable() {
        val workTable = "Рабочий стол"
        if (DT >7) println("Test нажатия на $workTable")
        tools.qtipClickLast(workTable)
        //Thread.sleep(threadSleep)
        tools.xpathClickLast("//span[text()= '$workTable (SYSADMIN)']") // встать в дереве на Рабочий стол (SYSADMIN)
        assertTrue(tools.titleContain(workTable), "@@@@ После нажатия $workTable - нет заголовка вкладки $workTable @@")  // сбоит 1 раз на 100
        assertTrue(tools.qtipPressedLast(workTable), "@@@@ После нажатия $workTable - кнопка $workTable нет утоплена @@")
        // проверить что справа Рабочий стол (SYSADMIN)
        // Здесь проверка дерева и отображения
        tools.xpathClickLast("//span[contains(text(), 'Фильтры')]")
        Thread.sleep(threadSleep)
    }
    private fun clickFilter(nomberFilter: String, clickRef: String = "CMD_EDIT_ATTRS" ) {
        val tipWindow = if (clickRef == "CMD_DELETE_USER_QUERY")  "messagebox" else "tdmsEditObjectDialog"
        val titleWindow = if (clickRef == "CMD_DELETE_USER_QUERY") "TDM365" else
                if (clickRef == "CMD_EDIT_ATTRS") "Редактирование объекта" else "Просмотр свойств"
        if (DT > 6) println("Test нажатия на Фильтр $nomberFilter действие: $clickRef")
        Thread.sleep(threadSleep)
        tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
        Thread.sleep(threadSleep)
        assertContains(tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")?.getAttribute("value") ?: "NONE", "Фильтр $nomberFilter $localDateNow",false,
            "@@@@ Проверка наличия имени фильтра после создания не прошла @@")

        Thread.sleep(threadSleep)
        if (tools.referenceLast("CMD_DELETE_USER_QUERY") == null) {
            println("$$$$$$$$$$$$$$$ НЕТ ИНСТРУМЕНТОВ $nomberFilter действие: $clickRef")
            screenShot()
        }

        Thread.sleep(threadSleep)
        tools.referenceClickLast(clickRef)
        Thread.sleep(threadSleep)
        assertTrue(tools.titleWait(tipWindow, titleWindow),
            "@@@@ После нажатия $clickRef - окно типа $tipWindow не имеет заголовка $tipWindow @@")
        Thread.sleep(threadSleep)
        val filterText = if (clickRef == "CMD_DELETE_USER_QUERY")
                tools.xpathLast("//div[contains(text(),'Вы действительно хотите удалить объект')]")?.text ?: "NONE"
            else
                tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")?.getAttribute("value") ?: "NONE"
         assertContains(filterText, "Фильтр $nomberFilter $localDateNow", false,
             "@@@@ Нет правильного текста Фильтр $nomberFilter $localDateNow на всплывающем окне $filterText @@")
    }
    @AfterEach
    fun afterEach(){
        if (DT >7) println("Вызов AfterEach FilterTest")
        //screenShot()
        tools.closeEsc5()
        Thread.sleep(threadSleep)
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
    @DisplayName("Создать фильтры")
    fun n01_CreateUserQuery(repetitionInfo: RepetitionInfo) {
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val createUser = "Создать фильтр"
        if (DT > 6) println("Test нажатия на $createUser")

        tools.referenceClickLast("CMD_CREATE_USER_QUERY")
        assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"),
            "@@@@ После нажатия $createUser - нет окна с заголовком Редактирование объекта @@")
        //  оставить ждать Омск
        // tools.referenceClickLast("tabbar-FORM_USER_QUERY")
        //  assertTrue(tools.referenceLast("tabbar-FORM_USER_QUERY")?.getAttribute("aria-selected") == "true")
        assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра"),
            "@@@@ На форме фильтра при $createUser - не нашлось текста Наименование фильтра @@")
        tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
            ?.sendKeys(" $nomberFilter $localDateNow")
        Thread.sleep(threadSleep)
        // Проверить что в поле стоит дата, если нет, то Скрин
        val ATTR_USER_QUERY_NAME = tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
            ?.getAttribute("value") ?: "NONE"
         if (ATTR_USER_QUERY_NAME.contains(" $nomberFilter $localDateNow").not()){
             if (DT > 0) println("&&&&&&&&&&01&&&&&&&&&&&& Название Фильтра не прописано: $ATTR_USER_QUERY_NAME")
             screenShot()
         }
        tools.clickOK()
        if (DT > 6) println("Конец Test нажатия на $createUser")
    }
    /**
     *  тест создание нового фильтра
     */
    @RepeatedTest(NN)
    @DisplayName("Посмотреть фильтр")
    fun n02_ViewUserQuery(repetitionInfo: RepetitionInfo) {
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val viewUser = "Посмотреть фильтр"
        if (DT > 6) println("Test посмотреть на $viewUser")
        // проверка что фильтр создан, если нет
        workTable()
        clickFilter(nomberFilter, "CMD_VIEW_ONLY")

        // Проверить что в поле стоит дата, если нет, то Скрин
        val ATTR_USER_QUERY_NAME = tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Наименование фильтра
            ?.getAttribute("value") ?: "NONE"
        if (ATTR_USER_QUERY_NAME.contains(" $nomberFilter $localDateNow").not()){
            if (DT > 0) println("&&&&&&&&&02&&&&&&&&&&&&& Название Фильтра не прописано: $ATTR_USER_QUERY_NAME")
            screenShot()
        }

        tools.clickOK("Закрыть")
        //tools.xpathClickLast("//*[contains(text(), 'Фильтр $nomberFilter $localDateNow')]")
        if (DT > 6) println("Конец Test посмотреть на $viewUser")

    }
        /**
         *  тест заполнение и сохранение фильтра
         */
    @RepeatedTest(NN)
    @DisplayName("Заполнение текстовых полей фильтра")
    fun n03_fillingFilterTest(repetitionInfo: RepetitionInfo) {
            workTable()
            val nomberFilter = "${repetitionInfo.currentRepetition}"
            Thread.sleep(threadSleep)
            val fillingUser = "Заполнение текстовых полей фильтра"
            if (DT > 6) println("Test нажатия на $fillingUser")

            clickFilter(nomberFilter)  // встать на фильтр

            val ATTR_USER_QUERY_NAME = tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")
            ATTR_USER_QUERY_NAME?.sendKeys(" #")  // Наименование фильтра
            assertContains(ATTR_USER_QUERY_NAME?.getAttribute("value") ?: "NONE", "#",false,
                "@@@@ В Наименовании фильтра не прописалось # при редактировании @@")

            Thread.sleep(threadSleep)
            val ATTR_QUERY_TechDoc_Num = tools.xpathLast("//*[@data-reference='ATTR_QUERY_TechDoc_Num']/descendant::input")
            ATTR_QUERY_TechDoc_Num?.sendKeys("Обозначение $nomberFilter $localDateNow")  // Обозначение
            Thread.sleep(threadSleep)
            assertContains(ATTR_QUERY_TechDoc_Num?.getAttribute("value") ?: "NONE", "Обозначение", false,
                "@@@@ В Обозначение фильтра не прописалось Обозначение при редактировании @@")

            Thread.sleep(threadSleep)
            val ATTR_QUERY_TechDoc_RevNum = tools.xpathLast("//*[@data-reference='ATTR_QUERY_TechDoc_RevNum']/descendant::input")
            ATTR_QUERY_TechDoc_RevNum ?.sendKeys("77") // Изм. №
            assertContains(ATTR_QUERY_TechDoc_RevNum?.getAttribute("value") ?: "NONE", "77", false,
                "@@@@ В Изм. № фильтра не прописалось 77 при редактировании @@")

            Thread.sleep(threadSleep)
            val ATTR_QUERY_TechDoc_Name = tools.xpathLast("//*[@data-reference='ATTR_QUERY_TechDoc_Name']/descendant::input")
            ATTR_QUERY_TechDoc_Name?.sendKeys("Наименование $nomberFilter $localDateNow")  // Наименование
            assertContains(ATTR_QUERY_TechDoc_Name?.getAttribute("value") ?: "NONE", "Наименование", false,
                "@@@@ В Наименование фильтра не прописалось Наименование при редактировании @@")

            Thread.sleep(threadSleep)
            val ATTR_DESCRIPTION = tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::textarea")
            ATTR_DESCRIPTION ?.sendKeys("Описание $nomberFilter $localDateNow") // Описание
            assertContains(ATTR_DESCRIPTION?.getAttribute("value") ?: "NONE", "Описание", false,
                "@@@@ В Описание фильтра не прописалось Описание при редактировании @@")
            Thread.sleep(threadSleep)

            tools.clickOK()
            if (DT > 6) println("Конец Test нажатия на $fillingUser")
        }

    /**
         *  тест редактирование фильтра
         */
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Заполнение ссылочных полей фильтра")
        fun n04_EditFilterTest(repetitionInfo: RepetitionInfo) {
            workTable()
            val nomberFilter = "${repetitionInfo.currentRepetition}"
            val editFilter = "Заполнение ссылочных полей фильтра"
        if (DT > 6) println("Test нажатия на $editFilter")

        clickFilter(nomberFilter)  // встать на фильтр

            val description =
                tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Описание
            assertTrue(description?.getAttribute("value")!!.contains("Фильтр $nomberFilter $localDateNow"),
                "@@@@ В Название фильтра нет названия Фильтр $nomberFilter $localDateNow @@")
            description.sendKeys(" @")
            assertTrue(description.getAttribute("value").contains("@"),
                "@@@@ В Наименовании фильтра не прописалось @ при редактировании @@")

            val BUTTON_OBJDEV_SEL = {
                tools.referenceClickLast("BUTTON_OBJDEV_SEL")
                assertTrue(tools.titleWait("tdmsSelectObjectGridDialog", "Выбор объекта структуры"),
                    "@@@@ карандашик BUTTON_OBJDEV_SEL на поле Объект разработки : нет справочника с заголовком Выбор объекта структуры @@")

                tools.xpathLast("//*[contains(text(),'Ферма_омшанник')]/ancestor::td")?.click()

                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_OBJDEV_SEL()
            Thread.sleep(threadSleep)
            val attrObjectDev = tools.xpathLast("//*[@data-reference='ATTR_OBJECT_DEV']/descendant::input")
            val getFerma = attrObjectDev?.getAttribute("value")
            assertContains(attrObjectDev?.getAttribute("value") ?: "NONE", "Ферма_омшанник",
                false, "@@@@ карандашик BUTTON_OBJDEV_SEL : После выбора в поле фильтра объекта разработки из справочника в поле фильтра Объект разработки - пусто, а должно стоять Ферма_омшанник @@")

            tools.referenceClickLast("BUTTON_OBJDEV_ERASE")
            Thread.sleep(threadSleep)
            assertTrue(((attrObjectDev?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_OBJDEV_ERASE(объекта разработки) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")

            BUTTON_OBJDEV_SEL()  // Всавляем еще раз омшанник

            val BUTTON_PROJECT_SEL = {
                tools.referenceClickLast("BUTTON_PROJECT_SEL")
                assertTrue(tools.titleWait("tdmsSelectObjectGridDialog", "Выбор проекта"),
                    "@@@@ карандашик BUTTON_PROJECT_SEL на поле Проект : нет справочника с заголовком Выбор проекта @@")

                tools.xpathLast("//*[contains(text(),'Разработка проекта электроснабжения Омшанника')]/ancestor::td")
                    ?.click()                   //tr  tbody  table

                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_PROJECT_SEL()
            Thread.sleep(threadSleep)
            val ATTR_RefToProject = tools.xpathLast("//*[@data-reference='ATTR_RefToProject']/descendant::input")
            assertContains(
                ATTR_RefToProject?.getAttribute("value") ?: "NONE",
                "Разработка проекта электроснабжения Омшанника", false,
                "@@@@ карандашик BUTTON_PROJECT_SEL : После выбора в поле Проекта разработки из справочника в поле фильтра Проект - пусто, а должно стоять Разработка проекта электроснабжения Омшанника @@"
            )
            tools.referenceClickLast("BUTTON_ERASE_PROJECT")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_RefToProject?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_PROJECT(Проект) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_PROJECT_SEL()  // Всавляем еще раз

            val BUTTON_TYPE_DOC = {
                tools.referenceClickLast("BUTTON_TYPE_DOC")
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Типы технической документации"),
                    "@@@@ карандашик BUTTON_TYPE_DOC на поле Тип документации : нет справочника с заголовком Типы технической документации @@")

                tools.xpathLast("//a[contains(text(),'Документация на коммуникации')]/ancestor::tr")?.click()
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_TYPE_DOC()
            Thread.sleep(threadSleep)
            val ATTR_TechDoc_Sort = tools.xpathLast("//*[@data-reference='ATTR_TechDoc_Sort']/descendant::input")
            assertContains(ATTR_TechDoc_Sort?.getAttribute("value") ?: "NONE", "Документация на коммуникации",false,
                "@@@@ поле фильтра Тип документации не содержит значение Документация на коммуникации после выбора @@")
            tools.referenceClickLast("BUTTON_ERASE_TTD")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_TechDoc_Sort?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_TTD(Тип документации) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_TYPE_DOC()  // Всавляем еще раз

            val BUTTON_OBJ_STR = {
                tools.referenceClickLast("BUTTON_OBJ_STR")
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Объекты структуры"),
                    "@@@@ карандашик BUTTON_OBJ_STR на поле Объект структуры : нет справочника с заголовком Объекты структуры @@")

                tools.xpathLast("//a[contains(text(),'Проект сооружения омшанника')]/ancestor::tr")?.click()
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_OBJ_STR()
            Thread.sleep(threadSleep)
            val ATTR_OCC = tools.xpathLast("//*[@data-reference='ATTR_OCC']/descendant::input")
            assertContains(ATTR_OCC?.getAttribute("value") ?: "NONE", "Проект сооружения омшанника",false,
                "@@@@ поле фильтра Объект структуры не содержит значение Проект сооружения омшанника после выбора @@")
            tools.referenceClickLast("BUTTON_ERASE_OS")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_OCC?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_OS(Объект структуры) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_OBJ_STR()  // Всавляем еще раз

            val BUTTON_ORG_SEL = {
                tools.referenceClickLast("BUTTON_ORG_SEL")
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Организации/Подразделения"),
                    "@@@@ карандашик BUTTON_ORG_SEL на поле Организация/Подразд. : нет справочника с заголовком Организации/Подразделения @@")

                tools.xpathLast("//a[contains(text(),'СМУ')]/ancestor::tr")?.click()
                Thread.sleep(threadSleep)
                tools.clickOK("Ок")
            }
            BUTTON_ORG_SEL()
            Thread.sleep(threadSleep *3)
            val ATTR_ORGANIZATION_LINK =
                tools.xpathLast("//*[@data-reference='ATTR_ORGANIZATION_LINK']/descendant::input")
            assertContains(ATTR_ORGANIZATION_LINK?.getAttribute("value") ?: "NONE", "СМУ",false,
                "@@@@ поле фильтра Организация/Подразд. не содержит значение СМУ после выбора @@")
            tools.referenceClickLast("BUTTON_ERASE_ORG")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_ORGANIZATION_LINK?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_ORG(Организация/Подразд.) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_ORG_SEL()  // Всавляем еще раз

            tools.clickOK()
        if (DT > 6) println("Конец Test нажатия на $editFilter")
        }

    @RepeatedTest(NN)
    //@Disabled
    @DisplayName("Заполнение дат и выпадающих фильтра")
    fun n06_EditFilterTest(repetitionInfo: RepetitionInfo) {
        workTable()
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val editFilter = "Заполнение дат и выпадающих фильтра"
        if (DT > 6) println("Test нажатия на $editFilter")

        clickFilter(nomberFilter)  // встать на фильтр

        val description =
            tools.xpathLast("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input")  // Описание
        assertTrue(description?.getAttribute("value")!!.contains("Фильтр $nomberFilter $localDateNow"),
            "@@@@ Нет в списке фильтра с именем Фильтр $nomberFilter $localDateNow @@")
        description.sendKeys(" @@")
        assertTrue(description.getAttribute("value").contains("@@"),
            "@@@@ В Наименовании фильтра не прописалось @@ при редактировании @@")



            tools.xpathLast("// *[@data-reference='ATTR_DATE_START']/descendant::input")
                ?.sendKeys(localDateNow)
            tools.xpathLast("// *[@data-reference='ATTR_DATE_END']/descendant::input")
                ?.sendKeys(localDateNow)

            tools.xpathLast("// *[@data-reference='ATTR_DATE_RELEASE_DOCUMENT']/descendant::div[contains(@id, 'picker')]")?.click()
            Thread.sleep(threadSleep)
            tools.xpathLast("//span[text()='Сегодня']")?.click()
            Thread.sleep(threadSleep)
            val ATTR_DATE_RELEASE_DOCUMENT = tools.xpathLast("//*[@data-reference='ATTR_DATE_RELEASE_DOCUMENT']/descendant::input")
            assertEquals(ATTR_DATE_RELEASE_DOCUMENT?.getAttribute("value") ?:"NONE", localDateNow,
                "@@@@ В Дата не стоит сегодняшняя дата после занесения из выбора Сегодня @@")

            tools.xpathLast("// *[@data-reference='ATTR_USER_QUERY_STATUS']/descendant::input")
                ?.sendKeys("В разработке")
            tools.xpathLast("// *[@data-reference='ATTR_DOC_AUTHOR']/descendant::input")
                ?.sendKeys("SYSADMIN")

            //tools. referenceClickLast("ATTR_RESPONSIBLE_USER")   ОШИБОЧНОЕ ПОЛЕ Ответственный
            tools. qtipClickLast("Выбрать объект")
            assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Выбор объектов"),
                "@@@@ стрелочка ATTR_RESPONSIBLE_USER (Ответственный) на поле Ответственный : нет справочника с заголовком Выбор объектов @@")
            tools.xpathLast("//a[contains(text(),'SYSADMIN')]/ancestor::tr")?.click()
            Thread.sleep(threadSleep)
            tools.clickOK("Ок")

            tools.clickOK()
        if (DT > 6) println("Конец Test нажатия на $editFilter")
    }
    @RepeatedTest(NN)
    @Disabled
    @DisplayName("Очистка фильтров")
    fun n08_clearUserQuery(repetitionInfo: RepetitionInfo) {
        workTable()
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val clearFilter = "Очистка фильтров"
        if (DT > 6) println("Test нажатия на $clearFilter")

        clickFilter(nomberFilter)  // встать на фильтр
    }

    /**
     *  тест Удаление фильтра
     */
   @RepeatedTest(NN)
   @DisplayName("Удаление фильтров")
   fun n11_DeleteUserQuery(repetitionInfo: RepetitionInfo) {
        workTable()

       val nomberFilter = "${repetitionInfo.currentRepetition}"
       val deleteFilter = "Удалить фильтр"
       if (DT > 6) println("Test нажатия на $deleteFilter")

        clickFilter(nomberFilter, "CMD_DELETE_USER_QUERY")  // встать на фильтр

       // Вы действительно хотите удалить объект "(Все проекты) Фильтр" из системы?
       tools.clickOK("Да")
        if (DT > 6) println("Конец Test нажатия на $deleteFilter")

   }
}
