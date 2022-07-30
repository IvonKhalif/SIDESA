package com.gov.sidesa.ui.approval.submissiondetail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.data.letterdetail.models.DetailApprovalResponse
import com.gov.sidesa.databinding.ActivityDetailApprovalSubmissionBinding
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.ui.letter.input.adapter.LetterInputAdapter
import com.gov.sidesa.ui.letter.input.models.mapper.WidgetUiModelMapper
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactoryImpl
import com.gov.sidesa.utils.constants.LetterConstant
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailApprovalSubmissionActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailApprovalSubmissionBinding
    private val viewModel: DetailApprovalSubmissionViewModel by viewModel()

    private val viewHolderFactory: LetterInputViewHolderFactory by lazy {
        LetterInputViewHolderFactoryImpl()
    }

    private val adapter by lazy {
        LetterInputAdapter(viewHolderFactory = viewHolderFactory, listener = viewModel)
    }

    private val letterDetail by lazy {
        intent.getParcelableExtra<DetailApprovalModel>(LetterConstant.EXTRA_LETTER_DETAIL_SUBMISSION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailApprovalSubmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainView()
    }

    private fun mainView() {
        with(binding) {
            rvComponents.apply {
                rvComponents.adapter = this@DetailApprovalSubmissionActivity.adapter
                rvComponents.layoutManager = LinearLayoutManager(this@DetailApprovalSubmissionActivity)
            }
            rvComponents.post {
                adapter.submitList(getWidgetList())
            }
            customToolbar.toolbarDetailProfile.apply {
                title = getString(R.string.letter_detail_submission_title)
                setNavigationOnClickListener { finish() }
            }
        }
    }

    private fun getWidgetList() = letterDetail?.documentFilled?.map {
        WidgetUiModelMapper.asUiModel(widget = it)
    }

}