package com.gov.sidesa.ui.approval

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.databinding.ActivityDetailApprovalLetterBinding
import com.gov.sidesa.databinding.DialogConfirmationApprovalBinding
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.domain.letter.detail.models.HistoryApprovalModel
import com.gov.sidesa.ui.approval.submissiondetail.DetailApprovalSubmissionActivity
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.constants.LetterConstant.APPROVED_STATUS
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_LETTER_DETAIL_SUBMISSION
import com.gov.sidesa.utils.constants.LetterConstant.REJECTED_STATUS
import com.gov.sidesa.utils.constants.LetterConstant.TYPE_APPROVAL_RT
import com.gov.sidesa.utils.constants.LetterConstant.TYPE_APPROVAL_RW
import com.gov.sidesa.utils.enums.ApprovalStatusEnum
import com.gov.sidesa.utils.enums.TypeSubmissionEnum
import com.gov.sidesa.utils.extension.observeNonNull
import com.gov.sidesa.utils.extension.orZero
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailApprovalLetterActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailApprovalLetterBinding
    private val viewModel by viewModel<DetailApprovalViewModel>()

    private val letterId by lazy {
        intent.getStringExtra(LetterConstant.EXTRA_SUBMISSION_LETTER_ID).orEmpty()
    }

    private val actor by lazy {
        intent.getStringExtra(LetterConstant.EXTRA_ACTOR_APPROVAL).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailApprovalLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
        initObserver()
    }

    private fun initObserver() = with(viewModel) {
        letterDetail.observe(this@DetailApprovalLetterActivity, ::handleLetterDetail)
        loadingState.observe(this@DetailApprovalLetterActivity) {
            handleLoadingWidget(isLoading = it)
        }
        statusApprovalPassword.observeNonNull(
            this@DetailApprovalLetterActivity,
            ::handleStatusApproval
        )
    }

    private fun mainView() {
        with(binding) {
            customToolbar.toolbarDetailProfile.apply {
                title = getString(R.string.letter_detail_detail_Submission_title)
                setNavigationOnClickListener { finish() }
            }
            buttonReject.setOnClickListener {
                onRejectClick()
            }
            buttonSeeHistoryApproval.setOnClickListener {
                onHistoryApprovalClick()
            }
            buttonApproved.setOnClickListener {
                onApproveClick()
            }
            buttonDetailApproval.setOnClickListener {
                goToDetailApproval()
            }
        }

        viewModel.onLoad(letterId)
    }

    private fun handleLetterDetail(detailApprovalModel: DetailApprovalModel) {
        with(binding) {
            textIdLetter.text = detailApprovalModel.letterNumber
            textLetterType.text = detailApprovalModel.letterType
            textLetterDateSubmission.text = detailApprovalModel.createdDate
            textLetterStatus.text = detailApprovalModel.status?.let { getLetterStatus(it) }
            textReason.text = detailApprovalModel.description
            customUserSubmittedLetter.textName.text = detailApprovalModel.userName
            customUserSubmittedLetter.textKtpNumber.text = detailApprovalModel.nik
            customUserSubmittedLetter.textAddress.text = detailApprovalModel.address
            containerButton.isVisible = !hasApproved(detailApprovalModel.historyApproval) && !hasRejected(detailApprovalModel.historyApproval)
            when {
                hasApproved(detailApprovalModel.historyApproval) -> {
                    containerApprovalStatus.isVisible = true
                    divider1.isVisible = true
                    containerReason.isVisible = false
                    iconApprovalLetter.setImageResource(R.drawable.ic_approved)
                    containerApprovalStatus.setBackgroundResource(R.drawable.background_approved)
                }
                hasRejected(detailApprovalModel.historyApproval) -> {
                    containerApprovalStatus.isVisible = true
                    divider1.isVisible = true
                    containerReason.isVisible = true
                    iconApprovalLetter.setImageResource(R.drawable.ic_rejected)
                    containerApprovalStatus.setBackgroundResource(R.drawable.background_error_rejected)
                }
                else -> {
                    containerApprovalStatus.isVisible = false
                    divider1.isVisible = false
                }
            }
        }
    }

    private fun goToDetailApproval() {
        val intent = Intent(this, DetailApprovalSubmissionActivity::class.java)
        intent.putExtra(EXTRA_LETTER_DETAIL_SUBMISSION, viewModel.letterDetail.value)
        startActivity(intent)
    }

    private fun onRejectClick() {
        val bottomSheet = RejectSubmissionBottomSheet { reason ->
            viewModel.doApproval(
                createRequestDoApprovalParam(
                    REJECTED_STATUS,
                    reason
                )
            )
        }
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun onHistoryApprovalClick() {
        val bottomSheet = viewModel.letterDetail.value?.let {
            HistoryApprovalBottomSheet.newInstance(
                it
            )
        }
        bottomSheet?.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun onApproveClick() {
        val dialog = Dialog(this)

        val bindingToast: DialogConfirmationApprovalBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_confirmation_approval, null, false
        )
//        bindingToast.viewModel = viewModel
        bindingToast.lifecycleOwner = this

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(bindingToast.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bindingToast.buttonApproved.setOnClickListener {
            viewModel.doApproval(createRequestDoApprovalParam(APPROVED_STATUS, null))
            dialog.dismiss()
        }

        bindingToast.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getLetterStatus(status: String) = when (status) {
        TypeSubmissionEnum.SUBMISSION.type -> getString(R.string.letter_detail_submission_marker_caption)
        TypeSubmissionEnum.RT_WAITING_Submission.type -> getString(R.string.letter_detail_approval_rt_marker_caption)
        TypeSubmissionEnum.RT_Submission.type -> getString(R.string.letter_detail_has_approval_rt_marker_caption)
        TypeSubmissionEnum.RT_REJECTED.type -> getString(R.string.letter_detail_approval_rt_marker_caption)
        TypeSubmissionEnum.RW_WAITING_Submission.type -> getString(R.string.letter_detail_approval_rw_marker_caption)
        TypeSubmissionEnum.RW_Submission.type -> getString(R.string.letter_detail_has_approval_rw_marker_caption)
        TypeSubmissionEnum.RW_REJECTED.type -> getString(R.string.letter_detail_approval_rw_marker_caption)
        else -> getString(R.string.letter_detail_approval_rw_marker_caption)
    }

    private fun hasApproved(historylist: List<HistoryApprovalModel>): Boolean {
        var isApproved = false
        kotlin.run {
            historylist.forEach {
                if (actor == TYPE_APPROVAL_RT &&
                    it.typeApproval == TYPE_APPROVAL_RT &&
                    it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE &&
                    it.statusApproval == APPROVED_STATUS.toString()
                ) {
                    isApproved = true
                    return@run
                } else if (actor == TYPE_APPROVAL_RW &&
                    it.typeApproval == TYPE_APPROVAL_RW &&
                    it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE &&
                    it.statusApproval == APPROVED_STATUS.toString()
                ) {
                    isApproved = true
                    return@run
                } else isApproved = false
            }
        }
        return isApproved
    }

    private fun hasRejected(status: List<HistoryApprovalModel>): Boolean {
        var isRejected = false
        kotlin.run {
            status.forEach {
                if (actor == TYPE_APPROVAL_RT &&
                    it.typeApproval == TYPE_APPROVAL_RT &&
                    it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE &&
                    it.statusApproval == REJECTED_STATUS.toString()
                ) {
                    isRejected = true
                    return@run
                } else if (actor == TYPE_APPROVAL_RW &&
                    it.typeApproval == TYPE_APPROVAL_RW &&
                    it.descriptionType == LetterConstant.TYPE_APPROVAL_APPROVE &&
                    it.statusApproval == REJECTED_STATUS.toString()
                ) {
                    isRejected = true
                    return@run
                } else isRejected = false
            }
        }
        return isRejected
    }

    private fun createRequestDoApprovalParam(statusApproval: Int, reason: String?) =
        DoApprovalRequest(
            letterId = letterId,
            accountId = PreferenceUtils.getAccount()?.id.orZero(),
            status = statusApproval.toString(),
            actor = actor,
            description = reason
        )

    private fun handleStatusApproval(status: String) {
        when (status) {
            ApprovalStatusEnum.HAS_APPROVED.status -> {
                val intent = Intent()
                intent.putExtra(LetterConstant.EXTRA_SUBMISSION_HAS_APPROVED, true)
                intent.putExtra(
                    LetterConstant.EXTRA_LETTER_TYPE,
                    viewModel.letterDetail.value?.letterType.orEmpty()
                )
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            ApprovalStatusEnum.HAS_REJECTED.status -> {
                val intent = Intent()
                intent.putExtra(LetterConstant.EXTRA_SUBMISSION_HAS_REJECTED, true)
                intent.putExtra(
                    LetterConstant.EXTRA_LETTER_TYPE,
                    viewModel.letterDetail.value?.letterType.orEmpty()
                )
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else -> {
            }
        }
    }
}