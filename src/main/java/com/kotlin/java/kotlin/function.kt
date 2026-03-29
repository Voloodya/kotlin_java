package com.kotlin.java.kotlin

fun greetUser() {
    val name = readln()
    println("Hello ${name.ifBlank { "World" }}!")
}
