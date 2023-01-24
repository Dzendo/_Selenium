package ru.cs.tdm

import ru.cs.tdm.code.printDual
import ru.cs.tdm.ui.StartDialog
import ru.cs.tdm.ui.TestsProperties
import ru.cs.tdm.ui.startTests
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * Если стартуют с параметром, то сразу на выполнение
 * Если стартуют без параметра, то окошко выбора Параметров
 */
fun main(args: Array<String>) {
    if (printDual()) println("Output: double to the console and to the file out+err")
    println("Program arguments: ${args.joinToString()}")
    // метод помещает приложение в очередь событий Swing
    if (args.isEmpty()) SwingUtilities.invokeLater {      // этот поток называется EDT (поток диспетчеризации событий).
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = StartDialog()
        frame.startDialog()
        frame.pack()
        frame.setLocationRelativeTo(null)  // Эта строка используется для центрирования окна на экране.
        frame.setSize(540, 240)
        frame.isVisible = true
    }
    else {
        TestsProperties.testCases.clear()
        TestsProperties.testCases.addAll(args.toSet())
        startTests()
    }
}