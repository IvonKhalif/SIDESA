package com.gov.sidesa.ui.dashboard.comingsoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ItemComingSoonFeatureBinding
import com.gov.sidesa.domain.comingsoon.models.ComingSoonModel

class ComingSoonViewHolder(
    val binding: ItemComingSoonFeatureBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ComingSoonModel) {
        binding.apply {
            textFeatureComingSoon.text = item.title
            imageFeature.setImageResource(item.icon)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): ComingSoonViewHolder {
            val view = DataBindingUtil
                .inflate<ItemComingSoonFeatureBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_coming_soon_feature,
                    parent,
                    false
                )
            return ComingSoonViewHolder(view)
        }
    }
}