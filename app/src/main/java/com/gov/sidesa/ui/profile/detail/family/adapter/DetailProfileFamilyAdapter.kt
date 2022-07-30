package com.gov.sidesa.ui.profile.detail.family.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.gov.sidesa.base.dynamic_adapter.DiffUtilItemCallback
import com.gov.sidesa.ui.profile.detail.family.model.FamilyUiModel

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class DetailProfileFamilyAdapter : ListAdapter<FamilyUiModel, DetailProfileFamilyViewHolder>(
    DiffUtilItemCallback(
        isContentTheSame = { oldItem, newItem -> oldItem == newItem },
        isItemsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailProfileFamilyViewHolder {
        return DetailProfileFamilyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DetailProfileFamilyViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}