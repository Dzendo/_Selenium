package examples

fun main(args: Array<String>) {

    println("Program arguments: ${args.joinToString()}")
    val jetBrainsTest = JetBrainsTest()
    if (args.isNotEmpty()) println("Test ${args[0]}!")

    jetBrainsTest.setUp()
    jetBrainsTest.search()
    jetBrainsTest.tearDown()
    repeat(3) {
        jetBrainsTest.setUp()
        jetBrainsTest.toolsMenu()
        jetBrainsTest.tearDown()
    }
    repeat(3) {
        jetBrainsTest.setUp()
        jetBrainsTest.navigationToAllTools()
        jetBrainsTest.tearDown()
    }

    println("Good bye  World!")
}