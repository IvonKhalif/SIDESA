package com.gov.sidesa.ui.letter.input.models.text_view

import com.gov.sidesa.ui.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class TextViewWidgetUiModel(
    override val name: String,
    val title: String,
    val value: String
) : BaseLetterInputModel(type = WidgetType.TextView, name = name) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseLetterInputModel): Boolean {
        val textView = newItem as? TextViewWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = textView)
                && name == textView.name
                && title == textView.title
                && value == textView.value
    }
}
