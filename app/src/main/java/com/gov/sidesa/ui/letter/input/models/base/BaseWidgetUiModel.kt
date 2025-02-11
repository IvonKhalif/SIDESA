package com.gov.sidesa.ui.letter.input.models.base

import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

abstract class BaseWidgetUiModel(
    open val type: WidgetType,
    open val name: String,
    open val title: String?,
    open val value: String? = null,
    open val initialState: InitialState = InitialState()
) {

    abstract fun type(typeFactory: LetterInputViewHolderFactory): Int

    open fun areItemsTheSame(
        newItem: BaseWidgetUiModel
    ): Boolean = type == newItem.type && name == newItem.name

    open fun areContentsTheSame(
        newItem: BaseWidgetUiModel
    ): Boolean = areItemsTheSame(newItem = newItem)
}