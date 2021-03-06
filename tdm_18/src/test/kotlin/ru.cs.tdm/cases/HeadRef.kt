package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.By
import org.openqa.selenium.Point
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties

/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 * data-reference="SUB_SYSADMIN"
 */

@DisplayName("Testing Tools Menu-Icons Test")
@TestMethodOrder(MethodOrderer.MethodName::class)
class HeadRef {
    companion object {
        const val threadSleep = 1000L
        const val DT: Int = 9
        const val NN: Int = 10

        // переменная для драйвера
        lateinit var driver: WebDriver

        // объявления переменных на созданные ранее классы-страницы
        lateinit var tools: Tools
        lateinit var loginIN: String
        lateinit var passwordIN: String

        /**
         * осуществление первоначальной настройки
         * Предупреждение: Не смешивайте неявные и явные ожидания.
         * Это может привести к непредсказуемому времени ожидания.
         * Например, установка неявного ожидания 10 секунд и явного ожидания 15 секунд
         * может привести к таймауту через 20 секунд.
         */
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            if (DT > 7) println("Вызов BeforeAll")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
            WebDriverManager.chromedriver().setup()
            driver = ChromeDriver()
            //окно разворачивается на полный второй экран-1500 1500 3000 2000,0
            driver.manage().window().position = Point(2000, -1000)
            driver.manage().window().maximize()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = ConfProperties.getProperty("loginpageTDM")
            if (DT > 8) println("Открытие страницы $loginpage")
            driver.get(loginpage)

            val login = ConfProperties.getProperty("loginTDM")
            val password = ConfProperties.getProperty("passwordTDM")
            if (DT > 8) println("login= $login   password= $password")
            Login(driver).loginIn(login, password)
            loginIN = login
            passwordIN = password
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            if (DT > 7) println("Вызов BeforeAll")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера
        }
    }

    // верхний срабатывает на все тесты
    @BeforeEach
    fun beforeEach() {
        if (DT > 7) println("Вызов BeforeEach верхний")
        //driver.navigate().refresh()
    }

    // верхний срабатывает на все тесты
    @AfterEach
    fun afterEach() {
        if (DT > 7) println("Вызов AfterEach верхний")
        tools.closeEsc5()
        //driver.navigate().refresh()
    }

    /**
     * тестовый метод нажатия на пункты меню в верхней полосе
     */
    @Nested
    @DisplayName("Testing each menu separately")
    inner class HeadMenuTest {

        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("Вызов inner Head BeforeEach")
            tools.qtipClickLast("Объекты")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("Вызов inner Head AfterEach 5 раз closeEsc")
            tools.closeEsc5()
        }

        @RepeatedTest(NN)
        @DisplayName("Объекты")
        fun mainMenuTest() {
            val mainMenu = "Объекты"
            if (DT > 8) println("Test нажатия на $mainMenu TDMS Web")
            tools.qtipClickLast(mainMenu)
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }

        @RepeatedTest(NN)
        @DisplayName("Рабочий стол")
        fun workTableTest() {
            val workTable = "Рабочий стол"
            if (DT > 8) println("Test нажатия на $workTable")
            tools.qtipClickLast(workTable)
            assertTrue(tools.titleContain(workTable))
            assertTrue(tools.qtipPressedLast(workTable))
        }

        @RepeatedTest(NN)
        @DisplayName("Объекты")
        fun objectsTest() {
            val objects = "Объекты"
            if (DT > 8) println("Test нажатия на $objects")
            tools.qtipClickLast(objects)
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast(objects))
        }

        @RepeatedTest(NN)
        @DisplayName("Почта")
        fun mailTest() {
            val mail = "Почта"
            if (DT > 8) println("Test нажатия на $mail")
            tools.qtipClickLast(mail)
            assertTrue(tools.titleContain(mail))
            assertTrue(tools.qtipPressedLast(mail))
        }

        @RepeatedTest(NN)
        @DisplayName("Совещания")
        fun meetingTest() {
            var meeting = "Совещания"
            var title = meeting
            if (driver.findElements(By.xpath("//*[contains(@data-qtip, 'Чат')]")).size > 0) {
                meeting = "Чат"
                title = "Каналы"
            }
            if (DT > 8) println("Test нажатия на $meeting")
            tools.qtipClickLast(meeting)
            assertTrue(tools.titleContain(title))
            assertTrue(tools.qtipPressedLast(meeting))
        }

        @RepeatedTest(NN)
        @DisplayName("Диаграмма Ганта")
        fun ganttchartTest(repetitionInfo: RepetitionInfo) {

            //if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            val ganttchart = "Диаграмма Ганта"
            if (DT > 8) println("Test нажатия на $ganttchart")
            tools.qtipClickLast(ganttchart)
            //assertTrue(tools.titleContain(ganttchart))  // Нет заголовка
            assertTrue(tools.qtipPressedLast(ganttchart))
            Thread.sleep(threadSleep)
            driver.navigate().refresh()
            Thread.sleep(threadSleep)
            if (driver.title == "Tdms") Login(driver).loginIn(loginIN, passwordIN) // Костыль
        }

        @RepeatedTest(NN)
        @DisplayName("Справка")
        fun helpTest() {
            val help = "Справка"
            if (DT > 8) println("Test нажатия на $help")
            tools.qtipClickLast(help)
            //assertFalse(tools.titleContain(help))      // Исправить!!! NOT
            assertTrue(tools.qtipPressedLast(help))
        }

        @RepeatedTest(NN)
        @DisplayName("Искать")
        fun searchTest() {
            val search = "Искать"
            if (DT > 8) println("Test нажатия на $search")
            tools.qtipClickLast("Рабочий стол")  // Ошибка TDMS - отжимается кнопка
            tools.qtipClickLast("Объекты")
            tools.qtipLast("Введите текст")?.sendKeys("Лебедев")
            tools.qtipClickLast(search)
            assertTrue(tools.titleContain("Результаты"))
            assertEquals("Лебедев", tools.qtipLast("Введите текст")?.getAttribute("value"))
            tools.qtipClickLast("Очистить")
            assertEquals("", tools.qtipLast("Введите текст")?.getAttribute("value"))
        }

        @RepeatedTest(NN)
        @DisplayName("Уведомления")
        fun notificationTest() {
            val notification = "Уведомления"
            if (DT > 8) println("Test нажатия на $notification")
            tools.qtipClickLast(notification)
            assertEquals("Окно сообщений", tools.windowTitle())
            tools.closeXLast()
        }
    }
    /**
     * тестовый метод нажатия на иконки инструментов
     * во второй строке под SYSADMIN только ОБЪЕКТЫ
     * системного подменю расположено ниже в отдельном классе
     */
    @Nested
    @DisplayName("Testing Tools Box")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class ToolTest {
        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("Вызов inner Tools BeforeEach")
            tools.qtipClickLast("Объекты")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("Вызов inner Tools AfterEach пять раз closeEsc")
            tools.closeEsc5()
        }

        @RepeatedTest(NN)
        @DisplayName("Показать/скрыть дерево")
        fun open_showTreeTest(repetitionInfo: RepetitionInfo) {
            if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            val open_showTree = "Показать/скрыть дерево"
            if (DT > 8) println("Test нажатия на $open_showTree")
            assertTrue(tools.qtipPressedLast(open_showTree))
            tools.referenceClickLast("TDMS_COMMAND_COMMON_SHOWTREE")  // Скрыть дерево
            //tools.qtipClickLast(open_showTree)   // Скрыть дерево
            assertFalse(tools.qtipPressedLast(open_showTree))
            tools.referenceClickLast("TDMS_COMMAND_COMMON_SHOWTREE")  // Показать дерево
            //tools.qtipClickLast(open_showTree)   // Показать дерево
            assertTrue(tools.qtipPressedLast(open_showTree))
        }

        @RepeatedTest(NN)  // Не сделан reference
        @DisplayName("Показать/скрыть панель предварительного просмотра")
        fun open_showPreviewPanelTest(repetitionInfo: RepetitionInfo) {
            if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            val open_showPreviewPanel = "Показать/скрыть панель предварительного просмотра"
            if (DT > 8) println("Test нажатия на $open_showPreviewPanel")
            assertTrue(tools.qtipPressedLast(open_showPreviewPanel))
            tools.qtipClickLast(open_showPreviewPanel)
            assertFalse(tools.qtipPressedLast(open_showPreviewPanel))
            tools.qtipClickLast(open_showPreviewPanel)
            assertTrue(tools.qtipPressedLast(open_showPreviewPanel))

        }

        @RepeatedTest(NN)
        @DisplayName("Создать фильтр")
        fun filterTest(repetitionInfo: RepetitionInfo) {
            if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            val filter = "Создать фильтр"
            if (DT > 8) println("Test нажатия на $filter")
            tools.referenceClickLast("CMD_CREATE_USER_QUERY")
            //tools.qtipClickLast(filter)
            assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"))
            assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Обновить")
        fun renewTest(repetitionInfo: RepetitionInfo) {
            if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            val renew = "Обновить"
            if (DT > 8) println("Test нажатия на $renew")
            tools.referenceClickLast("TDMS_COMMAND_UPDATE")
            //tools.qtipClickLast(renew)
        }

        @RepeatedTest(NN)
        @DisplayName("Администрирование групп")
        fun adminUserTest() {
            val adminUser = "Администрирование групп"
            if (DT > 8) println("Test нажатия на $adminUser")
            tools.referenceClickLast("CMD_GROUP_CHANGE")
            //tools.qtipClickLast(adminUser)
            assertTrue(tools.titleWait("window", "Редактирование групп"))
            assertTrue(tools.referenceWaitText("STATIC1", "Группы пользователей"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Создать объект разработки")
        fun createObjectTest() {
            val createObject = "Создать объект разработки"
            if (DT > 8) println("Test нажатия на $createObject")
            tools.referenceClickLast("CMD_OBJECT_STRUCTURE_CREATE")
            //tools.qtipClickLast(createObject)
            assertTrue(tools.titleWait("tdmsEditObjectDialog","Редактирование объекта"))
            assertTrue(tools.referenceWaitText("T_ATTR_OCC_CODE", "Код объекта"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Настройка шаблона уведомлений")
        fun configuringNotificationTest(repetitionInfo: RepetitionInfo) {
            val configuringNotification = "Настройка шаблона уведомлений"
            val nomerTesta: Int = repetitionInfo.currentRepetition
            if ((nomerTesta % 10 == 1)) driver.navigate().refresh()
            if (DT > 8) println("Test $nomerTesta нажатия на $configuringNotification")
            Thread.sleep(threadSleep*3)
            tools.referenceClickLast("CMD_NOTIFICATIONS_SETTINGS")
            //tools.qtipClickLast(configuringNotification)
            Thread.sleep(threadSleep*2)
            assertTrue(tools.titleWait("tdmsEditObjectDialog", "Редактирование объекта"))
            assertTrue(tools.referenceWaitText("T_ATTR_NAME", "Наименование"))
            assertTrue(tools.referenceWaitText("T_ATTR_REGULATION_START_TIME", "Время запуска проверки регламента"))
            tools.closeXLast()
            Thread.sleep(threadSleep*2)
        }
    }  // конец inner - nested Testing Tools Box

    /**
     * тестовый метод нажатия на пункты системного подменю
     * во второй строке под SYSADMIN не только ОБЪЕКТЫ
     * имеет вложенный класс тестирования СЭТД
     */
    @Nested
    @DisplayName("Testing SubSysadmin")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class SubSysadminTest {
        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("Вызов inner SubSysadmin BeforeEach")
            tools.qtipClickLast("Объекты")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("Вызов inner SubSysadmin AfterEach 5 раз closeEsc")
            tools.closeEsc5()
        }

        // вспомогательная процедура открытия системного меню SubSysadmin и для СЭТД
        private fun openSubSysadmin() {
            repeat(7) {
                ///Thread.sleep(threadSleep)
                tools.referenceClickLast("SUB_SYSADMIN")
                ///Thread.sleep(threadSleep)
                if (tools.qtipLastClass("Меню разработчика")?.contains("x-btn-menu-active") ?: false) return
                if (DT > 6) println("####### SUB_SYSADMIN Повтор *##*$it  открытия через $it sec #######")
                repeat(3) { tools.closeEsc() }
                tools.qtipClickLast("Объекты")
                Thread.sleep(threadSleep * it)
            }
            if (DT > 5) println("&&&&&&&&& Не открылось SUB_SYSADMIN за 7 опросов  &&&&&&&&&")
            assertTrue(tools.qtipLastClass("Меню разработчика")?.contains("x-btn-menu-active") ?: false)
        }

        /**
         * вспомогательная процедура нажатия на пункты системного меню SubSysadmin и СЭТД
         * ожидает рткрытия попап окна с заданнным заголовком
         * вообще годится для любого меню - вынести в Tools.kt
         */
        private fun clickMenu(menu: String, window: String, title: String): Boolean {
            repeat(7) {
                //openSubSysadmin()
                ///Thread.sleep(threadSleep)
                tools.xpathClickMenu(menu)
                ///Thread.sleep(threadSleep)
                if (tools.titleWait(window, title)) return true
                if (DT > 6) println("####### пункт MENU за *##*$it не нажалось  $menu - нет  $title ждем $it sec #######")
                tools.closeEsc()
                tools.qtipClickLast("Объекты")
                Thread.sleep(threadSleep * it)
            }
            if (DT > 5) println("&&&&&&&&& Не нажалось $menu за 7 нажатий $title  &&&&&&&&&")
            assertTrue(tools.titleWait(window, title))
            return false
        }

        // data-reference="FORM_SYSTEM_SETTINGS"
        @RepeatedTest(NN)
        @DisplayName("Параметры системы")
        fun systemParametersTest() {
            val systemParameters = "Информация о системе"
            if (DT > 8) println("Test нажатия на $systemParameters")
            openSubSysadmin()
            clickMenu(systemParameters, "window", systemParameters)
            //tools.referenceClickLast("CMD_SYSTEM_SETTINGS")
            //tools.qtipClickLast(systemParameters)
            //println("FORM_SYSTEM_SETTING = ${tools.referenceLast("FORM_SYSTEM_SETTINGS")?.text}")
            assertTrue(tools.referenceWaitText("FORM_SYSTEM_SETTINGS", systemParameters))
            assertTrue(tools.titleWait("window", systemParameters))
            assertTrue(tools.referenceWaitText("T_VER_SERVER", "Версия сервера"))
            assertTrue(tools.referenceWaitText("T_VER_TDM365", "Версия TDM365"))
            tools.closeXLast()
        }

        // data-reference="FORM_ATTRS_LIST"
        @RepeatedTest(NN)
        @DisplayName("Системные атрибуты")
        fun sysAttributesTest() {
            val sysAttributes = "Системные атрибуты"
            if (DT > 8) println("Test нажатия на $sysAttributes")
            openSubSysadmin()
            clickMenu(sysAttributes, "window", "Атрибуты")
            //println("FORM_ATTRS_LIST = ${tools.referenceLast("FORM_ATTRS_LIST")?.text}")
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_ATTRS_LIST']")) != null)
            assertTrue(tools.referenceWaitText("FORM_ATTRS_LIST", "Атрибуты"))
            assertTrue(tools.titleWait("window", "Атрибуты"))
            tools.closeXLast()
        }

        // data-reference="FORM_TREE_OBJS"
        // data-reference="TREE"
        @RepeatedTest(NN)
        @DisplayName("Схема данных")
        fun dataTreeTest() {
            val dataTree = "Схема данных"
            if (DT > 8) println("Test нажатия на $dataTree")
            openSubSysadmin()
            clickMenu(dataTree, "window", dataTree)
            //println("FORM_TREE_OBJS = ${tools.referenceLast("FORM_TREE_OBJS")?.text}")
            //println("TREE = ${tools.referenceLast("TREE")?.text}")
            // Лучше проверять присутствует ли этот элемент в DOM presenceOfElementLocated(By locator)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_TREE_OBJS']")) != null)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='TREE']")) != null)
            assertTrue(tools.referenceWaitText("FORM_TREE_OBJS", dataTree))
            assertTrue(tools.referenceWaitText("TREE", "Типы объектов"))
            assertTrue(tools.titleWait("window", dataTree))
            tools.closeXLast()
        }

        // data-reference="FORM_EVENTS_LOG"
        // data-reference="GRID"
        @RepeatedTest(NN)
        @DisplayName("Журнал событий")
        fun eventsLogTest() {
            val eventsLog = "Журнал событий"
            if (DT > 8) println("Test нажатия на $eventsLog")
            openSubSysadmin()
            clickMenu(eventsLog, "window", eventsLog)
            //println("FORM_EVENTS_LOG = ${tools.referenceLast("FORM_EVENTS_LOG")?.text}")
            //println("GRID = ${tools.referenceLast("GRID")?.text}")
            // Лучше проверять присутствует ли этот элемент в DOM presenceOfElementLocated(By locator)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_EVENTS_LOG']")) != null)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='GRID']")) != null)
            assertTrue(tools.referenceWaitText("FORM_EVENTS_LOG", eventsLog))
            assertTrue(tools.referenceWaitText("GRID", ""))
            assertTrue(tools.titleWait("window", eventsLog))
            tools.closeXLast()
        }

        // data-reference="FORM_SERVER_LOG"
        // data-reference="QUERY_SERVER_LOG"
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("Журнал сервера")
        fun serverLogTest() {
            val serverLog = "Журнал сервера"
            if (DT > 8) println("Test нажатия на $serverLog")
            openSubSysadmin()
            clickMenu(serverLog, "window", serverLog)
            //println("FORM_SERVER_LOG = ${tools.referenceLast("FORM_SERVER_LOG")?.text}")
            //println("QUERY_SERVER_LOG = ${tools.referenceLast("QUERY_SERVER_LOG")?.text}")
            // Лучше проверять присутствует ли этот элемент в DOM presenceOfElementLocated(By locator)
            Thread.sleep(threadSleep)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_SERVER_LOG']")) != null)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='QUERY_SERVER_LOG']")) != null)
            assertTrue(tools.referenceWaitText("FORM_SERVER_LOG", serverLog))
            assertTrue(tools.referenceWaitText("QUERY_SERVER_LOG", "Timestamp"))
            assertTrue(tools.titleWait("window", serverLog))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Удалить структуру объектов")
        fun delObjectsTest() {
            val delObjects = "Удалить структуру объектов"
            if (DT > 8) println("Test нажатия на $delObjects")
            openSubSysadmin()
            clickMenu(delObjects, "tdmsSelectObjectDialog", "Удаление структуры объектов")
            assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Удаление структуры объектов"))
            tools.closeXLast()
        }

        /**
         * тестовый метод нажатия на пункты подменю СЭТД
         * во второй строке под SYSADMIN и Имитация ИУС СЭТД
         * вложенн в верхний класс тестирования меню системщика
         */
        @Nested
        @DisplayName("Testing CETD")
        @TestMethodOrder(MethodOrderer.MethodName::class)
        inner class CETDTest {
            @BeforeEach
            fun beforeEach() {
                if (DT > 7) println("Вызов inner Tools BeforeEach")
                tools.qtipClickLast("Объекты")
                assertTrue(tools.titleContain("TDM365"))
                assertTrue(tools.qtipPressedLast("Объекты"))
            }

            @AfterEach
            fun afterEach() {
                if (DT > 7) println("Вызов inner Tools AfterEach 5 раз closeEsc")
                tools.closeEsc5()
            }

            // Имитация ИУС СЭТД
            private fun openCETD() {
                repeat(7) {
                    openSubSysadmin()
                    Thread.sleep(threadSleep)
                    val click = tools.xpathClickMenu("Имитация ИУС СЭТД")
                    Thread.sleep(threadSleep)
                    if (click) return
                    if (DT > 6) println("####### ИУС СЭТД Повтор *##*$it  открытия через $it sec #######")
                    repeat(3) { tools.closeEsc() }
                    tools.qtipClickLast("Объекты")
                    Thread.sleep(threadSleep * it)
                }
                if (DT > 5) println("&&&&&&&&& Не открылось ИУС СЭТД за 7 опросов  &&&&&&&&&")
                assertTrue(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
            }

            @RepeatedTest(NN)
            @DisplayName("Поток - Проверка связи без авторизации")
            fun flow_2Test() {
                val flow0 = "Поток - Проверка связи без авторизации"
                if (DT > 8) println("Test нажатия на $flow0")
                openCETD()
                clickMenu(flow0, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
            }
            // Отправка запроса на сервер - не надо
            // ✔ Соединение установлено (с авторизацией)
            @RepeatedTest(NN)
            @DisplayName("Поток - Проверка связи с авторизацией")
            fun flow_1Test() {
                val flow0 = "Поток - Проверка связи с авторизацией"
                if (DT > 8) println("Test нажатия на $flow0")
                openCETD()
                clickMenu(flow0, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("Поток - Проверка статуса проекта")
            fun flowTest() {
                val flow = "Поток - Проверка статуса проекта"
                if (DT > 8) println("Test нажатия на $flow")
                openCETD()
                //tools.xpathClickMenu(flow)
                clickMenu(flow, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                val msgText = tools.xpathGetText("//div[starts-with(@id,'messagebox-') and  contains(@id,'-msg')]")
                //assertTrue(msgText.contains("Да")) // - Ввод GUID проекта вручную"))
                //assertTrue(msgText.contains("Нет")) // - Выбор проекта в системе"))
                tools.closeXLast()
                assertTrue(tools.titleWait("messagebox","Ввод значения"))
                tools.closeXLast()
                Thread.sleep(threadSleep)
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("Поток 0 - Отправка проекта")
            fun flow0Test() {
                val flow0 = "Поток 0 - Отправка проекта"
                if (DT > 8) println("Test нажатия на $flow0")
                openCETD()
                clickMenu(flow0, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("Поток 1 - Отправка передаточного документа")
            fun flow1Test() {
                val flow1 = "Поток 1 - Отправка передаточного документа"
                if (DT > 8) println("Test нажатия на $flow1")
                openCETD()
                clickMenu(flow1, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.clickOK()
                //tools.closeXLast()
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Выбор Передаточного документа"))
                tools.closeXLast()
                //assertTrue(tools.titleWait("messagebox","TDMS"))
                //tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("Поток 2.1 - Ответ о результате передаче РЗ")
            fun flow2Test() {
                val flow2 = "Поток 2.1 - Ответ о результате передаче РЗ"
                if (DT > 8) println("Test нажатия на $flow2")
                openCETD()
                clickMenu(flow2, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Выбор Реестра замечаний"))
                tools.closeXLast()
                Thread.sleep(threadSleep)
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("Поток 3 - Отправка ответов на замечания")
            fun flow3Test() {
                val flow3 = "Поток 3 - Отправка ответов на замечания"
                if (DT > 8) println("Test нажатия на $flow3")
                openCETD()
                clickMenu(flow3, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "Выбор Реестра замечаний"))
                tools.closeXLast()
            }
        }   // конец Имитация ИУС СЭТД
    }  // конец Testing SubSysadmin
}