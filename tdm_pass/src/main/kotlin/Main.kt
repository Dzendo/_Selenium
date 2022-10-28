import ru.cs.tdm.examples.JetBrainsTest

fun main(args: Array<String>) {

    println("Program arguments: ${args.joinToString()}")
    val jetBrainsTest = JetBrainsTest()
    println("Hello main World!")

    jetBrainsTest.setUp()
    jetBrainsTest.search()
    jetBrainsTest.tearDown()
    jetBrainsTest.setUp()
    jetBrainsTest.toolsMenu()
    jetBrainsTest.tearDown()
    jetBrainsTest.setUp()
    jetBrainsTest.navigationToAllTools()
    jetBrainsTest.tearDown()

    println("Good bye main World!")
}