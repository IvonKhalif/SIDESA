package com.gov.sidesa.ui.letter.input.view_holder.edit_text

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.gov.sidesa.R
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.databinding.ItemLetterInputEditTextWidgetBinding
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class EditTextViewHolder(
    private val binding: ItemLetterInputEditTextWidgetBinding,
    private val listener: LetterInputViewHolderListener
) : AbstractViewHolder<EditTextWidgetUiModel>(binding.root) {

    companion object {
        const val LAYOUT = R.layout.item_letter_input_edit_text_widget

        fun create(
            viewGroup: ViewGroup,
            listener: LetterInputViewHolderListener
        ): EditTextViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputEditTextWidgetBinding.inflate(
                inflater, viewGroup, false
            )
            return EditTextViewHolder(binding = binding, listener = listener)
        }
    }

    override fun bind(model: EditTextWidgetUiModel): Unit = with(binding) {
        tilInputLayout.hint = model.title
        etInput.inputType = InputType.TYPE_CLASS_TEXT

        etInput.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onEditTextChanged(model)
        })
    }
}