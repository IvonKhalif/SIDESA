package com.gov.sidesa.ui.letter.input.models.header

import com.gov.sidesa.ui.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class HeaderWidgetUiModel(
    val title: String
) : BaseLetterInputModel(type = WidgetType.Header, name = "header") {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseLetterInputModel): Boolean {
        val header = newItem as? HeaderWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = header)
                && title == header.title
    }
}
