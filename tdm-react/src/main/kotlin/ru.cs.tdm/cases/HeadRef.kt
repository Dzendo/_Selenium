package ru.cs.tdm.cases

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.TDM365
import ru.cs.tdm.data.startDriver
import ru.cs.tdm.data.TestsProperties

/**
 *
 * data-reference="SUB_SYSADMIN"
 */
// Видимое имя класса при тестах, но нигде не показывается
@DisplayName("Testing Tools Menu-Icons Test")
// Аннотация Junit выполнять тесты по алфавиту. Здесь не обязательно, исследую для сквозного порядка
@TestMethodOrder(MethodOrderer.MethodName::class)
// Класс тестовый в котором лежит все остальное - требование Котлин
class HeadRef {
    // Статический блок - распределяется при старте программы, а не при создании экземпляра
    // BeforeAll и AfterAll должны быть статическими требование Junit
    // и естественно все переменные, которыми они пользуются
    //private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(7))

    companion object {
        // вынесены переменные, что бы менять их только здесь, а они поменяются там внизу в тестах
        private val threadSleep = TestsProperties.threadSleepNomber        // задержки где они есть
        private val DT: Int = TestsProperties.debugPrintNomber            // глубина отладочной информации 0 - ничего не печатать, 9 - все
        //private val NN:Int = TestsProperties.repeateTestsNomber        // количество повторений тестов
        private const val NN:Int = 100                    // количество повторений тестов
        private val repeateIn: Int = TestsProperties.repeateInNomber
        private val repeateOut: Int = TestsProperties.repeateOutNomber
        // переменная для драйвера
        lateinit var driver: WebDriver


        // объявления переменных на созданные ранее классы-страницы
        lateinit var tools: Tools

        /**
         * Осуществление первоначальной настройки
         * Предупреждение: Не смешивайте неявные и явные ожидания.
         * Это может привести к непредсказуемому времени ожидания.
         * Например, установка неявного ожидания 10 секунд и явного ожидания 15 секунд
         * может привести к таймауту через 20 секунд.
         */
        // Аннтотация Котлин, которая говорит, что эта функция будет статичная и Java совместимая
        @JvmStatic
        // Аннотация Junit выполнять один раз перед всеми тестами
        @BeforeAll
        fun beforeAll() {
            if (DT > 7) println("Вызов BeforeAll")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
           driver = startDriver()

            // Создаем экземпляры классов и присваиваем ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = TestsProperties.loginpage
            if (DT > 8) println("Открытие страницы $loginpage")
            val login = TestsProperties.login
            val password = TestsProperties.password
            if (DT > 8) println("login= $login   password= $password")
            driver.get(loginpage)
            //###assertTrue(driver.title == Tdms, "@@@@ Не открылась страница $loginpage - нет заголовка вкладки Tdms @@")
            Login(driver).loginIn(login, password)

            if (DT > 7) println("Конец Вызов BeforeAll")
        }

        @JvmStatic
        // Аннотация Junit выполнять один раз после всех тестов
        @AfterAll
        fun afterAll() {
            if (DT > 7) println("Вызов afterAll")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера
            if (DT > 7) println("Конец Вызов afterAll")
        }
    }  //конец companion object

    // Как обычно, выполняется перед каждым тестом, только он пустой
    @BeforeEach
    fun beforeEach() {
        if (DT > 7) println("Вызов BeforeEach верхний")
        //driver.navigate().refresh()
        if (DT > 7) println("Конец Вызов BeforeEach верхний")
    }

    // верхний срабатывает на все тесты

    // Как обычно, выполняется после каждого теста, только он пустой
    @AfterEach
    fun afterEach() {
        if (DT > 7) println("Вызов AfterEach верхний")
        tools.closeEsc5()
        // Можно послать CTRL/ALT/T
        if (DT > 7) println("Конец Вызов AfterEach верхний")
    }

    /**
     * Тестовый метод нажатия на пункты меню в верхней полосе
     */

