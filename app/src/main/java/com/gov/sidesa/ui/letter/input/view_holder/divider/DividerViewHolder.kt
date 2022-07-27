package com.gov.sidesa.ui.letter.input.view_holder.divider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gov.sidesa.R
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.databinding.ItemLetterInputDividerWidgetBinding
import com.gov.sidesa.ui.letter.input.models.divider.DividerWidgetUiModel

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/


class DividerViewHolder(
    private val binding: ItemLetterInputDividerWidgetBinding
) : AbstractViewHolder<DividerWidgetUiModel>(binding.root) {

    companion object {
        const val LAYOUT = R.layout.item_letter_input_divider_widget

        fun create(
            viewGroup: ViewGroup,
        ): DividerViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemLetterInputDividerWidgetBinding.inflate(
                inflater, viewGroup, false
            )
            return DividerViewHolder(binding = binding)
        }
    }

    override fun bind(model: DividerWidgetUiModel) {
    }
}