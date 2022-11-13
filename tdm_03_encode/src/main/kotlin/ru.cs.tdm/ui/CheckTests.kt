package ru.cs.tdm.ui

enum class CheckTests(var check: Boolean) {
    Pass(false),
    Head(false),
    User(false),
    Filter(false)
}
fun checkTest(name: String) {
    val checkTests = CheckTests.Pass
    CheckTests.Pass.check = true
    println (CheckTests.Pass.name)
    println (checkTests.check)
}

fun main() {
    checkTest("Head")
}