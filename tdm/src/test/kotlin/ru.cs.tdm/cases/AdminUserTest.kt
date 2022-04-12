package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.interactions.Action
import org.openqa.selenium.interactions.Actions
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import kotlin.concurrent.thread


/**
Проверка Администрирование групп:
Добавление/удаление пользователя:
- TDMS Web> Администрирование групп > Окно Редактирование групп:
В левом списке «Группы пользователей» выделить «Все пользователи»
Справа кнопка «Создать пользователя» > Окно «Редактирование пользователя»
Описание: Фамилия 1.2.9-6.1.109 22032022
Логин: ФИО
Чекбокс: Разрешить вход в TDMS
Имя: Имя
Отчество: Отчество
Фамилия: Фамилия
Телефон: 1
E-mail: ya@ya.ru
«Добавить профиль пользователю» > окно «Выбор профиля» > выделить «Приемка документации»> OK
check, что в списке «Профиль» есть строка «Приемка документации»> ERR? >
Два раза : сначала отмена и check  затем заполнить еще раз и >OK
Про скролить правый список до конца > check что последняя строка «Фамилия»>  ERR?
Редактирование пользователя:
В правом списке выделить Фамилия 1.2.9-6.1.109 22032022
Справа кнопка Редактировать пользователя
Сверить все 7 полей с введенными ранее
Сверить Чек-бокс Разрешить вход в TDMS
Сверить наличие профиля Приемка документации
Сверить наличие профиля Default profile
Добавить к описанию @ > Отмена
Сверить отсутствие @ в правом списке у пользователя  Фамилия 1.2.9-6.1.109 22032022
В правом списке выделить Фамилия 1.2.9-6.1.109 22032022
Справа кнопка Редактировать пользователя
Добавить к описанию @ > ОК
Сверить Присутствие @ в правом списке у пользователя  Фамилия 1.2.9-6.1.109 22032022 @
ERR? – Проверить отсутствие ошибок в консоли сервера (если есть – в лог)

-	Удаление пользователя:
Выделить в правом списке  «Все пользователи» последнего Фамилия
Кнопка «Удалить пользователя из системы» > popup TDMS «Удалить пользователя "Фамилия "?»
Check что Фамилия == Фамилия > ERR? > Нет
Про скролить правый список до конца > check что последняя строка «Фамилия»>  ERR?
Выделить в правом списке  «Все пользователи» последнего Фамилия
Кнопка «Удалить пользователя из системы» > popup TDMS «Удалить пользователя "Фамилия "?»
Check что Фамилия == Фамилия > ERR? > Да
check что в списке отсутствует строка «Фамилия»>  ERR? > OK
Добавление/удаление группы:
- TDMS Web> Администрирование групп > Окно Редактирование групп:
Слева кнопка «Создать группу» > Окно «Создание новой группы»
Введите название новой группы: Новая TDM > ERR? > Отмена
check что в левом списке отсутствует строка «Новая TDM»>  ERR?
Слева кнопка «Создать группу» > Окно «Создание новой группы»
Введите название новой группы: Новая TDM > ERR? > ОK > Popup Группа "Новая TDM " создана
check что в левом списке присутствует последняя строка «Новая TDM» и одна>  ERR?
Выделить в левом списке «Группы пользователей» последнюю Новая TDM
Кнопка «Удалить группу» > popup TDMS «Удалить группу "Новая TDM"? »
Check что "Новая TDM"?  == "Новая TDM"?  > ERR? > Нет
Выделить в левом списке «Группы пользователей» последнюю Новая TDM
Кнопка «Удалить группу» > popup TDMS «Удалить группу "Новая TDM"? »
Check что "Новая TDM"?  == "Новая TDM"?  > ERR? > Да
check что в левом списке отсутствует строка «Новая TDM»>  ERR?
	OK  закрываем окно Редактирование групп
Проверки журнала ошибок итд.

Можно выйти из Логина и/или закрыть/открыть Browser
PS (что делает Defult profile - неизвестно) польз удалить - удалился (нет-БАГ)
PPS Можно добавить тест: не удалять созданного пользователя,
в созданную группу добавить созданного пользователя
Затем удалить созданную группу
Затем удалить созданного пользователя
Можно еще проверить работоспособность остальных кнопок формы 2 шт.
 */
