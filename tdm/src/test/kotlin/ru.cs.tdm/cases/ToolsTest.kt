package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
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
class ToolsTest {
    companion object {
        const val DT: Int = 9
    const val NN:Int = 30
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
            //окно разворачивается на полный второй экран
            driver = ChromeDriver(ChromeOptions().addArguments("--window-position=2000,0"))
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
            tools.qtipClickLast("Главное меню")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }
        @AfterEach
        fun afterEach(){
            if (DT>7) println("Вызов inner Head AfterEach 5 раз closeEsc")
            tools.closeEsc5()
        }

        @RepeatedTest(NN)
        @DisplayName("Главное меню")
        fun mainMenuTest() {
            tools.qtipClickLast("Главное меню")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }
        @RepeatedTest(NN)
        @DisplayName("Рабочий стол")
        fun workTableTest() {
            tools.qtipClickLast("Рабочий стол")
            assertTrue(tools.titleContain("Рабочий"))
            assertTrue(tools.qtipPressedLast("Рабочий стол"))
        }
        @RepeatedTest(NN)
        @DisplayName("Объекты")
        fun objectTest() {
            tools.qtipClickLast("Объекты")
            tools.qtipClickLast("Объекты")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }
        @RepeatedTest(NN)
        @DisplayName("Почта")
        fun mailTest() {
            tools.qtipClickLast("Почта")
            assertTrue(tools.titleContain("Почта"))
            assertTrue(tools.qtipPressedLast("Почта"))
        }
        @RepeatedTest(NN)
        @DisplayName("Совещания")
        fun meetingTest() {
            tools.qtipClickLast("Совещания")
            assertTrue(tools.titleContain("Совещания"))
            assertTrue(tools.qtipPressedLast("Совещания"))
        }
        @RepeatedTest(NN)
        @DisplayName("Справка")
        fun helpTest() {
            tools.qtipClickLast("Справка")
            //assertFalse(tools.titleContain("Справка"))      // Исправить!!! NOT
            assertTrue(tools.qtipPressedLast("Справка"))
        }
        @RepeatedTest(NN)
        @DisplayName("Искать")
        fun searchTest() {
            tools.qtipClickLast("Рабочий стол")  // Ошибка TDMS - отжимается кнопка
            tools.qtipClickLast("Объекты")
            tools.qtipLast("Введите текст")?.sendKeys("Лебедев")
            tools.qtipClickLast("Искать")
            assertTrue(tools.titleContain("Результаты"))
            //val value = driver.findElement(By.xpath("//input[contains(@data-qtip, 'Введите текст')]")).getAttribute("value")
            assertEquals("Лебедев", tools.qtipLast("Введите текст")?.getAttribute("value"))
            tools.qtipClickLast("Очистить")
            assertEquals("", tools.qtipLast("Введите текст")?.getAttribute("value"))
        }
        @RepeatedTest(NN)
        @DisplayName("Уведомления")
        fun notificationTest() {
            tools.qtipClickLast("Уведомления")
            assertEquals("Окно сообщений", tools.windowTitle())
            tools.closeXLast()
        }
    }

    @Nested
    @DisplayName("Testing Tools Box")
    inner class ToolTest  {
        @BeforeEach
        fun beforeEach(){
            if (DT>7)  println("Вызов inner Tools BeforeEach")
            tools.qtipClickLast("Главное меню")
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
        fun open_showTreeTest() {
            assertTrue(tools.qtipPressedLast("Показать/скрыть дерево"))
            tools.qtipClickLast("Показать/скрыть дерево")   // Скрыть дерево
            assertFalse(tools.qtipPressedLast("Показать/скрыть дерево"))
            tools.qtipClickLast("Показать/скрыть дерево")   // Показать дерево
            assertTrue(tools.qtipPressedLast("Показать/скрыть дерево"))
        }
        @RepeatedTest(NN)
        @DisplayName("Показать/скрыть панель предварительного просмотра")
        fun open_showPreviewPanelTest() {
            assertTrue(tools.qtipPressedLast("Показать/скрыть панель предварительного просмотра"))
            tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")
            assertFalse(tools.qtipPressedLast("Показать/скрыть панель предварительного просмотра"))
            tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")
            assertTrue(tools.qtipPressedLast("Показать/скрыть панель предварительного просмотра"))

        }
        @RepeatedTest(NN)
        @DisplayName("Создать фильтр")
        fun filterTest() {
            tools.qtipClickLast("Создать фильтр")
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Обновить")
        fun renewTest() {
            tools.qtipClickLast("Обновить")
        }
        @RepeatedTest(NN)
        @DisplayName("Администрирование групп")
        fun adminUserTest() {
            tools.qtipClickLast("Администрирование групп")
            assertTrue(tools.windowTitleWait("Редактирование групп"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Настройка Camunda")
        fun camundaTest() {
            tools.qtipClickLast("Настройка Camunda")
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Удалить структуру объектов")
        fun delObjectsTest() {
            tools.qtipClickLast("Удалить структуру объектов")
            assertTrue(tools.selectedDialogTitleWait("Удаление структуры объектов"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Создать объект разработки")
        fun createObjectsTest() {
            tools.qtipClickLast("Создать объект разработки")
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Параметры системы")
        fun systemParametersTest() {
            tools.qtipClickLast("Параметры системы")
            assertTrue(tools.windowTitleWait("Параметры системы"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Настройка шаблона уведомлений")
        fun configuringNotificationTest() {
            tools.qtipClickLast("Настройка шаблона уведомлений")
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            tools.closeXLast()
        }
            private fun openCETD() {
                // assertFalse(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
                // tools.qtipClickLast("СЭТД")
                // assertTrue(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
                repeat(7) {
                    tools.qtipClickLast("СЭТД")
                    if (tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false) return
                    if (DT>6)  println("####### СЭТД Повтор *##*$it  открытия через $it sec #######")
                    repeat(3) { tools.closeEsc() }
                    tools.qtipClickLast("Главное меню")
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
                    tools.qtipClickLast("Главное меню")
                    Thread.sleep(1000L * it)
                }
                if (DT>5)  println("&&&&&&&&& Не нажалось $menu за 7 нажатий $title  &&&&&&&&&")
                assertTrue(tools.messageTitleWait(title))
                return false
            }
        @RepeatedTest(NN)
        @DisplayName("Поток - Проверка статуса проекта")
        fun flowTest() {
            //openCETD()
            //tools.xpathClickMenu("Поток - Проверка статуса проекта")
            clickMenu("Поток - Проверка статуса проекта", "TDMS")
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
            //openCETD()
            //tools.xpathClickMenu("Поток 0 - Отправка проекта")
            clickMenu("Поток 0 - Отправка проекта", "TDMS")
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Поток 1 - Отправка передаточного документа")
        fun flow1Test() {
            //openCETD()
            //tools.xpathClickMenu("Поток 1 - Отправка передаточного документа")
            clickMenu("Поток 1 - Отправка передаточного документа", "Ввод пути к папке синхронизации")
            assertTrue(tools.messageTitleWait("Ввод пути к папке синхронизации"))
            tools.closeXLast()
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Поток 2.1 - Ответ о результате передаче РЗ")
        fun flow2Test() {
            //openCETD()
            //tools.xpathClickMenu("Поток 2.1 - Ответ о результате передаче РЗ")
            clickMenu("Поток 2.1 - Ответ о результате передаче РЗ", "TDMS")
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
            //openCETD()
            //tools.xpathClickMenu("Поток 3 - Отправка ответов на замечания")
            clickMenu("Поток 3 - Отправка ответов на замечания", "TDMS")
            assertTrue(tools.messageTitleWait("TDMS"))
            tools.closeXLast()
            assertTrue(tools.selectedDialogTitleWait("Выбор Реестра замечаний"))
            tools.closeXLast()
        }
    }
}