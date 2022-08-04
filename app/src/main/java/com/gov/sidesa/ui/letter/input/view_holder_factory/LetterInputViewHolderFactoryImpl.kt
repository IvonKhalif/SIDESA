package com.gov.sidesa.ui.letter.input.view_holder_factory

import android.view.ViewGroup
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.ui.letter.input.models.date_picker.DatePickerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.divider.DividerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.header.HeaderWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.text_view.TextViewWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder.date_picker.DatePickerViewHolder
import com.gov.sidesa.ui.letter.input.view_holder.divider.DividerViewHolder
import com.gov.sidesa.ui.letter.input.view_holder.drop_down.DropDownViewHolder
import com.gov.sidesa.ui.letter.input.view_holder.edit_text.EditTextViewHolder
import com.gov.sidesa.ui.letter.input.view_holder.header.HeaderViewHolder
import com.gov.sidesa.ui.letter.input.view_holder.text_view.TextViewHolder

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class LetterInputViewHolderFactoryImpl : LetterInputViewHolderFactory {

    override fun type(header: HeaderWidgetUiModel): Int {
        return HeaderViewHolder.LAYOUT
    }

    override fun type(divider: DividerWidgetUiModel): Int {
        return DividerViewHolder.LAYOUT
    }

    override fun type(textView: TextViewWidgetUiModel): Int {
        return TextViewHolder.LAYOUT
    }

    override fun type(editText: EditTextWidgetUiModel): Int {
        return EditTextViewHolder.LAYOUT
    }

    override fun type(dropDown: DropDownWidgetUiModel): Int {
        return DropDownViewHolder.LAYOUT
    }

    override fun type(datePicker: DatePickerWidgetUiModel): Int {
        return DatePickerViewHolder.LAYOUT
    }

    override fun createViewHolder(
        parent: ViewGroup,
        type: Int,
        listener: LetterInputViewHolderListener
    ): AbstractViewHolder<*> {
        return when (type) {
            HeaderViewHolder.LAYOUT -> HeaderViewHolder.create(parent)
            DividerViewHolder.LAYOUT -> DividerViewHolder.create(parent)
            TextViewHolder.LAYOUT -> TextViewHolder.create(parent, listener)
            EditTextViewHolder.LAYOUT -> EditTextViewHolder.create(parent, listener)
            DropDownViewHolder.LAYOUT -> DropDownViewHolder.create(parent, listener)
            DatePickerViewHolder.LAYOUT -> DatePickerViewHolder.create(parent, listener)
            else -> throw IllegalAccessException("view holder not found...")
        }
    }
}