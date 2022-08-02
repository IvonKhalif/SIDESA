package com.gov.sidesa.utils.extension

fun Long?.isNullOrZero() = this == null || this == 0L

fun Long?.isNotNullOrZero() = !isNullOrZero()