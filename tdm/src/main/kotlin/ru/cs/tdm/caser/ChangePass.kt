package ru.cs.tdm.caser

import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Toolr
import ru.cs.tdm.code.clickSend
import ru.cs.tdm.data.TDM365
import ru.cs.tdm.data.Tdms
import ru.cs.tdm.data.startDriver
import ru.cs.tdm.data.TestsProperties
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@DisplayName("Pass Change Test")
//@DisplayName("Тест смены пароля")
@TestMethodOrder(MethodOrderer.MethodName::class)
class ChangePass {

    companion object {
    private val threadSleep = TestsProperties.threadSleepNomber     // задержки где они есть
    private val DT: Int = TestsProperties.debugPrintNomber          // глубина отладочной информации 0 - ничего не печатать, 9 - все
        //private val NN:Int = TestsProperties.repeateTestsNomber        // количество повторений тестов
        private const val NN:Int = 1                    // количество повторений тестов
    // переменная для драйвера
   private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
   private lateinit var toolr: Toolr
   private lateinit var loginSYS: String
   private lateinit var passwordSYS: String
   private lateinit var loginpage: String
   private lateinit var login: String
   private lateinit var password: String
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
        if (DT >7) println("Вызов BeforeAll PassTest")
            // Создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
            driver = startDriver()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            toolr = Toolr(driver)

            loginpage = TestsProperties.loginpage
            if (DT > 8) println("Открытие страницы $loginpage")
            loginSYS = TestsProperties.login
            passwordSYS = TestsProperties.password
            driver.get(loginpage)
            assertTrue(driver.title == Tdms, "@@@@ Не открылась страница $loginpage - нет заголовка вкладки Tdms @@")
            login = loginSYS
            password = passwordSYS
            if (DT >7) println("Конец Вызов BeforeAll PassTest")
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            //tools.idList()
            if (DT >7) println("Вызов AfterAll PassTest")
            toolr.closeEsc(5)
            driver.quit() //  закрытия окна браузера
            if (DT >7) println("Конец Вызов AfterAll PassTest")
        }
    }   // конец companion object

    /**
     * screenShot
     */
        fun screenShot(name: String = "image") {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val sdf = SimpleDateFormat("ddMMyyyyhhmmss")
        copyFile(scrFile, File("./$name${sdf.format(Date())}.png"))
            if (DT >5) println("Скрин сохранен ")
    }

    // Как обычно, выполняется перед каждым тестом, только он пустой
    @BeforeEach
    fun beforeEach() {
//        beforeAll()
        if (DT > 7) println("Вызов BeforeEach ChangePass")
        //driver.navigate().refresh()
       if (DT > 8) println("login= $login   password= $password")
       Login(driver).loginIn(login, password)
        if (DT > 7) println("Конец Вызов BeforeEach ChangePass")
    }

    // Как обычно, выполняется после каждого теста, только он пустой
    @AfterEach
    fun afterEach() {
        if (DT > 7) println("Вызов AfterEach ChangePass")
        toolr.closeEsc(5)
        Login(driver).loginOut()
        //driver.navigate().refresh()
        if (DT > 7) println("Конец Вызов AfterEach ChangePass")
//        afterAll()
    }
    /**
     *  тест наличия/ удаление пользователя
     */
    private fun openAllUsers(click: String = "NONE") {
        val adminUser = "Администрирование групп"
        val mainMenu = "Объекты"
        val allUsers = "Все пользователи"
        if (DT > 8) println("Test openAllUsers")
        if (DT > 8) println("Test нажатия на $mainMenu TDMS Web")
        toolr.byIDClick("objects-tab")
        assertTrue(toolr.titleContain(TDM365), "@@@@ После нажатия $mainMenu - нет заголовка вкладки TDM365 @@")
        assertTrue(toolr.byIDPressed("objects-tab"), "@@@@ После нажатия Объекты - кнопка Объекты нет утоплена @@")

        if (DT > 8) println("Test нажатия на $adminUser")
        Thread.sleep(threadSleep)   // ***########################
        toolr.referenceClick("CMD_GROUP_CHANGE","ROOT666")
       // Thread.sleep(threadSleep)
        assertTrue(toolr.headerWait( "Редактирование групп"),
            "@@@@ После нажатия $adminUser - нет заголовка окна Редактирование групп @@")
        assertTrue(toolr.referenceWaitText("STATIC1", "Группы пользователей","MODAL"),
            "@@@@ В окне Редактирование групп нет обязательного заголовка списка Группы пользователей @@")


        if (DT > 8) println("Test нажатия на $allUsers")

        toolr.referenceClick("GRID_GROUPS","MODAL","//descendant::span[text()= '$allUsers']")

        assertTrue(toolr.referenceWaitText("GROUP_NAME", allUsers,"MODAL"),  // xpath: /html/body//*[@data-reference= 'GROUP_NAME']]
            "@@@@ В окне Редактирование групп после выделения $adminUser нет обязательного заголовка списка $adminUser @@")
        if (DT > 7) println("Открыли всех пользователей")

        if (DT >8) println("Test нажатия на $click")
        if (DT >8) println("Редактирование ChangePass")
        if ((click == "BUTTON_USER_EDIT")  or (click == "BUTTON_USER_DELETE"))
            toolr.referenceClick("GRID_USERS","MODAL","//descendant::span[contains(text(),'ChangePass')]")


        //  Редактировать пользователя data-reference="BUTTON_USER_EDIT"
        if ( (click == "NONE").not())
        toolr. referenceClick(click,"MODAL")
        //Thread.sleep(threadSleep)
        if ((click == "BUTTON_USER_CREATE") or (click == "BUTTON_USER_EDIT"))
        assertTrue(toolr.headerWait("Редактирование пользователя"),
            "@@@@ После нажатия $click - нет заголовка окна Редактирование пользователя @@")
        if (DT > 8) println("Конец Test нажатия на $mainMenu TDMS Web")
    }
    fun isChangePassPresent():Boolean {
        openAllUsers()
        val rez = toolr.reference("GRID_USERS", "MODAL")
            ?.findElements(By.xpath("./descendant::span[contains(text(),'ChangePass')]"))
            .isNullOrEmpty().not()

        toolr.OK()
        return rez
    }

    @Test
    //@DisplayName("Delete user Pass")
    @DisplayName("0. Проверка пользователя ChangePass")
    fun n00_checkUserPass() {
        if (DT > 6) println("Test проверка user Pass")

        while  (isChangePassPresent()) {
            openAllUsers("BUTTON_USER_DELETE")
            if (DT > 8) println("Удаление Pass")
            toolr.OK("yes-modal-window-btn")
            toolr.OK()
        }
        if (DT > 6) println("Конец Test проверка user Pass")
    }

     /**
     *  тест создание нового пользователя Pass
     */
    @Test
    //@DisplayName("Create user Pass")
    @DisplayName("1. Создание нового пользователя ChangePass")
    fun n01_CreateUserChangePass() {
         if (DT > 6) println("Test нажатия на Create user Pass")
         openAllUsers("BUTTON_USER_CREATE")

         toolr.reference("ATTR_DESCRIPTION","MODAL","//descendant::input")  // Описание
             ?.clickSend("ChangePass")
         toolr.reference("ATTR_LOGIN","MODAL","//descendant::input")  // Логин
             ?.clickSend("ChangePass")
         toolr.OK()
         //Thread.sleep(threadSleep)
         // Проверить что Pass есть в списке
         assertTrue(toolr.headerWait("Редактирование групп"),
             "@@@@ После создания  пользователя ChangePass ОК не стоим в родительском окне Редактирование групп @@")
         if (DT > 6) println("Проверка Pass")
         toolr.referenceClick("GRID_USERS","MODAL","//descendant::span[contains(text(),'ChangePass')]")
         //Thread.sleep(threadSleep)

         assertTrue((toolr.reference("GRID_USERS","MODAL","//descendant::span[contains(text(),'ChangePass')]//ancestor::tr")?.getAttribute("class")
             ?.contains("Selected")?: false),
             "@@@@ После выделения созданного пользователя ChangePass в таблице нет такого пользователя @@")

         toolr.OK()
         if (DT > 6) println("Конец Test нажатия на Create user Pass")
     }
         /**
          *  тест заполнение и сохранение нового пользователя
          */
         @Test
         //@DisplayName("Create user Pass")
         @DisplayName("2. Заполнение нового пользователя ChangePass")
         fun n02_FillingChangePass() {

             if (DT > 6) println("Test нажатия на n02_FillingChangePass")
             openAllUsers("BUTTON_USER_EDIT")

            // val description = tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание
         val fillingUser = "Редактирование пользователя"
         if (DT >6) println("Test нажатия на $fillingUser")
         assertTrue(toolr.headerWait(fillingUser),
             "@@@@ После поднятия на редактирование(BUTTON_USER_EDIT) созданного пользователя ChangePass нет заголовка окна $fillingUser @@" )

         // //html/body/descendant::div[@data-reference]
         toolr.reference("ATTR_DESCRIPTION","MODAL","//descendant::input")  // Описание
         ?.clickSend(" @")
//       tools.reference("ATTR_LOGIN","MODAL","//descendant::input")  // Логин
//       ?.clickSend("ChangePass")
         toolr.referenceClick("ATTR_TDMS_LOGIN_ENABLE","MODAL")  // Разрешить вход в TDMS
         toolr.reference("ATTR_USER_NAME","MODAL","//descendant::input")    // Имя
             ?.clickSend("Имя")
         toolr.reference("ATTR_USER_MIDDLE_NAME","MODAL","//descendant::input")    // Отчество
             ?.clickSend("Отчество")
         toolr.reference("ATTR_USER_LAST_NAME","MODAL","//descendant::input")   // Фамилия
                 ?.clickSend("Фамилия")
         toolr.reference("ATTR_USER_PHONE","MODAL","//descendant::input")    // Телефон
                 ?.clickSend("9291234567")
         toolr.reference("ATTR_USER_EMAIL","MODAL","//descendant::input")    // E-mail
                 ?.clickSend("ya@ya")

         toolr.OK()
         //    Thread.sleep(threadSleep)
         // Проверить что Pass есть в списке
         assertTrue(toolr.headerWait("Редактирование групп"),
             "@@@@ После заполнения полей пользователя ChangePass и ОК не открыто родительское окно Редактирование групп @@")
         if (DT > 6) println("Проверка Pass")

             assertTrue((toolr.reference("GRID_USERS","MODAL","//descendant::tr[contains(@class,'Selected')]//ancestor::span")
                 ?.text?.contains("ChangePass")?: false),
                 "@@@@ После выделения созданного пользователя ChangePass в таблице нет такого пользователя @@")
         toolr.OK()

         if (DT >7) println("Выход из под SYSADMIN")
         toolr.closeEsc(5)
         login = "ChangePass"
         password = "tdm365"
         //Login(driver).loginIn(login, password)
        // Login(driver).loginOut() // Переехало в BeforeEach
             if (DT > 6) println("Конец Test нажатия на n02_FillingChangePass")
    }

    @Test
    //@Disabled
    //@DisplayName("Enter User Pass")
    @DisplayName("4. Смена пользовательского пароля ChangePass")
    fun n04_changeUserPass() {
        val fillingUser = "Смена пароля"
        if (DT > 6) println("Test нажатия на n04_changeUserPass $fillingUser")
        toolr.byIDClick("current-user")
        //tools.qtipClickLast("Надежный пароль предотвращает несанкционированный доступ к вашей учетной записи.")
        toolr.referenceClick("user-change-password","MODAL")
        assertTrue(toolr.headerWait(fillingUser),
            "@@@@ После click Надежный пароль не открыто окно $fillingUser @@")
        toolr.reference("current-password","MODAL")  // Старый пароль
            ?.clickSend("tdm365")
        toolr.reference("new-password-first","MODAL")  // Новый пароль
            ?.clickSend("Tdm365")
        toolr.reference("new-password-second","MODAL")  // Подтверждение
            ?.clickSend("Tdm365")
        toolr.OK("change-password-accept")
        login = "ChangePass"
        password = "Tdm365"
//        Login(driver).loginIn(login, password)
        //Login(driver).loginIn("ChangePass", "Tdm365")
        // Проверить, что вошли
        //Thread.sleep(threadSleep)
        //assertTrue( Login(driver).loginUserName() == "ChangePass")
        if (DT > 6) println("Конец Test нажатия на n04_changeUserPass $fillingUser")

    }
    @Test
    //@Disabled
    //@DisplayName("Enter User Pass")
    @DisplayName("5. Смена пользовательского пароля ChangePass")
    fun n05_changeUserPass() {
        val fillingUser = "Смена пароля"
        if (DT > 6) println("Test нажатия на n04_changeUserPass $fillingUser")
        toolr.byIDClick("current-user")
        //tools.qtipClickLast("Надежный пароль предотвращает несанкционированный доступ к вашей учетной записи.")
        toolr.referenceClick("user-change-password","MODAL")
        assertTrue(toolr.headerWait(fillingUser),
            "@@@@ После click Надежный пароль не открыто окно $fillingUser @@")
        toolr.reference("current-password","MODAL")  // Старый пароль
            ?.clickSend("Tdm365")
        toolr.reference("new-password-first","MODAL")  // Новый пароль
            ?.clickSend("TDm365")
        toolr.reference("new-password-second","MODAL")  // Подтверждение
            ?.clickSend("TDm365")
        toolr.OK("change-password-accept")
// Проверить, что вышли
        login = "ChangePass"
        password = "TDm365"
        //Login(driver).loginIn(login, password)
        //Login(driver).loginIn
        //Login(driver).loginIn("ChangePass", "TDm365")
        // Проверить, что вошли
        //Thread.sleep(threadSleep)
        //assertTrue( Login(driver).loginUserName() == "ChangePass")
        //Login(driver).loginOut()
        login = loginSYS
        password = passwordSYS
        if (DT > 6) println("Конец Test нажатия на n05_changeUserPass $fillingUser")
    }

    /**
     *  тест удаление пользователя
     */
    @Test
    //@DisplayName("Delete user Pass")
    @DisplayName("9. Удаление пользователя ChangePass")
    fun n09_deleteUserPass() {
        if (DT > 6) println("Test удаление n09_deleteUserPass user ChangePass")

        while  (isChangePassPresent()) {
            openAllUsers("BUTTON_USER_DELETE")
            if (DT > 8) println("Удаление Pass")
            toolr.OK("yes-modal-window-btn")
            toolr.OK()
        }

        if (DT > 6) println("Test удаление n09_deleteUserPass user ChangePass")
    }

}

