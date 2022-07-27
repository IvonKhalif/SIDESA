package com.gov.sidesa.ui.letter.input.models.base

import com.gov.sidesa.ui.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

abstract class BaseLetterInputModel(
    open val type: WidgetType,
    open val name: String
) {

    abstract fun type(typeFactory: LetterInputViewHolderFactory): Int

    open fun areItemsTheSame(
        newItem: BaseLetterInputModel
    ): Boolean = type == newItem.type && name == newItem.name

    open fun areContentsTheSame(
        newItem: BaseLetterInputModel
    ): Boolean = this == newItem
}