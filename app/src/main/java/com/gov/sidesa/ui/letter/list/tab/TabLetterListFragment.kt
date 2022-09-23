package com.gov.sidesa.ui.letter.list.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseFragment
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.databinding.TabLetterListBinding
import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterListApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.ui.approval.DetailApprovalLetterActivity
import com.gov.sidesa.ui.letter.detail.DetailSubmissionLetterActivity
import com.gov.sidesa.ui.letter.list.needapproval.LetterNeedApprovalAdapter
import com.gov.sidesa.ui.letter.list.submission.LetterSubmissionAdapter
import com.gov.sidesa.ui.widget.notification_dialog.NotificationBottomSheet
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.constants.LetterConstant.ARG_IS_SUBMISSION_PAGE
import com.gov.sidesa.utils.constants.LetterConstant.ARG_TAB_CATEGORY
import com.gov.sidesa.utils.enums.TypeSubmissionEnum
import com.gov.sidesa.utils.extension.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabLetterListFragment : BaseFragment() {
    companion object {
        internal fun newInstance(
            type: String?,
            fromSubmissionPage: Boolean
        ): TabLetterListFragment {
            val args = bundleOf(
                ARG_TAB_CATEGORY to type, ARG_IS_SUBMISSION_PAGE
                        to fromSubmissionPage
            )
            return TabLetterListFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var binding: TabLetterListBinding
    private val viewModel by viewModel<TabLetterListViewModel>()
    private val category by lazy {
        requireArguments().getString(ARG_TAB_CATEGORY)
    }
    private val isSubmissionPage by lazy {
        requireArguments().getBoolean(ARG_IS_SUBMISSION_PAGE, false)
    }
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

    private var actor = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.tab_letter_list, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView()
        initObserver()
    }

    private fun initObserver() = viewModel.apply {

        submissionLettersLiveData.observeNonNull(
            viewLifecycleOwner,
            ::handleSubmissionLetters
        )
        approvalLettersLiveData.observeNonNull(
            viewLifecycleOwner,
            ::handleApprovalLetters
        )
        loadingState.observe(viewLifecycleOwner) {
            handleLoadingWidget(childFragmentManager, isLoading = it)
        }
    }

    private fun mainView() {
        with(binding) {
            recyclerNeedApproval.adapter = needApprovalAdapter
            recyclerNeedApproval.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerSubmission.adapter = submissionAdapter
            recyclerSubmission.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            if (isSubmissionPage) {
                viewModel.getSubmissionLetters()
                recyclerNeedApproval.isVisible = false
                recyclerSubmission.isVisible = true
            } else {
                viewModel.getApprovalLetters()
                recyclerNeedApproval.isVisible = true
                recyclerSubmission.isVisible = false
            }
        }
    }

    private fun onItemNeedApprovalClick(letterListApprovalModel: LetterListApprovalModel) {
        val intent = Intent(activity, DetailApprovalLetterActivity::class.java)
        intent.putExtra(LetterConstant.EXTRA_SUBMISSION_LETTER_ID, letterListApprovalModel.letterId)
        intent.putExtra(LetterConstant.EXTRA_ACTOR_APPROVAL, actor)
        resultLauncher.launch(intent)
    }

    private fun onItemSubmissionClick(letterSubmissionModel: LetterSubmissionModel) {
        val intent = Intent(activity, DetailSubmissionLetterActivity::class.java)
        intent.putExtra(LetterConstant.EXTRA_SUBMISSION_LETTER_ID, letterSubmissionModel.letterId)
        startActivity(intent)
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
            if (category.isNullOrBlank())
                needApprovalAdapter.items = letterApprovalModel.letters
            else
                needApprovalAdapter.items = letterApprovalModel.letters.filter {
                    it.letterDetail == category
                }
        }
    }

    private fun handleSubmissionLetters(list: List<LetterSubmissionModel>) {
        if (category.isNullOrBlank())
            submissionAdapter.items = list
        else
            if (category == TypeSubmissionEnum.FINISH.type)
                submissionAdapter.items = list.filter {
                    it.letterStatus == category || it.letterStatus == TypeSubmissionEnum.LETTER_PRINT_OUT.type
                }
            else
                submissionAdapter.items = list.filter {
                    it.letterStatus == category
                }
    }

    override fun onResultData(result: Intent?) {
        super.onResultData(result)
        val user = PreferenceUtils.getAccount()
        val letterType = result?.getStringExtra(LetterConstant.EXTRA_LETTER_TYPE).orEmpty()
        if (result?.getBooleanExtra(LetterConstant.EXTRA_SUBMISSION_HAS_APPROVED, false) == true) {
            viewModel.getSubmissionLetters()
            showNotification(
                getString(R.string.letter_detail_success_approve_submission_headline),
                getString(
                    R.string.letter_detail_success_approve_submission_subheadline,
                    letterType,
                    user?.name.orEmpty()
                )
            )
        } else if (result?.getBooleanExtra(
                LetterConstant.EXTRA_SUBMISSION_HAS_REJECTED,
                false
            ) == true
        ) {
            viewModel.getSubmissionLetters()
            showNotification(
                getString(R.string.letter_detail_success_reject_submission_headline),
                getString(
                    R.string.letter_detail_success_reject_submission_subheadline,
                    letterType,
                    user?.name.orEmpty()
                )
            )
        }
    }

    private fun showNotification(title: String, description: String) {
        val tag = "notification_tag"

        showImmediately(childFragmentManager, tag) {
            NotificationBottomSheet.newInstance(
                title = title,
                description = description
            )
        }
    }
}