package com.kotlin.java.kotlin.extend

val String.lastChar: Char
    get() = this.get(length - 1)