package com.gov.sidesa.ui.profile.edit.family.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.databinding.ItemEditProfileFamilyHeaderBinding

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class EditProfileFamilyHeaderViewHolder(
    private val binding: ItemEditProfileFamilyHeaderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(
            parent: ViewGroup,
        ): EditProfileFamilyHeaderViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val binding = ItemEditProfileFamilyHeaderBinding.inflate(inflate, parent, false)
            return EditProfileFamilyHeaderViewHolder(binding = binding)
        }
    }
}