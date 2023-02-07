package ru.cs.tdm.data

object TestsProperties {
    var fluentInDurationNomber = 3000L
    var pollingInDurationNomber = 500L
    var fluentOutDurationNomber = 7000L
    var pollingOutDurationNomber = 1000L
    var repeateInNomber = 5
    var repeateOutNomber = 3

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

    // -2 - Start; нажали start: -1
    // -1 - Starting...; команда стартовать тесты и ждем пока стартуют; стартовали тесты: 1
    //  0 - резервное состояние - Ошибка
    //  1 - STOP и тесты работают, пока не нажмут STOP: 2
    //  2 - STOPping... останавливаем тесты: когда остановили -2
    var isStartStop: Int = -2
    var isPaused: Boolean = false  // false - светим Pause и не тормозим  true - светим Resume и стоим на паузе
    var isLog: Boolean =false
    var isExit: Boolean =false

    var loginpage = "http://tdms-srv1.csoft-msc.ru:443/client/?classic#objects"
    var login = "SYSADMIN"
    var password = "Cons123"

    var summuryOfErrors = 0L

    init {
        loginpage = ConfProperties.getProperty("loginpageTDM")
        login = ConfProperties.getProperty("loginTDM")
        password = ConfProperties.getProperty("passwordTDM")
    }
}