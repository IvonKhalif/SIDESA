package com.gov.sidesa.ui.letter.template

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityLetterTemplateBinding
import com.gov.sidesa.ui.letter.input.LetterInputActivity
import com.gov.sidesa.ui.letter.template.models.LetterTemplateUiModel
import com.gov.sidesa.utils.picker.MenuIconWithHeadlineAdapter
import com.gov.sidesa.utils.picker.RecyclerViewItemDecoration
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
class LetterTemplateActivity : BaseActivity() {

    private var _binding: ActivityLetterTemplateBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<LetterTemplateViewModel>()

    private val adapter by lazy {
        MenuIconWithHeadlineAdapter(
            setIconLeft = { R.drawable.ic_letter_templates },
            setHeadline = { it.name },
            areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            areContentTheSame = { oldItem, newItem -> oldItem == newItem },
            onClick = ::onItemSelected,
            customUi = {
                tvHeadline.maxLines = 2
                tvHeadline.ellipsize = TextUtils.TruncateAt.END
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLetterTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    /**
     * prepare content view
     */
    private fun initView() = with(binding) {
        customToolbar.apply {
            toolbarDetailProfile.title = getString(R.string.letter_choose_template)
            toolbarDetailProfile.setNavigationIcon(R.drawable.ic_arrow_left)
        }

        rvTemplates.apply {
            adapter = this@LetterTemplateActivity.adapter
            layoutManager = LinearLayoutManager(this@LetterTemplateActivity)
            addItemDecoration(RecyclerViewItemDecoration())
        }
    }

    /**
     * observing view-model data
     */
    private fun initObserver() = with(viewModel) {
        loadingState.observe(this@LetterTemplateActivity) {
            handleLoadingWidget(isLoading = it)
        }

        networkErrorState.observe(this@LetterTemplateActivity) {
            showErrorMessage(throwable = it)
        }

        serverErrorState.observe(this@LetterTemplateActivity) {
            showErrorMessage(message = it.status.orEmpty())
        }

        templateData.observe(this@LetterTemplateActivity) {
            adapter.submitList(it)
        }
    }

    /**
     * set listener for handling input from user
     */
    private fun initEvent() = with(binding) {
        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }
    }

    private fun onItemSelected(template: LetterTemplateUiModel) {
        val intent = LetterInputActivity.newIntent(
            context = this,
            layoutId = template.id.toString(),
            layoutName = template.name
        )
        startActivity(intent)
    }
}