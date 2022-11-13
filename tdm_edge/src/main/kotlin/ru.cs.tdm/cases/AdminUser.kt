package ru.cs.tdm.cases

import io.github.bonigarcia.wdm.WebDriverManager
import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import org.openqa.selenium.edge.EdgeDriver
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


/**
Проверка Администрирование групп:
Добавление/удаление пользователя:
- TDMS Web> Администрирование групп > Окно Редактирование групп:
В левом списке «Группы пользователей» выделить «Все пользователи»
Справа кнопка «Создать пользователя» > Окно «Редактирование пользователя»
Описание: Фамилия 1.2.11-6.1.123 25042022
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
@DisplayName("Administration users group Test")
@TestMethodOrder(MethodOrderer.MethodName::class)
class AdminUser {
    companion object {
// задержки : 0- все сбоят 100 - 1 шт 1000 - 0 шт
    const val threadSleep = 1000L
    const val DT: Int = 7
    const val NN:Int = 3
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
        if (DT >7) println("Вызов BeforeAll AdminUserTest")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        //WebDriverManager.chromedriver().setup()
        WebDriverManager.edgedriver().setup()
        //driver = ChromeDriver()
        driver = EdgeDriver()
        //окно разворачивается на полный второй экран-1500 1500 3000 2000,0
        driver.manage().window().position = Point(2000,-1000)
        //driver.manage().window().position = Point(0,-1000)
        driver.manage().window().maximize()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = ConfProperties.getProperty("loginpageTDM")
            if (DT >8) println("Открытие страницы $loginpage")
            driver.get(loginpage)

            val login = ConfProperties.getProperty("loginTDM")
            val password = ConfProperties.getProperty("passwordTDM")
            if (DT >8) println("login= $login   password= $password")
            Login(driver).loginIn(login, password)
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            if (DT >7) println("Вызов AfterAll AdminUserTest")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера

        }
    }   // конец companion object

    @BeforeEach
    fun beforeEach(){
        // data-reference="STATIC1" = Группы пользователей
        // data-reference="GROUP_NAME" - Выделенная группа
        // data-reference="GRID_GROUPS" - левый список групп
        // data-reference="GRID_USERS" - правый список пользователей
        if (DT >7) println("Начало BeforeEach AdminUserTest")
        val mainMenu = "Объекты"
        if (DT >8) println("Test нажатия на $mainMenu TDMS Web")
        tools.qtipClickLast(mainMenu)
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))

        val adminUser = "Администрирование групп"
        if (DT >8) println("Test нажатия на $adminUser")
        tools.qtipClickLast(adminUser)
        val nomberWindowAdminUser = tools.nomberTitle("window", "Редактирование групп")
        assertTrue(tools.titleWait("window", "Редактирование групп"))
        assertTrue(tools.referenceWaitText("STATIC1", "Группы пользователей"))

       // data-reference="GRID_GROUPS"
        val headTeg = tools.idRef("GRID_GROUPS")
        val allUsers = "Все пользователи"
        if (DT >8) println("Test нажатия на $allUsers")
        // //div[text()= '$allUsers']   //*[@id='$headTeg']/descendant::div[text()= '$allUsers']
        tools.xpathLast("//*[@id='$headTeg']/descendant::div[text()= '$allUsers']")?.click()
        assertTrue(tools.referenceWaitText("GROUP_NAME", allUsers))

        if (DT >7) println("Конец BeforeEach AdminUserTest")
    }
    @AfterEach
    fun afterEach(){
        if (DT >7) println("Вызов AfterEach AdminUserTest")
        //screenShot()
        tools.closeEsc5()
        // Thread.sleep(threadSleep)
        //driver.navigate().refresh()
    }
    fun screenShot(name: String = "image") {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val sdf = SimpleDateFormat("ddMMyyyyhhmmss")
        copyFile(scrFile, File("./$name${sdf.format(Date())}.png"))
    }

     /**
     * Общий длинный тест пока : создание, редактирование, добавление роли, удаление
      */
    /**
     *  тест создание нового пользователя
     */
    @RepeatedTest(NN)
    @DisplayName("Создать пользователя")
    fun n04_createUserTest(repetitionInfo: RepetitionInfo) {
        val createUser = "Создать пользователя"
        if (DT >8) println("Test нажатия на $createUser")

        // создать пользователя data-reference="BUTTON_USER_CREATE"
        tools.referenceClickLast("BUTTON_USER_CREATE")
        Thread.sleep(threadSleep)
        if (DT >8) println("Ура заработало = ${tools.nomberTitle("window", "Редактирование пользователя")}")
        assertTrue(tools.titleWait("window", "Редактирование пользователя"))
    //}

        /**
         *  тест заполнение и сохранение нового пользователя
         */
    //@Test
    //@DisplayName("Заполнение нового пользователя")
    //fun n05_fillingUserTest() {
        val nomberUser = "${repetitionInfo.currentRepetition} ${LocalDateTime.now()}"
        //val testFIO = "Тестовая Фамилия $nomberUser"
        val fillingUser = "Редактирование пользователя"
        if (DT >8) println("Test нажатия на $fillingUser")
        assertTrue(tools.titleWait("window", fillingUser))
        // //html/body/descendant::div[@data-reference]
        tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание
            ?.sendKeys("Тестовый $nomberUser")
        tools.xpathLast("//*[@data-reference='ATTR_LOGIN']/descendant::input")  // Логин
            ?.sendKeys("Логин $nomberUser")
        tools.xpathLast("//*[@data-reference='ATTR_TDMS_LOGIN_ENABLE']/descendant::input")  // Разрешить вход в TDMS
            ?.click()
        tools.xpathLast("//*[@data-reference='ATTR_USER_NAME']/descendant::input")  // Имя
            ?.sendKeys("Имя")
        tools.xpathLast("//*[@data-reference='ATTR_USER_MIDDLE_NAME']/descendant::input")  // Отчество
            ?.sendKeys("Отчество")
        tools.xpathLast("//*[@data-reference='ATTR_USER_LAST_NAME']/descendant::input")  // Фамилия
            ?.sendKeys("Фамилия")
        tools.xpathLast("//*[@data-reference='ATTR_USER_PHONE']/descendant::input")  // Телефон
            ?.sendKeys("9291234567")
        tools.xpathLast("//*[@data-reference='ATTR_USER_EMAIL']/descendant::input")  // E-mail
            ?.sendKeys("ya@ya")
        tools.clickOK()
        tools.clickOK()

        afterEach()
        /**
         *  тест редактирование пользователя
         */

        beforeEach()
        val editUser = "Редактировать пользователя"
        if (DT >8) println("Test нажатия на $editUser")

        //val testUpFIO = "Тестовая Фамилия"

        if (DT >8) println("Редактирование $nomberUser")
        tools.xpathLast("//div[contains(text(), '$nomberUser')]")?.click()

        //  Редактировать пользователя data-reference="BUTTON_USER_EDIT"
        tools. referenceClickLast("BUTTON_USER_EDIT")
        Thread.sleep(threadSleep)
        assertTrue(tools.titleWait("window", "Редактирование пользователя"))

        val description = tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание

        //.sendKeys("Тестовая Фамилия $nomberUser")
        assertTrue(description?.getAttribute("value") == "Тестовый $nomberUser")
        description?.sendKeys(" @")
        assertTrue(description?.getAttribute("value") == "Тестовый $nomberUser @")

        /**
         *  тест добавление роли пользователю
         */
        //  кнопка Добавить профиль data-reference="BUTTON_PROFILE_ADD"
        tools.referenceClickLast("BUTTON_PROFILE_ADD")
        //assertTrue(tools.selectedGridDialogTitleWait("Выбор профиля"))
        if (DT >8) println("Ура заработало = ${tools.nomberTitle("tdmsSelectObjectGridDialog","Выбор профиля")}")
        assertTrue(tools.titleWait("tdmsSelectObjectGridDialog","Выбор профиля"))

        val profileUser = "Руководитель"
        if (DT >8) println("Test нажатия на $profileUser")
        //tools.idList()
        tools.xpathLast("//span[text()= '$profileUser']/ancestor::td")?.click()
        tools.clickOK()  // закрыть выбор профиля с выбором руководителя

        assertTrue(tools.titleWait("window", "Редактирование пользователя"))
        // проверка что есть профиль руководитель

        val  description_new= tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")
        assertTrue(description_new?.getAttribute("value") == "Тестовый $nomberUser @")
        tools.clickOK()
        tools.clickOK()

        afterEach()
        /**
         *  тест Создания Группы
         */
        beforeEach()
        val createGroup = "Создание новой группы"
        if (DT >8) println("Test нажатия на $createGroup")
        //  Создания Группы data-reference="BUTTON_GROUP_CREATE"
        tools. referenceClickLast("BUTTON_GROUP_CREATE")
        Thread.sleep(threadSleep)
        if (DT >8) println("Ура заработало = ${tools.nomberTitle("messagebox", "Создание новой группы")}")
        assertTrue(tools.titleWait("messagebox", "Создание новой группы"))
        val inputGpoup = tools.xpathLast("//div[text() = 'Введите название новой группы']/parent::*/descendant::input")
        // // *[@id="messagebox-1194-textfield-inputEl"] - можно получить из "messagebox", "Создание новой группы"
        inputGpoup?.sendKeys("Тестовая")
        tools.clickOK("OK")  // создать Тестовая
        if (DT >8) println("Ура заработало = ${tools.nomberTitle("messagebox", "TDMS")}")
        Thread.sleep(threadSleep)
        assertTrue(tools.titleWait("messagebox", "TDMS"))
        Thread.sleep(threadSleep)
        assertTrue(tools.titleWait("messagebox", "TDMS"))
        tools.clickOK("OK")  // создана тестовая
        tools.clickOK("ОК")     // закрыть адимин

        /**
         *  тест Удаление Группы
         */
        beforeEach()

        val deleteGroup = "Удаление группы"
        if (DT >8) println("Test нажатия на $deleteGroup")
        // data-reference="GRID_GROUPS"   data-reference="GRID_USERS"
        tools.xpathLast("//div[contains(text(), 'Тестовая')]")?.click()
        //  Проверить что выделенная группа Тестовая data-reference="GROUP_NAME"
        tools. referenceClickLast("BUTTON_GROUP_DELETE")
        Thread.sleep(threadSleep)
        assertTrue(tools.titleWait("messagebox", "TDMS"))
        val msgGpoup = tools.xpathLast("//div[text() = 'Удалить группу \"Тестовая\"?']")
        // // *[@id="messagebox-1194-textfield-inputEl"] - можно получить из "messagebox", "Создание новой группы"
        val nomberMessage = tools.nomberTitle("messagebox", "TDMS")
        // id="messagebox-1329-msg"
        assertEquals(tools.byID("messagebox-$nomberMessage-msg")?.text, "Удалить группу \"Тестовая\"?")
        tools.clickOK("Да")  // удалить тестовая
        tools.clickOK("ОК")     // закрыть адимин
   // }
   // @Test
   // @DisplayName("Удаление пользователя")
   // fun n06_deleteUserTest() {
        afterEach()
        /**
         *  тест удаление пользователя
         */
        beforeEach()

        assertTrue(tools.titleWait("window", "Редактирование групп"))
        //val testFIO = "Тестовая Фамилия"
        if (DT >8) println("Удаление на $nomberUser")
        tools.xpathLast("//div[contains(text(), '$nomberUser')]")?.click()
        tools.referenceClickLast("BUTTON_USER_DELETE")  // //  кнопка Удалить пользователя
        tools.clickOK("Да")
        tools.clickOK("ОК")

    }
    @RepeatedTest(92)
    @Disabled
    @DisplayName("Удаление пользователей")
    fun n11_DeleteUserQuery(repetitionInfo: RepetitionInfo) {
        val nomberUser = "Тестовый ${repetitionInfo.currentRepetition + 7 } "
        assertTrue(tools.titleWait("window", "Редактирование групп"))
        //val testFIO = "Тестовая Фамилия"
        if (DT >8) println("Удаление на $nomberUser")
        tools.xpathLast("//div[contains(text(), '$nomberUser')]")?.click()
        tools.referenceClickLast("BUTTON_USER_DELETE")  // //  кнопка Удалить пользователя
        tools.clickOK("Да")
        tools.clickOK("ОК")

    }
}

