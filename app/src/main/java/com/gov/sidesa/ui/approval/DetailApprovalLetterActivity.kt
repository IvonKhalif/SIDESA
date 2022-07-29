package com.gov.sidesa.ui.approval

import android.app.Dialog
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

class DetailApprovalLetterActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailApprovalLetterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailApprovalLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        with(binding) {
            customToolbar.toolbarDetailProfile.apply {
                title = getString(R.string.letter_detail_submission_title)
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

            }
        }
    }

    private fun onRejectClick() {
        val bottomSheet = RejectSubmissionBottomSheet()
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
            dialog.dismiss()
        }

        bindingToast.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}