    // Аннотация Junit5 вложенных классов тестов - ничего не дает кроме порядка в тестах
    // HeadMenuTest все тесты нажимающие на верхнее меню - можно запустить отдельно
    @Nested
    @DisplayName("Testing each menu separately")
    inner class HeadMenuTest {

        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("Вызов inner Head BeforeEach")
            val mainMenu = "Объекты"
            tools.byIDClick("objects-tab")

            assertTrue(tools.titleContain(TDM365), "@@@@ После нажатия $mainMenu - нет заголовка вкладки TDM365 @@")
            assertTrue(tools.byIDPressed("objects-tab"), "@@@@ После нажатия $mainMenu - кнопка Объекты не утоплена @@")
            if (DT > 7) println("Конец Вызов inner Head BeforeEach")
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("Вызов inner Head AfterEach 5 раз closeEsc")
            tools.closeEsc5()
            // Можно послать CTRL/ALT/T
            if (DT > 7) println("Конец Вызов inner Head AfterEach")
        }

        @RepeatedTest(NN)
        @DisplayName("Объекты")
        fun objectsTest() {
            val mainMenu = "Объекты"
            if (DT > 6) println("Test нажатия на $mainMenu TDMS Web")
            // Клик на элемент с title="Объекты"
            tools.byIDClick("objects-tab")
            // Проверяем заголовок закладки Junit
            assertTrue(tools.titleContain(TDM365), "@@@@ После нажатия $mainMenu - нет заголовка вкладки TDM365 @@")
            // Проверяем, что утоплена кнопка Объекты
            assertTrue(tools.byIDPressed("objects-tab"), "@@@@ После нажатия $mainMenu - кнопка $mainMenu нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $mainMenu TDMS Web")
        }

        @RepeatedTest(NN)
        @DisplayName("Рабочий стол")
        fun workTableTest() {
            val workTable = "Рабочий стол"
            if (DT > 6) println("Test нажатия на $workTable")
            tools.byIDClick("desktop-tab")
            assertTrue(tools.titleContain(workTable), "@@@@ После нажатия $workTable - нет заголовка вкладки $workTable @@")
            assertTrue(tools.byIDPressed("desktop-tab"), "@@@@ После нажатия $workTable - кнопка $workTable нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $workTable")
        }

        @RepeatedTest(NN)
        @DisplayName("Почта")
        fun mailTest() {
            val mail = "Почта"
            if (DT > 6) println("Test нажатия на $mail")
            tools.byIDClick("mail-tab")
            assertTrue(tools.titleContain(mail), "@@@@ После нажатия $mail - нет заголовка вкладки $mail @@")
            assertTrue(tools.byIDPressed("mail-tab"), "@@@@ После нажатия $mail - кнопка $mail нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $mail")
        }

        @RepeatedTest(NN)
        @DisplayName("Совещания")
        fun meetingTest() {
            val meeting = "Совещания"
            tools.byIDClick("chat-tab")
            assertTrue(tools.titleContain(meeting), "@@@@ После нажатия $meeting - нет заголовка вкладки $meeting @@")
            assertTrue(tools.byIDPressed("chat-tab"),
                    "@@@@ После нажатия $meeting - кнопка $meeting нет утоплена @@" )
            if (DT > 6) println("Конец Test нажатия на $meeting")
        }

        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Диаграмма Ганта")
        fun ganttchartTest() {  //(repetitionInfo: RepetitionInfo) {
            val ganttchart = "Диаграмма Ганта"
            if (DT > 6) println("Test нажатия на $ganttchart")
            tools.byIDClick("ganttchart-tab")
            assertTrue(tools.titleContain(ganttchart), "@@@@ После нажатия $ganttchart - нет заголовка вкладки $ganttchart @@")
            assertTrue(tools.byIDPressed("ganttchart-tab"),"@@@@ После нажатия $ganttchart - кнопка $ganttchart нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $ganttchart")
        }

        @RepeatedTest(NN)
        @DisplayName("Справка")
        fun helpTest() {
            val help = "Справка"
            if (DT > 6) println("Test нажатия на $help")
            tools.byIDClick("help-tab")
            assertTrue(tools.titleContain(help), "@@@@ После нажатия $help - нет заголовка вкладки $help @@")
            assertTrue(tools.byIDPressed("help-tab"), "@@@@ После нажатия $help - кнопка $help нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $help")
        }

