package com.gov.sidesa.ui.letter.input.models.edit_text

import com.gov.sidesa.domain.letter.input.models.layout.InputType
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class EditTextWidgetUiModel(
    override val name: String,
    val inputType: InputType,
    val title: String?,
    override val value: String = ""
) : BaseWidgetUiModel(type = WidgetType.EditText, name = name, value = value) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseWidgetUiModel): Boolean {
        val editText = newItem as? EditTextWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = editText)
                && name == editText.name
                && inputType == editText.inputType
                && title == editText.title
    }
}
