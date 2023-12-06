package ru.cs.tdm.caser

import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.FluentWait
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Toolr
import ru.cs.tdm.data.*
import java.io.File
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertContains


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
        private val threadSleep = TestsProperties.threadSleepNomber     // задержки где они есть
        private val DT: Int = TestsProperties.debugPrintNomber          // глубина отладочной информации 0 - ничего не печатать, 9 - все
        //private val NN:Int = repeateTestsNomber                       // количество повторений тестов
        private const val NN:Int = 1                                    // количество повторений тестов

        private val localDateNow: String = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss").format(LocalDateTime.now())

    // переменная для драйвера
    lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var toolr: Toolr

    /**
     * Осуществление первоначальной настройки
     * Предупреждение: Не смешивайте неявные и явные ожидания.
     * Это может привести к непредсказуемому времени ожидания.
     * Например, установка неявного ожидания 10 секунд и явного ожидания 15 секунд
     * может привести к таймауту через 20 секунд.
     */
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
        if (DT >7) println("Вызов BeforeAll AdminUserTest")
            // Создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        driver = startDriver()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            toolr = Toolr(driver)

        val loginpage = TestsProperties.loginpage
        if (DT > 8) println("Открытие страницы $loginpage")
        val login = TestsProperties.login
        val password = TestsProperties.password
        if (DT > 8) println("login= $login   password= $password")
        driver.get(loginpage)
        assertTrue(driver.title == Tdms,"@@@@ Не открылась страница $loginpage - нет заголовка вкладки Tdms @@")
        Login(driver).loginIn(login, password)
        if (DT >7) println("Конец Вызов BeforeAll AdminUserTest")
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            if (DT >7) println("Вызов AfterAll AdminUserTest")
            toolr.closeEsc(5)
            //Login(driver).loginOut()
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
        //Thread.sleep(threadSleep)  //54-41
        toolr.byIDClick("objects-tab")
        assertTrue(toolr.titleContain(TDM365), "@@@@ После нажатия $mainMenu - нет заголовка вкладки TDM365 @@")
        assertTrue(toolr.byIDPressed("objects-tab"), "@@@@ После нажатия Объекты - кнопка Объекты нет утоплена @@")

        val adminUser = "Администрирование групп"
        if (DT >8) println("Test нажатия на $adminUser")
//        Thread.sleep(threadSleep)  // ***#############################################################
        toolr.referenceClick("CMD_GROUP_CHANGE","ROOT666")
