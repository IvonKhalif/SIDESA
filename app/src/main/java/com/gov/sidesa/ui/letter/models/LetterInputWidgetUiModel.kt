package com.gov.sidesa.ui.letter.models

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class LetterInputWidgetUiModel(
    val type: String,
    val name: String,
    val inputType: String,
    val placeholder: String?,
    val api: String?,
    val apiType: String?,
    val apiParam: String?
)
