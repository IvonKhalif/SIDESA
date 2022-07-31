package com.gov.sidesa.ui.letter.detail

import android.os.Bundle
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailSubmissionLetterBinding
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_SUBMISSION_LETTER_ID
import com.gov.sidesa.utils.enums.TypeSubmissionEnum
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailSubmissionLetterActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailSubmissionLetterBinding
    private val viewModel by viewModel<DetailSubmissionLetterViewModel>()
    private val letterId by lazy {
        intent.getStringExtra(EXTRA_SUBMISSION_LETTER_ID).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSubmissionLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
        initObserver()
    }

    private fun initObserver() = with(viewModel) {
        letterDetail.observe(this@DetailSubmissionLetterActivity, ::handleLetterDetail)
        loadingState.observe(this@DetailSubmissionLetterActivity) {
            handleLoadingWidget(isLoading = it)
        }
    }

    private fun handleLetterDetail(detailApprovalModel: DetailApprovalModel) {
        with(binding) {
            textIdLetter.text = detailApprovalModel.letterNumber
            textLetterType.text = detailApprovalModel.letterType
            textLetterDateSubmission.text = detailApprovalModel.createdDate
            textLetterStatus.text = detailApprovalModel.status?.let { getLetterStatus(it) }
            historyApproval.initialize(detailApprovalModel.historyApproval)
        }

    }

    private fun mainView() {
        binding.customToolbar.toolbarDetailProfile.apply {
            title = getString(R.string.letter_detail_submission_title)
            setNavigationOnClickListener { finish() }
        }

        viewModel.onLoad(letterId)
    }

    private fun getLetterStatus(status: String) = when (status) {
        TypeSubmissionEnum.SUBMISSION.type -> getString(R.string.letter_detail_submission_marker_caption)
        TypeSubmissionEnum.RT_WAITING_Submission.type -> getString(R.string.letter_detail_approval_rt_marker_caption)
        TypeSubmissionEnum.RT_Submission.type -> getString(R.string.letter_detail_has_approval_rt_marker_caption)
        TypeSubmissionEnum.RW_WAITING_Submission.type -> getString(R.string.letter_detail_approval_rw_marker_caption)
        TypeSubmissionEnum.RW_Submission.type -> getString(R.string.letter_detail_has_approval_rw_marker_caption)
        else -> getString(R.string.letter_detail_approval_rw_marker_caption)
    }
}