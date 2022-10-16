package com.gov.sidesa.ui.approval

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseBottomSheet
import com.gov.sidesa.databinding.BottomSheetHistoryApprovalBinding
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel

class HistoryApprovalBottomSheet : BaseBottomSheet() {
    companion object {
        internal fun newInstance(
            detailApprovalModel: DetailApprovalModel
        ) = HistoryApprovalBottomSheet().apply {
            this.detailApprovalModel = detailApprovalModel
        }
    }


private lateinit var binding: BottomSheetHistoryApprovalBinding
internal var detailApprovalModel: DetailApprovalModel? = null

override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View {
    binding = DataBindingUtil.inflate(
        inflater,
        R.layout.bottom_sheet_history_approval,
        container,
        false
    )
//        binding.viewModel = viewModel
    binding.lifecycleOwner = requireActivity()
    return binding.root
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mainView()
}

private fun mainView() {
    with(binding) {
        btnClose.setOnClickListener {
            dismissAllowingStateLoss()
        }
        detailApprovalModel?.let { historyApproval.initialize(it.historyApproval) }
    }
}
}