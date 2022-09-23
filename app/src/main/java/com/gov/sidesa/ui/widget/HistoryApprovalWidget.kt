package com.gov.sidesa.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.gov.sidesa.R
import com.gov.sidesa.databinding.HistoryApprovalWidgetBinding
import com.gov.sidesa.domain.letter.detail.models.HistoryApprovalModel
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.constants.LetterConstant.APPROVED_STATUS
import com.gov.sidesa.utils.constants.LetterConstant.NORMAL_STATUS
import com.gov.sidesa.utils.constants.LetterConstant.REJECTED_STATUS
import com.gov.sidesa.utils.enums.TypeSubmissionEnum

class HistoryApprovalWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val binding: HistoryApprovalWidgetBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = HistoryApprovalWidgetBinding.inflate(layoutInflater, this, false)
        addView(binding.root)
    }

    fun initialize(approvalListModel: List<HistoryApprovalModel>): HistoryApprovalWidget {
        approvalListModel.forEach {
            when {
                it.typeApproval == TypeSubmissionEnum.SUBMISSION.type -> setSubmitted(it)
                it.typeApproval == LetterConstant.TYPE_APPROVAL_RT &&
                        it.descriptionType == LetterConstant.TYPE_APPROVAL_WAITING_APPROVE -> setWaitingApproveRt(it)
                it.typeApproval == LetterConstant.TYPE_APPROVAL_RT &&
                        it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE -> setApprovedRt(it)
                it.typeApproval == LetterConstant.TYPE_APPROVAL_RW &&
                        it.descriptionType == LetterConstant.TYPE_APPROVAL_WAITING_APPROVE -> setWaitingApproveRw(it)
                it.typeApproval == LetterConstant.TYPE_APPROVAL_RW &&
                        it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE -> setApprovedRw(it)
                it.typeApproval == LetterConstant.TYPE_APPROVAL_VILLAGE &&
                        it.descriptionType == LetterConstant.TYPE_APPROVAL_WAITING_APPROVE -> setWaitingApproveVillage(it)
                it.typeApproval == LetterConstant.TYPE_APPROVAL_VILLAGE &&
                        it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE -> setApprovedVillage(it)
                it.typeApproval == TypeSubmissionEnum.FINISH.type &&
                        it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE -> setFinished(it)
                else -> {}

            }
        }
        return this
    }

    private fun setSubmitted(approvalModel: HistoryApprovalModel) {
        binding.textSubmissionDate.text = approvalModel.createdDate
        binding.iconSubmissionMarker.setImageResource(drawableStatus(approvalModel.statusApproval))
    }

    private fun setWaitingApproveRt(approvalModel: HistoryApprovalModel) {
        binding.iconWaitingApprovalRtMarker.setImageResource(drawableStatus(approvalModel.statusApproval))
    }

    private fun setApprovedRt(approvalModel: HistoryApprovalModel) {
        binding.textHasApprovalRtDate.text = approvalModel.createdDate
        binding.iconHasApprovalRtMarker.setImageResource(drawableStatus(approvalModel.statusApproval))
        when {
            approvalModel.statusApproval.toInt() == REJECTED_STATUS -> {
                binding.textHasApprovalRtRejected.isVisible = true
                binding.textHasApprovalRtDate.isVisible = false
            }
            approvalModel.statusApproval.toInt() == APPROVED_STATUS -> {
                binding.textHasApprovalRtRejected.isVisible = false
                binding.textHasApprovalRtDate.isVisible = true
            }
            else -> {
                binding.textHasApprovalRtRejected.isVisible = false
                binding.textHasApprovalRtDate.isVisible = true
            }
        }
    }

    private fun setWaitingApproveRw(approvalModel: HistoryApprovalModel) {
        binding.iconWaitingApprovalRwMarker.setImageResource(drawableStatus(approvalModel.statusApproval))
    }

    private fun setApprovedRw(approvalModel: HistoryApprovalModel) {
        binding.textHasApprovalRwDate.text = approvalModel.createdDate
        binding.iconHasApprovalRwMarker.setImageResource(drawableStatus(approvalModel.statusApproval))
        when {
            approvalModel.statusApproval.toInt() == REJECTED_STATUS -> {
                binding.textHasApprovalRwRejected.isVisible = true
                binding.textHasApprovalRwDate.isVisible = false
            }
            approvalModel.statusApproval.toInt() == APPROVED_STATUS -> {
                binding.textHasApprovalRwRejected.isVisible = false
                binding.textHasApprovalRwDate.isVisible = true
            }
            else -> {
                binding.textHasApprovalRwRejected.isVisible = false
                binding.textHasApprovalRwDate.isVisible = true
            }
        }
    }

    private fun setWaitingApproveVillage(approvalModel: HistoryApprovalModel) {
        binding.iconWaitingApprovalKelurahanMarker.setImageResource(drawableStatus(approvalModel.statusApproval))
    }

    private fun setApprovedVillage(approvalModel: HistoryApprovalModel) {
        binding.textHasApprovalKelurahanDate.text = approvalModel.createdDate
        binding.iconHasApprovalKelurahanMarker.setImageResource(drawableStatus(approvalModel.statusApproval))
        when {
            approvalModel.statusApproval.toInt() == REJECTED_STATUS -> {
                binding.textHasApprovalKelurahanRejected.isVisible = true
                binding.textHasApprovalKelurahanDate.isVisible = false
            }
            approvalModel.statusApproval.toInt() == APPROVED_STATUS -> {
                binding.textHasApprovalRwRejected.isVisible = false
                binding.textHasApprovalKelurahanDate.isVisible = true
            }
            else -> {
                binding.textHasApprovalKelurahanRejected.isVisible = false
                binding.textHasApprovalKelurahanDate.isVisible = true
            }
        }
    }

    private fun setFinished(approvalModel: HistoryApprovalModel) {
        binding.iconReadyToPickupMarker.setImageResource(
//            R.drawable.ic_history_green
            drawableStatus(approvalModel.statusApproval)
        )
    }

    private fun drawableStatus(status: String) = when (status.toInt()) {
        NORMAL_STATUS -> R.drawable.ic_history_grey
        APPROVED_STATUS -> R.drawable.ic_history_green
        REJECTED_STATUS -> R.drawable.ic_history_red
        else -> R.drawable.ic_history_grey
    }
}