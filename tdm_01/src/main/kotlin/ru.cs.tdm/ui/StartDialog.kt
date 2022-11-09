package ru.cs.tdm.ui

import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.system.exitProcess

class StartDialog : JFrame("TDM365 Tests ") {
     fun startDialog(testCase: String) {
         println("стартовало окно с $testCase")
        // Выход из программы при закрытии
        defaultCloseOperation = EXIT_ON_CLOSE
        // Кнопки для создания диалоговых окон
        val button1 = JButton("START")
        button1.addActionListener {
            startTests(testCase)
            //val dialog = createDialog("Non-modal", false)
            //dialog.isVisible = true
        }
        val button2 = JButton("STOP")
        button2.addActionListener {
            exitProcess(0)
            //val dialog = createDialog("Modal", true)
            //dialog.isVisible = true
        }
        // Создание панели содержимого с размещением кнопок
        val contents = JPanel()
        contents.add(button1)
        contents.add(button2)
        contentPane = contents
        // Определение размера и открытие окна
         setLocationRelativeTo(null)
        //setLocation(300, 200)
        setSize(550, 300)
        isVisible = true
         println("закрылось окно с $testCase")
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
            StartDialog().startDialog("Pass")
        }

