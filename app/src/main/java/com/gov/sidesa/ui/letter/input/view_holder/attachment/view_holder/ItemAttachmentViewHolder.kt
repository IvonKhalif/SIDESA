package com.gov.sidesa.ui.letter.input.view_holder.attachment.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ItemLetterInputItemAtthachmentBinding
import com.gov.sidesa.ui.letter.input.models.attachment.AttachmentWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener
import com.gov.sidesa.utils.extension.load
import com.gov.sidesa.utils.picker.isPdf
import java.io.File

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class ItemAttachmentViewHolder(
    private val binding: ItemLetterInputItemAtthachmentBinding,
    private val listener: LetterInputViewHolderListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(
            viewGroup: ViewGroup,
            listener: LetterInputViewHolderListener
        ): ItemAttachmentViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputItemAtthachmentBinding.inflate(
                inflater, viewGroup, false
            )
            return ItemAttachmentViewHolder(binding = binding, listener = listener)
        }
    }

    fun bind(model: AttachmentWidgetUiModel, file: File) = with(binding) {
        if (file.isPdf()) {
            ivFile.load(
                source = R.drawable.background_attachment_pdf,
                builder = {
                    transform(CenterCrop(), RoundedCorners(16))
                }
            )
        } else {
            ivFile.load(
                source = file,
                builder = {
                    transform(CenterCrop(), RoundedCorners(16))
                }
            )
        }

        ivRemove.setOnClickListener {
            listener.onAttachmentRemove(model = model, file = file)
        }
    }
}