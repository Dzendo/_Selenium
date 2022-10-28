
fun main(args: Array<String>) {
    val jetBrainsTest = JetBrainsTest()
    println("Hello World!")

    jetBrainsTest.setUp()
    jetBrainsTest.search()
    jetBrainsTest.tearDown()
    jetBrainsTest.setUp()
    jetBrainsTest.toolsMenu()
    jetBrainsTest.tearDown()
    jetBrainsTest.setUp()
    jetBrainsTest.navigationToAllTools()
    jetBrainsTest.tearDown()

    println("Good bye World!")
}
