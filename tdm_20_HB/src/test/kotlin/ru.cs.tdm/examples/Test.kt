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

    // ���������� ��� ��������
    lateinit var driver: WebDriver
// ���������� ���������� �� ��������� ����� ������-��������
    lateinit var tools: Tools
    println( "window-1156_header-targetEl".split('-','_'))
    val aaa = "window-1156_header-targetEl".split('-','_')
    println( aaa[2])

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    // �������� ���������� �������� (�.�. �� �������� � �������� ����������):
    WebDriverManager.chromedriver().setup()
    driver = ChromeDriver()
    //���� ��������������� �� ������ ������ �����-1500 1500 3000 2000,0
    driver.manage().window().position = Point(2000,-1000)
    //driver.manage().window().position = Point(0,-1000)
    driver.manage().window().maximize()

    // ������� ���������� ������� ��������� ����� �������, � �������� ������ �� ���.
    // � �������� ��������� ��������� ��������� ����� ���� ������ driver,
    tools = Tools(driver)

    val loginpage = ConfProperties.getProperty("loginpageTDM")
    if (DT>8) println("Close Chrome �������� �������� $loginpage")
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
    driver.quit() //  �������� ���� ��������
}