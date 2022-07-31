package com.gov.sidesa.ui.profile.edit.family.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.base.dynamic_adapter.DiffUtilItemCallback
import com.gov.sidesa.ui.profile.edit.family.adapter.view_holder.EditProfileFamilyAddChildViewHolder
import com.gov.sidesa.ui.profile.edit.family.adapter.view_holder.EditProfileFamilyHeaderViewHolder
import com.gov.sidesa.ui.profile.edit.family.adapter.view_holder.EditProfileFamilyViewHolder
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyUiModel
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyViewType

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class EditProfileFamilyAdapter(
    private val listener: EditProfileFamilyListener
) : ListAdapter<EditProfileFamilyUiModel, RecyclerView.ViewHolder>(
    DiffUtilItemCallback<EditProfileFamilyUiModel>(
        isItemsTheSame = { oldItem, newItem -> oldItem.areItemsTheSame(newItem) },
        isContentTheSame = { oldItem, newItem -> oldItem.areContentsTheSame(newItem) }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EditProfileFamilyViewType.Header.type -> EditProfileFamilyHeaderViewHolder.create(parent)
            EditProfileFamilyViewType.Form.type -> EditProfileFamilyViewHolder.create(
                parent,
                listener
            )
            EditProfileFamilyViewType.AddChild.type -> EditProfileFamilyAddChildViewHolder.create(
                parent,
                listener
            )
            else -> EditProfileFamilyHeaderViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item.type) {
            is EditProfileFamilyViewType.Header -> {

            }
            is EditProfileFamilyViewType.Form -> {
                (holder as EditProfileFamilyViewHolder).binding(item)
            }
            is EditProfileFamilyViewType.AddChild -> {
                (holder as EditProfileFamilyAddChildViewHolder).binding()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.type
    }
}