package com.gov.sidesa.ui.letter.input.models.divider

import com.gov.sidesa.domain.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class DividerWidgetUiModel(
    override val name: String = WidgetType.Divider.type
) : BaseLetterInputModel(type = WidgetType.Divider, name = name, value = WidgetType.Divider.type) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }
}
