
fun main() {
    val jetBrainsTest0 = JetBrainsTest0()
    println("Hello World!")

    jetBrainsTest0.setUp()
    jetBrainsTest0.search()
    jetBrainsTest0.tearDown()
    jetBrainsTest0.setUp()
    jetBrainsTest0.toolsMenu()
    jetBrainsTest0.tearDown()
    jetBrainsTest0.setUp()
    jetBrainsTest0.navigationToAllTools()
    jetBrainsTest0.tearDown()

    println("Good bye World!")
}
