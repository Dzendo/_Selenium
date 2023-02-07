package ru.cs.tdm.ui

import ru.cs.tdm.data.TestsProperties
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import kotlin.system.exitProcess

/**
 * https://zetcode.com/kotlin/swing/
 * https://java-online.ru/swing-layout.xhtml
 */

class StartDialog : JFrame("TDM365 Tests "), ActionListener  {
    //private val classStart = StartTests(this)  // перенес вниз чтобы был повторный старт
    //private var summuryOfErrors : Long = 0L
    //private val testCases: MutableSet<String> = mutableSetOf()
    private val contents = JPanel()
    private val buttonStartStop = JButton("START")
    private val buttonPauseResume = JButton("PAUSE")
    private val buttonLog = JButton("LOG")
    private val buttonExit = JButton("EXIT")

    private val passBox = JCheckBox("Change Password", true)
    private val headBox = JCheckBox("Test Head TDM", true)
    private val userBox = JCheckBox("Test Admin User", true)
    private val filterBox = JCheckBox("Test Filters", true)

    private val outBox = JCheckBox("File Out", true)
    private val consBox = JCheckBox("Console", true)
    private val uiBox = JCheckBox("Window", true)
    private val errBox = JCheckBox("Errors", true)

    private val browsers: Array<String> = arrayOf(
        "Chrome", "Edge", "Firefox", "Opera", "Yandex", "Brave", "CCleaner", "IntExp", "Safari")

    private val servers: Array<String> = arrayOf(
        "srv1.ru:443",
        "srv2a.ru:443",
        "srv2a.ru:444",
        "srv10.ru:443",
        "tdms2012:444",
        "srv2a:443",
        "srv2a:444",
        "SRV10:443",
    )

    private val logins: Array<String> = arrayOf("SYSADMIN", "Cher", "rest", "ChangePass")
    private val passwords: Array<String> = arrayOf("Cons123", "753951", "tdm365")

    private val browserBox: JComboBox<String> = JComboBox<String>(browsers)
    private val serverBox: JComboBox<String> = JComboBox<String>(servers)
    private val loginBox: JComboBox<String> = JComboBox<String>(logins)
    private val passwordBox: JComboBox<String> = JComboBox<String>(passwords)

    private val spinRepeateCases =JSpinner( SpinnerNumberModel(
            3,  //initial value
            1,  //min
            99,  //max
            1   //step
        ))
    private val spinRepeateTests =JSpinner( SpinnerNumberModel(
        1,  //initial value
        1,  //min
        99,  //max
        1   //step
    ))
    private val spinThreadSleep =JSpinner(SpinnerNumberModel(
        1,  //initial value
        0,  //min
        99,  //max
        1   //step
    ))
    private val spinDebugPrint =JSpinner(SpinnerNumberModel(
        5,  //initial value
        1,  //min
        9,  //max
        1   //step
    ))

    private val loginPages: Array<String> = arrayOf(
        "http://tdms-srv1.csoft-msc.ru:443/client/?classic#objects",
        "http://tdms-srv2a.csoft-msc.ru:443/client/?classic#objects",
        "http://tdms-srv2a.csoft-msc.ru:444/client/?classic#objects",
        "http://tdms-srv10.csoft-msc.ru:443/client/?classic#objects",
        "http://tdms-temp-2012:444/client/#objects/",
        "http://tdms-srv2a:443/client/#objects/",
        "http://tdms-srv2a:444/client/#objects/",
        "http://TDMS-SRV10:443/client/#objects/",
    )

    private val actionRepeate: JLabel = JLabel("-1")
    private val actionCase : JLabel = JLabel("NULL")
    private val allErrors : JLabel = JLabel("-1")

    //fun startDialog()
    init  {
        // Выход из программы при закрытии
        defaultCloseOperation = EXIT_ON_CLOSE
        // Создание панели содержимого с размещением кнопок
        val gridLayout = GridLayout(0,4,5,5)
        contents.layout = gridLayout

        contentPane = contents
        contents.alignmentX = JComponent.LEFT_ALIGNMENT

        contents.add(JLabel("Тесты:"),)
        contents.add(JLabel("Повтор:"))
        contents.add(JLabel("Сервер:"))
        contents.add(JLabel("Печать:"))

        contents.add(passBox)
        //contents.add("Case:", spinRepeateCases)
        val spinRepeateC = JPanel(FlowLayout(FlowLayout.LEFT))
        spinRepeateC.add(spinRepeateCases)
        spinRepeateC.add(JLabel("Case:"))
        contents.add(spinRepeateC)

        contents.add(browserBox)
        contents.add(outBox)

        contents.add(headBox)
        val spinRepeateT = JPanel(FlowLayout(FlowLayout.LEFT))
        spinRepeateT.add(spinRepeateTests)
        spinRepeateT.add(JLabel("Test:"))
        contents.add(spinRepeateT)

        contents.add(serverBox)
        contents.add(consBox)

        contents.add(userBox)
        val spinThread = JPanel(FlowLayout(FlowLayout.LEFT))
        spinThread.add(spinThreadSleep)
        spinThread.add(JLabel("Задержка:"))
        contents.add(spinThread)

        contents.add(loginBox)
        contents.add(uiBox)

        contents.add(filterBox)
        val spinDebug = JPanel(FlowLayout(FlowLayout.LEFT))

        spinDebug.add(spinDebugPrint)
        spinDebug.add(JLabel("Печать:"))
        contents.add(spinDebug)

        contents.add(passwordBox)
        contents.add(errBox)

        // Кнопки для создания диалоговых окон
        contents.add(buttonStartStop)
        buttonStartStop.addActionListener(this)
        contents.add(buttonPauseResume)
        buttonPauseResume.addActionListener(this)
        contents.add(buttonLog)
        buttonLog.addActionListener(this)
        contents.add(buttonExit)
        buttonExit.addActionListener(this)


        contents.add(JLabel("allErrors:"))
        contents.add(allErrors)
        contents.add(JLabel(":"))
        contents.add(JLabel(":"))
        contents.add(JLabel("Repeat:"))
        contents.add(actionRepeate)
        contents.add(JLabel(":"))
        contents.add(JLabel(":"))
        contents.add(JLabel("Case:"))
        contents.add(actionCase)
        contents.add(JLabel(":"))
        contents.add(JLabel(":"))

        showButtons()

       setDefaultLookAndFeelDecorated(true)
       pack()
       setLocationRelativeTo(null)
       setSize(540, 300)
       isVisible = true


    }

