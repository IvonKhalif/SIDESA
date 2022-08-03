package com.gov.sidesa.domain.letter.input.models.layout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Widget(
    val name: String = "",
    val type: String = "",
    val inputType: String? = null,
    val title: String = "",
    val value: String = "",
    val selectedText: String = "",
    val api: String? = null,
    val apiType: String? = null,
    val apiParam: String? = null
): Parcelable