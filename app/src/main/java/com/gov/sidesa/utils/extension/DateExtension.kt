package com.gov.sidesa.utils.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

fun Date.format(pattern: String = "dd-MM-yyyy"): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this)
}