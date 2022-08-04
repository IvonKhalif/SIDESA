package com.gov.sidesa.utils.extension

import com.gov.sidesa.utils.constants.Constants
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

fun Date.format(pattern: String): String {
    val format = SimpleDateFormat(pattern, Constants.LOCALE)
    return format.format(this)
}

fun Date.formatFE(): String {
    return format(Constants.DATE_FE_FORMAT)
}

fun Date.formatBE(): String {
    return format(Constants.DATE_BE_FORMAT)
}

fun String.toDate(pattern: String = Constants.DATE_BE_FORMAT): Date? {
    val format = SimpleDateFormat(pattern, Constants.LOCALE)

    return try {
        format.parse(this)
    } catch (_: Throwable) {
        null
    }
}

fun Date?.orToday() = this ?: Date()

fun Date.utcToLocale(): Long {
    val timezone = TimeZone.getDefault()
    return timezone.getOffset(time) + time
}