package com.gov.sidesa.ui.approval

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailApprovalLetterBinding
import com.gov.sidesa.databinding.DialogConfirmationApprovalBinding
import com.gov.sidesa.ui.approval.submissiondetail.DetailApprovalSubmissionActivity
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_LETTER_DETAIL_SUBMISSION
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailApprovalLetterActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailApprovalLetterBinding
    private val viewModel by viewModel<DetailApprovalViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailApprovalLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
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

        viewModel.onLoad("21", "Surat Keterangan Kerja Dan Untuk Calon Tenaga Kerja Indonesia")
    }

    private fun goToDetailApproval() {
        val intent = Intent(this, DetailApprovalSubmissionActivity::class.java)
        intent.putExtra(EXTRA_LETTER_DETAIL_SUBMISSION, viewModel.letterDetail.value)
        startActivity(intent)
    }

    private fun onRejectClick() {
        val bottomSheet = RejectSubmissionBottomSheet {
            val intent = Intent()
            intent.putExtra(LetterConstant.EXTRA_SUBMISSION_HAS_REJECTED, true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun onHistoryApprovalClick() {
        val bottomSheet = HistoryApprovalBottomSheet()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
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
            val intent = Intent()
            intent.putExtra(LetterConstant.EXTRA_SUBMISSION_HAS_APPROVED, true)
            setResult(Activity.RESULT_OK, intent)
            dialog.dismiss()
            finish()
        }

        bindingToast.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}