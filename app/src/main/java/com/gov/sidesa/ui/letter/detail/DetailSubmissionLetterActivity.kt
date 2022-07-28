package com.gov.sidesa.ui.letter.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailSubmissionLetterBinding
import com.gov.sidesa.utils.enums.CategoryLetterEnum

class DetailSubmissionLetterActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailSubmissionLetterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSubmissionLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        with(binding) {
            customToolbar.toolbarDetailProfile.apply {
                title = getString(R.string.letter_detail_submission_title)
                setNavigationOnClickListener { finish() }
            }

            textIdLetter.text = "21/07/2022/SKKDUCTKI"
            textLetterType.text = "Surat Keterangan Kerja Dan Untuk Calon Tenanga Kerja Indonesia"
            textLetterDateSubmission.text = "Kamis, 28 Juli 2022"
            textLetterStatus.text = "Menunggu Disetujui RT"
            historyApproval.setSubmissionDate("Kamis, 28 Juli 2022")
        }
    }
}