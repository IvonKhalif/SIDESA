package com.gov.sidesa.ui.letter.input.models.drop_down

import com.gov.sidesa.ui.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class DropDownWidgetUiModel(
    override val name: String,
    val selectedId: String?,
    val inputType: String,
    val title: String?,
    val api: String,
    val apiType: String?,
    val apiParam: String?
) : BaseLetterInputModel(type = WidgetType.DropDown, name = name) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseLetterInputModel): Boolean {
        val dropDown = newItem as? DropDownWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = dropDown)
                && name == dropDown.name
                && selectedId == dropDown.selectedId
                && inputType == dropDown.inputType
                && title == dropDown.title
                && api == dropDown.api
                && apiParam == dropDown.apiParam
    }
}
