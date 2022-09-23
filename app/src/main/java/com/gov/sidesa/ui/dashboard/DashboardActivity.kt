package com.gov.sidesa.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.databinding.ActivityDashboardBinding
import com.gov.sidesa.domain.comingsoon.models.ComingSoonModel
import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterListApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.ui.approval.DetailApprovalLetterActivity
import com.gov.sidesa.ui.dashboard.comingsoon.ComingSoonAdapter
import com.gov.sidesa.ui.letter.detail.DetailSubmissionLetterActivity
import com.gov.sidesa.ui.letter.list.LetterListActivity
import com.gov.sidesa.ui.letter.list.needapproval.LetterNeedApprovalAdapter
import com.gov.sidesa.ui.letter.list.submission.LetterSubmissionAdapter
import com.gov.sidesa.ui.letter.template.LetterTemplateActivity
import com.gov.sidesa.ui.profile.ProfileActivity
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPActivity
import com.gov.sidesa.ui.widget.notification_dialog.NotificationBottomSheet
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_ACTOR_APPROVAL
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_LETTER_TYPE
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_SUBMISSION_HAS_APPROVED
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_SUBMISSION_HAS_REJECTED
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_SUBMISSION_LETTER_ID
import com.gov.sidesa.utils.constants.LetterConstant.EXTRA_SUCCESS_REGISTRATION
import com.gov.sidesa.utils.constants.UserExtrasConstant.PROFILE_NOT_COMPLETE
import com.gov.sidesa.utils.enums.CategoryLetterEnum
import com.gov.sidesa.utils.enums.TypeApprovalEnum
import com.gov.sidesa.utils.extension.observeNonNull
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    private val viewModel by viewModel<DashboardViewModel>()
    private var actor = ""

    private val submissionAdapter by lazy {
        LetterSubmissionAdapter(
            emptyList(), ::onItemSubmissionClick
        )
    }
    private val needApprovalAdapter by lazy {
        LetterNeedApprovalAdapter(
            emptyList(), ::onItemNeedApprovalClick
        )
    }

    private val comingSoonAdapter by lazy {
        ComingSoonAdapter(
            listOf(
                ComingSoonModel(R.drawable.ic_call, getString(R.string.dashboard_coming_soon_complaint_service)),
                ComingSoonModel(R.drawable.ic_news, getString(R.string.dashboard_coming_soon_village_news))
            )
        )
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
            recyclerComingSoonFeature.adapter = comingSoonAdapter
            recyclerComingSoonFeature.layoutManager = LinearLayoutManager(
                this@DashboardActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            buttonAccount.setOnClickListener {

                doClickOtherThanRegister {
                    startActivity(Intent(this@DashboardActivity, ProfileActivity::class.java))
                }
            }
            buttonSeeAllNeedApproval.setOnClickListener {
                doClickOtherThanRegister {
                    goToLetterList(CategoryLetterEnum.NEED_APPROVAL.category)
                }
            }
            buttonSeeAllSubmission.setOnClickListener {
                doClickOtherThanRegister {
                    goToLetterList(CategoryLetterEnum.SUBMISSION.category)
                }
            }
            buttonChooseLetter.setOnClickListener {
                doClickOtherThanRegister(::goToLetterTemplate)
            }
            buttonRegisterNow.setOnClickListener {
                val intent = Intent(this@DashboardActivity, RegistrationKTPActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

    private fun initObserver() = with(viewModel) {
        notificationState.observe(this@DashboardActivity) {
            showNotification(title = it.first, description = it.second)
        }
        userProfileLiveData.observe(this@DashboardActivity) {
            binding.buttonRegisterNow.isVisible = it.account.statusUser == PROFILE_NOT_COMPLETE
        }
        submissionLettersLiveData.observeNonNull(this@DashboardActivity, ::handleSubmissionLetters)
        approvalLettersLiveData.observeNonNull(this@DashboardActivity, ::handleApprovalLetters)
        loadingState.observe(this@DashboardActivity) {
            handleLoadingWidget(isLoading = it)
        }
    }

    private fun handleApprovalLetters(letterApprovalModel: LetterApprovalModel) {
        actor = when {
            letterApprovalModel.profile.isRt -> {
                LetterConstant.TYPE_APPROVAL_RT
            }
            letterApprovalModel.profile.isRw -> {
                LetterConstant.TYPE_APPROVAL_RW
            }
            else -> ""
        }
        if (letterApprovalModel.profile.isRt || letterApprovalModel.profile.isRw) {
            needApprovalAdapter.items = letterApprovalModel.letters.filter {
                it.letterDetail == TypeApprovalEnum.NOT_APPROVED_YET.type
            }
            if (letterApprovalModel.letters.isNotEmpty())
                binding.containerNeedApproval.isVisible = true
        }
    }

    private fun handleSubmissionLetters(list: List<LetterSubmissionModel>) {
        submissionAdapter.items = list
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

    private fun onItemNeedApprovalClick(letterListApprovalModel: LetterListApprovalModel) {
        val intent = Intent(this, DetailApprovalLetterActivity::class.java)
        intent.putExtra(EXTRA_SUBMISSION_LETTER_ID, letterListApprovalModel.letterId)
        intent.putExtra(EXTRA_ACTOR_APPROVAL, actor)
        resultLauncher.launch(intent)
    }

    private fun onItemSubmissionClick(letterSubmissionModel: LetterSubmissionModel) {
        val intent = Intent(this, DetailSubmissionLetterActivity::class.java)
        intent.putExtra(EXTRA_SUBMISSION_LETTER_ID, letterSubmissionModel.letterId)
        startActivity(intent)
    }

    /**
     * Show notification dialog with bottom sheet behavior
     */
    private fun showNotification(title: String, description: String) {
        val tag = "notification_tag"
        viewModel.updateDataUserLocal()
        showImmediately(supportFragmentManager, tag) {
            NotificationBottomSheet.newInstance(
                title = title,
                description = description
            )
        }
    }

    override fun onResultData(result: Intent?) {
        super.onResultData(result)
        val user = PreferenceUtils.getProfile()?.account
        val letterType = result?.getStringExtra(EXTRA_LETTER_TYPE).orEmpty()
        if (result?.getBooleanExtra(EXTRA_SUBMISSION_HAS_APPROVED, false) == true) {
            showNotification(
                getString(R.string.letter_detail_success_approve_submission_headline),
                getString(
                    R.string.letter_detail_success_approve_submission_subheadline,
                    letterType,
                    user?.name.orEmpty()
                )
            )
        } else if (result?.getBooleanExtra(EXTRA_SUBMISSION_HAS_REJECTED, false) == true) {
            showNotification(
                getString(R.string.letter_detail_success_reject_submission_headline),
                getString(
                    R.string.letter_detail_success_reject_submission_subheadline,
                    letterType,
                    user?.name.orEmpty()
                )
            )
        } else if (result?.getBooleanExtra(EXTRA_SUCCESS_REGISTRATION, false) == true) {
            viewModel.updateDataUserLocal()
        }
    }

    private fun doClickOtherThanRegister(onHasRegister: () -> Unit) {
        val user = viewModel.userProfileLiveData.value?.account
        if (user?.statusUser == PROFILE_NOT_COMPLETE)
            showNotification(
                getString(R.string.dashboard_must_regist_first_headline),
                getString(R.string.dashboard_must_regist_first_subheadline)
            )
        else
            onHasRegister()
    }
}