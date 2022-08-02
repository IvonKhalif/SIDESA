package com.gov.sidesa.utils.extension

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/
 
fun Long?.orZero() = this ?: 0L
fun Long?.toEmptyString() = if (this.isNullOrZero()) "" else this.toString()
fun Int?.orZero() = this ?: 0