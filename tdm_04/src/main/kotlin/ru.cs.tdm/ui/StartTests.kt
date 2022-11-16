package ru.cs.tdm.ui

import ru.cs.tdm.cases.AdminUser
import ru.cs.tdm.cases.Filter
import ru.cs.tdm.cases.HeadRef
import ru.cs.tdm.cases.Pass
import ru.cs.tdm.code.Runner
import ru.cs.tdm.examples.JetBrainsTest
import ru.cs.tdm.ui.TestsProperties.repeateCasesNomber
import ru.cs.tdm.ui.TestsProperties.testCases

fun startTests() {
    println("startTests arguments: ${testCases.joinToString()}")
    for (test in testCases)
        repeat(repeateCasesNomber) {
            when (test) {
                "Pass" -> {
                    Runner().runTest(Pass::class.java)
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

                "Example" -> {
                    Runner().runTest(JetBrainsTest::class.java)
                }

                "ALL" -> {
                    Runner().runTest(Pass::class.java)
                    Runner().runTest(HeadRef::class.java)
                    Runner().runTest(AdminUser::class.java)
                    Runner().runTest(Filter::class.java)
                }

                else -> {
                    println("Test $test! Unknowns")
                }
            }
        }
}

