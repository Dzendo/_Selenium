package ru.cs.tdm.cases

import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.startDriver
import ru.cs.tdm.ui.TestsProperties
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
   private lateinit var tools: Tools
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
            tools = Tools(driver)

            loginpage = TestsProperties.loginpage
            if (DT > 8) println("Открытие страницы $loginpage")
            loginSYS = TestsProperties.login
            passwordSYS = TestsProperties.password
            driver.get(loginpage)
            assertTrue(driver.title == "Tdms")
            login = loginSYS
            password = passwordSYS
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            //tools.idList()
            if (DT >7) println("Вызов AfterAll PassTest")
            tools.closeEsc5()
            driver.quit() //  закрытия окна браузера
        }
    }   // конец companion object

        fun screenShot(name: String = "image") {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val sdf = SimpleDateFormat("ddMMyyyyhhmmss")
        copyFile(scrFile, File("./$name${sdf.format(Date())}.png"))
    }

    // Как обычно, выполняется перед каждым тестом, только он пустой
    @BeforeEach
    fun beforeEach() {
        if (DT > 7) println("Вызов BeforeEach ChangePass")
        //driver.navigate().refresh()
       if (DT > 8) println("login= $login   password= $password")
//        assertTrue(driver.title == "Tdms")
        Login(driver).loginIn(login, password)
    }

    // Как обычно, выполняется после каждого теста, только он пустой
    @AfterEach
    fun afterEach() {
        if (DT > 7) println("Вызов AfterEach ChangePass")
        tools.closeEsc5()
        Login(driver).loginOut()
        //driver.navigate().refresh()
    }
    private fun openAllUsers(click: String) {
        val mainMenu = "Объекты"
        if (DT > 8) println("Test нажатия на $mainMenu TDMS Web")
        tools.qtipClickLast(mainMenu)
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))

        val adminUser = "Администрирование групп"
        if (DT > 8) println("Test нажатия на $adminUser")
        tools.qtipClickLast(adminUser)
        assertTrue(tools.titleWait("window", "Редактирование групп"))
        assertTrue(tools.referenceWaitText("STATIC1", "Группы пользователей"))

        // data-reference="GRID_GROUPS"
        val headTeg = tools.idRef("GRID_GROUPS")
        val allUsers = "Все пользователи"
        if (DT > 8) println("Test нажатия на $allUsers")
        // //div[text()= '$allUsers']   //*[@id='$headTeg']/descendant::div[text()= '$allUsers']
        tools.xpathLast("//*[@id='$headTeg']/descendant::div[text()= '$allUsers']")?.click()
        if (loginpage.contains("555").not())   //%%6.2
            assertTrue(tools.referenceWaitText("GROUP_NAME", allUsers))
        if (DT > 7) println("Открыли всех пользователей")

        if (DT >8) println("Test нажатия на $click")
        if (DT >8) println("Редактирование ChangePass")
        if ((click == "BUTTON_USER_EDIT")  or (click == "BUTTON_USER_DELETE"))
        tools.xpathLast("//div[contains(text(), 'ChangePass')]")?.click()

        //  Редактировать пользователя data-reference="BUTTON_USER_EDIT"
        if ( (click == "NONE").not())
        tools. referenceClickLast(click)
        Thread.sleep(threadSleep)
        if ((click == "BUTTON_USER_CREATE") or (click == "BUTTON_USER_EDIT"))
        assertTrue(tools.titleWait("window", "Редактирование пользователя"))
    }

    /**
     *  тест наличия/ удаление пользователя
     */
    @Test
    //@DisplayName("Delete user Pass")
    @DisplayName("0. Проверка пользователя ChangePass")
    fun n00_checkUserPass() {
        if (DT > 8) println("Test проверка user Pass")
        openAllUsers("NONE")

        // Проверка, что Pass отсутствует GRID_USERS
        Thread.sleep(threadSleep)
       // if (TestsProperties.loginpage.contains("555").not())   //%%6.2
           val changePassNo = driver.findElements(By.xpath("//div [@data-reference='GRID_USERS']/descendant::div[contains(text(),'ChangePass')]")).isEmpty()
        //if (DT > 8) println("ChangePass отсутствует GRID_USERS")
            if (changePassNo.not()) {
                if (DT > 8) println("Удаление Pass")
                tools.xpathLast("//div[contains(text(), 'ChangePass')]")?.click()
                tools.referenceClickLast("BUTTON_USER_DELETE")  // //  кнопка Удалить пользователя
                tools.clickOK("Да")
            }
        tools.clickOK("ОК")

    }

     /**
     *  тест создание нового пользователя Pass
     */
    @Test
    //@DisplayName("Create user Pass")
    @DisplayName("1. Создание нового пользователя ChangePass")
    fun n01_CreateUserChangePass() {
         if (DT > 8) println("Test нажатия на Create user Pass")
         openAllUsers("BUTTON_USER_CREATE")

         tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание
             ?.sendKeys("ChangePass")
         tools.xpathLast("//*[@data-reference='ATTR_LOGIN']/descendant::input")  // Логин
             ?.sendKeys("ChangePass")
         tools.clickOK()
         Thread.sleep(threadSleep)
         // Проверить что Pass есть в списке
         assertTrue(tools.titleWait("window", "Редактирование групп"))
         if (DT > 8) println("Проверка Pass")
         tools.xpathLast("//div[contains(text(), 'ChangePass')]")?.click()
         Thread.sleep(threadSleep)
         if (loginpage.contains("555").not())   //%%6.2
          //   assertTrue((tools.xpathLast("//tr[@aria-selected='true']")?.text ?: "None") == "ChangePass")
         assertTrue((tools.xpathLast("//table[contains(@class, 'x-grid-item-selected')]")?.text ?: "None") == "ChangePass")

         tools.clickOK()
     }
         /**
          *  тест заполнение и сохранение нового пользователя
          */
         @Test
         //@DisplayName("Create user Pass")
         @DisplayName("2. Заполнение нового пользователя ChangePass")
         fun n02_FillingChangePass() {

             if (DT > 8) println("Test нажатия на Create user Pass")
             openAllUsers("BUTTON_USER_EDIT")

             val description = tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание
         val fillingUser = "Редактирование пользователя"
         if (DT >8) println("Test нажатия на $fillingUser")
         assertTrue(tools.titleWait("window", fillingUser))

         // //html/body/descendant::div[@data-reference]
         tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание
             ?.sendKeys(" @")
         //tools.xpathLast("//*[@data-reference='ATTR_LOGIN']/descendant::input")  // Логин
         //    ?.sendKeys("ChangePass")
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
             Thread.sleep(threadSleep)
         // Проверить что Pass есть в списке
         assertTrue(tools.titleWait("window", "Редактирование групп"))
         if (DT > 8) println("Проверка Pass")
         tools.xpathLast("//div[contains(text(), 'ChangePass')]")?.click()
         Thread.sleep(threadSleep)
         if (loginpage.contains("555").not())   //%%6.2
//         assertTrue((tools.xpathLast("//tr[@aria-selected='true']")?.text ?: "None") == "ChangePass")
         assertTrue((tools.xpathLast("//table[contains(@class, 'x-grid-item-selected')]")?.text ?: "None").contains("ChangePass"))

         tools.clickOK()

         if (DT >7) println("Выход из под SYSADMIN")
         tools.closeEsc5()
         login = "ChangePass"
         password = "tdm365"
         //Login(driver).loginIn(login, password)
        // Login(driver).loginOut() // Переехало в BeforeEach
    }

    @Test
    //@Disabled
    //@DisplayName("Enter User Pass")
    @DisplayName("4. Смена пользовательского пароля ChangePass")
    fun n04_changeUserPass() {
        val fillingUser = "Смена пароля"
        if (DT > 8) println("Test нажатия на $fillingUser")
        tools.qtipClickLast("Настройки")
        //tools.qtipClickLast("Надежный пароль предотвращает несанкционированный доступ к вашей учетной записи.")
        tools.qtipClickLast("Надежный пароль")
        assertTrue(tools.titleWait("window", fillingUser))
        tools.xpathLast("//input[@name='oldpass']")  // Старый пароль
            ?.sendKeys("tdm365")
        tools.xpathLast("//input[@name='newpass']")  // Новый пароль
            ?.sendKeys("Tdm365")
        tools.xpathLast("//input[@name='confirm']")  // Подтверждение
            ?.sendKeys("Tdm365")
        Thread.sleep(threadSleep)
        tools.clickOK("Сменить пароль")
        login = "ChangePass"
        password = "Tdm365"
        Login(driver).loginIn(login, password)
        //Login(driver).loginIn("ChangePass", "Tdm365")
        // Проверить, что вошли
        //Thread.sleep(threadSleep)
        //assertTrue( Login(driver).loginUserName() == "ChangePass")

    }
    @Test
    //@Disabled
    //@DisplayName("Enter User Pass")
    @DisplayName("5. Смена пользовательского пароля ChangePass")
    fun n05_changeUserPass() {
        val fillingUser = "Смена пароля"

        if (DT > 8) println("Test нажатия на $fillingUser")
        tools.qtipClickLast("Настройки")
        //tools.qtipClickLast("Надежный пароль предотвращает несанкционированный доступ к вашей учетной записи.")
        tools.qtipClickLast("Надежный пароль")
        assertTrue(tools.titleWait("window", fillingUser))
        tools.xpathLast("//input[@name='oldpass']")  // Старый пароль
            ?.sendKeys("Tdm365")
        tools.xpathLast("//input[@name='newpass']")  // Новый пароль
            ?.sendKeys("TDm365")
        tools.xpathLast("//input[@name='confirm']")  // Подтверждение
            ?.sendKeys("TDm365")
        Thread.sleep(threadSleep)
        tools.clickOK("Сменить пароль")
// Проверить, что вышли
        login = "ChangePass"
        password = "TDm365"
        Login(driver).loginIn(login, password)
        //Login(driver).loginIn
        //Login(driver).loginIn("ChangePass", "TDm365")
        // Проверить, что вошли
        //Thread.sleep(threadSleep)
        //assertTrue( Login(driver).loginUserName() == "ChangePass")
        //Login(driver).loginOut()
        login = loginSYS
        password = passwordSYS
    }

    /**
     *  тест удаление пользователя
     */
    @Test
    //@DisplayName("Delete user Pass")
    @DisplayName("9. Удаление пользователя ChangePass")
    fun n09_deleteUserPass() {
        if (DT > 8) println("Test удаление user Pass")
        openAllUsers("BUTTON_USER_DELETE")

        if (DT > 8) println("Удаление Pass")

        tools.clickOK("Да")
        // Проверка, что Pass отсутствует GRID_USERS
        Thread.sleep(threadSleep)
        if (TestsProperties.loginpage.contains("555").not())   //%%6.2
        assertTrue (driver.findElements(By.xpath("//div [@data-reference='GRID_USERS']/descendant::div[text()= 'ChangePass']")).isEmpty())
        //if (DT > 8) println("ChangePass отсутствует GRID_USERS")
        tools.clickOK("ОК")

    }

}

