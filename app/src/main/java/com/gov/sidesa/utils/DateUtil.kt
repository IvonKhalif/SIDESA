package com.gov.sidesa.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    val locale = Locale("id", "ID")
    fun formatStringToDateTime(
        date: String?,
        pattern: String = LOCAL_DATE_TIME_PATTERN,
        def: LocalDateTime? = null
    ): LocalDateTime? {
        if (date == null) {
            return def
        }
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.parse(date, formatter)
    }

    fun expiredDateLeft(date: LocalDateTime): Long {
        val date1 = convertLocalDateTimeToLocalDate(LocalDateTime.now())
        val date2 = convertLocalDateTimeToLocalDate(date)

        return ChronoUnit.DAYS.between(date2, date1)
    }

    fun convertLocalDateTimeToLocalDate(dateTime: LocalDateTime): LocalDate {
        val dateTimeFormatter =
            DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN, locale)

        val dueDateFormatted = dateTime.format(dateTimeFormatter)

        return LocalDate.parse(dueDateFormatted, dateTimeFormatter)
    }

    fun formatLocalDateTimeToString(date: LocalDateTime): String {
        val dateTimeFormatter =
            DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN, Locale("id", "ID"))
        return date.format(dateTimeFormatter)
    }

    fun formatLocalDateToString(date: LocalDate, pattern: String): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }

    fun convertPattern(date: String, pattern1: String, pattern2: String): String {
        val inputFormat: DateFormat = SimpleDateFormat(pattern1)
        val outputFormat: DateFormat = SimpleDateFormat(pattern2)
        val dateInput = inputFormat.parse(date)
        return outputFormat.format(dateInput)

    }

    fun format(date: Date, pattern: String = "dd-MM-yyyy"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(date)
    }

    fun convertToDayAndDate(baseDate: String): String {
        val localDateTime =
            if (baseDate.isBlank())
                LocalDateTime.now()
            else
                formatStringToDateTime(baseDate) ?: LocalDateTime.now()

        val date = convertLocalDateTimeToLocalDate(localDateTime)
//        val dateFormat = if (isHistoryDetail) {
//            val formatter = formatLocalDateTimeToString(localDateTime)
//            convertPattern(formatter, DateUtil.LOCAL_DATE_TIME_PATTERN, "dd MMM yyyy HH:mm")
//        } else {
        return formatLocalDateToString(date, "EEEE, dd MMMM yyyy")
//        }
    }
}