package com.gov.sidesa.ui.letter.input.models.date_picker

import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.base.InitialState
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory
import com.gov.sidesa.utils.extension.formatFE
import com.gov.sidesa.utils.extension.toDate

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class DatePickerWidgetUiModel(
    override val name: String,
    override val value: String?,
    override val initialState: InitialState,
    val title: String?
) : BaseWidgetUiModel(
    type = WidgetType.DropDown,
    name = name,
    value = value, // yyyy-MM-dd, adjust like BE format
    initialState = initialState
) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseWidgetUiModel): Boolean {
        val dropDown = newItem as? DatePickerWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = dropDown)
                && value == dropDown.value // ensure update when selected item
    }

    val dateFormatted: String
        get() = run {
            try {
                if (value == null) return ""
                val date = value.toDate()
                date?.formatFE().orEmpty()
            } catch (_: Throwable) {
                ""
            }
        }
}
