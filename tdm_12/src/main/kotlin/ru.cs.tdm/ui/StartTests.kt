package ru.cs.tdm.ui

import ru.cs.tdm.cases.*
import ru.cs.tdm.code.Runner

fun startTests() :Long {
    println("startTests arguments: ${TestsProperties.testCases.joinToString()}")
    if (TestsProperties.debugPrintNomber > 1) println("Повторов ${TestsProperties.repeateCasesNomber} Задержка ${TestsProperties.threadSleepNomber} Печать ${TestsProperties.debugPrintNomber}")
    if (TestsProperties.debugPrintNomber > 1) println("Открытие страницы ${TestsProperties.loginpage}")
    if (TestsProperties.debugPrintNomber > 1) println("login= ${TestsProperties.login}   password= ${TestsProperties.password}")

    var allSumErrors : Long = 0L
    repeat(TestsProperties.repeateCasesNomber) {
        var repeateSumErrors : Long = 0L
        for (test in TestsProperties.testCases) {
            var caseErrors : Long = 0L
            if (TestsProperties.debugPrintNomber > 7) println("-------------- старт $test Повтор $it ------------")
            when (test) {
                "Pass" -> {
                    caseErrors += Runner(it).runTest(ChangePass::class.java)
                }

                "Head" -> {
                    caseErrors += Runner(it).runTest(HeadRef::class.java)
                }

                "User" -> {
                    caseErrors += Runner(it).runTest(AdminUser::class.java)
                }

                "Filter" -> {
                    caseErrors += Runner(it).runTest(Filter::class.java)
                }


                "ALL" -> {
                    caseErrors += Runner(it).runTest(ChangePass::class.java)
                    caseErrors += Runner(it).runTest(HeadRef::class.java)
                    caseErrors += Runner(it).runTest(AdminUser::class.java)
                    caseErrors += Runner(it).runTest(Filter::class.java)
                }

                else -> {
                    println("Test $test! Unknowns")
                }
            }
            if (TestsProperties.debugPrintNomber > 7) println("-------------- стоп $test Повтор $it  --------------")
            repeateSumErrors += caseErrors
        }
        if (repeateSumErrors > 0)  println("@@@@@ repeateSumErrors = $repeateSumErrors  @@@")
        allSumErrors += repeateSumErrors
    }
    if (allSumErrors > 0)  println("@@@@@ allSumErrors = $allSumErrors  @@@")
    return allSumErrors
}

