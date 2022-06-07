package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.Point
import org.openqa.selenium.chrome.ChromeOptions
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties



/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */

@DisplayName("Testing Tools Menu-Icons Test")
@TestMethodOrder(MethodOrderer.MethodName::class)
class ToolsRef {
    companion object {
    const val DT: Int = 9
    const val NN:Int = 10
    // переменная для драйвера
    lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var tools: Tools
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
        if (DT>7) println("Вызов BeforeAll")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
            WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()
        //окно разворачивается на полный второй экран-1500 1500 3000 2000,0
        driver.manage().window().position = Point(2000,-1000)
        driver.manage().window().maximize()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = ConfProperties.getProperty("loginpageTDM")
            if (DT>8) println("Открытие страницы $loginpage")
            driver.get(loginpage)

            val login = ConfProperties.getProperty("loginTDM")
            val password = ConfProperties.getProperty("passwordTDM")
            if (DT>8) println("login= $login   password= $password")
            Login(driver).loginIn(login, password)
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            if (DT>7) println("Вызов BeforeAll")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера
        }
    }

    @BeforeEach
    fun beforeEach(){
        if (DT>7) println("Вызов BeforeEach верхний")
        //driver.navigate().refresh()
    }
    @AfterEach
    fun afterEach(){
        if (DT>7) println("Вызов AfterEach верхний")
        tools.closeEsc5()
        //driver.navigate().refresh()
    }

    /**
     * тестовый метод нажатия на кнопки
     *
     */
    @Nested
    @DisplayName("Testing each menu separately")
    inner class HeadMenuTest  {
        @BeforeEach
        fun beforeEach(){
            if (DT>7) println("Вызов inner Head BeforeEach")
            tools.qtipClickLast("Объекты")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }
        @AfterEach
        fun afterEach(){
            if (DT>7) println("Вызов inner Head AfterEach 5 раз closeEsc")
            tools.closeEsc5()
        }

        @RepeatedTest(NN)
        @DisplayName("Объекты")
        fun mainMenuTest() {
            val mainMenu = "Объекты"
            if (DT>8) println("Test нажатия на $mainMenu TDMS Web")
            tools.qtipClickLast(mainMenu)
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }
        @RepeatedTest(NN)
        @DisplayName("Рабочий стол")
        fun workTableTest() {
            val workTable = "Рабочий стол"
            if (DT>8) println("Test нажатия на $workTable")
            tools.qtipClickLast(workTable)
            assertTrue(tools.titleContain(workTable))
            assertTrue(tools.qtipPressedLast(workTable))
        }
        @RepeatedTest(NN)
        @DisplayName("Объекты")
        fun objectsTest() {
            val objects = "Объекты"
            if (DT>8) println("Test нажатия на $objects")
            tools.qtipClickLast(objects)
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast(objects))
        }
        @RepeatedTest(NN)
        @DisplayName("Почта")
        fun mailTest() {
            val mail = "Почта"
            if (DT>8) println("Test нажатия на $mail")
            tools.qtipClickLast(mail)
            assertTrue(tools.titleContain(mail))
            assertTrue(tools.qtipPressedLast(mail))
        }
       //@Disabled
        @RepeatedTest(NN)
        @DisplayName("Совещания")
        fun meetingTest() {
            val meeting = "Совещания"
            // val meeting = "Чат"
            if (DT>8) println("Test нажатия на $meeting")
            tools.qtipClickLast(meeting)
            assertTrue(tools.titleContain(meeting))
            //assertTrue(tools.titleContain("Каналы"))
            assertTrue(tools.qtipPressedLast(meeting))
        }
        @Disabled
        @RepeatedTest(NN)
        @DisplayName("Диаграмма Ганта")
        fun ganttchartTest(repetitionInfo: RepetitionInfo) {
            driver.navigate().refresh()
        if (repetitionInfo.currentRepetition == 1) driver.navigate().refresh()
            val ganttchart = "Диаграмма Ганта"
            if (DT>8) println("Test нажатия на $ganttchart")
            tools.qtipClickLast(ganttchart)
            //assertTrue(tools.titleContain(ganttchart))  // Нет заголовка
            assertTrue(tools.qtipPressedLast(ganttchart))
        }
        @RepeatedTest(NN)
        @DisplayName("Справка")
        fun helpTest() {
            val help = "Справка"
            if (DT>8) println("Test нажатия на $help")
            tools.qtipClickLast(help)
            //assertFalse(tools.titleContain(help))      // Исправить!!! NOT
            assertTrue(tools.qtipPressedLast(help))
        }
        @RepeatedTest(NN)
        @DisplayName("Искать")
        fun searchTest() {
            val search = "Искать"
            if (DT>8) println("Test нажатия на $search")
            tools.qtipClickLast("Рабочий стол")  // Ошибка TDMS - отжимается кнопка
            tools.qtipClickLast("Объекты")
            tools.qtipLast("Введите текст")?.sendKeys("Лебедев")
            tools.qtipClickLast(search)
            assertTrue(tools.titleContain("Результаты"))
            //val value = driver.findElement(By.xpath("//input[contains(@data-qtip, 'Введите текст')]")).getAttribute("value")
            assertEquals("Лебедев", tools.qtipLast("Введите текст")?.getAttribute("value"))
            tools.qtipClickLast("Очистить")
            assertEquals("", tools.qtipLast("Введите текст")?.getAttribute("value"))
        }
        @RepeatedTest(NN)
        @DisplayName("Уведомления")
        fun notificationTest() {
            val notification = "Уведомления"
            if (DT>8) println("Test нажатия на $notification")
            tools.qtipClickLast(notification)
            assertEquals("Окно сообщений", tools.windowTitle())
            tools.closeXLast()
        }
    }

    @Nested
    @DisplayName("Testing Tools Box")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class ToolTest  {
        @BeforeEach
        fun beforeEach(){
            if (DT>7)  println("Вызов inner Tools BeforeEach")
            tools.qtipClickLast("Объекты")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }
        @AfterEach
        fun afterEach(){
            if (DT>7) println("Вызов inner Tools AfterEach 5 раз closeEsc")
            tools.closeEsc5()
        }
        @RepeatedTest(NN)
        @DisplayName("Показать/скрыть дерево")
        fun open_showTreeTest(repetitionInfo: RepetitionInfo) {
            if (repetitionInfo.currentRepetition == 1) driver.navigate().refresh()
            val open_showTree = "Показать/скрыть дерево"
            if (DT>8) println("Test нажатия на $open_showTree")
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
            if (repetitionInfo.currentRepetition == 1) driver.navigate().refresh()
            val open_showPreviewPanel = "Показать/скрыть панель предварительного просмотра"
            if (DT>8) println("Test нажатия на $open_showPreviewPanel")
            assertTrue(tools.qtipPressedLast(open_showPreviewPanel))
            tools.qtipClickLast(open_showPreviewPanel)
            assertFalse(tools.qtipPressedLast(open_showPreviewPanel))
            tools.qtipClickLast(open_showPreviewPanel)
            assertTrue(tools.qtipPressedLast(open_showPreviewPanel))

        }
        @RepeatedTest(NN)
        @DisplayName("Создать фильтр")
        fun filterTest(repetitionInfo: RepetitionInfo) {
            if (repetitionInfo.currentRepetition == 1) driver.navigate().refresh()
            val filter = "Создать фильтр"
            if (DT>8) println("Test нажатия на $filter")
            tools.referenceClickLast("CMD_CREATE_USER_QUERY")
            //tools.qtipClickLast(filter)
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "Наименование фильтра"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Обновить")
        fun renewTest(repetitionInfo: RepetitionInfo) {
            if (repetitionInfo.currentRepetition == 1) driver.navigate().refresh()
            val renew = "Обновить"
            if (DT>8) println("Test нажатия на $renew")
            tools.referenceClickLast("TDMS_COMMAND_UPDATE")
            //tools.qtipClickLast(renew)
        }
        @RepeatedTest(NN)
        @DisplayName("Администрирование групп")
        fun adminUserTest() {
            val adminUser = "Администрирование групп"
            if (DT>8) println("Test нажатия на $adminUser")
            tools.referenceClickLast("CMD_GROUP_CHANGE")
            //tools.qtipClickLast(adminUser)
            assertTrue(tools.windowTitleWait("Редактирование групп"))
            assertTrue(tools.referenceWaitText("STATIC1", "Группы пользователей"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Удалить структуру объектов")
        fun delObjectsTest() {
            val delObjects = "Удалить структуру объектов"
            if (DT>8) println("Test нажатия на $delObjects")
            tools.referenceClickLast("CMD_STRUCT_OBJS_DELETE")
            //tools.qtipClickLast(delObjects)
            assertTrue(tools.selectedDialogTitleWait("Удаление структуры объектов"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Создать объект разработки")
        fun createObjectTest() {
            val createObject = "Создать объект разработки"
            if (DT>8) println("Test нажатия на $createObject")
            tools.referenceClickLast("CMD_OBJECT_STRUCTURE_CREATE")
            //tools.qtipClickLast(createObject)
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            assertTrue(tools.referenceWaitText("T_ATTR_OCC_CODE", "Код объекта"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Параметры системы")
        fun systemParametersTest() {
            val systemParameters = "Параметры системы"
            if (DT>8) println("Test нажатия на $systemParameters")
            tools.referenceClickLast("CMD_SYSTEM_SETTINGS")
            //tools.qtipClickLast(systemParameters)
            assertTrue(tools.windowTitleWait("Параметры системы"))
            assertTrue(tools.referenceWaitText("T_VER_SERVER", "Версия сервера:"))
            assertTrue(tools.referenceWaitText("T_VER_TDM365", "Версия TDM365:"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Настройка шаблона уведомлений")
        fun configuringNotificationTest(repetitionInfo: RepetitionInfo) {
            val configuringNotification = "Настройка шаблона уведомлений"
            val nomerTesta: Int = repetitionInfo.currentRepetition
           if ((nomerTesta % 10 == 1)) driver.navigate().refresh()
            if (DT>8) println("Test $nomerTesta нажатия на $configuringNotification")
            Thread.sleep(2000)
            tools.referenceClickLast("CMD_NOTIFICATIONS_SETTINGS")
            //tools.qtipClickLast(configuringNotification)
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            assertTrue(tools.referenceWaitText("T_ATTR_NAME", "Наименование"))
            assertTrue(tools.referenceWaitText("T_ATTR_REGULATION_START_TIME", "Время запуска проверки регламента"))
            tools.closeXLast()
            Thread.sleep(2000)
        }
    }
    @Nested
    @DisplayName("Testing CETD")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class CETDTest  {
            @BeforeEach
            fun beforeEach(){
                if (DT>7)  println("Вызов inner Tools BeforeEach")
                tools.qtipClickLast("Объекты")
                assertTrue(tools.titleContain("TDM365"))
                assertTrue(tools.qtipPressedLast("Объекты"))
            }
            @AfterEach
            fun afterEach(){
                if (DT>7) println("Вызов inner Tools AfterEach 5 раз closeEsc")
                tools.closeEsc5()
            }
            private fun openCETD() {
                // assertFalse(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
                // tools.qtipClickLast("СЭТД")
                // assertTrue(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
                repeat(7) {
                    tools.referenceClickLast("SUB_SETD_SYNC")
                    //tools.qtipClickLast("СЭТД")
                    if (tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false) return
                    if (DT>6)  println("####### СЭТД Повтор *##*$it  открытия через $it sec #######")
                    repeat(3) { tools.closeEsc() }
                    tools.qtipClickLast("Объекты")
                    Thread.sleep(1000L * it)
                }
                if (DT>5) println("&&&&&&&&& Не открылось СЭТД за 7 опросов  &&&&&&&&&")
                assertTrue(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
            }

            private fun clickMenu(menu: String, title: String): Boolean {
                repeat(7) {
                    openCETD()
                    tools.xpathClickMenu(menu)
                    if (tools.messageTitleWait(title)) return true
                    if (DT>6)  println("####### пункт MENU за *##*$it не нажалось  $menu - нет  $title ждем $it sec #######")
                    tools.closeEsc()
                    tools.qtipClickLast("Объекты")
                    Thread.sleep(1000L * it)
                }
                if (DT>5)  println("&&&&&&&&& Не нажалось $menu за 7 нажатий $title  &&&&&&&&&")
                assertTrue(tools.messageTitleWait(title))
                return false
            }
        @RepeatedTest(NN)
        @DisplayName("Поток - Проверка статуса проекта")
        fun flowTest() {
            val flow = "Поток - Проверка статуса проекта"
            if (DT>8) println("Test нажатия на $flow")
            //openCETD()
            //tools.xpathClickMenu(flow)
            clickMenu(flow, "TDMS")
            assertTrue(tools.messageTitleWait("TDMS"))
            val msgText = tools.xpathGetText("//div[starts-with(@id,'messagebox-') and  contains(@id,'-msg')]")
            assertTrue(msgText.contains("Да - Ввод GUID проекта вручную"))
            assertTrue(msgText.contains("Нет - Выбор проекта в системе"))
            tools.closeXLast()
            assertTrue(tools.messageTitleWait("Ввод значения"))
            tools.closeXLast()
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Поток 0 - Отправка проекта")
        fun flow0Test() {
            val flow0 = "Поток 0 - Отправка проекта"
            if (DT>8) println("Test нажатия на $flow0")
            //openCETD()
            //tools.xpathClickMenu(flow0)
            clickMenu(flow0, "TDMS")
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Поток 1 - Отправка передаточного документа")
        fun flow1Test() {
            val flow1 = "Поток 1 - Отправка передаточного документа"
            if (DT>8) println("Test нажатия на $flow1")
            //openCETD()
            //tools.xpathClickMenu("Поток 1 - Отправка передаточного документа")
            clickMenu(flow1, "Ввод пути к папке синхронизации")
            assertTrue(tools.messageTitleWait("Ввод пути к папке синхронизации"))
            tools.closeXLast()
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("Поток 2.1 - Ответ о результате передаче РЗ")
        fun flow2Test() {
            val flow2 = "Поток 2.1 - Ответ о результате передаче РЗ"
            if (DT>8) println("Test нажатия на $flow2")
            //openCETD()
            //tools.xpathClickMenu(flow2)
            clickMenu(flow2, "TDMS")
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
           assertTrue(tools.selectedDialogTitleWait("Выбор Реестра замечаний"))
            tools.closeXLast()
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Поток 3 - Отправка ответов на замечания")
        fun flow3Test() {
            val flow3 = "Поток 3 - Отправка ответов на замечания"
            if (DT>8) println("Test нажатия на $flow3")
            //openCETD()
            //tools.xpathClickMenu("Поток 3 - Отправка ответов на замечания")
            clickMenu(flow3, "TDMS")
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
            assertTrue(tools.selectedDialogTitleWait("Выбор Реестра замечаний"))
            tools.closeXLast()
        }
    }
}