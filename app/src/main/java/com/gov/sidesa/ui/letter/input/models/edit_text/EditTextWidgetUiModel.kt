package com.gov.sidesa.ui.letter.input.models.edit_text

import com.gov.sidesa.ui.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class EditTextWidgetUiModel(
    override val name: String,
    val inputType: String,
    val title: String?,
) : BaseLetterInputModel(type = WidgetType.EditText, name = name) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseLetterInputModel): Boolean {
        val editText = newItem as? EditTextWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = editText)
                && name == editText.name
                && inputType == editText.inputType
                && title == editText.title
    }
}
