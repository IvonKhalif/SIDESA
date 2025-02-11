package com.gov.sidesa.domain.letter.input.models.layout

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

sealed class WidgetType(val type: String) {
    object EditText: WidgetType("edit_text")
    object TextView: WidgetType("text_view")
    object DropDown: WidgetType("drop_down")
    object DatePicker: WidgetType("datepicker")
    object Header: WidgetType("header") // internal android
    object Divider: WidgetType("divider")
    object Attachment: WidgetType("attachment")
}
