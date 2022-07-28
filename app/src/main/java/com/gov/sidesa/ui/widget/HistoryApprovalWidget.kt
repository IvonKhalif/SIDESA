package com.gov.sidesa.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.gov.sidesa.databinding.HistoryApprovalWidgetBinding

class HistoryApprovalWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val binding: HistoryApprovalWidgetBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = HistoryApprovalWidgetBinding.inflate(layoutInflater, this, false)
        addView(binding.root)
    }

    fun setSubmissionDate(date: String): HistoryApprovalWidget {
        binding.textSubmissionDate.text = date
        return this
    }

    fun setApprovedRtDate(date: String): HistoryApprovalWidget {
        binding.textHasApprovalRtDate.text = date
        return this
    }

    fun setApprovedRwDate(date: String): HistoryApprovalWidget {
        binding.textHasApprovalRwDate.text = date
        return this
    }
}