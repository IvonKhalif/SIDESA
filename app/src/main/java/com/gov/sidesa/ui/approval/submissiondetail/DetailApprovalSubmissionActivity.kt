package com.gov.sidesa.ui.approval.submissiondetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailApprovalSubmissionBinding

class DetailApprovalSubmissionActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailApprovalSubmissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailApprovalSubmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainView()
    }

    private fun mainView() {
        with(binding) {
            customToolbar.toolbarDetailProfile.apply {
                title = getString(R.string.letter_detail_submission_title)
                setNavigationOnClickListener { finish() }
            }
        }
    }
}