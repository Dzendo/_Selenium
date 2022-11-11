package ru.cs.tdm

import ru.cs.tdm.code.printDual
import ru.cs.tdm.ui.StartDialog
import ru.cs.tdm.ui.startTests
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * ���� �������� � ����������, �� ����� �� ����������
 * ���� �������� ��� ���������, �� ������ ������
 */
fun main(args: Array<String>) {
    if (printDual()) println("Output: double to the console and to the file out+err")
    println("Program arguments: ${args.joinToString()}")
    if (args.isEmpty()) SwingUtilities.invokeLater {      // ���� ����� ���������� EDT (����� ��������������� �������).
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = StartDialog()
        frame.startDialog()
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.setSize(550, 300)
        frame.isVisible = true
    }
    else startTests(args.toSet())
}