//        Thread.sleep(threadSleep)
        if (DT >7) println(" Hажатия на $adminUser")
        assertTrue(toolr.headerWait("Редактирование групп"),
            "@@@@ После нажатия $adminUser - нет заголовка окна Редактирование групп @@")
        if (DT >7) println(" проверили заголовок на $adminUser")
        assertTrue(toolr.referenceWaitText("STATIC1", "Группы пользователей","MODAL"),
            "@@@@ В окне Редактирование групп нет обязательного заголовка списка Группы пользователей @@")
        clickAllUsers()
        if (DT >7) println("Конец BeforeEach AdminUserTest")
    }
    @AfterEach
    fun afterEach(){
        if (DT >7) println("Вызов AfterEach AdminUserTest")
        //screenShot()
        toolr.closeEsc(5)
        if (DT >7) println("Конец Вызов AfterEach AdminUserTest")
    }
    fun screenShot(name: String = "image") {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val localDateNow = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm").format(LocalDateTime.now())
        copyFile(scrFile, File("./$name$localDateNow.png"))
        if (DT >5) println("Скрин сохранен ")
    }
    private fun clickAllUsers(click: String = "NONE") {
        val adminUser = "Администрирование групп"
        val allUsers = "Все пользователи"
        if (DT > 8) println("Test openAllUsers")

        assertTrue(
            toolr.headerWait( "Редактирование групп"),
            "@@@@ После нажатия $adminUser - нет заголовка окна Редактирование групп @@")
        assertTrue(
            toolr.referenceWaitText("STATIC1", "Группы пользователей","MODAL"),
            "@@@@ В окне Редактирование групп нет обязательного заголовка списка Группы пользователей @@")

        if (DT > 8) println("Test нажатия на $allUsers")
        toolr.referenceClick("GRID_GROUPS","MODAL","//descendant::span[text()= '$allUsers']")
        //Thread.sleep(threadSleep) //39
        assertTrue(
            toolr.referenceWaitText("GROUP_NAME", allUsers,"MODAL"),  // xpath: /html/body//*[@data-reference= 'GROUP_NAME']]
            "@@@@ В окне Редактирование групп после выделения $adminUser нет обязательного заголовка списка $adminUser @@")
        if (DT > 7) println("Открыли всех пользователей")

        if (DT >8) println("Test нажатия на $click")
        if (DT >8) println("Редактирование $localDateNow")
        if ((click == "BUTTON_USER_EDIT")  or (click == "BUTTON_USER_DELETE"))
            toolr.referenceClick("GRID_USERS","MODAL","//descendant::span[starts-with(text(),'Тестовый $localDateNow')]")

        //  Редактировать пользователя data-reference="BUTTON_USER_EDIT"
        if ( (click == "NONE").not())
                toolr. referenceClick(click,"MODAL")

        if ((click == "BUTTON_USER_CREATE") or (click == "BUTTON_USER_EDIT"))
            assertTrue(toolr.headerWait("Редактирование пользователя"),
                "@@@@ После нажатия $click - нет заголовка окна Редактирование пользователя @@")
        if (DT > 8) println("Конец Test openAllUsers")
    }
    fun isTestPresent(grid: String = "GRID_USERS"): Boolean {
        clickAllUsers()
        return toolr.reference(grid, "MODAL")
            ?.findElements(By.xpath("./descendant::span[contains(text(),'Тестов')]"))
            .isNullOrEmpty().not()
    }
    @Test
    @DisplayName("0. Проверка и удаление пользователей и групп Тестовый")
    fun n00_checkUsersAndGroups() {
        if (DT > 6) println("Test проверка user Тестовый")

        var nomerDel = 0

        while(true){
            clickAllUsers()
            val testPresent =  toolr.reference("GRID_USERS", "MODAL")
                ?.findElements(By.xpath("./descendant::span[contains(text(),'Тестов')]"))?.toList()
                ?.reversed()
            if (testPresent.isNullOrEmpty()) break
            if (testPresent.size <= nomerDel) break

            // Обрабатывать RecyclerVuew
            testPresent[nomerDel]?.click()


            if (toolr.reference("BUTTON_USER_DELETE","MODAL") == null) {
                println("$$$$$$$$$$$$$$$ НЕТ ИНСТРУМЕНТОВ удаления Тестовый действие:")
                //screenShot()
                nomerDel++
                continue
            }

            toolr.referenceClick("BUTTON_USER_DELETE", "MODAL")
            assertTrue(
                toolr.headerWait(TDM365),
                "@@@@ После нажатия BUTTON_USER_DELETE - окно типа не имеет заголовка $TDM365 @@")

            // Вы действительно хотите удалить объект "(Все проекты) Тест 31-05-2023_18-51-20  @@#" из системы?
            val userText =
                toolr.xpath("//*[contains(text(),'Удалить пользователя \"Тест')]", "MODAL")?.text ?: "NONE"
            assertContains(userText, "Тест", false,
                "@@@@ Нет правильного текста Тест на всплывающем окне $userText @@")

            // Вы действительно хотите удалить объект "(Все проекты) Фильтр" из системы?
            toolr.OK("yes-modal-window-btn")  // удалить тестового
        }
     // return // Linux не работает
        if (DT > 6) println("Test проверка групп Тестовый")
        while  (true) { // (isTestPresent("GRID_GROUPS")) {
            val testPresent =  toolr.reference("GRID_GROUPS", "MODAL")
                ?.findElements(By.xpath("./descendant::span[contains(text(),'Тестов')]"))?.toList()
                ?.reversed()
            if (testPresent.isNullOrEmpty()) break


            testPresent[0]?.click()
            //toolr.referenceClick("GRID_GROUPS","MODAL","//descendant::span[contains(text(), 'Тестовая')]")
            toolr.referenceClick("BUTTON_GROUP_DELETE", "MODAL")
            assertTrue(toolr.headerWait(TDM365),
                "@@@@ При удалить группу - нет окна сообщения с заголовком TDMS (удалить группу) @@")

            assertContains(toolr.xpath("", "MODAL")?.text?: "None", "Удалить группу",false,
                "@@@@ При удалить группу - нет в окне сообщения с заголовком TDMS Удалить группу @@")
            toolr.OK("yes-modal-window-btn")  // удалить тестовая
            if (DT > 8) println("Удаление Тестовой группы")
        }

        toolr.OK()
        if (DT > 6) println("Конец Test нажатия на проверка user Тестовый Заблокировано $nomerDel фильтров")
    }
    /**
     *  Тест создание нового пользователя
     */
    @RepeatedTest(NN)
    @DisplayName("Создать пользователя")
    fun n04_createUserTest(repetitionInfo: RepetitionInfo) {
        val createUser = "Создать пользователя"
        if (DT > 6) println("Test нажатия на $createUser")

        clickAllUsers("BUTTON_USER_CREATE")

        assertTrue(toolr.headerWait("Редактирование пользователя"),
            "@@@@ После нажатия BUTTON_USER_CREATE - нет заголовка окна Редактирование пользователя @@")

        toolr.reference("ATTR_DESCRIPTION","MODAL","//descendant::input")  // Описание
            ?.sendKeys("Тестовый $localDateNow")  //  ${repetitionInfo.currentRepetition}
        toolr.reference("ATTR_LOGIN","MODAL","//descendant::input")  // Логин
            ?.sendKeys("Логин $localDateNow") // ") //  ${repetitionInfo.currentRepetition}
      //  Thread.sleep(threadSleep)
        toolr.OK()

        // Проверить что Pass есть в списке
        assertTrue(
            toolr.headerWait("Редактирование групп"),
            "@@@@ После создания  пользователя Логин $localDateNow ОК не стоим в родительском окне Редактирование групп @@")
        if (DT > 6) println("Логин $localDateNow")
        toolr.referenceClick("GRID_USERS","MODAL","//descendant::span[contains(text(),'$localDateNow')]")

        assertTrue((toolr.reference("GRID_USERS","MODAL","//descendant::span[contains(text(),'$localDateNow')]//ancestor::tr")?.getAttribute("class")
            ?.contains("Selected")?: false),
            "@@@@ После выделения созданного пользователя $localDateNow в таблице нет такого пользователя @@")

        toolr.OK()

        if (DT > 6) println("Конец Test нажатия на $createUser")
    }

        /**
         *  Тест заполнение и сохранение нового пользователя
         */
    @Test
    @DisplayName("Заполнение нового пользователя")
    fun n05_fillingUserTest() {
            val fillingUser = "Редактирование пользователя"
            if (DT > 6) println("Test нажатия на $fillingUser")

            clickAllUsers("BUTTON_USER_EDIT")

            assertTrue(toolr.headerWait(fillingUser),
                "@@@@ После нажатия BUTTON_USER_EDIT - нет заголовка окна Редактирование пользователя @@")
            // //html/body/descendant::div[@data-reference]
            toolr.reference("ATTR_DESCRIPTION","MODAL","//descendant::input")  // Описание
                ?.sendKeys(" #")
            toolr.reference("ATTR_LOGIN","MODAL","//descendant::input")  // Логин
                ?.sendKeys(" #")

         //   toolr.referenceClick("ATTR_TDMS_LOGIN_ENABLE","MODAL","//descendant::span")  // Разрешить вход в TDMS

            toolr.reference("ATTR_USER_NAME","MODAL","/descendant::input")  // Имя
                ?.sendKeys("Имя")
            toolr.reference("ATTR_USER_MIDDLE_NAME","MODAL","//descendant::input")  // Отчество
                ?.sendKeys("Отчество")
            toolr.reference("ATTR_USER_LAST_NAME","MODAL","//descendant::input")  // Фамилия
                ?.sendKeys("Фамилия")
            toolr.reference("ATTR_USER_PHONE","MODAL","//descendant::input")  // Телефон
                ?.sendKeys("9291234567")
            toolr.reference("ATTR_USER_EMAIL","MODAL","//descendant::input")  // E-mail
                ?.sendKeys("ya@ya")
        //    Thread.sleep(threadSleep)
            toolr.OK()
//            Thread.sleep(threadSleep)
      //      Thread.sleep(threadSleep)
            toolr.OK()
            if (DT > 6) println("Конец Test нажатия на $fillingUser")
        }
        /**
         *  тест редактирование пользователя
         */
    /**
     *  тест заполнение и сохранение нового пользователя
     */
    @Test
    @DisplayName("Редактирование пользователя")
    fun n06_EditUserTest() {

        val fillingUser = "Редактирование пользователя"
        if (DT > 6) println("Test нажатия на $fillingUser")

        clickAllUsers("BUTTON_USER_EDIT")

        val editUser = "Редактировать пользователя"
        if (DT > 6) println("Test нажатия на $editUser")

        if (DT > 8) println("Редактирование $localDateNow")

        val description = toolr.reference("ATTR_DESCRIPTION","MODAL","//descendant::input")  // Описание

        //.sendKeys("Тестовая Фамилия $localDateNow")
        assertTrue(description?.getAttribute("value") == "Тестовый $localDateNow #",
            "@@@@ Нет измененного описания с # ")
        description?.sendKeys(" @")
        assertTrue(description?.getAttribute("value") == "Тестовый $localDateNow # @",
            "@@@@ Нет измененного описания с # и @ ")

        toolr.OK()
//        Thread.sleep(threadSleep)
        toolr.OK()
        if (DT > 6) println("Конец Test нажатия на $fillingUser")
    }
        /**
         *  тест добавление роли пользователю
         */
        @Test
        @DisplayName("Добавление роли пользователю")
        fun n07_AddRoleUserTest() {

            val fillingUser = "Редактирование пользователя"
            if (DT > 6) println("Test n07_AddRoleUserTest нажатия на $fillingUser")

            clickAllUsers("BUTTON_USER_EDIT")
            //  кнопка Добавить профиль data-reference="BUTTON_PROFILE_ADD"
            toolr.referenceClick("BUTTON_PROFILE_ADD", "MODAL")
            assertTrue(toolr.headerWait("Выбор профиля"),
                "@@@@ После нажатия BUTTON_PROFILE_ADD - нет заголовка окна Выбор профиля @@")

            val profileUser = "Руководитель"
            if (DT > 6) println("Test нажатия на $profileUser")

            toolr.xpathClick("//span[text()= '$profileUser']", "MODAL", "//ancestor::td")
            toolr.OK()  // закрыть выбор профиля с выбором руководителя

            assertTrue(toolr.headerWait("Редактирование пользователя"),
                "@@@@ После выхода из редактирования - нет заголовка окна Редактирование пользователя @@")
            // проверка что есть профиль руководитель

            val description_new = toolr.reference("ATTR_DESCRIPTION","MODAL","//descendant::input")  // Описание
            assertTrue(description_new?.getAttribute("value") == "Тестовый $localDateNow # @",
                "@@@@ После редактирования - нет пользователя Тестовый $localDateNow # @  @@")
            toolr.OK()
            assertTrue(toolr.headerWait("Редактирование групп"),
                "@@@@ После выхода из редактирования - нет заголовка окна Редактирование групп @@")
//            Thread.sleep(threadSleep)
            toolr.OK()
            if (DT > 6) println("Конец Test n07_AddRoleUserTest нажатия на $fillingUser")
        }

        /**
         *  тест Создания Группы
         */

    @RepeatedTest(NN)
