package com.gov.sidesa.ui.letter.list.submission

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.databinding.ItemSubmissionBinding
import com.gov.sidesa.utils.enums.TypeSubmissionEnum

class LetterSubmissionViewHolder(
    val binding: ItemSubmissionBinding,
    private val onItemClick: (LetterSubmissionModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LetterSubmissionModel) {
        binding.apply {
            textLetterNumber.text = item.letterNumber
            textLetterTitle.text = item.letterType
            imageStatusApproval.setImageResource(drawableStatus(item.letterStatus))
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    fun drawableStatus(status: String) = when (status) {
        TypeSubmissionEnum.RT_WAITING_Submission.type -> R.drawable.background_waiting_approval_rt
        TypeSubmissionEnum.RW_WAITING_Submission.type -> R.drawable.background_waiting_approval_rw
        TypeSubmissionEnum.FINISH.type -> R.drawable.background_ready_to_pickup
        else -> R.drawable.background_waiting_approval_rt
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClicked: (LetterSubmissionModel) -> Unit
        ): LetterSubmissionViewHolder {
            val view = DataBindingUtil
                .inflate<ItemSubmissionBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_submission,
                    parent,
                    false
                )
            return LetterSubmissionViewHolder(view, onItemClicked)
        }
    }
}