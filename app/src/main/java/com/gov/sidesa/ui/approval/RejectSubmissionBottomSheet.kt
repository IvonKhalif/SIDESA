package com.gov.sidesa.ui.approval

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseBottomSheet
import com.gov.sidesa.databinding.BottomSheetRejectSubmissionBinding

class RejectSubmissionBottomSheet(
    private val onReject: (reason: String) -> Unit
) : BaseBottomSheet() {
    private lateinit var binding: BottomSheetRejectSubmissionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_reject_submission,
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
            inputReason.value().doOnTextChanged { text, start, before, count ->
                buttonSaveReason.isEnabled = !text.isNullOrBlank()
            }
            buttonClose.setOnClickListener {
                dismissAllowingStateLoss()
            }
            buttonSaveReason.setOnClickListener {
                onReject(inputReason.value().text.toString())
                dismissAllowingStateLoss()
            }
        }
    }
}