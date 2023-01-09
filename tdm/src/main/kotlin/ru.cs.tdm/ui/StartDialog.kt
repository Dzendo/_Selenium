package ru.cs.tdm.ui

import ru.cs.tdm.ui.TestsProperties.browserIndex
import ru.cs.tdm.ui.TestsProperties.repeateCasesNomber
import ru.cs.tdm.ui.TestsProperties.threadSleepNomber
import ru.cs.tdm.ui.TestsProperties.debugPrintNomber
import ru.cs.tdm.ui.TestsProperties.loginIndex
import ru.cs.tdm.ui.TestsProperties.pageIndex
import ru.cs.tdm.ui.TestsProperties.passwordIndex
import ru.cs.tdm.ui.TestsProperties.repeateTestsNomber
import ru.cs.tdm.ui.TestsProperties.testCases
import java.awt.BorderLayout
import javax.swing.*
import kotlin.system.exitProcess

/**
 * https://zetcode.com/kotlin/swing/
 * https://java-online.ru/swing-layout.xhtml
 */

class StartDialog : JFrame("TDM365 Tests ") {
    //private val testCases: MutableSet<String> = mutableSetOf()
    private val contents = JPanel()
    private val startButton = JButton("START")
    private val stopButton = JButton("STOP")
    private val testsPanel = JPanel()
    private val spinPanel = JPanel()
    private val comboPanel = JPanel()
    private val buttonPanel = JPanel()
    private val printPanel = JPanel()

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

     fun startDialog() {

        // Выход из программы при закрытии
        defaultCloseOperation = EXIT_ON_CLOSE

        // Создание панели содержимого с размещением кнопок
        contentPane = contents
       // Кнопки для создания диалоговых окон
         testsPanel.alignmentX = JComponent.LEFT_ALIGNMENT
         testsPanel.add(JLabel("Тесты:"))
         testsPanel.layout = BoxLayout(testsPanel, BoxLayout.Y_AXIS)
         contents.add(testsPanel) //, BorderLayout.SOUTH)

         spinPanel.alignmentX = JComponent.LEFT_ALIGNMENT
         val label = JLabel("Повтор:")
         label.alignmentX = JComponent.LEFT_ALIGNMENT
         spinPanel.add(label)
         contents.add(spinPanel)
         spinPanel.layout = BoxLayout(spinPanel, BoxLayout.Y_AXIS)

         comboPanel.alignmentX = JComponent.LEFT_ALIGNMENT
         comboPanel.add(JLabel("Сервер:"))
         contents.add(comboPanel)
         comboPanel.layout = BoxLayout(comboPanel, BoxLayout.Y_AXIS)

         printPanel.alignmentX = JComponent.LEFT_ALIGNMENT
         printPanel.add(JLabel("Печать:"))
         contents.add(printPanel)
         printPanel.layout = BoxLayout(printPanel, BoxLayout.Y_AXIS)

         contents.add(buttonPanel)

         testsPanel.add(passBox)
         testsPanel.add(headBox)
         testsPanel.add(userBox)
         testsPanel.add(filterBox)

         val spinRepeateC = JPanel()
         spinRepeateC.add(spinRepeateCases)
         spinRepeateC.add(JLabel("Повтор Case:"))
         spinPanel.add(spinRepeateC)

         val spinRepeateT = JPanel()
         spinRepeateT.add(spinRepeateTests)
         spinRepeateT.add(JLabel("Повтор Test:"))
         spinPanel.add(spinRepeateT)

         val spinThread = JPanel()
         spinThread.add(spinThreadSleep)
         spinThread.add(JLabel("Задержка:"))
         spinPanel.add(spinThread)

         val spinDebug = JPanel()
         spinDebug.add(spinDebugPrint)
         spinDebug.add(JLabel("Печать:"))
         spinPanel.add(spinDebug)

         val browsers: Array<String> = arrayOf(
             "Chrome", "Edge", "Firefox", "Opera", "Yandex", "Brave", "CCleaner", "IntExp", "Safari")
         browser  = JComboBox<String>(browsers)
         comboPanel.add(browser)

         val servers: Array<String> = arrayOf(
             "srv2a.ru:777",
             "srv2a:777",
             "srv2a.ru:444",
             "srv2a:444",
             "srv10.ru:443",
             "SRV10:443",
             "tdms2012:444",
             "srv2a.ru:777",
             "srv2a:777",
             )
         val loginPages: Array<String> = arrayOf(
             "http://tdms-srv2a.csoft-msc.ru:777/client/?classic#objects",
             "http://tdms-srv2a:777/client/#objects/",
             "http://tdms-srv2a.csoft-msc.ru:444/client/?classic#objects",
             "http://tdms-srv2a:444/client/#objects/",
             "http://tdms-srv10.csoft-msc.ru:443/client/?classic#objects",
             "http://TDMS-SRV10:443/client/#objects/",
             "http://tdms-temp-2012:444/client/#objects/",
             "http://tdms-srv2a.csoft-msc.ru:777/client/?classic#objects",
             "http://tdms-srv2a:777/client/#objects/",
             )

         server  = JComboBox<String>(servers)
         comboPanel.add(server)

         val logins: Array<String> = arrayOf("SYSADMIN", "Cher", "rest", "ChangePass")
         login  = JComboBox<String>(logins)
         comboPanel.add(login)

         val passwords: Array<String> = arrayOf("753951", "Cons123", "tdm365")
         password = JComboBox<String>(passwords)
         comboPanel.add(password)

         printPanel.add(outBox)
         printPanel.add(consBox)
         printPanel.add(uiBox)
         printPanel.add(errBox)


         buttonPanel.add(startButton)
         startButton.addActionListener {
             repeateCasesNomber = spinRepeateCases.value.toString().toInt()
             repeateTestsNomber = spinRepeateTests.value.toString().toInt()
             threadSleepNomber = spinThreadSleep.value.toString().toLong()*1000L
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
             // Здесь надо организовывать поток и стартовать в нем + Листенер с указанием туда в поток
             startTests()
             TestsProperties.startIsOn = false
         }
         buttonPanel.add(stopButton)
         stopButton.addActionListener { exitProcess(0) }


        // Определение размера и открытие окна
        // setLocationRelativeTo(null)  // Эта строка используется для центрирования окна на экране.
        //setLocation(300, 200)
        //setSize(550, 300)
        //isVisible = true
    }

    /** Функция создания диалогового окна.
     * @param title - заголовок окна
     * @param modal - флаг модальности
     */
    private fun createDialog(title: String, modal: Boolean): JDialog {
        val dialog = JDialog(this, title, modal)
        dialog.defaultCloseOperation = DISPOSE_ON_CLOSE
        dialog.setSize(180, 90)
        return dialog
    }
}

        fun main() {
            SwingUtilities.invokeLater {      // этот поток называется EDT (поток диспетчеризации событий).
                JFrame.setDefaultLookAndFeelDecorated(true)
                val frame = StartDialog()
                frame.startDialog()
                frame.pack()
                frame.setLocationRelativeTo(null)
                frame.setSize(800, 300)
                frame.isVisible = true
            }
        }

