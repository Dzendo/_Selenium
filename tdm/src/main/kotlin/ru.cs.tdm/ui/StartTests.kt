package ru.cs.tdm.ui

import ru.cs.tdm.cases.*
import ru.cs.tdm.code.Runner

fun startTests() {
    println("startTests arguments: ${TestsProperties.testCases.joinToString()}")
    if (TestsProperties.debugPrintNomber > 1) println("Повторов ${TestsProperties.repeateCasesNomber} Задержка ${TestsProperties.threadSleepNomber} Печать ${TestsProperties.debugPrintNomber}")
    if (TestsProperties.debugPrintNomber > 1) println("Открытие страницы ${TestsProperties.loginpage}")
    if (TestsProperties.debugPrintNomber > 1) println("login= ${TestsProperties.login}   password= ${TestsProperties.password}")

    repeat(TestsProperties.repeateCasesNomber) {
        for (test in TestsProperties.testCases) {
            if (TestsProperties.debugPrintNomber > 7) println("-------------- старт $test Повтор $it ------------")
            when (test) {
                "Pass" -> {
                    Runner(it).runTest(ChangePass::class.java)
                }

                "Head" -> {
                    Runner(it).runTest(HeadRef::class.java)
                }

                "User" -> {
                    Runner(it).runTest(AdminUser::class.java)
                }

                "Filter" -> {
                    Runner(it).runTest(Filter::class.java)
                }


                "ALL" -> {
                    Runner(it).runTest(ChangePass::class.java)
                    Runner(it).runTest(HeadRef::class.java)
                    Runner(it).runTest(AdminUser::class.java)
                    Runner(it).runTest(Filter::class.java)
                }

                else -> {
                    println("Test $test! Unknowns")
                }
            }
            if (TestsProperties.debugPrintNomber > 7) println("-------------- стоп $test Повтор $it  --------------")
        }
    }
}

