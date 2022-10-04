package com.gov.sidesa.ui.letter.input.view_holder.attachment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gov.sidesa.R
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.databinding.ItemLetterInputDatePickerWidgetBinding
import com.gov.sidesa.ui.letter.input.models.date_picker.DatePickerWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class AttachmentViewHolder(
    private val binding: ItemLetterInputDatePickerWidgetBinding,
    private val listener: LetterInputViewHolderListener
) : AbstractViewHolder<DatePickerWidgetUiModel>(binding.root) {

    companion object {
        const val LAYOUT = R.layout.item_letter_input_attachment_widget

        fun create(
            viewGroup: ViewGroup,
            listener: LetterInputViewHolderListener
        ): AttachmentViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputDatePickerWidgetBinding.inflate(
                inflater, viewGroup, false
            )
            return AttachmentViewHolder(binding = binding, listener = listener)
        }
    }

    override fun bind(model: DatePickerWidgetUiModel) = with(binding) {
        tlDatePicker.hint = model.title
        etDatePicker.setText(model.dateFormatted)

        tlDatePicker.setEndIconOnClickListener {
            listener.onDatePickerClicked(model = model)
        }
        etDatePicker.setOnClickListener {
            listener.onDatePickerClicked(model = model)
        }
    }
}