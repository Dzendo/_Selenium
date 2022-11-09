package ru.cs.tdm

import ru.cs.tdm.code.printDual
import ru.cs.tdm.ui.StartDialog

/**
 * ���� �������� � ����������, �� ����� �� ����������
 * ���� �������� ��� ���������, �� ������ ������
 */
fun main(args: Array<String>) {
    if (printDual()) println("Output: double to the console and to the file out+err")
    var testCase = "ALL"
    println("Program arguments: ${args.joinToString()}")
    if (args.isNotEmpty()) testCase = args[0]
        println("Tests $testCase!")
    StartDialog().startDialog(testCase)

    println("Good Bye $testCase")
}