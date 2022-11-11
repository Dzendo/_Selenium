package ru.cs.tdm.ui

import java.awt.BorderLayout
import javax.swing.*
import kotlin.system.exitProcess
var DT:Int = 5
var NN:Int = 3
var TS:Int = 1
class StartDialog : JFrame("TDM365 Tests ") {
    private val contents = JPanel()
    private val startButton = JButton("START")
    private val stopButton = JButton("STOP")
    private val boxesPanel = JPanel()
    private val passBox = JCheckBox("Change Password")
    private val headBox = JCheckBox("Test Head TDM")
    private val userBox = JCheckBox("Test Admin User")
    private val filterBox = JCheckBox("Test Filters")


    private val modelDT: SpinnerModel = SpinnerNumberModel(
        5,  //initial value
        1,  //min
        9,  //max
        1   //step
    )
    private val modelNN: SpinnerModel = SpinnerNumberModel(
        3,  //initial value
        1,  //min
        99,  //max
        1   //step
    )
    private val modelTS: SpinnerModel = SpinnerNumberModel(
        1,  //initial value
        1,  //min
        99,  //max
        1   //step
    )
    //val DT =JSpinner()  // 0
    private val spinDT =JSpinner(modelDT)
    private val spinNN =JSpinner(modelNN)
    private val spinTS =JSpinner(modelTS)


     fun startDialog() {
         val testCases: MutableSet<String> = mutableSetOf()
        // Выход из программы при закрытии
        defaultCloseOperation = EXIT_ON_CLOSE

        // Создание панели содержимого с размещением кнопок
        contentPane = contents
       // Кнопки для создания диалоговых окон
         boxesPanel.add(passBox)
         passBox.addItemListener { val box = "Pass"; if (passBox.isSelected) testCases.add(box) else testCases.remove(box) }
         boxesPanel.add(headBox)
         headBox.addItemListener { val box = "Head"; if (headBox.isSelected) testCases.add(box) else testCases.remove(box) }
         boxesPanel.add(userBox)
         userBox.addItemListener { val box = "User"; if (userBox.isSelected) testCases.add(box) else testCases.remove(box)}
         boxesPanel.add(filterBox)
         filterBox.addItemListener { val box = "Filter"; if (filterBox.isSelected) testCases.add(box) else testCases.remove(box)}

         contents.add(JLabel("Печать:"))
         //spinDT.addChangeListener { DT = modelDT.value}
         contents.add(spinDT)

         contents.add(JLabel("  Повтор:"))
         contents.add(spinNN)

         contents.add(JLabel("  Задержка:"))
         contents.add(spinTS)

         contents.add(boxesPanel, BorderLayout.SOUTH)
         contents.add(startButton)
         startButton.addActionListener { startTests(testCases) }
         contents.add(stopButton)
         stopButton.addActionListener { exitProcess(0) }
        // Определение размера и открытие окна
        // setLocationRelativeTo(null)
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
                frame.setSize(550, 300)
                frame.isVisible = true
            }
          //  StartDialog().startDialog("Pass")
        }

