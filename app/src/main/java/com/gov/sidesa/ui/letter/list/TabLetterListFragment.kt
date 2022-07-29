package com.gov.sidesa.ui.letter.list

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
import com.gov.sidesa.data.letterlist.models.LettersModel
import com.gov.sidesa.databinding.TabLetterListBinding
import com.gov.sidesa.ui.approval.DetailApprovalLetterActivity
import com.gov.sidesa.ui.letter.detail.DetailSubmissionLetterActivity
import com.gov.sidesa.ui.letter.list.needapproval.LetterNeedApprovalAdapter
import com.gov.sidesa.ui.letter.list.submission.LetterSubmissionAdapter
import com.gov.sidesa.utils.constants.LetterConstant.ARG_IS_SUBMISSION_PAGE
import com.gov.sidesa.utils.constants.LetterConstant.ARG_TAB_CATEGORY

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
    private val category by lazy {
        requireArguments().getString(ARG_TAB_CATEGORY)
    }
    private val isSubmissionPage by lazy {
        requireArguments().getBoolean(ARG_IS_SUBMISSION_PAGE, false)
    }
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
        LetterNeedApprovalAdapter(
            listOf(
                LettersModel(
                    "1",
                    "Kamis, 21 Juli 2022",
                    "Surat Keterangan Kerja Dan Untuk Calon Tenaga Kerja Indonesia",
                    "ID: 21/07/2022/SKKDUCTKI"
                )
            ), ::onItemNeedApprovalClick
        )
    }

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
                recyclerNeedApproval.isVisible = false
                recyclerSubmission.isVisible = true
            } else {
                recyclerNeedApproval.isVisible = true
                recyclerSubmission.isVisible = false
            }
        }
    }

    private fun onItemNeedApprovalClick(lettersModel: LettersModel) {
        startActivity(Intent(context, DetailApprovalLetterActivity::class.java))
    }

    private fun onItemSubmissionClick(lettersModel: LettersModel) {
        startActivity(Intent(context, DetailSubmissionLetterActivity::class.java))
    }
}