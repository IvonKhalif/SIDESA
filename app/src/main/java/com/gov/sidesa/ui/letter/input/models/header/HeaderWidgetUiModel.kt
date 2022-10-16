package com.gov.sidesa.ui.letter.input.models.header

import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class HeaderWidgetUiModel(
    override val title: String,
) : BaseWidgetUiModel(type = WidgetType.Header, name = "header", value = "header", title = title) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseWidgetUiModel): Boolean {
        val header = newItem as? HeaderWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = header)
                && title == header.title
    }
}
