package com.gov.sidesa.ui.letter.input.view_holder.drop_down

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.gov.sidesa.R
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.databinding.ItemLetterInputDropDownWidgetBinding
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class DropDownViewHolder(
    private val binding: ItemLetterInputDropDownWidgetBinding,
    private val listener: LetterInputViewHolderListener
) : AbstractViewHolder<DropDownWidgetUiModel>(binding.root) {

    companion object {
        const val LAYOUT = R.layout.item_letter_input_drop_down_widget

        fun create(
            viewGroup: ViewGroup,
            listener: LetterInputViewHolderListener
        ): DropDownViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputDropDownWidgetBinding.inflate(
                inflater, viewGroup, false
            )
            return DropDownViewHolder(binding = binding, listener = listener)
        }
    }

    override fun bind(model: DropDownWidgetUiModel) = with(binding) {
       /* etInput.hint = model.placeholder
        etInput.inputType = InputType.TYPE_CLASS_TEXT

        etInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onEditTextChanged(model)
        })*/
    }
}