//    @Disabled
    @DisplayName("Создание новой группы")
    fun n08_AddGroupUserTest(repetitionInfo: RepetitionInfo) {

            val createGroup = "Создание новой группы"
            if (DT > 6) println("Test нажатия на $createGroup")
            //  Создания Группы data-reference="BUTTON_GROUP_CREATE"
            toolr.referenceClick("BUTTON_GROUP_CREATE","MODAL")

            assertTrue(toolr.headerWait("Создание новой группы"),
                "@@@@ После нажатия $createGroup - нет окна с заголовком Создание новой группы @@")
            assertTrue(toolr.reference("FormSimpleEditDlg-prompt", "MODAL")?.text == "Введите название новой группы",
                "@@@@ После нажатия $createGroup - нет окна с Введите название новой группы @@")
            toolr.reference("FormSimpleEditDlg-input", "MODAL")
            ?.sendKeys("Тестовая $localDateNow ${repetitionInfo.currentRepetition}")
            toolr.OK()  // создать Тестовая

            assertTrue(toolr.headerWait(TDM365),
//ASTRA            assertTrue(toolr.headerWait(TDMS),
                "@@@@ При $createGroup - нет окна сообщения с заголовком TDM365 (группа создана) @@")
            assertTrue(toolr.headerWait(TDM365),
//ASTRA            assertTrue(toolr.headerWait(TDMS),
                "@@@@ Повторная проверка $createGroup - нет окна сообщения с заголовком TDMS (группа создана) @@")
            toolr.OK()  // создана тестовая
 //           Thread.sleep(threadSleep)
            toolr.OK()     // закрыть адимин
            if (DT > 6) println("Конец Test нажатия на $createGroup")
        }
        /**
         *  тест Удаление Группы
         */
        @Test
