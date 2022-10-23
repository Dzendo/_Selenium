import ru.cs.tdm.cases.Pass

fun main(args: Array<String>) {
    println("Hello World! 2")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    val pass = Pass()

    repeat(5) {
        println("========================================== ${it+1} ======================================= ")
        Pass.beforeAll()
    pass.n01_CreateUserPass()
    pass.n02_enterUserPass()
    pass.n03_changeUserPass()
    pass.n09_deleteUserPass()
        Pass.afterAll()
    }
}