package ru.cs.tdm.ui

import ru.cs.tdm.data.TestsProperties
import ru.cs.tdm.data.TestsProperties.browserIndex
import ru.cs.tdm.data.TestsProperties.repeateCasesNomber
import ru.cs.tdm.data.TestsProperties.threadSleepNomber
import ru.cs.tdm.data.TestsProperties.debugPrintNomber
import ru.cs.tdm.data.TestsProperties.loginIndex
import ru.cs.tdm.data.TestsProperties.pageIndex
import ru.cs.tdm.data.TestsProperties.passwordIndex
import ru.cs.tdm.data.TestsProperties.repeateTestsNomber
import ru.cs.tdm.data.TestsProperties.testCases
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

    private var summuryOfErrors : Long = 0L
    //private val testCases: MutableSet<String> = mutableSetOf()
    private val contents = JPanel()
    private val startButton = JButton("START")
    private val pauseButton = JButton("PAUSE")
    private val stopButton = JButton("STOP")
    private val exitButton = JButton("EXIT")

    private val passBox = JCheckBox("Change Password", true)
    private val headBox = JCheckBox("Test Head TDM", true)
    private val userBox = JCheckBox("Test Admin User", true)
    private val filterBox = JCheckBox("Test Filters", true)

    private val outBox = JCheckBox("File Out", true)
    private val consBox = JCheckBox("Console", true)
    private val uiBox = JCheckBox("Window", true)
    private val errBox = JCheckBox("Errors", true)

    private lateinit var browser: JComboBox<String>
    private lateinit var server: JComboBox<String>
    private lateinit var login: JComboBox<String>
    private lateinit var password: JComboBox<String>

    private val modelRepeateCases: SpinnerModel = SpinnerNumberModel(
        3,  //initial value
        1,  //min
        999,  //max
        1   //step
    )
    private val modelRepeateTests: SpinnerModel = SpinnerNumberModel(
        1,  //initial value
        1,  //min
        99,  //max
        1   //step
    )
    private val modelThreadSleep: SpinnerModel = SpinnerNumberModel(
        1,  //initial value
        0,  //min
        99,  //max
        1   //step
    )
    private val modelDebugPrint: SpinnerModel = SpinnerNumberModel(
        5,  //initial value
        1,  //min
        9,  //max
        1   //step
    )
    private val spinRepeateCases =JSpinner(modelRepeateCases)
    private val spinRepeateTests =JSpinner(modelRepeateTests)
    private val spinThreadSleep =JSpinner(modelThreadSleep)
    private val spinDebugPrint =JSpinner(modelDebugPrint)

    private val browsers: Array<String> = arrayOf(
        "Chrome", "Edge", "Firefox", "Opera", "Yandex", "Brave", "CCleaner", "IntExp", "Safari")

    private val loginPages: Array<String> = arrayOf(
        "http://tdms-srv2a.csoft-msc.ru:777/client/?classic#objects",
        "http://tdms-srv2a:777/client/#objects/",
        "http://tdms-srv2a.csoft-msc.ru:444/client/?classic#objects",
        "http://tdms-srv2a:444/client/#objects/",
        "http://tdms-srv10.csoft-msc.ru:443/client/?classic#objects",
        "http://TDMS-SRV10:443/client/#objects/",
        "http://tdms-temp-2012:444/client/#objects/",
        "http://tdms-srv1a:777/client/#objects",
        "http://tdms-srv1:777/client/#objects",
        "http://tdms-srv1.csoft-msc.ru:777/client/?classic#objects"

    )

    private val servers: Array<String> = arrayOf(
        "srv2a.ru:777",
        "srv2a:777",
        "srv2a.ru:444",
        "srv2a:444",
        "srv10.ru:443",
        "SRV10:443",
        "tdms2012:444",
        "srv1a:777 Server",
        "srv1:777 Web",
        "srv1.ru:777"
    )
    private val logins: Array<String> = arrayOf("SYSADMIN", "Cher", "rest", "ChangePass")
    private val passwords: Array<String> = arrayOf("753951", "Cons123", "tdm365")

    private val actionRepeate: JLabel = JLabel("-1")
    private val actionCase : JLabel = JLabel("NULL")
    private val allErrors : JLabel = JLabel("-1")

    fun startDialog() {

        // Выход из программы при закрытии
        defaultCloseOperation = EXIT_ON_CLOSE

        // Создание панели содержимого с размещением кнопок
        contents.layout = GridLayout(0,4,5,5)
        contentPane = contents
        // Кнопки для создания диалоговых окон
        contents.alignmentX = JComponent.LEFT_ALIGNMENT
        contents.add(JLabel("Тесты:"))
        contents.add(JLabel("Повтор:"))
        contents.add(JLabel("Сервер:"))
        contents.add(JLabel("Печать:"))

        contents.add(passBox)
        val spinRepeateC = JPanel(FlowLayout(FlowLayout.LEFT))
        spinRepeateC.add(spinRepeateCases)
        spinRepeateC.add(JLabel("Case:"))
        contents.add(spinRepeateC)

        browser  = JComboBox<String>(browsers)
        contents.add(browser)
        contents.add(outBox)

        contents.add(headBox)
        val spinRepeateT = JPanel(FlowLayout(FlowLayout.LEFT))
        spinRepeateT.add(spinRepeateTests)
        spinRepeateT.add(JLabel("Test:"))
        contents.add(spinRepeateT)

        server  = JComboBox<String>(servers)
        contents.add(server)
        contents.add(consBox)

        contents.add(userBox)
        val spinThread = JPanel(FlowLayout(FlowLayout.LEFT))
        spinThread.add(spinThreadSleep)
        spinThread.add(JLabel("Задержка:"))
        contents.add(spinThread)

        login  = JComboBox<String>(logins)
        contents.add(login)
        contents.add(uiBox)

        contents.add(filterBox)
        val spinDebug = JPanel(FlowLayout(FlowLayout.LEFT))

        spinDebug.add(spinDebugPrint)
        spinDebug.add(JLabel("Печать:"))
        contents.add(spinDebug)

        password = JComboBox<String>(passwords)
        contents.add(password)

        contents.add(errBox)

        contents.add(startButton)
        startButton.addActionListener(this)
        contents.add(pauseButton)
        pauseButton.addActionListener(this)
        contents.add(stopButton)
        stopButton.addActionListener(this)
        contents.add(exitButton)
        exitButton.addActionListener(this)


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

    }

    /**
     * Должна высвечивать ход выполнения, но не обновляет UI - не работает
     */
    fun showActionCase(actionRepeate : Int, actionCase : String ) {
        this.actionRepeate.text = actionRepeate.toString()
        this.actionCase.text = actionCase
        this.actionRepeate.isVisible = true
        this.actionCase.isVisible = true
        this.actionRepeate.revalidate()
        this.actionCase.revalidate()
        this.actionRepeate.repaint()
        this.actionCase.repaint()
    }

    override fun actionPerformed(e: ActionEvent?) {
        if (e == null) return
        when (e.source) {
            startButton -> {
                repeateCasesNomber = spinRepeateCases.value.toString().toInt()
                repeateTestsNomber = spinRepeateTests.value.toString().toInt()
                threadSleepNomber = spinThreadSleep.value.toString().toLong() * 1000L
                debugPrintNomber = spinDebugPrint.value.toString().toInt()

                browserIndex = browser.selectedIndex
                pageIndex = server.selectedIndex
                //TestsProperties.loginpage = server.selectedItem.toString()
                TestsProperties.loginpage = loginPages[pageIndex]
                loginIndex = login.selectedIndex
                //TestsProperties.login = login.selectedItem.toString()
                TestsProperties.login = logins[loginIndex]
                passwordIndex = password.selectedIndex
                //TestsProperties.password = password.selectedItem.toString()
                TestsProperties.password = passwords[passwordIndex]

                testCases.clear()
                if (passBox.isSelected) testCases.add("Pass")
                if (headBox.isSelected) testCases.add("Head")
                if (userBox.isSelected) testCases.add("User")
                if (filterBox.isSelected) testCases.add("Filter")

                TestsProperties.fileOutCheck = outBox.isSelected
                TestsProperties.consOutCheck = consBox.isSelected
                TestsProperties.uiOutCheck = uiBox.isSelected
                TestsProperties.assertOutCheck = errBox.isSelected

                /**
                 * int selectedIndex = myComboBox.getSelectedIndex();
                 * Object selectedObject = myComboBox.getSelectedItem();
                 * String selectedValue = myComboBox.getSelectedValue().toString();
                 */
                TestsProperties.startIsOn = true
                actionCase.text = "LUNN"
                actionCase.isVisible
                actionCase.revalidate()
                actionCase.repaint()
                //println("@@@@@ actionCase.text = ${actionCase.text}  @@@")
                // Здесь надо организовывать поток и стартовать в нем + Листенер с указанием туда в поток
                val classStart = StartTests(this)
                classStart.execute()  // СТАРТ циклов тестов
                //summuryOfErrors = classStart.get()  // нельзя здесь спрашивать - заморожу интерфейс

                println("@@@@@ summuryOfErrors = $summuryOfErrors  @@@")
                TestsProperties.startIsOn = false
            }

            pauseButton -> {}
            stopButton -> {}
            exitButton -> {
                println("@@@@@ allSummuryOfErrors = $summuryOfErrors  @@@")
                exitProcess(0)
            }

            else -> {}
        }
    }
}

fun main() {
    SwingUtilities.invokeLater {      // этот поток называется EDT (поток диспетчеризации событий).
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = StartDialog()
        frame.startDialog()
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.setSize(540, 240)
        frame.isVisible = true
    }
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

