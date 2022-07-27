package com.gov.sidesa.ui.letter.input.view_holder_factory

import android.view.ViewGroup
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.ui.letter.input.models.divider.DividerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.header.HeaderWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.text_view.TextViewWidgetUiModel

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

interface LetterInputViewHolderFactory {

    fun type(header: HeaderWidgetUiModel): Int

    fun type(divider: DividerWidgetUiModel): Int

    fun type(textView: TextViewWidgetUiModel): Int

    fun type(editText: EditTextWidgetUiModel): Int

    fun type(dropDown: DropDownWidgetUiModel): Int

    fun createViewHolder(
        parent: ViewGroup,
        type: Int,
        listener: LetterInputViewHolderListener
    ): AbstractViewHolder<*>
}