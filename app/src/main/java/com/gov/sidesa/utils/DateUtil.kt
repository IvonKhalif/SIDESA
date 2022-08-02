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
    const val LOCAL_DATE_PATTERN = "yyyy-MM-dd"
    val locale = Locale("id", "ID")
    fun formatStringToDateTime(
        date: String?,
        pattern: String = LOCAL_DATE_TIME_PATTERN,
        def: LocalDateTime? = LocalDateTime.now()
    ): LocalDateTime? {
        if (date == null) {
            return def
        }
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.parse(date, formatter)
    }

    fun formatStringToLocalDate(
        date: String?,
        pattern: String = LOCAL_DATE_PATTERN,
        def: LocalDate? = LocalDate.now()
    ): LocalDate? {
        if (date == null) {
            return def
        }
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDate.parse(date, formatter)
    }

    fun expiredDateLeft(date: String): Long {
        val currentDate = LocalDate.now()
        val expiredDate = formatStringToLocalDate(date)

        return ChronoUnit.DAYS.between(currentDate, expiredDate)
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

    fun convertToDayAndDate(
        baseDate: String,
        pattern: String = LOCAL_DATE_TIME_PATTERN
    ): String {
        val localDateTime =
            if (baseDate.isBlank())
                LocalDateTime.now()
            else
                formatStringToDateTime(baseDate, pattern) ?: LocalDateTime.now()

        val date = convertLocalDateTimeToLocalDate(localDateTime)
//        val dateFormat = if (isHistoryDetail) {
//            val formatter = formatLocalDateTimeToString(localDateTime)
//            convertPattern(formatter, DateUtil.LOCAL_DATE_TIME_PATTERN, "dd MMM yyyy HH:mm")
//        } else {
        return formatLocalDateToString(date, "EEEE, dd MMMM yyyy")
//        }
    }

    fun convertStringDate(
        baseDate: String,
        toPattern: String = LOCAL_DATE_TIME_PATTERN
    ): String {
        val localDate =
            if (baseDate.isBlank())
                LocalDate.now()
            else
                formatStringToLocalDate(baseDate) ?: LocalDate.now()

        return formatLocalDateToString(localDate, toPattern)
    }
}