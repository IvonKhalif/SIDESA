package com.gov.sidesa.ui.letter.input.view_holder.attachment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.databinding.ItemLetterInputAttachmentWidgetBinding
import com.gov.sidesa.ui.letter.input.models.attachment.AttachmentWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder.attachment.adapter.AttachmentAdapter
import com.gov.sidesa.ui.letter.input.view_holder.attachment.model.ItemAttachment
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class AttachmentViewHolder(
    private val binding: ItemLetterInputAttachmentWidgetBinding,
    private val listener: LetterInputViewHolderListener
) : AbstractViewHolder<AttachmentWidgetUiModel>(binding.root) {

    private var attachmentAdapter: AttachmentAdapter? = null

    override fun bind(model: AttachmentWidgetUiModel): Unit = with(binding) {
        initRecyclerView(model = model)
        attachmentAdapter?.submitList(model.asItemAttachment())
    }

    private fun initRecyclerView(model: AttachmentWidgetUiModel) = with(binding.rvAttachment) {
        attachmentAdapter = AttachmentAdapter(widget = model, listener = listener)
        adapter = attachmentAdapter
        layoutManager = GridLayoutManager(context, 4)
    }

    private fun AttachmentWidgetUiModel.asItemAttachment(): List<ItemAttachment> {
        val items = files.map {
            ItemAttachment(type = ItemAttachment.IMAGE, it)
        }.toMutableList()

        if (items.size < limit) {
            items.add(ItemAttachment(type = ItemAttachment.ADD, null))
        }

        return items
    }

    companion object {
        const val LAYOUT = R.layout.item_letter_input_attachment_widget

        fun create(
            viewGroup: ViewGroup,
            listener: LetterInputViewHolderListener
        ): AttachmentViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputAttachmentWidgetBinding.inflate(
                inflater, viewGroup, false
            )
            return AttachmentViewHolder(binding = binding, listener = listener)
        }
    }

}