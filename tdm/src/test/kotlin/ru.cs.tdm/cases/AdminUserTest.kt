package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.interactions.Actions
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import java.time.LocalDateTime


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
class AdminUserTest {
    companion object {
    const val DT: Int = 9
    const val NN:Int = 25
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
        if (DT>7) println("Вызов BeforeAll AdminUserTest")
            // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
            WebDriverManager.chromedriver().setup()
            //окно разворачивается на полный второй экран
            driver = ChromeDriver(ChromeOptions().addArguments("--window-position=3000,-1000"))
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
            if (DT>7) println("Вызов AfterAll AdminUserTest")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера

        }
    }   // конец companion object

    @BeforeEach
    fun beforeEach(){
        if (DT>7) println("Начало BeforeEach AdminUserTest")
        val mainMenu = "Объекты"
        if (DT>8) println("Test нажатия на $mainMenu TDMS Web")
        tools.qtipClickLast(mainMenu)
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))

        val adminUser = "Администрирование групп"
        if (DT>8) println("Test нажатия на $adminUser")
        tools.qtipClickLast(adminUser)
        assertTrue(tools.windowTitleWait("Редактирование групп"))

        val allUsers = "Все пользователи"
        if (DT>8) println("Test нажатия на $allUsers")
        tools.xpathLast("//div[text()= '$allUsers']")?.click()
        assertTrue(tools.xpathLast("//label[text()= '$allUsers']") != null)
        if (DT>7) println("Конец BeforeEach AdminUserTest")
    }
    @AfterEach
    fun afterEach(){
        if (DT>7) println("Вызов AfterEach AdminUserTest")
        tools.closeEsc5()
        // Thread.sleep(2000)
        driver.navigate().refresh()
    }

    /**
     * Функция выдачи WebElement окна по его заголовку
     * при DT > 8 т.е. 9... печатает коорлинаты левого верхнего угла x,y и размеры окна w,h
     */
    fun eWin(title: String):WebElement {
        if (DT > 6) println("начало процедуры eWin $title")
        val eTitl = tools.xpathLast("//div[contains(text(),'$title')]")
        val idTitle = eTitl?.getAttribute("id")
        val idWin = idTitle?.split("_")?.get(0)
        if (DT > 8) println("idTitle = $idTitle idWin = $idWin")
        val eWin = driver.findElement(By.id(idWin))
        if (DT > 8) println("DIV ${eWin.getAttribute("id")}  ${eWin.getAttribute("style")}")
        if (DT > 8) println("location = ${eWin.location} size= ${eWin.size} ")
        if (DT > 8) println("x = ${eWin.location.x} y = ${eWin.location.y} width= ${eWin.size.width} height= ${eWin.size.height} }")
        if (DT > 6) println("конец процедуры eWin")
        return eWin
    }
    fun cursorElement(title: String, x: Int, y: Int):WebElement {
        val eWinC = eWin(title)

        val elementCursor = Actions(driver).moveToElement(eWinC,   // moveByOffset(1259, 331) //a[@id="button-1179"]
            //-eWinC.size.width/2 + 654 + 7+ 14,-eWinC.size.height/2 + 36 + 29 + 12)
            -eWinC.size.width/2 + x,-eWinC.size.height/2 + y)
            .contextClick().release().perform()
        val elementActive = driver.switchTo().activeElement()
        if (DT>8) println("Курсор (${elementActive.location.x},${elementActive.location.y }) ${elementActive.text}\n" +
                "//${elementActive.tagName}[@id ='${elementActive.getAttribute("id")}']")
        return elementActive
    }

    fun eButton(number: String):WebElement {
        if (DT > 6) println("начало процедуры eButton $number")
        val eButton = driver.findElement(By.id("button-$number"))
        if (DT>8) println ("DIV ${eButton.getAttribute("id")} style= ${eButton.getAttribute("style")}")
        if (DT>8) println("location = ${eButton.location} size= ${eButton.size} ")
        if (DT>8) println("x = ${eButton.location.x} y = ${eButton.location.y} width= ${eButton.size.width} height= ${eButton.size.height} }")
        if (DT > 6) println("конец процедуры eButton")
        return eButton
    }

    /**
     * Общий длинный тест пока : создание, редактирование, добавление роли, удаление
      */
    /**
     *  тест создание нового пользователя
     */
    @RepeatedTest(NN)
    @DisplayName("Создать/Удалить пользователя")
    fun n04_createUserTest( testInfo:TestInfo ,  repetitionInfo: RepetitionInfo) {
        val createUser = "Создать пользователя"
        if (DT>8) println("Test нажатия на $createUser")

        // создать пользователя moveByOffset(1259, 331) //a[@id="button-1179"]
        cursorElement("Редактирование групп", 654 + 7+ 14, 36 + 29 + 12).click()
        assertTrue(tools.windowTitleWait("Редактирование пользователя"))
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
        if (DT>8) println("Test нажатия на $fillingUser")
        assertTrue(tools.windowTitleWait(fillingUser))
        tools.xpathLast("//label[text()='Описание']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("Тестовая Фамилия $nomberUser")
        tools.xpathLast("//label[text()='Логин']/following-sibling::div[1]/descendant::input")
            ?.sendKeys("Логин $nomberUser")
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
        tools.clickOK()
        tools.clickOK()

        afterEach()
        /**
         *  тест редактирование пользователя
         */

        beforeEach()
        val editUser = "Редактировать пользователя"
        if (DT>8) println("Test нажатия на $editUser")

        //val testUpFIO = "Тестовая Фамилия"

        if (DT>8) println("Редактирование $nomberUser")
        tools.xpathLast("//div[contains(text(), '$nomberUser')]")?.click()

        //  Редактировать пользователя moveByOffset(1259, 387-388) //a[@id="button-1179"]
        cursorElement("Редактирование групп", 654 + 7+ 14,  36 +28*2 + 29 + 12).click()
        assertTrue(tools.windowTitleWait("Редактирование пользователя"))

        /**
         *  тест добавление роли пользователю
         */
        //  кнопка Добавить профиль moveByOffset(1169, 523) //a[@id="button-1179"]
        cursorElement("Редактирование пользователя", 471 + 7+ 14,  298 + 17 + 12).click()
        assertTrue(tools.selectedGridDialogTitleWait("Выбор профиля"))

        val profileUser = "Руководитель"
        if (DT>8) println("Test нажатия на $profileUser")

        tools.xpathLast("//span[text()= '$profileUser']/ancestor::td")?.click()
        tools.clickOK()  // закрыть выбор профиля с выбором руководителя

        assertTrue(tools.windowTitleWait("Редактирование пользователя"))
        // проверка что есть профиль руководитель
        tools.clickOK()
        tools.clickOK()
   // }
   // @Test
   // @DisplayName("Удаление пользователя")
   // fun n06_deleteUserTest() {
        afterEach()
        /**
         *  тест удаление пользователя
         */
        beforeEach()

     //   val eWinD = eWin("Редактирование групп")
        assertTrue(tools.windowTitleWait("Редактирование групп"))
        //val testFIO = "Тестовая Фамилия"
        if (DT>8) println("Удаление на $nomberUser")
        tools.xpathLast("//div[contains(text(), '$nomberUser')]")?.click()
        //  кнопка Удалить пользователя moveByOffset(1169, 523) //a[@id="button-1179"]
        cursorElement("Редактирование групп", 654 + 7+ 14,  64 + 29 + 12).click()
        /*Actions(driver).moveToElement(eWinD,
            -eWinD.size.width /2 + 654 + 7+ 14,-eWinD.size.height /2 + 64 + 29 + 12)
            .pause(1000).click().perform()
        if (DT>8) println("Нажата кнопка Удалить пользователя x= ${eWinD.location.x + 654 + 7} y= ${eWinD.location.y +  64 + 29}" )
         */
        tools.clickButton("Да")
        tools.clickButton("ОК")

    }
}

