package com.gov.sidesa.utils.extension

import java.util.regex.Pattern

fun String.isEmailPattern(): Boolean {
    val emailRegex = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]+" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                ")+"
    )
    return emailRegex.matcher(this).matches()
}

fun String?.orZero(): Long {
    val numb = this ?: "0"
    return try {
        numb.toLong()
    } catch (e: Exception) {
        0L
    }
}

fun String?.orNull(): Long? {
    val numb = this ?: return null
    return try {
        numb.toLong()
    } catch (e: Exception) {
        0L
    }
}