@DisplayName("Testing Tools Menu-Icons Test")
@TestMethodOrder(MethodOrderer.MethodName::class)
class AdminUserTest {
    companion object {
    const val DT: Int = 9
    const val NN:Int = 1
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
    }
    @AfterEach
    fun afterEach(){
        if (DT>7) println("Вызов AfterEach верхний")
        //driver.navigate().refresh()
    }
    @Test
    @DisplayName("Главное меню")
    fun n01_mainMenuTest() {
        val mainMenu = "Главное меню"
        if (DT>8) println("Test нажатия на $mainMenu TDMS Web")
        tools.qtipClickLast(mainMenu)
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))
    }
    @Test
    @DisplayName("Администрирование групп")
    fun n02_adminUserTest() {
        val adminUser = "Администрирование групп"
        if (DT>8) println("Test нажатия на $adminUser")
        tools.qtipClickLast(adminUser)
        assertTrue(tools.windowTitleWait("Редактирование групп"))
        //tools.xpathLast("//div[starts-with(@id, 'window-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]")
        val titl = tools.xpathLast("//div[contains(text(),'Редактирование групп')]")
        val idTitle = titl?.getAttribute("id")
        println("id = $idTitle")
        val idWin = idTitle?.split("_")?.get(0)
        println("id = $idWin")
        val win = driver.findElement(By.id(idWin))
        println ("DIV ${win.getAttribute("id")}  ${win.getAttribute("style")}")
        println("location = ${win.location} size= ${win.size} ")
        println("x = ${win.location.x} y = \${win.location.y width= ${win.size.width} height= ${win.size.height} }")
    }

    /**
     * div class = x-grid-item-container
     * <table    <tbody> < tr class = x-grid-row  < td  class = x-grid-cell
     *   <div unselectable="on" class="x-grid-cell-inner " style="text-align:;">Все пользователи</div>
     *          </td> </tr>  </tbody>  </table>
     *          < tbody ...
     *  </table>
     *   //div[text()= "Все пользователи"]
     *   //label[text()= 'Все пользователи']  проверка на null
     */
    @Test
    @DisplayName("Все пользователи")
    fun n03_allUsersTest() {
        val allUsers = "Все пользователи"
        if (DT>8) println("Test нажатия на $allUsers")
        tools.xpathLast("//div[text()= '$allUsers']")?.click()
        assertTrue(tools.xpathLast("//label[text()= '$allUsers']") != null)
    }

    /**
     * <div id="tooltip-1273-innerCt" data-ref="innerCt" style=""
     * class="x-autocontainer-innerCt">Создать пользователя</div>
     * // *[@id="button-1230"]
     * /html/body/div[6]/div[2]/div[1]/div/div/div/div/div/a[4]/span/span/span[1]
     * /html/body/div[8]/div[1]/div/div/div  // толтип
     *
     */
    @Test
    @DisplayName("Создать пользователя")
    fun n04_createUserTest() {
        val createUser = "Создать пользователя"
        if (DT>8) println("Test нажатия на $createUser")
        //tools.xpathLast("//div[text()= 'Создать пользователя']")?.click()?: println("null")
        //tools.xpathLast("//a[@id='button-1633']")?.click()    //  ХХХХХХХХХХХХ
        val button = tools.xpathLast("//a[@id='button-1229']")?: return
        val idButtom = button?.getAttribute("id")
        println("id = $idButtom")
        println ("DIV ${button.getAttribute("id")}  ${button.getAttribute("style")}")
        println("location = ${button.location} size= ${button.size} ")
        println("x = ${button.location.x} y = \${win.location.y width= ${button.size.width} height= ${button.size.height} }")

            Thread.sleep(2000)
            Actions(driver).moveByOffset(1259, 331).perform()
        repeat(10) {
            Thread.sleep(2000)
            Actions(driver).moveByOffset(25, 27).perform()
            Thread.sleep(2000)
            Actions(driver).moveByOffset(-25, -27).perform()
            Thread.sleep(2000)
        }
        assertTrue(tools.windowTitleWait("Редактирование пользователя"))
    }

    /**
     * Описание lable  //label[text()='Описание']/following::div[1]/span под ним div внутри input
     */
    @Test
    @DisplayName("Заполнение нового пользователя")
    fun n05_fillingUserTest() {
        val fillingUser = "Редактирование пользователя"
        if (DT>8) println("Test нажатия на $fillingUser")
        //tools.xpathLast("//div[text()= 'Создать пользователя']")?.click()
        assertTrue(tools.windowTitleWait(fillingUser))
        tools.xpathLast("//label[text()='Описание']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("Фамилия 1.2.9-6.1.111 01042022")
        tools.xpathLast("//label[text()='Логин']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("Логин")
        tools.xpathLast("//label[text()='Разрешить вход в TDMS']/following-sibling::div[1]/descendant::input")
            ?.click()
        tools.xpathLast("//label[text()='Имя']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("Имя")
        tools.xpathLast("//label[text()='Отчество']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("Отчество")
        tools.xpathLast("//label[text()='Фамилия']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("Фамилия")
        tools.xpathLast("//label[text()='Телефон']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("9291234567")
        tools.xpathLast("//label[text()='E-mail']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("ya@ya")
        /*
        tools.xpathLast("// *[@id='button-1280']")?.click()   // Добавить профиль  ХХХХХХХХ
        assertTrue(tools.selectedGridDialogTitleWait("Выбор профиля"))
        val profileUser = "Руководитель"
        if (DT>8) println("Test нажатия на $profileUser")
        val element = tools.xpathLast("//div[text()= 'Руководитель']/ancestor::table")
        val action = Actions(driver)   // Не работает  // *[@id="menuitem-2015-itemEl"]
        action.moveToElement(element).build().perform()
        tools.xpathLast("// *[@id='menuitem-1349-itemEl']")?.click()
        //element?.click()
        //action.doubleClick(element).build().perform()
        tools.clickOK()
        */
        tools.clickOK()
        tools.clickOK()
    }
}


    /**
     * тестовый метод нажатия на кнопки
     *
     */
/*
    @Nested
    @DisplayName("Testing each menu separately")
    @TestMethodOrder(MethodOrderer.Alphanumeric::class)
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
        fun n9_mainMenuTest() {
            val mainMenu = "Главное меню"
            if (DT>8) println("Test нажатия на $mainMenu TDMS Web")
            tools.qtipClickLast(mainMenu)
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("Объекты"))
        }
        @RepeatedTest(NN)
        @DisplayName("Рабочий стол")
        fun n8_workTableTest() {
            val workTable = "Рабочий стол"
            if (DT>8) println("Test нажатия на $workTable")
            tools.qtipClickLast(workTable)
            assertTrue(tools.titleContain(workTable))
            assertTrue(tools.qtipPressedLast(workTable))
        }
        @RepeatedTest(NN)
        @DisplayName("Объекты")
        fun n7_objectsTest() {
            val objects = "Объекты"
            if (DT>8) println("Test нажатия на $objects")
            tools.qtipClickLast(objects)
            tools.qtipClickLast(objects)
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast(objects))
        }
        @RepeatedTest(NN)
        @DisplayName("Почта")
        fun n6_mailTest() {
            val mail = "Почта"
            if (DT>8) println("Test нажатия на $mail")
            tools.qtipClickLast(mail)
            assertTrue(tools.titleContain(mail))
            assertTrue(tools.qtipPressedLast(mail))
        }
        @RepeatedTest(NN)
        @DisplayName("Совещания")
        fun n5_meetingTest() {
            val meeting = "Совещания"
            if (DT>8) println("Test нажатия на $meeting")
            tools.qtipClickLast(meeting)
            assertTrue(tools.titleContain(meeting))
            assertTrue(tools.qtipPressedLast(meeting))
        }
        @RepeatedTest(NN)
        @DisplayName("Справка")
        fun n4_helpTest() {
            val help = "Справка"
            if (DT>8) println("Test нажатия на $help")
            tools.qtipClickLast(help)
            //assertFalse(tools.titleContain(help))      // Исправить!!! NOT
            assertTrue(tools.qtipPressedLast(help))
        }
        @RepeatedTest(NN)
        @DisplayName("Искать")
        fun n3_searchTest() {
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
        fun n2_notificationTest() {
            val notification = "Уведомления"
            if (DT>8) println("Test нажатия на $notification")
            tools.qtipClickLast(notification)
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
            driver.navigate().refresh()
            val open_showTree = "Показать/скрыть дерево"
            if (DT>8) println("Test нажатия на $open_showTree")
            assertTrue(tools.qtipPressedLast(open_showTree))
            tools.qtipClickLast(open_showTree)   // Скрыть дерево
            assertFalse(tools.qtipPressedLast(open_showTree))
            tools.qtipClickLast(open_showTree)   // Показать дерево
            assertTrue(tools.qtipPressedLast(open_showTree))
        }
        @RepeatedTest(NN)
        @DisplayName("Показать/скрыть панель предварительного просмотра")
        fun open_showPreviewPanelTest() {
            driver.navigate().refresh()
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
        fun filterTest() {
            val filter = "Создать фильтр"
            if (DT>8) println("Test нажатия на $filter")
            tools.qtipClickLast(filter)
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Обновить")
        fun renewTest() {
            driver.navigate().refresh()
            val renew = "Обновить"
            if (DT>8) println("Test нажатия на $renew")
            tools.qtipClickLast(renew)
        }
        @RepeatedTest(NN)
        @DisplayName("Администрирование групп")
        fun adminUserTest() {
            val adminUser = "Администрирование групп"
            if (DT>8) println("Test нажатия на $adminUser")
            tools.qtipClickLast(adminUser)
            assertTrue(tools.windowTitleWait("Редактирование групп"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Настройка Camunda")
        fun camundaTest() {
            val camunda = "Настройка Camunda"
            if (DT>8) println("Test нажатия на $camunda")
            tools.qtipClickLast("Настройка Camunda")
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Удалить структуру объектов")
        fun delObjectsTest() {
            val delObjects = "Удалить структуру объектов"
            if (DT>8) println("Test нажатия на $delObjects")
            tools.qtipClickLast(delObjects)
            assertTrue(tools.selectedDialogTitleWait("Удаление структуры объектов"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Создать объект разработки")
        fun createObjectTest() {
            val createObject = "Создать объект разработки"
            if (DT>8) println("Test нажатия на $createObject")
            tools.qtipClickLast(createObject)
            assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Параметры системы")
        fun systemParametersTest() {
            val systemParameters = "Параметры системы"
            if (DT>8) println("Test нажатия на $systemParameters")
            tools.qtipClickLast(systemParameters)
            assertTrue(tools.windowTitleWait("Параметры системы"))
            tools.closeXLast()
        }
        @RepeatedTest(NN)
        @DisplayName("Настройка шаблона уведомлений")
        fun configuringNotificationTest() {
            val configuringNotification = "Настройка шаблона уведомлений"
            if (DT>8) println("Test нажатия на $configuringNotification")
            tools.qtipClickLast(configuringNotification)
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
*/