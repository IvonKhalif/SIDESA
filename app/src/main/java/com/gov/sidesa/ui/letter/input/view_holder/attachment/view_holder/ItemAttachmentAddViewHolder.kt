package com.gov.sidesa.ui.letter.input.view_holder.attachment.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.databinding.ItemLetterInputItemAtthachmentAddBinding
import com.gov.sidesa.ui.letter.input.models.attachment.AttachmentWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class ItemAttachmentAddViewHolder(
    private val binding: ItemLetterInputItemAtthachmentAddBinding,
    private val listener: LetterInputViewHolderListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(
            viewGroup: ViewGroup,
            listener: LetterInputViewHolderListener
        ): ItemAttachmentAddViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputItemAtthachmentAddBinding.inflate(
                inflater, viewGroup, false
            )
            return ItemAttachmentAddViewHolder(binding = binding, listener = listener)
        }
    }

    fun bind(item: AttachmentWidgetUiModel) = with(binding) {
        ivFile.setOnClickListener {
            listener.onAttachmentClicked(model = item)
        }
    }
}