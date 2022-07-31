package ru.cs.tdm.examples

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.Point
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties

const val threadSleep = 1000L
const val DT: Int = 9
const val NN:Int = 10


fun main(args: Array<String>) {

    // переменна€ дл€ драйвера
    lateinit var driver: WebDriver
// объ€влени€ переменных на созданные ранее классы-страницы
    lateinit var tools: Tools
    println( "window-1156_header-targetEl".split('-','_'))
    val aaa = "window-1156_header-targetEl".split('-','_')
    println( aaa[2])

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    // создание экземпл€ра драйвера (т.к. он объ€влен в качестве переменной):
    WebDriverManager.chromedriver().setup()
    driver = ChromeDriver()
    //окно разворачиваетс€ на полный второй экран-1500 1500 3000 2000,0
    driver.manage().window().position = Point(2000,-1000)
    //driver.manage().window().position = Point(0,-1000)
    driver.manage().window().maximize()

    // —оздаем экземпл€ры классов созданных ранее страниц, и присвоим ссылки на них.
    // ¬ качестве параметра указываем созданный перед этим объект driver,
    tools = Tools(driver)

    val loginpage = ConfProperties.getProperty("loginpageTDM")
    if (DT>8) println("Close Chrome ќткрытие страницы $loginpage")
    driver.get(loginpage)

    val login = ConfProperties.getProperty("loginTDM")
    val password = ConfProperties.getProperty("passwordTDM")
    if (DT>8) println("login= $login   password= $password")
    Login(driver).loginIn(login, password)

    val elementList = driver.findElements(By.xpath("//*[contains(@id,'-')]"))
    println ("elementlist = ${elementList.size}")

    val idList: MutableList<String> = mutableListOf()
    for (element in elementList)
        try {
            idList.add(element.getAttribute("id"))
        }catch (_: StaleElementReferenceException) {}
        println ("idlist = ${idList.size}")

        //  forEach{it.split('-','_')}

    if (DT >7) println("Close Chrome")
    tools.closeEsc5()
    Login(driver).loginOut()
    driver.quit() //  закрыти€ окна браузера
}