package com.gov.sidesa.ui.letter.input.view_holder.header

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gov.sidesa.R
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.databinding.ItemLetterInputHeaderWidgetBinding
import com.gov.sidesa.ui.letter.input.models.header.HeaderWidgetUiModel

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class HeaderViewHolder(
    private val binding: ItemLetterInputHeaderWidgetBinding
) : AbstractViewHolder<HeaderWidgetUiModel>(binding.root) {

    companion object {
        const val LAYOUT = R.layout.item_letter_input_header_widget

        fun create(
            viewGroup: ViewGroup,
        ): HeaderViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputHeaderWidgetBinding.inflate(
                inflater, viewGroup, false
            )
            return HeaderViewHolder(binding = binding)
        }
    }

    override fun bind(model: HeaderWidgetUiModel) = with(binding) {
        tvTitle.text = model.title
    }
}