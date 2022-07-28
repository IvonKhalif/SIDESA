package com.gov.sidesa.ui.letter.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.base.findSheetByTag
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.databinding.ActivityLetterInputBinding
import com.gov.sidesa.domain.letter.input.models.Resource
import com.gov.sidesa.ui.letter.input.adapter.LetterInputAdapter
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactoryImpl
import com.gov.sidesa.utils.picker.MenuIconWithHeadlineAdapter
import com.gov.sidesa.utils.picker.MenuIconWithHeadlineBottomSheet
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
class LetterInputActivity : BaseActivity() {

    private var _binding: ActivityLetterInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<LetterInputViewModel>()

    private val viewHolderFactory: LetterInputViewHolderFactory by lazy {
        LetterInputViewHolderFactoryImpl()
    }

    private val adapter by lazy {
        LetterInputAdapter(viewHolderFactory = viewHolderFactory, listener = viewModel)
    }

    private val argsLayoutId by lazy {
        intent.getStringExtra(ARGS_LAYOUT_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLetterInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    /**
     * set listener for handling input from user
     */
    private fun initEvent() = with (binding){

        viewModel.onLoad(layoutId = argsLayoutId.orEmpty())

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }

        buttonSave.setOnClickListener {
            viewModel.onSubmit()
        }
    }

    /**
     * observing view-model data
     */
    private fun initObserver() = with(viewModel) {
        loadingState.observe(this@LetterInputActivity) {
            handleLoadingWidget(isLoading = it)
        }

        networkErrorState.observe(this@LetterInputActivity) {
            showErrorMessage(throwable = it)
        }

        serverErrorState.observe(this@LetterInputActivity) {
            showErrorMessage(message = it.status.orEmpty())
        }

        widgetList.observe(this@LetterInputActivity) {
            binding.rvComponents.post {
                adapter.submitList(it)
            }
        }

        menuList.observe(this@LetterInputActivity) {
            showMenuSheet(uiModel = it.first, items = it.second)
        }
    }

    /**
     * prepare content view
     */
    private fun initView() = with (binding) {
        customToolbar.apply {
            toolbarDetailProfile.title = getString(R.string.letter_input_submission)
            toolbarDetailProfile.setNavigationIcon(R.drawable.ic_arrow_left)
        }

        rvComponents.apply {
            rvComponents.adapter = this@LetterInputActivity.adapter
            rvComponents.layoutManager = LinearLayoutManager(this@LetterInputActivity)
        }
    }

    /**
     * show bottom sheet for menu list as general choice
     */
    private fun showMenuSheet(uiModel: DropDownWidgetUiModel, items: List<Resource>) {
        val sheetTag = "menu_sheet_tag"
        val adapter = MenuIconWithHeadlineAdapter<Resource>(
            setIconLeft = { 0 },
            setHeadline = { it.name },
            onClick = { item ->
                viewModel.onMenuSelected(uiModel = uiModel, selected = item)
                supportFragmentManager.findSheetByTag(tag = sheetTag)?.dismissAllowingStateLoss()
            },
            customUi = {
                ivIcon.isVisible = false
            },
            areContentTheSame = {oldItem, newItem -> oldItem == newItem },
            areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
        )
        adapter.submitList(items)

        showImmediately(supportFragmentManager, tag = sheetTag) {
            MenuIconWithHeadlineBottomSheet.newInstance(
                title = uiModel.title.orEmpty(),
                adapter = adapter
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARGS_LAYOUT_ID = "layout_id"

        fun newIntent(
            context: Context,
            layoutId: String
        ): Intent {
            val intent = Intent(context, LetterInputActivity::class.java)
            intent.putExtra(ARGS_LAYOUT_ID, layoutId)
            return intent
        }
    }
}