//       @Disabled
        @DisplayName("Удаление Группы")
        fun n09_DeleteGroupUserTest() {

            val deleteGroup = "Удаление группы"
            if (DT > 6) println("Test нажатия на $deleteGroup")
            // data-reference="GRID_GROUPS"   data-reference="GRID_USERS"
            toolr.referenceClick("GRID_GROUPS","MODAL","//descendant::span[contains(text(), 'Тестовая $localDateNow')]")

            //  Проверить что выделенная группа Тестовая data-reference="GROUP_NAME"
            toolr.referenceClick("BUTTON_GROUP_DELETE", "MODAL")

            assertTrue(toolr.headerWait(TDM365),
//ASTRA            assertTrue(toolr.headerWait(TDMS),
                "@@@@ При $deleteGroup - нет окна сообщения с заголовком TDMS (удалить группу) @@")
            //val msgGpoup = tools.xpathLast("//div[text() = 'Удалить группу \"Тестовая\"?']")
            // // *[@id="messagebox-1194-textfield-inputEl"] - можно получить из "messagebox", "Создание новой группы"

            assertContains(toolr.xpath("", "MODAL")?.text?: "None", "Удалить группу \"Тест",false,
                "@@@@ При $deleteGroup - нет в окне сообщения с заголовком TDMS Удалить группу @@")
            toolr.OK("yes-modal-window-btn")  // удалить тестовая
//            Thread.sleep(threadSleep)
            toolr.OK()     // закрыть адимин
            if (DT > 6) println("Test нажатия на $deleteGroup")
        }

        /**
         *  тест удаление пользователя
         */
        @Test
        //@Disabled
        @DisplayName("удаление пользователя")
        fun n10_DeleteUserTest() {
            if (DT > 6) println("Test нажатия на Удаление на $localDateNow")
            clickAllUsers("BUTTON_USER_DELETE")
            assertTrue(toolr.headerWait(TDM365),
                //ASTRA           assertTrue(toolr.headerWait(TDMS),
                "@@@@ При BUTTON_USER_DELETE - нет окна подтверждения с заголовком TDMS (удалить пользователя) @@")

            assertContains(toolr.xpath("", "MODAL")?.text?: "None", "Удалить пользователя \"Тест",false,
//            assertContains(toolr.xpath("", "MODAL")?.text?: "None", "Удалить пользователя",false,
                "@@@@ При Удалить пользователя - нет в окне сообщения с заголовком TDMS Удалить пользователя @@")

        toolr.OK("yes-modal-window-btn")
 //       Thread.sleep(threadSleep)
        toolr.OK()
            if (DT > 6) println("Конец Test нажатия на Удаление на $localDateNow")
    }
}

