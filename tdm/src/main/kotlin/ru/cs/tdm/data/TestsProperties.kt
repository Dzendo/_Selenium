package ru.cs.tdm.data

import ru.cs.tdm.ui.StartDialog
const val Tdms = "Tdms"
const val TDMS = "TDMS"
//const val TDM365 = "Объекты"
const val TDM365 = "TDM365"
const val VER = "1.4.54_7.0.41"
object TestsProperties {

    var fluentInDurationNomber = 13000L
    var pollingInDurationNomber = 100L
    var fluentOutDurationNomber = 18000L
    var pollingOutDurationNomber = 1000L
    var repeateInNomber = 5
    var repeateOutNomber = 3

    var repeateCasesNomber: Int = 3 // количество повторений тестов
    var repeateTestsNomber: Int = 3 // количество повторений тестов RepeateTests
    var threadSleepNomber = 4000L // задержки где они есть 1сек
    var debugPrintNomber: Int = 9  // глубина отладочной информации 0 - ничего не печатать, 9 - все

    var browserIndex = 0  // Chrome
    var pageIndex = 0    // 2A
    var loginIndex = 0 //"SYSADMIN"
    var passwordIndex = 0 //"753951"

    var fileOutCheck: Boolean = true
    var consOutCheck: Boolean = true
    var uiOutCheck: Boolean = true
    var assertOutCheck: Boolean = true

    val browsers: Array<String> = arrayOf("Chrome", "Edge", "Firefox", "Opera", "Brave", "Yandex", "CCleaner", "IntExp", "Safari")
    val servers: Array<String> = arrayOf(

        "R localhost:443",
        "S localhost:443",
        "R srv2b.ru",
        "S srv2b.ru",
        "R srv1a.ru",
        "S srv1a.ru",
        "R srv10a.ru",
        "S srv10a.ru",
        "R tdms2012",
        "S tdms2012",
        "R srv2b",
        "S srv2b",
        "R SRV10a",
        "S SRV10a",
        "R hp-work:442",
        "S hp-work:442",
        "R tdms1:555",
        "S tdms1:555",
    )

    val loginPages: Array<String> = arrayOf(


        "http://localhost:443/tdms/?classic#objects",
        "http://localhost:443/client/?classic#objects",
        "http://tdms-srv2b.csoft-msc.ru:443/tdms/?classic#objects",
        "http://tdms-srv2b.csoft-msc.ru:443/client/?classic#objects",
        "http://tdms-srv1a.csoft-msc.ru:443/tdms/?classic#objects",
        "http://tdms-srv1a.csoft-msc.ru:443/client/?classic#objects",
        "http://tdms-srv10a.csoft-msc.ru:443/tdms/?classic#objects",
        "http://tdms-srv10a.csoft-msc.ru:443/client/?classic#objects",
        "http://tdms-temp-2012:443/tdms/#objects",
        "http://tdms-temp-2012:443/client/#objects",
        "http://tdms-srv2b:443/tdms/#objects/",
        "http://tdms-srv2b:443/client/#objects/",
        "http://TDMS-SRV10a:443/tdms/#objects/",
        "http://TDMS-SRV10a:443/client/#objects/",
        "http://hp-work:442/tdms/?classic#objects",
        "http://hp-work:442/client/?classic#objects",
        "http://tdms1:555/tdms/?classic#objects",
        "http://tdms1:555/client/?classic#objects",
    )
    val logins: Array<String> = arrayOf("SYSADMIN", "Cher", "rest", "ChangePass")
    val passwords: Array<String> = arrayOf("Tdm365","Cons123", "753951", "tdm365")

    val testCases: MutableSet<String> = mutableSetOf("Pass", "Head", "User", "Filter")

    // -2 - Start; нажали start: -1
    // -1 - Starting...; команда стартовать тесты и ждем пока стартуют; стартовали тесты: 1
    //  0 - резервное состояние - Ошибка
    //  1 - STOP и тесты работают, пока не нажмут STOP: 2
    //  2 - STOPping... останавливаем тесты: когда остановили -2
    var isStartStop: Int = -2
    var isPaused: Boolean = false  // false - светим Pause и не тормозим  true - светим Resume и стоим на паузе
    var isLog: Boolean =false
    var isExit: Boolean =false

    var loginpage = "http://WKW-930:443/tdms/?classic#objects"
    var login = "SYSADMIN"
    var password = "Tdm365"

    var summuryOfErrors = 0L

    var startDialog: StartDialog? = null

    init {
        loginpage = ConfProperties.getProperty("loginpageTDM")
        login = ConfProperties.getProperty("loginTDM")
        password = ConfProperties.getProperty("passwordTDM")
    }
}