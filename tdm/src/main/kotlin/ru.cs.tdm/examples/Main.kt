package ru.cs.tdm.examples

fun main(args: Array<String>) {
    println("Hello TDM365 example 3")
    println("Hello World!")
    println("Hello World!")
    println("TDM 365 ")
    println( "window-1156_header-targetEl".split('-','_'))
    val aaa = "window-1156_header-targetEl".split('-','_')
    println( aaa[2])

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}