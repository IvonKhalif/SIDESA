package com.gov.sidesa.ui.letter.list.needapproval

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ItemNeedApprovalBinding
import com.gov.sidesa.domain.letter.list.models.LetterListApprovalModel
import com.gov.sidesa.utils.DateUtil.convertToDayAndDate

class LetterNeedApprovalViewHolder(
    val binding: ItemNeedApprovalBinding,
    private val onItemClick: (LetterListApprovalModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LetterListApprovalModel) {
        binding.apply {
            textDateSubmission.text = convertToDayAndDate(item.submitDate)
            textLetterNumber.text = item.letterNumber
            textLetterTitle.text = item.letterType
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClicked: (LetterListApprovalModel) -> Unit
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