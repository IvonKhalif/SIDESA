package com.gov.sidesa.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.data.letterlist.models.LettersModel
import com.gov.sidesa.databinding.ActivityDashboardBinding
import com.gov.sidesa.ui.letterlist.LetterListActivity
import com.gov.sidesa.ui.letterlist.needapproval.LetterNeedApprovalAdapter
import com.gov.sidesa.ui.letterlist.submission.LetterSubmissionAdapter
import com.gov.sidesa.ui.profile.edit.EditProfileActivity
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.enums.CategoryLetterEnum
import com.gov.sidesa.ui.profile.edit.EditProfileKTPActivity

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val submissionAdapter by lazy {
        LetterSubmissionAdapter(
            listOf(
                LettersModel(
                    "1",
                    "Kamis, 21 Juli 2022",
                    "Surat Keterangan Kerja Dan Untuk Calon Tenaga Kerja Indonesia",
                    "ID: 21/07/2022/SKKDUCTKI"
                )
            ), ::onItemSubmissionClick
        )
    }
    private val needApprovalAdapter by lazy {
        LetterNeedApprovalAdapter(listOf(
            LettersModel(
                "1",
                "Kamis, 21 Juli 2022",
                "Surat Keterangan Kerja Dan Untuk Calon Tenaga Kerja Indonesia",
                "ID: 21/07/2022/SKKDUCTKI"
            )
        ), ::onItemNeedApprovalClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        with(binding) {
            recyclerNeedApproval.adapter = needApprovalAdapter
            recyclerNeedApproval.layoutManager = LinearLayoutManager(
                this@DashboardActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerSubmission.adapter = submissionAdapter
            recyclerSubmission.layoutManager = LinearLayoutManager(
                this@DashboardActivity,
                LinearLayoutManager.VERTICAL,
                false
            )

            buttonAccount.setOnClickListener {
                startActivity(Intent(this@DashboardActivity, EditProfileKTPActivity::class.java))
            }
            buttonSeeAllNeedApproval.setOnClickListener {
                goToLetterList(CategoryLetterEnum.NEED_APPROVAL.category)
            }
            buttonSeeAllSubmission.setOnClickListener {
                goToLetterList(CategoryLetterEnum.SUBMISSION.category)
            }
        }
    }

    private fun goToLetterList(category: String?) {
        val intent = Intent(this@DashboardActivity, LetterListActivity::class.java)
        intent.putExtra(
            LetterConstant.EXTRA_LETTER_CATEGORY,
            category
        )
        startActivity(intent)
    }

    private fun onItemNeedApprovalClick(lettersModel: LettersModel) {
        //TODO("Not yet implemented")
    }

    private fun onItemSubmissionClick(lettersModel: LettersModel) {
        //TODO("Not yet implemented")
    }
}