    /**
     * Должна высвечивать ход выполнения, но не обновляет UI - не работает
     */
    fun showActionCase(actionRepeate : Int, actionCase : String, allErrors: Long ) {

        this.actionRepeate.text = actionRepeate.toString()
        this.actionRepeate.isVisible = true
        this.actionRepeate.revalidate()
        this.actionRepeate.repaint()

        this.actionCase.text = actionCase
        this.actionCase.isVisible = true
        this.actionCase.revalidate()
        this.actionCase.repaint()

        this.allErrors.text = allErrors.toString()
        this.allErrors.isVisible = true
        this.allErrors.revalidate()
        this.allErrors.repaint()
    }
    fun showButtons() {
        when (TestsProperties.isStartStop) {
            -2 -> buttonStartStop.text = "START"
            -1 -> buttonStartStop.text = "Starting.."
             1 -> buttonStartStop.text = "STOP"
             2 -> buttonStartStop.text = "Stopping"
            else -> buttonStartStop.text = "ELSE"
        }
        buttonStartStop.isVisible = true
        this.buttonStartStop.revalidate()
        this.buttonStartStop.repaint()

        buttonPauseResume.text = if (TestsProperties.isPaused) "Resume" else "PAUSE"
        this.buttonPauseResume.revalidate()
        this.buttonPauseResume.repaint()
    }


    override fun actionPerformed(e: ActionEvent?) {
        if (e == null) return
        when (e.source) {
            buttonStartStop -> {
                    when (TestsProperties.isStartStop) {
                        -2 -> {     // START
                            TestsProperties.isStartStop = -1
                            showButtons()
                            with(TestsProperties) {
                                repeateCasesNomber = spinRepeateCases.value.toString().toInt()
                                repeateTestsNomber = spinRepeateTests.value.toString().toInt()
                                threadSleepNomber = spinThreadSleep.value.toString().toLong() * 1000L
                                debugPrintNomber = spinDebugPrint.value.toString().toInt()

                                browserIndex = browserBox.selectedIndex
                                pageIndex = serverBox.selectedIndex
                                //loginpage = server.selectedItem.toString()
                                loginpage = loginPages[pageIndex]
                                loginIndex = loginBox.selectedIndex
                                //login = login.selectedItem.toString()
                                login = logins[loginIndex]
                                passwordIndex = passwordBox.selectedIndex
                                //password = password.selectedItem.toString()
                                password = passwords[passwordIndex]

                                testCases.clear()
                                if (passBox.isSelected) testCases.add("Pass")
                                if (headBox.isSelected) testCases.add("Head")
                                if (userBox.isSelected) testCases.add("User")
                                if (filterBox.isSelected) testCases.add("Filter")

                                fileOutCheck = outBox.isSelected
                                consOutCheck = consBox.isSelected
                                uiOutCheck = uiBox.isSelected
                                assertOutCheck = errBox.isSelected

                            }
                            //println("@@@@@ actionCase.text = ${actionCase.text}  @@@")
                            // Здесь надо организовывать поток и стартовать в нем + Листенер с указанием туда в поток
                            // #################################################################################################
                            val classStart = StartTests(this)
                            val executeStart = classStart.execute()  // СТАРТ циклов тестов
                            // #################################################################################################
                            with(TestsProperties) {
                                isStartStop = 1
                                // summuryOfErrors = classStart.get()  // нельзя здесь спрашивать - заморожу интерфейс
                                showButtons()
                                println("@@@@@ START summuryOfErrors = ${summuryOfErrors}  @@@")
                            }
                        }

                        1 -> {   // STOP
                            // НЕ РАБОТАЕТ переделать в флажки
                            //classStart.cancel(true)
                            TestsProperties.isStartStop = 2
                            showButtons()
                            println("@@@@@ STOP summuryOfErrors = ${TestsProperties.summuryOfErrors}  @@@")
                        }

                        else -> println("ELSE Нельзя нажимать на ing... ${TestsProperties.isStartStop}")
                    }

            }

            buttonPauseResume -> {
                TestsProperties.isPaused = !TestsProperties.isPaused
                showButtons()
            }
            buttonLog -> {}
            buttonExit -> {
                println("@@@@@ EXIT allSummuryOfErrors = ${TestsProperties.summuryOfErrors}  @@@")
                exitProcess(0)
            }

            else -> {}
        }
    }
}

fun main() {
    SwingUtilities.invokeLater { StartDialog() }  // этот поток называется EDT (поток диспетчеризации событий).
}
/** Функция создания диалогового окна.
 * @param title - заголовок окна
 * @param modal - флаг модальности
 */

/*private fun createDialog(title: String, modal: Boolean): JDialog {
    val dialog = JDialog(this, title, modal)
    dialog.defaultCloseOperation = DISPOSE_ON_CLOSE
    dialog.setSize(180, 90)
    return dialog
}*/

