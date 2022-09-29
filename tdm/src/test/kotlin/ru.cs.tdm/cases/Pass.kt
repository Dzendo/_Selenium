package ru.cs.tdm.cases

import io.github.bonigarcia.wdm.WebDriverManager
import org.apache.commons.io.FileUtils.copyFile
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@DisplayName("Pass Change Test")
@TestMethodOrder(MethodOrderer.MethodName::class)
class Pass {

    companion object {
    const val threadSleep = 1000L
    const val DT: Int = 9
    const val NN:Int = 1
    // переменная для драйвера
    lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    lateinit var tools: Tools
    lateinit var loginSYS: String
    lateinit var passSYS: String
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
        if (DT>7) println("Вызов BeforeAll AdminUserTest")
            // Создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()
        //окно разворачивается на полный второй экран-1500 1500 3000 2000,0
        driver.manage().window().position = Point(2000,-1000)
        //driver.manage().window().position = Point(0,-1000)
        driver.manage().window().maximize()

            // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
            // В качестве параметра указываем созданный перед этим объект driver,
            tools = Tools(driver)

            val loginpage = ConfProperties.getProperty("loginpageTDM")
            if (DT>8) println("Открытие страницы $loginpage")
            driver.get(loginpage)
            assertTrue(driver.title == "Tdms")
            val login = ConfProperties.getProperty("loginTDM")
            val password = ConfProperties.getProperty("passwordTDM")
            if (DT>8) println("login= $login   password= $password")
            Login(driver).loginIn(login, password)
            loginSYS = login
            passSYS = password
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            //tools.idList()
            if (DT>7) println("Вызов AfterAll AdminUserTest")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  закрытия окна браузера

        }
    }   // конец companion object

        fun screenShot(name: String = "image") {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val sdf = SimpleDateFormat("ddMMyyyyhhmmss")
        copyFile(scrFile, File("./$name${sdf.format(Date())}.png"))
    }

     /**
     *  тест создание нового пользователя Pass
     */
    @Test
    @DisplayName("Create user Pass")
    fun n01_CreateUserPass() {
        if (DT>8) println("Test нажатия на Create user Pass")
         val mainMenu = "Объекты"
         if (DT >8) println("Test нажатия на $mainMenu TDMS Web")
         tools.qtipClickLast(mainMenu)
         assertTrue(tools.titleContain("TDM365"))
         assertTrue(tools.qtipPressedLast("Объекты"))

         val adminUser = "Администрирование групп"
         if (DT >8) println("Test нажатия на $adminUser")
         tools.qtipClickLast(adminUser)
         assertTrue(tools.titleWait("window", "Редактирование групп"))
         assertTrue(tools.referenceWaitText("STATIC1", "Группы пользователей"))

         // data-reference="GRID_GROUPS"
         val headTeg = tools.idRef("GRID_GROUPS")
         val allUsers = "Все пользователи"
         if (DT >8) println("Test нажатия на $allUsers")
         // //div[text()= '$allUsers']   //*[@id='$headTeg']/descendant::div[text()= '$allUsers']
         tools.xpathLast("//*[@id='$headTeg']/descendant::div[text()= '$allUsers']")?.click()
         assertTrue(tools.referenceWaitText("GROUP_NAME", allUsers))
         if (DT >7) println("Открыли всех пользователей")

         val createUser = "Создать пользователя"
         if (DT >8) println("Test нажатия на $createUser")

         // создать пользователя data-reference="BUTTON_USER_CREATE"
         tools.referenceClickLast("BUTTON_USER_CREATE")
         Thread.sleep(threadSleep)
         assertTrue(tools.titleWait("window", "Редактирование пользователя"))

         /**
          *  тест заполнение и сохранение нового пользователя
          */

         val fillingUser = "Редактирование пользователя"
         if (DT >8) println("Test нажатия на $fillingUser")
         assertTrue(tools.titleWait("window", fillingUser))

         // //html/body/descendant::div[@data-reference]
         tools.xpathLast("//*[@data-reference='ATTR_DESCRIPTION']/descendant::input")  // Описание
             ?.sendKeys("ChangePass")
         tools.xpathLast("//*[@data-reference='ATTR_LOGIN']/descendant::input")  // Логин
             ?.sendKeys("ChangePass")
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
         // Проверить что Pass есть в списке
         assertTrue(tools.titleWait("window", "Редактирование групп"))
         if (DT > 8) println("Проверка Pass")
         tools.xpathLast("//div[contains(text(), 'ChangePass')]")?.click()
         Thread.sleep(threadSleep)
         assertTrue((tools.xpathLast("//tr[@aria-selected='true']")?.text ?: "None") == "ChangePass")

         tools.clickOK()

         if (DT>7) println("Выход из под SYSADMIN")
         tools.closeEsc5()
         Login(driver).loginOut()
    }
        /**
         *  тест заполнение и сохранение пароля
         */
    @Test
    //@Disabled
    @DisplayName("Enter User Pass")
    fun n02_enterUserPass() {
            val fillingUser = "Вход под пользователем Pass"
            if (DT > 8) println("Test нажатия на $fillingUser")
            Login(driver).loginIn("ChangePass", "tdm365")
// Проверить, что вошли
            //
        }

    @Test
    //@Disabled
    @DisplayName("Enter User Pass")
    fun n03_changeUserPass() {
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
        tools.clickOK("Сменить пароль")

        Login(driver).loginIn("ChangePass", "Tdm365")
        // Проверить, что вошли
        assertTrue( Login(driver).loginUserName() == "ChangePass")

        //val fillingUser = "Смена пароля"
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
        tools.clickOK("Сменить пароль")
// Проверить, что вышли
        Login(driver).loginIn("ChangePass", "TDm365")
        // Проверить, что вошли
        assertTrue( Login(driver).loginUserName() == "ChangePass")
        Login(driver).loginOut()
    }
    /**
     *  тест удаление пользователя
     */
    @Test
    @DisplayName("Delete user Pass")
    fun n09_CreateUserPass() {
        if (DT > 8) println("Test удаление user Pass")
        Login(driver).loginIn(loginSYS, passSYS)
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
        assertTrue(tools.referenceWaitText("GROUP_NAME", allUsers))
        if (DT > 7) println("Открыли всех пользователей")


        assertTrue(tools.titleWait("window", "Редактирование групп"))

        if (DT > 8) println("Удаление Pass")
        tools.xpathLast("//div[contains(text(), 'ChangePass')]")?.click()
        tools.referenceClickLast("BUTTON_USER_DELETE")  // //  кнопка Удалить пользователя
        tools.clickOK("Да")
        // Проверка, что Pass отсутствует GRID_USERS
        Thread.sleep(threadSleep)
        assertTrue (driver.findElements(By.xpath("//div [@data-reference='GRID_USERS']/descendant::div[text()= 'ChangePass']")).isEmpty())

        tools.clickOK("ОК")

    }

}

