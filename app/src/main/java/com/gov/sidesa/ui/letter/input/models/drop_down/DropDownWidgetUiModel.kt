package com.gov.sidesa.ui.letter.input.models.drop_down

import com.gov.sidesa.domain.letter.input.models.InputType
import com.gov.sidesa.domain.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class DropDownWidgetUiModel(
    override val name: String,
    override val value: String?,
    val selectedText: String?,
    val inputType: InputType,
    val title: String?,
    val api: String,
    val apiType: String?,
    val apiParam: String?
) : BaseLetterInputModel(type = WidgetType.DropDown, name = name, value = value) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseLetterInputModel): Boolean {
        val dropDown = newItem as? DropDownWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = dropDown)
                && inputType == dropDown.inputType
                && title == dropDown.title
                && api == dropDown.api
                && apiParam == dropDown.apiParam
    }
}
