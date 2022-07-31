package com.gov.sidesa.ui.profile.edit.family.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.databinding.ItemEditProfileFamilyAddChildBinding
import com.gov.sidesa.ui.profile.edit.family.adapter.EditProfileFamilyListener

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class EditProfileFamilyAddChildViewHolder(
    private val binding: ItemEditProfileFamilyAddChildBinding,
    private val listener: EditProfileFamilyListener
) : RecyclerView.ViewHolder(binding.root) {

    fun binding() = with(binding) {
        buttonAddChild.setOnClickListener {
            listener.onAddChild()
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            listener: EditProfileFamilyListener
        ): EditProfileFamilyAddChildViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val binding = ItemEditProfileFamilyAddChildBinding.inflate(inflate, parent, false)
            return EditProfileFamilyAddChildViewHolder(binding = binding, listener = listener)
        }
    }
}