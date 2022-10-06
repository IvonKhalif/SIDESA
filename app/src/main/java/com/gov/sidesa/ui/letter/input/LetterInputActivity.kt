package com.gov.sidesa.ui.letter.input

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.base.findSheetByTag
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.databinding.ActivityLetterInputBinding
import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.ui.letter.input.adapter.LetterInputAdapter
import com.gov.sidesa.ui.letter.input.models.date_picker.DatePickerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactoryImpl
import com.gov.sidesa.utils.extension.orToday
import com.gov.sidesa.utils.extension.toDate
import com.gov.sidesa.utils.extension.utcToLocale
import com.gov.sidesa.utils.picker.FileUtil
import com.gov.sidesa.utils.picker.MenuIconWithHeadlineAdapter
import com.gov.sidesa.utils.picker.MenuIconWithHeadlineBottomSheet
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
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

    private val attachmentLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { onAttachmentSelected(uri = it) }

    private val argsLayoutId by lazy {
        intent.getStringExtra(ARGS_LAYOUT_ID).orEmpty()
    }

    private val argsLayoutName by lazy {
        intent.getStringExtra(ARGS_LAYOUT_NAME).orEmpty()
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
    private fun initEvent() = with(binding) {

        viewModel.onLoad(layoutId = argsLayoutId, letterName = argsLayoutName)

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            viewModel.onFinish()
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

        btnSubmitVisibilityState.observe(this@LetterInputActivity) {
            binding.buttonSave.isVisible = it.not()
        }

        closeViewState.observe(this@LetterInputActivity) {
            finish()
        }

        onSubmitSuccess.observe(this@LetterInputActivity) {
            finishWithSuccessState()
        }

        datePickerClicked.observe(this@LetterInputActivity) {
            showDatePicker(uiModel = it)
        }

        attachmentClicked.observe(this@LetterInputActivity) {
            attachmentLauncher.launch(it.fileType.toTypedArray())
        }
    }

    /**
     * prepare content view
     */
    private fun initView() = with(binding) {
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
            areContentTheSame = { oldItem, newItem -> oldItem == newItem },
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

    /**
     * prepare intent for return parcelable
     */
    private fun finishWithSuccessState() {
        val intent = Intent().apply {
            putExtra(RESULT_TITLE, getString(R.string.letter_input_success_dialog_title))
            putExtra(
                RESULT_CONTENT,
                getString(R.string.letter_input_success_dialog_description, argsLayoutName)
            )
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    /**
     * date-picker dialog
     */
    private fun showDatePicker(uiModel: DatePickerWidgetUiModel) {
        showImmediately(supportFragmentManager, "select_date_picker") {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.choose_birth_date)
                .setSelection(uiModel.value?.toDate().orToday().utcToLocale())
                .build()
            picker.addOnPositiveButtonClickListener {
                viewModel.onDatePickerSelected(model = uiModel, millis = it)
            }
            picker
        }
    }

    /**
     * onAttachmentSelected process
     */
    private fun onAttachmentSelected(uri: Uri?) {
        if (uri == null) return

        handleLoadingWidget(isLoading = true)

        lifecycleScope.launch {
            val file = try {
                FileUtil.from(this@LetterInputActivity, uri)
            } catch (e: Exception) {
                null
            }

            if (file != null) {
                val result = if (file.extension.contains("pdf")) {
                    file
                } else {
                    Compressor.compress(
                        context = this@LetterInputActivity,
                        imageFile = file
                    ) {
                        resolution(1280, 720)
                        quality(80)
                        size(1_048_576) // 1 MB
                    }
                }

                handleLoadingWidget(isLoading = false)
                // result can bigger from original
                if (file.length() < result.length()) {
                    viewModel.onAttachmentSelected(file = file)
                } else {
                    viewModel.onAttachmentSelected(file = result)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARGS_LAYOUT_ID = "layout_id"
        private const val ARGS_LAYOUT_NAME = "layout_name"

        const val RESULT_TITLE = "result_title"
        const val RESULT_CONTENT = "result_content"

        fun newIntent(
            context: Context,
            layoutId: String,
            layoutName: String
        ): Intent {
            val intent = Intent(context, LetterInputActivity::class.java)
            intent.putExtra(ARGS_LAYOUT_ID, layoutId)
            intent.putExtra(ARGS_LAYOUT_NAME, layoutName)
            return intent
        }
    }
}