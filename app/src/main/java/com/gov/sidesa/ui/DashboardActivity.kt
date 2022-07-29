package com.gov.sidesa.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.data.letterlist.models.LettersModel
import com.gov.sidesa.databinding.ActivityDashboardBinding
import com.gov.sidesa.ui.approval.DetailApprovalLetterActivity
import com.gov.sidesa.ui.letter.detail.DetailSubmissionLetterActivity
import com.gov.sidesa.ui.letter.list.LetterListActivity
import com.gov.sidesa.ui.letter.list.needapproval.LetterNeedApprovalAdapter
import com.gov.sidesa.ui.letter.list.submission.LetterSubmissionAdapter
import com.gov.sidesa.ui.letter.template.LetterTemplateActivity
import com.gov.sidesa.ui.profile.edit.EditProfileKTPActivity
import com.gov.sidesa.ui.widget.notification_dialog.NotificationBottomSheet
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.enums.CategoryLetterEnum
import kotlinx.coroutines.FlowPreview

@FlowPreview
class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    private val viewModel by viewModels<DashboardViewModel>()

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
        initObserver()
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
            buttonChooseLetter.setOnClickListener {
                goToLetterTemplate()
            }
        }
    }

    private fun initObserver() = with(viewModel) {
        notificationState.observe(this@DashboardActivity) {
            showNotification(title = it.first, description = it.second)
        }
    }

    /**
     * Launcher to start activity for result Letter Template
     * actually listening for [LetterInputActivity] result
     */
    private val letterTemplateLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.onLetterTemplateResult(it.resultCode, it.data)
    }

    /**
     * Goto Letter Template Activity
     * show letter template list
     */
    private fun goToLetterTemplate() {
        val intent = LetterTemplateActivity.newIntent(this)
        letterTemplateLauncher.launch(intent)
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
        startActivity(Intent(this, DetailApprovalLetterActivity::class.java))
    }

    private fun onItemSubmissionClick(lettersModel: LettersModel) {
        startActivity(Intent(this, DetailSubmissionLetterActivity::class.java))
    }

    /**
     * Show notification dialog with bottom sheet behavior
     */
    private fun showNotification(title: String, description: String) {
        val tag = "notification_tag"

        showImmediately(supportFragmentManager, tag) {
            NotificationBottomSheet.newInstance(
                title = title,
                description = description
            )
        }
    }
}