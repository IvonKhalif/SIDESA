package com.gov.sidesa.domain.letter.input.models.layout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Widget(
    // base
    val name: String = "",
    val type: String = "",
    val value: String = "",
    val enable: Boolean = true,

    // common widget
    val title: String = "",

    // edit text
    val inputType: String? = null,

    // dropdown field
    val selectedText: String = "",
    val api: String? = null,
    val apiType: String? = null,
    val apiParam: String? = null
): Parcelable