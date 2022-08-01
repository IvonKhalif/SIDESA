package com.gov.sidesa.utils.serialize

import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.gov.sidesa.utils.extension.format
import java.util.*


/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

val dateSerializer: JsonSerializer<Date> = JsonSerializer<Date> { src, _, _ ->
    val formatted = src.format("yyyy-MM-dd")
    JsonPrimitive(formatted)
}