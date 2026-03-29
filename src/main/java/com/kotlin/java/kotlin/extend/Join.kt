package com.kotlin.java.kotlin.extend

@JvmOverloads
fun <T> Collection<T>.joinToString(separator: String = " ",
                                   prefix: String = "[",
                                   postfix: String = "]"): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    return result.append(postfix).toString()
}

@JvmOverloads
fun Collection<String>.joinToStr(separator: String = " ",
                                        prefix: String = "[",
                                        postfix: String = "]"): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    return result.append(postfix).toString()
}