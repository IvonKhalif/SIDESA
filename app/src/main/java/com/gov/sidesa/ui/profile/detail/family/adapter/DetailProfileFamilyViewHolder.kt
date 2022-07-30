package com.gov.sidesa.ui.profile.detail.family.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ItemDetailFamilyBinding
import com.gov.sidesa.ui.profile.detail.family.model.FamilyUiModel

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class DetailProfileFamilyViewHolder(
    private val binding: ItemDetailFamilyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(family: FamilyUiModel) = with(binding) {
        labelHeaderFamily.text = family.relationFamily
        labelFamilyName.text = family.name
        textFamilyNik.text = family.ktpNumber
        textFamilyTtl.text = family.birthPlaceAndDate
        textFamilyAddress.text = family.address
    }

    companion object {

        const val LAYOUT = R.layout.item_detail_family

        fun create(parent: ViewGroup): DetailProfileFamilyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemDetailFamilyBinding.inflate(inflater)
            return DetailProfileFamilyViewHolder(binding = binding)
        }
    }
}