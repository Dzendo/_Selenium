package ru.cs.tdm.ui

import ru.cs.tdm.cases.*
import ru.cs.tdm.code.Runner
import ru.cs.tdm.data.TestsProperties
import java.time.LocalDateTime
import javax.swing.SwingWorker
/**
 * Computes a result, or throws an exception if unable to do so.
 * Вычисляет результат или выдает исключение, если это невозможно сделать.
 *
 *
 * Note that this method is executed only once.
 * Обратите внимание, что этот метод выполняется только один раз.
 *
 *
 * Note: this method is executed in a background thread.
 * Примечание: этот метод выполняется в фоновом потоке.
 *
 * @return the computed result
 * @throws Exception if unable to compute a result
 */
class StartTests(val startDialog: StartDialog? = null) : SwingWorker<Long, Int>() {


    override fun doInBackground(): Long {
        println("startTests ${LocalDateTime.now().withNano(0)} arguments: ${TestsProperties.testCases.joinToString()}")
        if (TestsProperties.debugPrintNomber > 1) println("Повторов ${TestsProperties.repeateCasesNomber} Задержка ${TestsProperties.threadSleepNomber} Печать ${TestsProperties.debugPrintNomber}")
        if (TestsProperties.debugPrintNomber > 1) println("Открытие страницы ${TestsProperties.loginpage}")
        if (TestsProperties.debugPrintNomber > 1) println("Браузер = ${TestsProperties.browserIndex} login= ${TestsProperties.login}   password= ${TestsProperties.password}")

        var allSumErrors: Long = 0L
        repeat(TestsProperties.repeateCasesNomber) {
            var repeateSumErrors: Long = 0L
            for (test in TestsProperties.testCases) {
                var caseErrors: Long = 0L
                if (TestsProperties.debugPrintNomber > 7) println("-------------- старт $test Повтор $it ------------")
                startDialog?.showActionCase(it, test)

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
            if (repeateSumErrors > 0) println("@@@@@ $it repeateSumErrors = $repeateSumErrors  @@@")
            allSumErrors += repeateSumErrors
        }
        if (allSumErrors > 0) println("@@@@@ allSumErrors = $allSumErrors  @@@")
        return allSumErrors
    }  // end doInBackground()
}

