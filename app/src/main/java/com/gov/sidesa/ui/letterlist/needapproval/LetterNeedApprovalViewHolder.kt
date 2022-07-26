package com.gov.sidesa.ui.letterlist.needapproval

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.data.letterlist.models.LettersModel
import com.gov.sidesa.databinding.ItemNeedApprovalBinding

class LetterNeedApprovalViewHolder(
    val binding: ItemNeedApprovalBinding,
    private val onItemClick: (LettersModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LettersModel) {
        binding.apply {
            textDateSubmission.text = item.createDate
            textLetterNumber.text = item.number
            textLetterTitle.text = item.title
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClicked: (LettersModel) -> Unit
        ): LetterNeedApprovalViewHolder {
            val view = DataBindingUtil
                .inflate<ItemNeedApprovalBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_need_approval,
                    parent,
                    false
                )
            return LetterNeedApprovalViewHolder(view, onItemClicked)
        }
    }
}