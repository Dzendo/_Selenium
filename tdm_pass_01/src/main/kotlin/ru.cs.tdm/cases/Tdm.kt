package ru.cs.tdm.cases

import org.junit.jupiter.api.RepetitionInfo
import ru.cs.tdm.examples.JetBrainsTest

fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")
    var testCase = "Pass"
    if (args.isNotEmpty()) testCase = args[0]
        println("Test $testCase!")
    when(testCase) {
        "Head" -> {
            val head = HeadRef()
            val headMenuTest =head.HeadMenuTest()
            val toolsTest = head.ToolTest()
            val subSysadminTest = head.SubSysadminTest()
            val cetdTest = subSysadminTest.CETDTest()

            repeat(3) {
                println("==================================  Head  ${it + 1} ======================================= ")
                HeadRef.beforeAll()

                println("########################## HeadMenuTest ${it + 1} ##################################### ")
               // head.beforeEach()
                headMenuTest.beforeEach()
                headMenuTest.mainMenuTest()
                headMenuTest.afterEach()
               // head.afterEach()

               // head.beforeEach()
                headMenuTest.beforeEach()
                headMenuTest.workTableTest()
                headMenuTest.afterEach()
               // head.afterEach()

               // head.beforeEach()
                headMenuTest.beforeEach()
                headMenuTest.objectsTest()
                headMenuTest.afterEach()
               // head.afterEach()

                // head.beforeEach()
                headMenuTest.beforeEach()
                headMenuTest.mailTest()
                headMenuTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                headMenuTest.beforeEach()
               // headMenuTest.meetingTest()  // нет на 2012 и на 10
                headMenuTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                headMenuTest.beforeEach()
               // val repetitionInfo = RepetitionInfo
                headMenuTest.ganttchartTest()
                headMenuTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                headMenuTest.beforeEach()
                headMenuTest.helpTest()
                headMenuTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                headMenuTest.beforeEach()
                headMenuTest.searchTest()
                headMenuTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                headMenuTest.beforeEach()
                headMenuTest.notificationTest()
                headMenuTest.afterEach()
                // head.afterEach()

                println("########################## ToolTest ${it + 1} ##################################### ")

                // head.beforeEach()
                toolsTest.beforeEach()
                toolsTest.open_showTreeTest()
                toolsTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                toolsTest.beforeEach()
                toolsTest.open_showPreviewPanelTest()
                toolsTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                toolsTest.beforeEach()
                toolsTest.filterTest()
                toolsTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                toolsTest.beforeEach()
                toolsTest.renewTest()
                toolsTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                toolsTest.beforeEach()
                toolsTest.adminUserTest()
                toolsTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                //toolsTest.beforeEach()
                // Нарвался - открывается окно Windows не имеющее отношения к HTML
                //assertTrue(tools.titleWait("window", "Редактирование групп"))
                //toolsTest.importUserTest()
                //toolsTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                toolsTest.beforeEach()
                toolsTest.createObjectTest()
                toolsTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                toolsTest.beforeEach()
                toolsTest.configuringNotificationTest()
                toolsTest.afterEach()
                // head.afterEach()

                println("########################## SubSysadminTest ${it + 1} ##################################### ")

                // head.beforeEach()
                subSysadminTest.beforeEach()
                subSysadminTest.systemParametersTest()
                subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                subSysadminTest.beforeEach()
                subSysadminTest.sysAttributesTest()
                subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                subSysadminTest.beforeEach()
                subSysadminTest.dataTreeTest()
                subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                subSysadminTest.beforeEach()
                subSysadminTest.eventsLogTest()
                subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                subSysadminTest.beforeEach()
                subSysadminTest.serverLogTest()
                subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                subSysadminTest.beforeEach()
                subSysadminTest.delObjectsTest()
                subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                subSysadminTest.beforeEach()
                subSysadminTest.delObjectsTest()
                subSysadminTest.afterEach()
                // head.afterEach()

                println("########################## CETDTest ${it + 1} ##################################### ")

                // head.beforeEach()
                // subSysadminTest.beforeEach()
                cetdTest.beforeEach()
                cetdTest.flow_2Test()
                cetdTest.afterEach()
                // subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                // subSysadminTest.beforeEach()
                cetdTest.beforeEach()
                cetdTest.flow_1Test()
                cetdTest.afterEach()
                // subSysadminTest.afterEach()
                // head.afterEach()

                // head.beforeEach()
                // subSysadminTest.beforeEach()
                cetdTest.beforeEach()
                cetdTest.flowTest()
                cetdTest.afterEach()
                // subSysadminTest.afterEach()
                // head.afterEach()
                // head.beforeEach()

                // subSysadminTest.beforeEach()
                cetdTest.beforeEach()
                cetdTest.flow0Test()
                cetdTest.afterEach()
                // subSysadminTest.afterEach()
                // head.afterEach()


                // subSysadminTest.beforeEach()
                cetdTest.beforeEach()
                cetdTest.flow1Test()
                cetdTest.afterEach()
                // subSysadminTest.afterEach()
                // head.afterEach()

                // subSysadminTest.beforeEach()
                cetdTest.beforeEach()
                cetdTest.flow2Test()
                cetdTest.afterEach()
                // subSysadminTest.afterEach()
                // head.afterEach()

                // subSysadminTest.beforeEach()
                cetdTest.beforeEach()
                cetdTest.flow3Test()
                cetdTest.afterEach()
                // subSysadminTest.afterEach()
                // head.afterEach()

                HeadRef.afterAll()
                println("=================================Конец Head ${it + 1} ======================================= ")

            }
        }
        "User" -> {}
        "Pass" -> {
            val pass = Pass()

            repeat(2) {
                println("==================================== Pass ${it+1} ======================================= ")
                Pass.beforeAll()
                pass.n01_CreateUserPass()
                pass.n02_enterUserPass()
                pass.n03_changeUserPass()
                pass.n09_deleteUserPass()
                Pass.afterAll()
            }

        }
        "Filter" -> {}
        "Example" -> {
            val jetBrainsTest = JetBrainsTest()
            if (args.isNotEmpty()) println("Test ${args[0]}!")

            jetBrainsTest.setUp()
            jetBrainsTest.search()
            jetBrainsTest.tearDown()
            repeat(1) {
                println("========================================== ${it+1} ======================================= ")
                jetBrainsTest.setUp()
                jetBrainsTest.toolsMenu()
                jetBrainsTest.tearDown()
            }
            repeat(1) {
                jetBrainsTest.setUp()
                println("========================================== ${it+1} ======================================= ")
                jetBrainsTest.navigationToAllTools()
                jetBrainsTest.tearDown()
            }
        }
        else -> {}
    }

    println("Good Bye $testCase  errors 0")
}