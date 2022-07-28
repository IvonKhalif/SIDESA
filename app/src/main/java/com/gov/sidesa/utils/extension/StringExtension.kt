package com.gov.sidesa.utils.extension

import android.text.Editable
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