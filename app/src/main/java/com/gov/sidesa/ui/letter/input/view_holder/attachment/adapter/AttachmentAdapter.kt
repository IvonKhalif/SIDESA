package com.gov.sidesa.ui.letter.input.view_holder.attachment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.ui.letter.input.models.attachment.AttachmentWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder.attachment.model.ItemAttachment
import com.gov.sidesa.ui.letter.input.view_holder.attachment.view_holder.ItemAttachmentAddViewHolder
import com.gov.sidesa.ui.letter.input.view_holder.attachment.view_holder.ItemAttachmentViewHolder
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 06/10/22"
 * Project name: SIDESA
 **/


class AttachmentAdapter(
    private val widget: AttachmentWidgetUiModel,
    private val listener: LetterInputViewHolderListener
) : ListAdapter<ItemAttachment,RecyclerView.ViewHolder>(DIFF_ITEM) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemAttachment.ADD -> ItemAttachmentAddViewHolder.create(
                viewGroup = parent,
                listener = listener
            )
            ItemAttachment.IMAGE -> ItemAttachmentViewHolder.create(
                viewGroup = parent,
                listener = listener
            )
            else -> throw RuntimeException("viewType $viewType not supported")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemAttachment.ADD -> (holder as? ItemAttachmentAddViewHolder)?.bind(widget)
            ItemAttachment.IMAGE -> {
                currentList[position].file?.let {
                    (holder as? ItemAttachmentViewHolder)?.bind(widget, file = it)
                }
            }
        }
    }

    override fun getItemViewType(
        position: Int
    ): Int = currentList[position]?.type ?: super.getItemViewType(position)

    companion object {

        private val DIFF_ITEM = object : DiffUtil.ItemCallback<ItemAttachment>() {
            override fun areItemsTheSame(
                oldItem: ItemAttachment,
                newItem: ItemAttachment
            ): Boolean = oldItem.type == newItem.type
                    && oldItem.file?.absolutePath == newItem.file?.absolutePath

            override fun areContentsTheSame(
                oldItem: ItemAttachment,
                newItem: ItemAttachment
            ): Boolean = oldItem == newItem
        }
    }
}