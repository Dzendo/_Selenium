package ru.cs.tdm.cases

import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.code.clickSend
import ru.cs.tdm.data.TDM365
import ru.cs.tdm.data.Tdms
import ru.cs.tdm.data.startDriver
import ru.cs.tdm.data.TestsProperties
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
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
    //val localDateNow = LocalDate.now().format(formatter)  //LocalDateTime.now()
    //private val localDateNow = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm").format(LocalDateTime.now())
   // private val localDateNow = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDateTime.now())

    companion object {
        private val threadSleep = TestsProperties.threadSleepNomber     // задержки где они есть
        private val DT: Int = TestsProperties.debugPrintNomber          // глубина отладочной информации 0 - ничего не печатать, 9 - все
        //private val NN:Int = TestsProperties.repeateTestsNomber       // количество повторений тестов
        private const val NN:Int = 1                                    // количество повторений тестов
        private val localDateNow = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss").format(LocalDateTime.now())
    // переменная для драйвера
    private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    private lateinit var tools: Tools

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
        if (DT >7) println("Вызов BeforeAll FilterTest")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
            driver = startDriver()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = TestsProperties.loginpage
            if (DT > 7) println("Открытие страницы $loginpage")
            val login = TestsProperties.login
            val password = TestsProperties.password
            if (DT > 7) println("login= $login   password= $password")
            driver.get(loginpage)
            assertTrue(driver.title == Tdms, "@@@@ Не открылась страница $loginpage - нет заголовка вкладки Tdms @@")
            Login(driver).loginIn(login, password)
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            //tools.idList()
            if (DT >7) println("Вызов AfterAll FilterTest")
            tools.closeEsc(5)
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера

        }
    }   // конец companion object

    @BeforeEach
    fun beforeEach() {

        if (DT > 7) println("Начало BeforeEach FilterTest")
        val mainMenu = "Объекты"
        if (DT > 7) println("Test нажатия на $mainMenu TDMS Web")
        tools.byIDClick("objects-tab")
        assertTrue(tools.titleContain(TDM365), "@@@@ После нажатия $mainMenu - нет заголовка вкладки TDM365 @@")
        assertTrue(tools.byIDPressed("objects-tab"), "@@@@ После нажатия Объекты - кнопка Объекты нет утоплена @@")
        if (DT >7) println("Конец BeforeEach FilterTest")

    }
    fun workTable() {
        val workTable = "Рабочий стол"
        if (DT >7) println("Test нажатия на $workTable")
        tools.byIDClick("desktop-tab")
        tools.xpathClick("//div[contains(@title,'Рабочий стол')]","Main-Tree")
        assertTrue(tools.titleContain(workTable), "@@@@ После нажатия $workTable - нет заголовка вкладки $workTable @@")  // сбоит 1 раз на 100
        assertTrue(tools.byIDPressed("desktop-tab"), "@@@@ После нажатия $workTable - кнопка $workTable нет утоплена @@")
        // проверить что справа Рабочий стол (SYSADMIN)
        // Здесь проверка дерева и отображения
        tools.xpathClick("//span[contains(text(), 'Фильтры')]","Main-Tree")
        assertTrue(tools.titleContain("Фильтры"), "@@@@ После нажатия Фильтры - нет заголовка вкладки Фильтры @@")
    }
    private fun clickFilter(nomberFilter: String, clickRef: String = "CMD_EDIT_ATTRS" ) {
        val titleWindow = when (clickRef) {
            "CMD_DELETE_USER_QUERY" -> TDM365
            "CMD_EDIT_ATTRS" -> "Редактирование объекта"
            else -> "Просмотр свойств"
        }
        if (DT > 6) println("Test нажатия на Фильтр $localDateNow действие: $clickRef")
        //tools.xpathClick("//span[contains(text(), 'Фильтры')]","Main-Tree")
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Thread.sleep(threadSleep)
        assertEquals(tools.reference("main-object-header-description","ROOT666","//span")?.text,"Фильтры",
            " Заголовок таблицы не Фильтры")
        // Обрабатывать RecyclerVuew
        tools.xpathClick("//a [contains(text(), 'Тест $localDateNow')]", "Main-Grid")
        Thread.sleep(threadSleep)
        assertContains(tools.reference("ATTR_USER_QUERY_NAME","Object-Preview","//descendant::input")?.getAttribute("value") ?: "NONE", "Тест $localDateNow",false,
            "@@@@ Проверка наличия имени фильтра после создания не прошла @@")

        if (tools.reference("CMD_DELETE_USER_QUERY","ROOT666") == null) {
            println("$$$$$$$$$$$$$$$ НЕТ ИНСТРУМЕНТОВ $localDateNow действие: $clickRef")
            screenShot()
        }
        Thread.sleep(threadSleep)
        tools.referenceClick(clickRef, "ROOT666")
        Thread.sleep(threadSleep)
        assertTrue(tools.headerWait(titleWindow),
            "@@@@ После нажатия $clickRef - окно типа не имеет заголовка $titleWindow @@")

        // Вы действительно хотите удалить объект "(Все проекты) Тест 31-05-2023_18-51-20  @@#" из системы?
        val filterText = if (clickRef == "CMD_DELETE_USER_QUERY")
                tools.xpath("//*[contains(text(),'Вы действительно хотите удалить объект')]", "MODAL")?.text ?: "NONE"
            else
                tools.xpath("//*[@data-reference='ATTR_USER_QUERY_NAME']/descendant::input", "MODAL")?.getAttribute("value") ?: "NONE"
         assertContains(filterText, "Тест $localDateNow", false,
             "@@@@ Нет правильного текста Тест $localDateNow на всплывающем окне $filterText @@")
    }
    @AfterEach
    fun afterEach(){
        if (DT >7) println("Вызов AfterEach FilterTest")
        Thread.sleep(threadSleep)
        //screenShot()
        tools.closeEsc(5)
//        Thread.sleep(threadSleep)
//        driver.navigate().refresh()
    }
    fun screenShot(name: String = "image") {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val sdf = SimpleDateFormat("ddMMyyyyhhmmss")
        copyFile(scrFile, File("./$name${sdf.format(Date())}.png"))
    }

    @RepeatedTest(NN)
    @DisplayName("Удаление фильтров")
    fun n00_DeleteUserQuery(repetitionInfo: RepetitionInfo) {
        val deleteFilter = "Удалить тестовые фильтры"
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        if (DT > 6) println("Test нажатия на $deleteFilter")
        val titleWindow =  TDM365
        var nomerDel = 0

        while(true) {
            workTable()
        val testPresent =  tools.xpath("//table", "Main-Grid")
            ?.findElements(By.xpath("//descendant::a[contains(text(),'Тест')]"))?.toList()
            if (testPresent.isNullOrEmpty()) break
            if (testPresent.size <= nomerDel) break

            // Обрабатывать RecyclerVuew
            testPresent[nomerDel]?.click()

            if (tools.reference("CMD_DELETE_USER_QUERY","ROOT666") == null) {
                println("$$$$$$$$$$$$$$$ НЕТ ИНСТРУМЕНТОВ $localDateNow действие:")
                //screenShot()
                nomerDel++
                continue
            }

            tools.referenceClick("CMD_DELETE_USER_QUERY", "ROOT666")
            assertTrue(tools.headerWait(titleWindow),
                "@@@@ После нажатия CMD_DELETE_USER_QUERY - окно типа не имеет заголовка $titleWindow @@")

            // Вы действительно хотите удалить объект "(Все проекты) Тест 31-05-2023_18-51-20  @@#" из системы?
            val filterText =
                tools.xpath("//*[contains(text(),'Вы действительно хотите удалить объект')]", "MODAL")?.text ?: "NONE"
            assertContains(filterText, "Тест", false,
                "@@@@ Нет правильного текста Тест на всплывающем окне $filterText @@")

            // Вы действительно хотите удалить объект "(Все проекты) Фильтр" из системы?
            tools.OK("yes-modal-window-btn")
        }

        if (DT > 6) println("Конец Test нажатия на $deleteFilter Заблокировано $nomerDel фильтров")

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

        // Выбрать рабочий стол
        workTable()
        // выбрать из дерева фильтры

        Thread.sleep(threadSleep)
        tools.referenceClick("CMD_CREATE_USER_QUERY", "ROOT666")
        Thread.sleep(threadSleep)
        assertTrue(tools.headerWait("Редактирование объекта"),
            "@@@@ После нажатия $createUser - нет окна с заголовком Редактирование объекта @@")

        assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра", "MODAL"),
            "@@@@ На форме фильтра при $createUser - не нашлось текста Наименование фильтра @@")
        tools.reference("ATTR_USER_QUERY_NAME", "MODAL" ,"//descendant::input")  // Наименование фильтра
            ?.clickSend("Тест $localDateNow", true)
        // Проверить что в поле стоит дата, если нет, то Скрин
        val ATTR_USER_QUERY_NAME = tools.reference("ATTR_USER_QUERY_NAME", "MODAL", "//descendant::input")  // Наименование фильтра
            ?.getAttribute("value") ?: "NONE"
         if (ATTR_USER_QUERY_NAME.contains(" $localDateNow").not()){
             if (DT > 0) println("&&&&&&&&&&01&&&&&&&&&&&& Название Фильтра не прописано: $ATTR_USER_QUERY_NAME")
             screenShot()
         }
        //Костыль
        tools.referenceClick("ATTR_DESCRIPTION", "MODAL", "//descendant::textarea")
        
        tools.OK()
        
        if (DT > 6) println("Конец Test нажатия на $createUser")
    }
    /**
     *  тест создание нового фильтра
     */
    @RepeatedTest(NN)
    //@Disabled
    @DisplayName("Посмотреть фильтр")
    fun n02_ViewUserQuery(repetitionInfo: RepetitionInfo) {
        val nomberFilter = "${repetitionInfo.currentRepetition}"
        val viewUser = "Посмотреть фильтр"
        if (DT > 6) println("Test посмотреть на $viewUser")
        // проверка что фильтр создан, если нет
        workTable()
        clickFilter(nomberFilter, "CMD_VIEW_ONLY")

        // Проверить что в поле стоит дата, если нет, то Скрин
        val ATTR_USER_QUERY_NAME = tools.reference("ATTR_USER_QUERY_NAME","MODAL","//descendant::input")  // Наименование фильтра
            ?.getAttribute("value") ?: "NONE"
        if (ATTR_USER_QUERY_NAME.contains(" $localDateNow").not()){
            if (DT > 0) println("&&&&&&&&&02&&&&&&&&&&&&& Название Фильтра не прописано: $ATTR_USER_QUERY_NAME")
            screenShot()
        }
        tools.OK("cancel-modal-window-btn")
        if (DT > 6) println("Конец Test посмотреть на $viewUser")
    }
        /**
         *  тест заполнение и сохранение фильтра
         */
    @RepeatedTest(NN)
    //@Disabled
    @DisplayName("Заполнение текстовых полей фильтра")
    fun n03_fillingFilterTest(repetitionInfo: RepetitionInfo) {
            workTable()
            val nomberFilter = "${repetitionInfo.currentRepetition}"

            val fillingUser = "Заполнение текстовых полей фильтра"
            if (DT > 6) println("Test нажатия на $fillingUser")

            clickFilter(nomberFilter)  // встать на фильтр

            val ATTR_USER_QUERY_NAME =  tools.reference("ATTR_USER_QUERY_NAME", "MODAL" ,"//descendant::input")
            ATTR_USER_QUERY_NAME?.clickSend(" #")  // Наименование фильтра
            assertContains(ATTR_USER_QUERY_NAME?.getAttribute("value") ?: "NONE", "#",false,
                "@@@@ В Наименовании фильтра не прописалось # при редактировании @@")

            val ATTR_QUERY_TechDoc_Num = tools.reference("ATTR_QUERY_TechDoc_Num", "MODAL" ,"//descendant::input")
            ATTR_QUERY_TechDoc_Num?.clickSend("Обозначение $localDateNow")  // Обозначение
            assertContains(ATTR_QUERY_TechDoc_Num?.getAttribute("value") ?: "NONE", "Обозначение", false,
                "@@@@ В Обозначение фильтра не прописалось Обозначение при редактировании @@")

            val ATTR_QUERY_TechDoc_RevNum =  tools.reference("ATTR_QUERY_TechDoc_RevNum", "MODAL" ,"//descendant::input")
            ATTR_QUERY_TechDoc_RevNum ?.clickSend("77") // Изм. №
            assertContains(ATTR_QUERY_TechDoc_RevNum?.getAttribute("value") ?: "NONE", "77", false,
                "@@@@ В Изм. № фильтра не прописалось 77 при редактировании @@")

            val ATTR_QUERY_TechDoc_Name = tools.reference("ATTR_QUERY_TechDoc_Name", "MODAL" ,"//descendant::input")
            ATTR_QUERY_TechDoc_Name?.clickSend("Наименование $localDateNow")  // Наименование
            assertContains(ATTR_QUERY_TechDoc_Name?.getAttribute("value") ?: "NONE", "Наименование", false,
                "@@@@ В Наименование фильтра не прописалось Наименование при редактировании @@")

            val ATTR_DESCRIPTION = tools.reference("ATTR_DESCRIPTION", "MODAL" ,"//descendant::textarea")
            ATTR_DESCRIPTION ?.clickSend("Описание $localDateNow") // Описание
            assertContains(ATTR_DESCRIPTION?.getAttribute("value") ?: "NONE", "Описание", false,
                "@@@@ В Описание фильтра не прописалось Описание при редактировании @@")

            //Костыль
            tools.referenceClick("ATTR_USER_QUERY_NAME", "MODAL" ,"//descendant::input")
            tools.referenceClick("ATTR_DESCRIPTION", "MODAL", "//descendant::textarea")

            tools.OK()
            
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

        clickFilter(nomberFilter)  // встать на фильтр  !!!!!!!!!!!!!!!!!!!!!!!!

            val description = tools.reference("ATTR_USER_QUERY_NAME", "MODAL" ,"//descendant::input") // Описание
            assertTrue(description?.getAttribute("value")!!.contains("Тест $localDateNow"),
                "@@@@ В Название фильтра нет названия Тест $localDateNow @@")
            description.clickSend(" @")
            assertTrue(description.getAttribute("value").contains("@"),
                "@@@@ В Наименовании фильтра не прописалось @ при редактировании @@")

            val BUTTON_OBJDEV_SEL = {   // Объект разработки
                tools.referenceClick("BUTTON_OBJDEV_SEL", "MODAL")
                assertTrue(tools.headerWait( "Выбор объекта структуры"),
                    "@@@@ карандашик BUTTON_OBJDEV_SEL на поле Объект разработки : нет справочника с заголовком Выбор объекта структуры @@")

                // 0203 / 051-1007345 Расширение ЕСГ для обеспечения подачи газа в газопровод «Южный поток».1-й этап (Западный коридор), для обеспечения подачи газа в объеме 31,5 млрд. м3/
                tools.xpathClick("//*[contains(text(),'Расширение ЕСГ')]/ancestor::td","MODAL")

                tools.OK()
            }
            BUTTON_OBJDEV_SEL()

            val attrObjectDev =  tools.reference("ATTR_OBJECT_DEV", "MODAL","//descendant::input")
            assertContains(attrObjectDev?.getAttribute("value") ?: "NONE", "Расширение ЕСГ",
                false, "@@@@ карандашик BUTTON_OBJDEV_SEL : После выбора в поле фильтра объекта разработки из справочника в поле фильтра Объект разработки - пусто, а должно стоять Ферма_омшанник @@")

            tools.referenceClick("BUTTON_OBJDEV_ERASE", "MODAL")
        Thread.sleep(threadSleep)
            assertTrue(((attrObjectDev?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_OBJDEV_ERASE(объекта разработки) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")

            BUTTON_OBJDEV_SEL()  // Всавляем еще раз омшанник

            val BUTTON_PROJECT_SEL = {      // Проект
                tools.referenceClick("BUTTON_PROJECT_SEL", "MODAL")
                assertTrue(tools.headerWait("Выбор проекта"),
                    "@@@@ карандашик BUTTON_PROJECT_SEL на поле Проект : нет справочника с заголовком Выбор проекта @@")
        // 0203.001.001 / 0203.001.001 1-Й ЭТАП (ВОСТОЧНЫЙ КОРИДОР), ДЛЯ ОБЕСПЕЧЕНИЯ ПОДАЧИ ГАЗА В ОБЪЕМЕ ДО 63 МЛРД.М3/ГОД Этап 2.1. Линейная часть. Участок «Починки-Анапа», км 0 –км 347,5 (км 0 – км 181, км 181 – км 295,7, км 295,7 – км 347,5)
                tools.xpathClick("//*[contains(text(),'1-Й ЭТАП (ВОСТОЧНЫЙ КОРИДОР)')]/ancestor::td", "MODAL")    //tr  tbody  table

                tools.OK()
            }
            BUTTON_PROJECT_SEL()

            val ATTR_RefToProject = tools.reference("ATTR_RefToProject", "MODAL","//descendant::input")
            assertContains(
                ATTR_RefToProject?.getAttribute("value") ?: "NONE",
                "1-Й ЭТАП (ВОСТОЧНЫЙ КОРИДОР)", false,
                "@@@@ карандашик BUTTON_PROJECT_SEL : После выбора в поле Проекта разработки из справочника в поле фильтра Проект - пусто, а должно стоять Название проекта из справочника @@"
            )
            tools.referenceClick("BUTTON_ERASE_PROJECT", "MODAL")
            Thread.sleep(threadSleep)
            assertTrue(((ATTR_RefToProject?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_PROJECT(Проект) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_PROJECT_SEL()  // Всавляем еще раз

            val BUTTON_TYPE_DOC = {     // Тип документации из справочника Типы технической документации title=" Марки РД"
                tools.referenceClick("BUTTON_TYPE_DOC", "MODAL")
                assertTrue(tools.headerWait("Типы технической документации"),
                    "@@@@ карандашик BUTTON_TYPE_DOC на поле Тип документации : нет справочника с заголовком Типы технической документации @@")
                //   Разделы документации / Марки РД - ни один нельзя присвоить надо раскрывать слева дерево до последнего
                // Только SRV1: слева Марки РД - Справа АР Архитектурные решения (галочку) и ОК

                tools.xpathClick("//div[contains(@title,'Марки РД')]", "MODAL")   //)/ancestor::tr")
                // table body tdr td div div+div(>)+img+span
                //tools.xpathLast("//span[contains(text(),'Марки РД')]/ancestor::tr")?.click()

                // Ошибка Firefox
                // Element: [[FirefoDriver: firefo on WINDOWS (1f8f6504-78cc-4d5e-9c4e-1642e0a2cf62)] -> xpath: //html/body/descendant::a[contains(text(),'АР Архитектурные решения')]/ancestor::tr]
                // org.openqa.selenium.ElementNotInteractableException: Element <tr class="  x-grid-row"> could not be scrolled into view
                //tools.xpathLast("//a[contains(text(),'АР Архитектурные решения')]/ancestor::tr")?.click()
                tools.xpathClick("//a[contains(text(),'АР Архитектурные решения')]/ancestor::td/preceding-sibling::td","MODAL")
                tools.OK()
            }

            BUTTON_TYPE_DOC()

            val ATTR_TechDoc_Sort = tools.reference("ATTR_TechDoc_Sort", "MODAL", "//descendant::input")

            assertContains(ATTR_TechDoc_Sort?.getAttribute("value") ?: "NONE", "АР Архитектурные решения",false,
                "@@@@ поле фильтра Тип документации не содержит значение АР Архитектурные решения после выбора @@")
            tools.referenceClick("BUTTON_ERASE_TTD", "MODAL")
        Thread.sleep(threadSleep)
            assertTrue(((ATTR_TechDoc_Sort?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_TTD(Тип документации) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_TYPE_DOC()  // Всавляем еще раз

            val BUTTON_OBJ_STR = {      // Объект структуры
                tools.referenceClick("BUTTON_OBJ_STR", "MODAL")
                assertTrue(tools.headerWait("Объекты структуры"),
                    "@@@@ карандашик BUTTON_OBJ_STR на поле Объект структуры : нет справочника с заголовком Объекты структуры @@")
                // Для всех 0203 / 051-1007345 Структура объекта "Расширение ЕСГ для обеспечения подачи газа в газопровод «Южный поток».1-й этап (Западный коридор), для обеспечения подачи газа в объеме 31,5 млрд. м3/"
                //  Слева 051-1007345 Структура объекта "Расшир..." тогда 0203.КТО.001 Комплекс термического обезвреживания отходов  (справа)
                //  или слева тогда 0203.КТО.001.4701- 4799.007 Линии электропередач воздушные и электротехнические коммуникации
                //tools.xpathLast("//a[contains(text(),'Структура объекта')]/ancestor::tr")?.click()
                // Firefox сбоит Element <tr class="x-grid-tree-node-expanded  x-grid-row"> could not be scrolled into view
                //tools.xpathLast("//span[contains(text(),'Расширение ЕСГ')]/ancestor::tr")?.click()
                tools.xpathClick("//div[contains(@title,'Расширение ЕСГ')]", "MODAL")
               // tools.xpathClick("//span[contains(text(),'Расширение ЕСГ')]/preceding-sibling::img")
                tools.OK()
            }
            BUTTON_OBJ_STR()

            val ATTR_OCC = tools.reference("ATTR_OCC","MODAL","//descendant::input")
            assertContains(ATTR_OCC?.getAttribute("value") ?: "NONE", "Расширение ЕСГ",false,
                "@@@@ поле фильтра Объект структуры не содержит значение Расширение ЕСГ @@")
            tools.referenceClick("BUTTON_ERASE_OS","MODAL")
        Thread.sleep(threadSleep)
            assertTrue(((ATTR_OCC?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_OS(Объект структуры) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_OBJ_STR()  // Всавляем еще раз

            val BUTTON_ORG_SEL = {      // Организация  ГПП  Газпромпроектирование для всех
                tools.referenceClick("BUTTON_ORG_SEL", "MODAL")
                assertTrue(tools.headerWait("Организации/Подразделения"),
                    "@@@@ карандашик BUTTON_ORG_SEL на поле Организация/Подразд. : нет справочника с заголовком Организации/Подразделения @@")
                //FireFox не работает Element <tr class="  x-grid-row"> could not be scrolled into view  td входит в ссылку
                //tools.xpathLast("//a[contains(text(),'Газпромпроектирование')]/ancestor::tr")?.click()
                tools.xpathClick("//a[contains(text(),'Газпромпроектирование')]/ancestor::td/preceding-sibling::td", "MODAL") // на квадратик слева
                tools.OK()
            }
            BUTTON_ORG_SEL()

            val ATTR_ORGANIZATION_LINK =
                tools.reference("ATTR_ORGANIZATION_LINK", "MODAL","//descendant::input")
            assertContains(ATTR_ORGANIZATION_LINK?.getAttribute("value") ?: "NONE", "Газпромпроектирование",false,
                "@@@@ поле фильтра Организация/Подразд. не содержит значение Газпромпроектирование после выбора @@")
            tools.referenceClick("BUTTON_ERASE_ORG", "MODAL")
        Thread.sleep(threadSleep)
            assertTrue(((ATTR_ORGANIZATION_LINK?.getAttribute("value") ?: "NONE").length) == 0,
                "@@@@ крестик BUTTON_ERASE_ORG(Организация/Подразд.) : После удаления поля фильтра поле не пусто, а должно быть пусто @@")
            BUTTON_ORG_SEL()  // Всавляем еще раз

            tools.OK()
        
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

        val description = tools.reference("ATTR_USER_QUERY_NAME", "MODAL" ,"//descendant::input") // Описание
        assertTrue(description?.getAttribute("value")!!.contains("Тест $localDateNow"),
            "@@@@ Нет в списке фильтра с именем Фильтр $localDateNow @@")
        description.clickSend(" @@")
        assertTrue(description.getAttribute("value").contains("@@"),
            "@@@@ В Наименовании фильтра не прописалось @@ при редактировании @@")



            tools.reference("ATTR_DATE_START", "MODAL","//descendant::input")
                ?.clickSend(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now()))
            tools.reference("ATTR_DATE_END", "MODAL", "//descendant::input")
                ?.clickSend(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now()))
             tools.reference("ATTR_DATE_RELEASE_DOCUMENT", "MODAL", "//descendant::input")
                ?.clickSend(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now()))
            // не работает
           // tools.referenceClick("ATTR_DATE_RELEASE_DOCUMENT", "MODAL", "//div[starts-with(@class, 'Edit_trigger_')]")     //"//descendant::div[contains(@id, 'picker')]")
           // tools.xpath("//span[text()='Сегодня']")?.click()

            val ATTR_DATE_RELEASE_DOCUMENT =  tools.reference("ATTR_DATE_RELEASE_DOCUMENT", "MODAL", "//descendant::input")
            val ATTR_DATE = ATTR_DATE_RELEASE_DOCUMENT?.getAttribute("value")
            assertEquals(ATTR_DATE_RELEASE_DOCUMENT?.getAttribute("value") ?:"NONE",
                DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()),
                "@@@@ В Дата не стоит сегодняшняя дата после занесения из выбора Сегодня $ATTR_DATE @@")

            tools.reference("ATTR_USER_QUERY_STATUS", "MODAL", "//descendant::input")
                ?.clickSend("В разработке")
            tools.reference("ATTR_DOC_AUTHOR","MODAL", "//descendant::input")
                ?.clickSend("SYSADMIN")

            // Только на SRV1 после выкачки-закачки схемы
            //tools. referenceClickLast("ATTR_RESPONSIBLE_USER")   ОШИБОЧНОЕ ПОЛЕ Ответственный
            //tools.xpathLast("// *[@data-reference='ATTR_RESPONSIBLE_USER']/descendant::input")
            //    ?.sendKeys("SYSADMIN")
            // Можно очистить BUTTON_ERASE_RESP_USER ??

            tools. reference("ATTR_RESPONSIBLE_USER", "MODAL", "//descendant::input")
                ?.clickSend("SYSADMIN")
           // assertTrue(tools.headerWait("Выбор пользователей"),
           //     "@@@@ стрелочка ATTR_RESPONSIBLE_USER (Ответственный) на поле Ответственный : нет справочника с заголовком Выбор пользователей @@")
        // Ошибка Firefox  Element <tr class="  x-grid-row"> could not be scrolled into view
            //tools.xpathLast("//a[contains(text(),'SYSADMIN')]/ancestor::tr")?.click()
           // tools.xpathClick("//span[contains(text(),'SYSADMIN')]/ancestor::td/preceding-sibling::td","MODAL")
           // tools.OK()

        // Надо поставить проверку всех заполненных полей

            tools.OK()
        
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
   //@Disabled
   @DisplayName("Удаление фильтров")
   fun n11_DeleteUserQuery(repetitionInfo: RepetitionInfo) {
        workTable()

       val nomberFilter = "${repetitionInfo.currentRepetition}"
       val deleteFilter = "Удалить фильтр"
       if (DT > 6) println("Test нажатия на $deleteFilter")

        clickFilter(nomberFilter, "CMD_DELETE_USER_QUERY")  // встать на фильтр

       // Вы действительно хотите удалить объект "(Все проекты) Фильтр" из системы?
       tools.OK("yes-modal-window-btn")
        if (DT > 6) println("Конец Test нажатия на $deleteFilter")

   }
}

