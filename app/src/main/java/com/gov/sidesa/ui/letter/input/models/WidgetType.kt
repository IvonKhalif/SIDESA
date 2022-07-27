package com.gov.sidesa.ui.letter.input.models

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

sealed class WidgetType(val type: String) {
    object EditText: WidgetType("edit_text")
    object TextView: WidgetType("text_view")
    object DropDown: WidgetType("drop_down")
    object Header: WidgetType("header")
    object Divider: WidgetType("divider")
}