        //@Disabled
        // placeholder="Найти..."
        @RepeatedTest(NN)
        @DisplayName("Искать")
        fun searchTest() {
            val search = "Искать"
            if (DT > 6) println("Test нажатия на $search")
            assertEquals(null, tools.byID("search-field-search")?.getAttribute("value"),
                "@@@@ При входе в поле поиска не пусто @@")
            tools.byID("search-field")?.sendKeys("Лебедев")
            assertEquals("Лебедев", tools.byID("search-field")?.getAttribute("value"), "@@@@ После ввода Лебедев в поле поиска в поле поиска не Лебедев @@")

            //tools.byIDClicked()
            tools.byIDClick("search-field-search")
            assertTrue(tools.titleContain("Результаты"), "@@@@ После нажатия лупы - нет заголовка вкладки Результаты @@")
            tools.byIDClick("search-field-clean")
            // Проверяется, что поле поиска пустое
            assertEquals(null, tools.byID("search-field-search")?.getAttribute("value"),
                "@@@@ После очистки поля поиска в поле поиска не пусто @@")

            assertTrue(tools.titleContain(TDM365), "@@@@ После нажатия - нет заголовка вкладки TDM365 @@")
            // Проверяем, что утоплена кнопка Объекты
            assertTrue(tools.byIDPressed("objects-tab"), "@@@@ После нажатия  - кнопка нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $search")
        }

