package ru.cs.tdm.ui

import ru.cs.tdm.cases.*
import ru.cs.tdm.code.Runner

fun startTests() {
    println("startTests arguments: ${TestsProperties.testCases.joinToString()}")
    if (TestsProperties.debugPrintNomber > 1) println("Открытие страницы ${TestsProperties.loginpage}")
    if (TestsProperties.debugPrintNomber > 1) println("login= ${TestsProperties.login}   password= ${TestsProperties.password}")

    repeat(TestsProperties.repeateCasesNomber) {
        for (test in TestsProperties.testCases) {
            if (TestsProperties.debugPrintNomber > 1) println("-------------- старт $test Повтор $it ------------")
            when (test) {
                "Pass" -> {
                    Runner().runTest(ChangePass::class.java)
                }

                "Head" -> {
                    Runner().runTest(HeadRef::class.java)
                }

                "User" -> {
                    Runner().runTest(AdminUser::class.java)
                }

                "Filter" -> {
                    Runner().runTest(Filter::class.java)
                }


                "ALL" -> {
                    Runner().runTest(ChangePass::class.java)
                    Runner().runTest(HeadRef::class.java)
                    Runner().runTest(AdminUser::class.java)
                    Runner().runTest(Filter::class.java)
                }

                else -> {
                    println("Test $test! Unknowns")
                }
            }
            if (TestsProperties.debugPrintNomber > 1) println("-------------- стоп $test Повтор $it  --------------")
        }
    }
}

