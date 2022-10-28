import ru.cs.tdm.cases.Pass

fun main(args: Array<String>) {
    println("Hello Passord!")

    val pass = Pass()

    repeat(3) {
        println("========================================== ${it+1} ======================================= ")
        Pass.beforeAll()
    pass.n01_CreateUserPass()
    pass.n02_enterUserPass()
    pass.n03_changeUserPass()
    pass.n09_deleteUserPass()
        Pass.afterAll()
    }
    println("Good Bye Passord!")
}