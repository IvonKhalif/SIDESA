package com.gov.sidesa.ui.letter.input.view_holder.text_view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gov.sidesa.R
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.databinding.ItemLetterInputTextViewWidgetBinding
import com.gov.sidesa.ui.letter.input.models.text_view.TextViewWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class TextViewHolder(
    private val binding: ItemLetterInputTextViewWidgetBinding,
    private val listener: LetterInputViewHolderListener
) : AbstractViewHolder<TextViewWidgetUiModel>(binding.root) {

    companion object {
        const val LAYOUT = R.layout.item_letter_input_text_view_widget

        fun create(
            viewGroup: ViewGroup,
            listener: LetterInputViewHolderListener
        ): TextViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputTextViewWidgetBinding.inflate(
                inflater, viewGroup, false
            )
            return TextViewHolder(binding = binding, listener = listener)
        }
    }

    override fun bind(model: TextViewWidgetUiModel): Unit = with(binding) {
        tvKey.text = model.title
        tvValue.text = model.value
    }
}