        @RepeatedTest(NN)
        @DisplayName("Уведомления")
        fun notificationTest() {
            val notification = "Уведомления"
            if (DT > 6) println("Test нажатия на $notification")
            tools.byIDClick("system-messages")

            // Запрашивает заголовок открывшегося окна и проверяет, что он Окно сообщений
            assertTrue( tools.headerWait("Окно сообщений"), "@@@@ После нажатия $notification - нет заголовка окна Окно сообщений @@")
            tools.closeX()

            if (DT > 6) println("Конец Test нажатия на $notification")
        }
    }
    /**
     * Тестовый метод нажатия на иконки инструментов
     * во второй строке под SYSADMIN только ОБЪЕКТЫ
     * системного подменю расположено ниже в отдельном классе
     */
    @Nested     //################################Testing Tools Box##################################################
    @DisplayName("Testing _Tools Box")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class ToolTest {
        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("Вызов inner Tools BeforeEach")
            tools.byIDClick("objects-tab")
            assertTrue(tools.titleContain(TDM365), "@@@@ После нажатия Объекты - нет заголовка вкладки TDM365 @@")
            assertTrue(tools.byIDPressed("objects-tab"), "@@@@ После нажатия Объекты - кнопка Объекты нет утоплена @@")
            if (DT > 7) println("Конец inner Tools BeforeEach")
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("Вызов inner Tools AfterEach пять раз closeEsc")
            tools.closeEsc5()
            //driver.navigate().refresh()
            if (DT > 7) println("Конец inner Tools AfterEach пять раз closeEsc")
        }

        @RepeatedTest(NN)
        @DisplayName("Показать/скрыть дерево")
        // repetitionInfo (Junit) объект, который содержит в т.ч. номер повтора теста repetitionInfo.currentRepetition
        fun open_showTreeTest(){     //(repetitionInfo: RepetitionInfo) {
            val open_showTree = "Показать/скрыть дерево"
            if (DT > 6) println("Test нажатия на $open_showTree")
            assertTrue(tools.referencePressed("TDMS_COMMAND_COMMON_SHOWTREE","ROOT666"), "@@@@ После нажатия $open_showTree - кнопка $open_showTree нет утоплена @@") // Исправить на референс
            tools.referenceClick("TDMS_COMMAND_COMMON_SHOWTREE", "ROOT666")  // Скрыть дерево
            assertFalse(tools.referencePressed("TDMS_COMMAND_COMMON_SHOWTREE","ROOT666"), "@@@@ После отжатия $open_showTree - кнопка $open_showTree утоплена @@")
            tools.referenceClick("TDMS_COMMAND_COMMON_SHOWTREE")  // Показать дерево
            assertTrue(tools.referencePressed("TDMS_COMMAND_COMMON_SHOWTREE","ROOT666"), "@@@@ После второго нажатия $open_showTree - кнопка $open_showTree нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $open_showTree")
        }

        @RepeatedTest(NN)  // Не сделан reference
        @DisplayName("Показать/скрыть панель предварительного просмотра")
        fun open_showPreviewPanelTest() {   //(repetitionInfo: RepetitionInfo) {
            // if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            //driver.navigate().refresh()
            // data-reference="TDMS_COMMAND_COMMON_SHOWPREVIEW"
            val open_showPreviewPanel = "Показать/скрыть панель предварительного просмотра"
            if (DT > 6) println("Test нажатия на $open_showPreviewPanel")
            assertTrue(tools.referencePressed("TDMS_COMMAND_COMMON_SHOWPREVIEW","ROOT666"), "@@@@ После нажатия $open_showPreviewPanel - кнопка $open_showPreviewPanel нет утоплена @@")
            tools.referenceClick("TDMS_COMMAND_COMMON_SHOWPREVIEW", "ROOT666")
            assertFalse(tools.referencePressed("TDMS_COMMAND_COMMON_SHOWPREVIEW","ROOT666"), "@@@@ После отжатия $open_showPreviewPanel - кнопка $open_showPreviewPanel утоплена @@")
            tools.referenceClick("TDMS_COMMAND_COMMON_SHOWPREVIEW", "ROOT666")
            assertTrue(tools.referencePressed("TDMS_COMMAND_COMMON_SHOWPREVIEW","ROOT666"), "@@@@ После второго нажатия $open_showPreviewPanel - кнопка $open_showPreviewPanel нет утоплена @@")
            if (DT > 6) println("Конец Test нажатия на $open_showPreviewPanel")

        }

        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Создать фильтр")
        fun filterTest(){ //(repetitionInfo: RepetitionInfo) {
            val filter = "Создать фильтр"
            if (DT > 6) println("Test нажатия на $filter")
            workTable()
            tools.referenceClick("CMD_CREATE_USER_QUERY","ROOT666")
            assertTrue(tools.headerWait("Редактирование объекта"),
                "@@@@ После нажатия $filter - нет заголовка окна Редактирование объекта @@")
            assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра", "MODAL"),
                "@@@@ После нажатия $filter и открытия окна с заголовком Редактирование объекта в окне нет поля T_ATTR_USER_QUERY_NAME Наименование фильтра @@")
            tools.closeX()
            tools.byIDClick("objects-tab")
            driver.navigate().refresh()
            if (DT > 6) println("Конец Test нажатия на $filter")
        }
        fun workTable() {
            val workTable = "Рабочий стол"
            if (DT >7) println("Test нажатия на $workTable")
            tools.byIDClick("desktop-tab")
            assertTrue(tools.titleContain(workTable), "@@@@ После нажатия $workTable - нет заголовка вкладки $workTable @@")  // сбоит 1 раз на 100
            assertTrue(tools.byIDPressed("desktop-tab"), "@@@@ После нажатия $workTable - кнопка $workTable нет утоплена @@")
            // проверить что справа Рабочий стол (SYSADMIN)
            // Здесь проверка дерева и отображения
            tools.xpathClick("//span[contains(text(), 'Фильтры')]","ROOT666")
            assertTrue(tools.titleContain("Фильтры"), "@@@@ После нажатия Фильтры - нет заголовка вкладки Фильтры @@")
        }

        @RepeatedTest(NN)
        @DisplayName("Обновить")
        fun renewTest() {  //(repetitionInfo: RepetitionInfo) {
            val renew = "Обновить"
            if (DT > 6) println("Test нажатия на $renew")
            tools.referenceClick("TDMS_COMMAND_UPDATE","ROOT666")

            if (DT > 6) println("Конец Test нажатия на $renew")
        }

        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Администрирование групп")
        fun adminUserTest() {
            val adminUser = "Администрирование групп"
            if (DT > 6) println("Test нажатия на $adminUser")
            tools.referenceClick("CMD_GROUP_CHANGE","ROOT666")
            assertTrue(tools.headerWait("Редактирование групп"),
                "@@@@ После нажатия $adminUser - нет заголовка окна Редактирование групп @@")
            assertTrue(tools.referenceWaitText("STATIC1", "Группы пользователей","MODAL"),
                "@@@@ После нажатия $adminUser и открытия окна с заголовком Редактирование групп в окне нет поля STATIC1 Группы пользователей @@")
            //tools.closeX()
            tools.clickOK()
            if (DT > 6) println("Конец Test нажатия на $adminUser")
        }

        @RepeatedTest(NN)
        @Disabled
        @DisplayName("Импорт пользователей")
        fun importUserTest() {
            val importUser = "Импорт пользователей"
            if (DT > 6) println("Test нажатия на $importUser")
            tools.referenceClick("CMD_IMPORT_USERS","ROOT666")
            //tools.qtipClick(importUser)
            // Нарвался - открывается окно Windows не имеющее отношения к HTML
            //assertTrue(tools.titleWait("window", "Редактирование групп"))
            //assertTrue(tools.referenceWaitText("STATIC1", "Группы пользователей"))
            //tools.closeX()
            tools.closeEsc()
            if (DT > 6) println("Конец Test нажатия на $importUser")
        }

        @RepeatedTest(NN)
        @DisplayName("Создать объект разработки")
        fun createObjectTest() {
            val createObject = "Создать объект разработки"
            if (DT > 6) println("Test нажатия на $createObject")
            tools.referenceClick("CMD_OBJECT_STRUCTURE_CREATE","ROOT666")  // CMD_OBJECT_STRUCTURE_CREATE
            assertTrue(tools.headerWait("Редактирование объекта"),
                "@@@@ После нажатия $createObject - нет заголовка окна Редактирование объекта @@")
            assertTrue(tools.referenceWaitText("T_ATTR_OCC_CODE", "Код объекта","MODAL"),
                "@@@@ После нажатия $createObject и открытия окна с заголовком Редактирование объекта в окне нет поля T_ATTR_OCC_CODE Код объекта @@")
            //tools.referenceClick("cancel-modal-window-btn", "MODAL")
            //tools.clickOK("Отмена")
            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $createObject")
        }

        @RepeatedTest(NN)
        @DisplayName("Настройка шаблона уведомлений")
        fun configuringNotificationTest() { //repetitionInfo: RepetitionInfo) {
            val configuringNotification = "Настройка шаблона уведомлений"
            if (DT > 6) println("Test нажатия на $configuringNotification")
            tools.referenceClick("CMD_NOTIFICATIONS_SETTINGS", "ROOT666")
            assertTrue(tools.headerWait("Редактирование объекта"),
                "@@@@ После нажатия $configuringNotification - нет заголовка окна Редактирование объекта @@")
            assertTrue(tools.referenceWaitText("T_ATTR_NAME", "Наименование", "MODAL"),
                "@@@@ После нажатия $configuringNotification и открытия окна с заголовком Редактирование объекта в окне нет поля T_ATTR_NAME Наименование @@")
            assertTrue(tools.referenceWaitText("T_ATTR_REGULATION_START_TIME", "Время запуска проверки регламента", "MODAL"),
                "@@@@ После нажатия $configuringNotification и открытия окна с заголовком Редактирование объекта в окне нет поля T_ATTR_REGULATION_START_TIME Время запуска проверки регламента @@")
            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $configuringNotification")
        }
    }  // конец inner - nested Testing Tools Box

    /**
     * Тестовый метод нажатия на пункты системного подменю
     * во второй строке под SYSADMIN не только ОБЪЕКТЫ
     * имеет вложенный класс тестирования СЭТД
     */
    //@Disabled
    @Nested              //###############################Testing SubSysadmin###################################################
    @DisplayName("Testing SubSysadmin")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class SubSysadminTest {
        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("Вызов inner SubSysadmin BeforeEach")
            tools.byIDClick("objects-tab")
            assertTrue(tools.titleContain(TDM365), "@@@@ После нажатия Объекты - нет заголовка вкладки TDM365 @@")
            assertTrue(tools.byIDPressed("objects-tab"), "@@@@ После нажатия Объекты - кнопка Объекты нет утоплена @@")
            if (DT > 7) println("Конец inner SubSysadmin BeforeEach")
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("Вызов inner SubSysadmin AfterEach 5 раз closeEsc")
            tools.closeEsc5()
            if (DT > 7) println("Конец inner SubSysadmin AfterEach")
        }

        // вспомогательная процедура открытия системного меню SubSysadmin и для СЭТД

        private fun openSubSysadmin() {
            if (DT > 7) println("Вызов openSubSysadmin")
            repeat(repeateOut) {

                tools.referenceClick("SUB_SYSADMIN","ROOT666")
                // Открыто Меню появляется под 666
                if (tools.byIDs("contmen")) return

                if (DT > 6) println("####### SUB_SYSADMIN Повтор *##*$it  открытия через $it sec #######")
                repeat(3) { tools.closeEsc() }
                tools.byIDClick("objects-tab")
            }
            if (DT > 5) println("&&&&&&&&& Не открылось SUB_SYSADMIN за $repeateOut опросов  &&&&&&&&&")
           // assertTrue(tools.qtipClass("Меню разработчика")?.contains("x-btn-menu-active") ?: false,
            //    "@@@@ После $repeateOut нажатия Меню разработчика меню все-таки не открылось @@")
            if (DT > 7) println("Конец openSubSysadmin")
        }

        /**
         * Вспомогательная процедура нажатия на пункты системного меню SubSysadmin и СЭТД
         * ожидает открытия попап окна с заданным заголовком
         * вообще годится для любого меню - вынести в Tools.kt
         */
        private fun clickMenu(menu: String, window: String, title: String): Boolean {
            if (DT > 7) println("Вызов clickMenu $menu $window $title")
            repeat(repeateOut) {
                //openSubSysadmin()
                tools.xpathClickMenu(menu)
                if (tools.headerWait(title)) return true
                if (DT > 6) println("####### пункт MENU за *##*$it не нажалось  $menu - нет  $title ждем $it sec #######")
                tools.closeEsc()
                tools.qtipClick("Объекты")
            }
            if (DT > 5) println("&&&&&&&&& Не нажалось $menu за $repeateOut нажатий $title  &&&&&&&&&")
            assertTrue(tools.headerWait(title),
                "@@@@ После клика на меню $menu не открылось окно $window с заголовком $title за $repeateOut попыток @@")
            if (DT > 7) println("Конец clickMenu $menu $window $title")
            return false
        }

        // data-reference="FORM_SYSTEM_SETTINGS"
        @RepeatedTest(NN)
        @DisplayName("Информация о системе")
        fun systemParametersTest() {
            val systemParameters = "Информация о системе"
            if (DT > 6) println("Test нажатия на $systemParameters")
            openSubSysadmin()
            tools.byIDClick("CMD_SYSTEM_INFO")

            assertTrue(tools.headerWait(systemParameters),
                "@@@@ После нажатия $systemParameters нет заголовка окна  $systemParameters @@" )
            assertTrue(tools.referenceWaitText("T_VER_SERVER", "Версия сервера", "MODAL"),
                "@@@@ После нажатия $systemParameters - в окне $systemParameters Нет T_VER_SERVER Версия сервера @@")
            assertTrue(tools.referenceWaitText("T_VER_TDM365", "Версия TDM365", "MODAL"),
                "@@@@ После нажатия $systemParameters - в окне $systemParameters Нет T_VER_TDM365 Версия TDM365 @@")

            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $systemParameters")
        }

        // data-reference="FORM_ATTRS_LIST"
        @RepeatedTest(NN)
        @DisplayName("Системные атрибуты")
        fun sysAttributesTest() {
            val sysAttributes = "Системные атрибуты"
            if (DT > 6) println("Test нажатия на $sysAttributes")
            openSubSysadmin()
            tools.byIDClick("CMD_SYSTEM_ATTRS")


            assertTrue(tools.headerWait(sysAttributes),
                "@@@@ После нажатия $sysAttributes нет заголовка окна  Атрибуты @@" )
            //assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_ATTRS_LIST']")) != null,
            //    "@@@@ После нажатия $sysAttributes нет FORM_ATTRS_LIST @@")
            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $sysAttributes")
        }
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Схема данных")
        fun dataTreeTest() {
            val dataTree = "Схема данных"
            if (DT > 6) println("Test нажатия на $dataTree")
            openSubSysadmin()

            tools.byIDClick("CMD_DATA_SCHEME")
            assertTrue(tools.headerWait( dataTree),"@@@@ После нажатия $dataTree нет заголовка окна  $dataTree @@" )

            assertTrue(presenceOfElementLocated(By.xpath("// *[data-reference='FORM_TREE_OBJS']")) != null,
                "@@@@ После нажатия $dataTree в окне нет FORM_TREE_OBJS @@")
            assertTrue(presenceOfElementLocated(By.xpath("// *[data-reference='TREE']")) != null,
                "@@@@ После нажатия $dataTree в окне нет TREE' @@")

            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $dataTree")
        }

        // data-reference="FORM_EVENTS_LOG"
        // data-reference="GRID"
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Журнал событий")
        fun eventsLogTest() {
            val eventsLog = "Журнал событий"
            if (DT > 6) println("Test нажатия на $eventsLog")
            openSubSysadmin()
            tools.byIDClick("CMD_EVENTS_LOG")

            assertTrue(tools.headerWait( eventsLog) ,
                "@@@@ После нажатия $eventsLog нет заголовка окна  $eventsLog @@" )

            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_EVENTS_LOG']")) != null,
                "@@@@ После нажатия $eventsLog в окне нет FORM_EVENTS_LOG @@")
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='GRID']")) != null,
                "@@@@ После нажатия $eventsLog в окне нет GRID @@")
            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $eventsLog")
        }

        // data-reference="FORM_SERVER_LOG"
        // data-reference="QUERY_SERVER_LOG"
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Журнал сервера")
        fun serverLogTest() {
            val serverLog = "Журнал сервера"
            if (DT > 6) println("Test нажатия на $serverLog")
            openSubSysadmin()
            tools.byIDClick("CMD_SERVER_LOG")
            assertTrue(tools.headerWait( serverLog),
                "@@@@ После нажатия $serverLog нет заголовка окна  $serverLog @@" )
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_SERVER_LOG']")) != null,
                "@@@@ После нажатия $serverLog в окне нет FORM_SERVER_LOG @@")
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='QUERY_SERVER_LOG']")) != null,
                "@@@@ После нажатия $serverLog в окне нет QUERY_SERVER_LOG @@")
            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $serverLog")
        }

        @RepeatedTest(NN)
        @DisplayName("Удалить структуру объектов")
        fun delObjectsTest() {
            val delObjects = "Удалить структуру объектов"
            if (DT > 6) println("Test нажатия на $delObjects")
            openSubSysadmin()
            tools.byIDClick("CMD_STRUCT_OBJS_DELETE")
            assertTrue(tools.headerWait("Удаление структуры объектов"),
                "@@@@ После нажатия $delObjects в окне заголовок не содержит Выбор объектов @@")
            tools.closeX()
            if (DT > 6) println("Конец Test нажатия на $delObjects")
        }

        /**
         * Тестовый метод нажатия на пункты подменю СЭТД
         * во второй строке под SYSADMIN и Имитация ИУС СЭТД
         * вложен в верхний класс тестирования меню системщика
         * вложенный класс во вложенный класс пробую и логически это так и есть
         * Можно его запускать отдельно, т.к. он оформлен отдельным классом
         */
        @Nested
        @DisplayName("Testing CETD")
        @TestMethodOrder(MethodOrderer.MethodName::class)
        inner class CETDTest {
            @BeforeEach
            fun beforeEach() {
                if (DT > 7) println("Вызов inner CETD BeforeEach")
                tools.byIDClick("objects-tab")
                assertTrue(tools.titleContain(TDM365), "@@@@ После нажатия Объекты - нет заголовка вкладки TDM365 @@")
                assertTrue(tools.byIDPressed("objects-tab"), "@@@@ После нажатия Объекты - кнопка Объекты не утоплена @@")
                if (DT > 7) println("Конец inner CETD BeforeEach")
            }

            @AfterEach
            fun afterEach() {
                if (DT > 7) println("Вызов inner CETD AfterEach 5 раз closeEsc")
                tools.closeEsc5()
                if (DT > 7) println("Конец inner CETD AfterEach")
            }

            // Имитация ИУС СЭТД
            private fun openCETD() {
                if (DT > 7) println("Вызов openCETD")
                repeat(repeateOut) {
                    openSubSysadmin()
                    // data-reference="SUB_SETD_SYNC"
                    val  element = tools.reference("SUB_SETD_SYNC")
                    Actions(driver).moveToElement(element).click().build().perform()

                    val elementViz = element?.findElement(By.xpath(".//div[starts-with(@class,'Popup_contextMenuContainer_')]"))
                    val styleViz = elementViz?.getAttribute("style")
                    if (styleViz!= null) {
                        if (styleViz.contains("visible")) return
                    }

                    if (DT > 6) println("####### ИУС СЭТД Повтор *##*$it  открытия через $it sec #######")
                    repeat(3) { tools.closeEsc() }
                    tools.byIDClick("objects-tab")
                }
                if (DT > 5) println("&&&&&&&&& Не открылось ИУС СЭТД за $repeateOut опросов  &&&&&&&&&")

                if (DT > 7) println("Конец openCETD")
            }

            // id="CMD_TEST_STREAM_CHECK_CONNECT_NO_AUTHORIZE"  data-reference="CMD_TEST_STREAM_CHECK_CONNECT_NO_AUTHORIZE"
            @RepeatedTest(NN)
            @DisplayName("Поток - Проверка связи без авторизации")
            fun flow_2Test() {
                val flow0 = "Поток - Проверка связи без авторизации"
                if (DT > 6) println("Test нажатия на $flow0")
                openCETD()
                tools.byIDClick("CMD_TEST_STREAM_CHECK_CONNECT_NO_AUTHORIZE")
                assertTrue(tools.headerWait(TDM365),
                    "@@@@ После нажатия $flow0 в окне заголовок не содержит TDM365 @@")

                tools.closeX()
                if (DT > 6) println("Конец Test нажатия на $flow0")
            }
            // ? Соединение установлено (с авторизацией)
            @RepeatedTest(NN)
            @DisplayName("Поток - Проверка связи с авторизацией")
            fun flow_1Test() {
                val flow0 = "Поток - Проверка связи с авторизацией"
                if (DT > 6) println("Test нажатия на $flow0")
                openCETD()
                tools.byIDClick("CMD_TEST_STREAM_CHECK_CONNECT_WITH_AUTHORIZE")
                assertTrue(tools.headerWait(TDM365),
                    "@@@@ После нажатия $flow0 в окне заголовок не содержит TDMS @@")
                tools.closeX()

                if (DT > 6) println("Конец Test нажатия на $flow0")
            }

            @RepeatedTest(NN)
            @DisplayName("Поток - Проверка статуса проекта")
            fun flowTest() {
                val flow = "Поток - Проверка статуса проекта"
                if (DT > 6) println("Test нажатия на $flow")
                openCETD()
                tools.byIDClick("CMD_TEST_STREAM_PROJECT_CHECK")

                assertTrue(tools.headerWait(TDM365),
                    "@@@@ После нажатия $flow в окне заголовок не содержит TDMS @@")
                //val msgText = tools.xpathGetText("//div[starts-with(@id,'messagebox-') and  contains(@id,'-msg')]")
                //assertTrue(msgText.contains("Да")) // - Ввод GUID проекта вручную"))
                //assertTrue(msgText.contains("Нет")) // - Выбор проекта в системе"))
                tools.closeX()
                assertTrue(tools.headerWait("Ввод значения"),
                    "@@@@ После следующего нажатия $flow в окне заголовок не содержит Ввод значения @@")
                tools.closeX()
                assertTrue(tools.headerWait(TDM365),
                    "@@@@ После последнего нажатия $flow в окне заголовок не содержит TDM365 @@")
                tools.closeX()
                if (DT > 6) println("Конец Test нажатия на $flow")
            }

            @RepeatedTest(NN)
            @DisplayName("Поток 0 - Отправка проекта")
            fun flow0Test() {
                val flow0 = "Поток 0 - Отправка проекта"
                if (DT > 6) println("Test нажатия на $flow0")
                openCETD()
                tools.byIDClick("CMD_TEST_STREAM_0")
                assertTrue(tools.headerWait(TDM365),
                    "@@@@ После нажатия $flow0 в окне заголовок не содержит TDM365 @@")
                tools.closeX()
                if (DT > 6) println("Конец Test нажатия на $flow0")
            }

            //@Disabled
            @RepeatedTest(NN)
            @DisplayName("Поток 1 - Отправка передаточного документа")
            fun flow1Test() {
                val flow1 = "Поток 1 - Отправка передаточного документа"
                if (DT > 6) println("Test нажатия на $flow1")
                openCETD()

                tools.byIDClick("CMD_TEST_STREAM_1")

                assertTrue(tools.headerWait( "Выбор сценария"),
                    "@@@@ После нажатия $flow1 в окне заголовок не содержит Выбор сценария @@")

                tools.closeX()
                //assertTrue(tools.headerWait(Tdms), "@@@@ После нажатия $flow1 в окне заголовок не содержит TDMS @@")
                //tools.closeX()
                if (DT > 6) println("Конец Test нажатия на $flow1")
            }


            @RepeatedTest(NN)
            @DisplayName("Поток 5.1 - Отправка окончательного ПД")
            fun flow5Test() {  // tdmsSelectObjectDialog-1593_header-title-textEl
                val flow5 = "Поток 5.1 - Отправка окончательного ПД"
                if (DT > 6) println("Test нажатия на $flow5")
                openCETD()
                tools.byIDClick("CMD_TEST_STREAM_5_1")
                assertTrue(tools.headerWait("Выбор Передаточного документа"),
                    "@@@@ После нажатия $flow5 в окне заголовок не содержит Выбор объектов @@")
                tools.closeX()
                if (DT > 6) println("Конец Test нажатия на $flow5")
            }
        }   // конец Имитация ИУС СЭТД
    }  // конец Testing SubSysadmin
}