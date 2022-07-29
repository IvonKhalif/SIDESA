package com.gov.sidesa.ui.letter.input.models.mapper

import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.input.models.layout.asInputType
import com.gov.sidesa.ui.letter.input.models.divider.DividerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.header.HeaderWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.text_view.TextViewWidgetUiModel

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/

object WidgetUiModelMapper {

    fun asUiModel(widget: Widget) = when(widget.type) {
        WidgetType.EditText.type -> createEditText(widget = widget)
        WidgetType.TextView.type -> createTextView(widget = widget)
        WidgetType.DropDown.type -> createDropDown(widget = widget)
        WidgetType.Header.type -> createHeader(widget = widget)
        else -> createDivider()
    }

    private fun createEditText(widget: Widget) = EditTextWidgetUiModel(
        name = widget.name,
        inputType = widget.inputType.asInputType(),
        title = widget.title,
        value = widget.value
    )

    private fun createTextView(widget: Widget) = TextViewWidgetUiModel(
        name = widget.name,
        title = widget.title,
        value = widget.value
    )

    private fun createDropDown(widget: Widget) = DropDownWidgetUiModel(
        name = widget.name,
        value = widget.value,
        selectedText = null,
        inputType = widget.inputType.asInputType(),
        title = widget.title,
        api = widget.api.orEmpty(),
        apiType = widget.apiType,
        apiParam = widget.apiParam
    )

    private fun createHeader(widget: Widget) = HeaderWidgetUiModel(
        title = widget.title
    )

    private fun createDivider() = DividerWidgetUiModel()
}

fun LetterLayout.asUiModel() = widgets.map {
    WidgetUiModelMapper.asUiModel(widget = it)
}