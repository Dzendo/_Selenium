package ru.cs.tdm.ui

import ru.cs.tdm.data.ConfProperties

object TestsProperties {
    var repeateCasesNomber: Int = 3 // количество повторений тестов
    var repeateTestsNomber: Int = 3 // количество повторений тестов RepeateTests
    var threadSleepNomber = 1000L // задержки где они есть 1сек
    var debugPrintNomber: Int = 9  // глубина отладочной информации 0 - ничего не печатать, 9 - все

    var browserIndex = 0  // Chrome
    var pageIndex = 0    // 2A
    var loginIndex = 0 //"SYSADMIN"
    var passwordIndex = 0 //"753951"

    var fileOutCheck: Boolean = true
    var consOutCheck: Boolean = true
    var uiOutCheck: Boolean = true
    var assertOutCheck: Boolean = true

    val testCases: MutableSet<String> = mutableSetOf("Pass", "Head", "User", "Filter")

    var startIsOn: Boolean = false
    var stopIsOn: Boolean = false

    var loginpage = "http://tdms-srv2a.csoft-msc.ru:444/client/?classic#objects"
    var login = "SYSADMIN"
    var password = "753951"

    init {
        loginpage = ConfProperties.getProperty("loginpageTDM")
        if (debugPrintNomber > 8) println("Открытие страницы $loginpage")
        //driver.get(loginpage)

        login = ConfProperties.getProperty("loginTDM")
        password = ConfProperties.getProperty("passwordTDM")
        if (debugPrintNomber > 8) println("login= $login   password= $password")
        //Login(driver).loginIn(login, password)
        // Запоминаю логин и пароль для диаграммы ганта - костыль.
    }
}