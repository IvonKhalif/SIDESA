package com.gov.sidesa.ui.letter.input.models.mapper

import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.input.models.layout.asInputType
import com.gov.sidesa.ui.letter.input.models.attachment.AttachmentWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.base.InitialState
import com.gov.sidesa.ui.letter.input.models.date_picker.DatePickerWidgetUiModel
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
        WidgetType.DatePicker.type -> createDatePicker(widget = widget)
        WidgetType.Attachment.type -> createAttachment(widget = widget)
        else -> createDivider()
    }

    private fun createEditText(widget: Widget) = EditTextWidgetUiModel(
        name = widget.name,
        inputType = widget.inputType.asInputType(),
        title = widget.title,
        value = widget.value,
        initialState = InitialState(enable = widget.enable)
    )

    private fun createTextView(widget: Widget) = TextViewWidgetUiModel(
        name = widget.name,
        title = widget.title,
        value = widget.value
    )

    private fun createDropDown(widget: Widget) = DropDownWidgetUiModel(
        name = widget.name,
        value = widget.value,
        initialState = InitialState(enable = widget.enable),
        selectedText = widget.selectedText,
        inputType = widget.inputType.asInputType(),
        title = widget.title,
        api = widget.api.orEmpty(),
        apiType = widget.apiType,
        apiParam = widget.apiParam
    )

    private fun createDatePicker(widget: Widget) = DatePickerWidgetUiModel(
        name = widget.name,
        value = widget.value,
        initialState = InitialState(enable = widget.enable),
        title = widget.title,
    )

    private fun createHeader(widget: Widget) = HeaderWidgetUiModel(
        title = widget.title
    )

    private fun createDivider() = DividerWidgetUiModel()

    private fun createAttachment(widget: Widget) = AttachmentWidgetUiModel(
        name = widget.name,
        value = null,
        initialState = InitialState(enable = true),
        title = widget.title,
        files = emptyList(),
        limit = widget.attachmentMax,
        fileType = widget.attachmentFileType.split(";")
    )
}

fun LetterLayout.asUiModel() = widgets.map {
    WidgetUiModelMapper.asUiModel(widget = it)
}