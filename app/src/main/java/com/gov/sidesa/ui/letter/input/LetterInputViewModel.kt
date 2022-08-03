package com.gov.sidesa.ui.letter.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.domain.letter.input.usecases.GetLetterLayoutUseCase
import com.gov.sidesa.domain.letter.input.usecases.GetResourcesUseCase
import com.gov.sidesa.domain.letter.input.usecases.SaveLetterUseCase
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.mapper.asUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener
import com.gov.sidesa.utils.extension.orZero
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: SIDESA
 **/


@FlowPreview
class LetterInputViewModel(
    private val getLayout: GetLetterLayoutUseCase,
    private val getResourcesUseCase: GetResourcesUseCase,
    private val saveLetterUseCase: SaveLetterUseCase
) : BaseViewModel(), LetterInputViewHolderListener {
    /**
     * layout data
     */
    private val _layoutData = MutableLiveData<LetterLayout>()

    /**
     * widget list should render to view
     */
    private val _widgetList = MutableLiveData<List<BaseWidgetUiModel>>()
    val widgetList: LiveData<List<BaseWidgetUiModel>> get() = _widgetList

    val btnSubmitVisibilityState: LiveData<Boolean> get() = mLoadingState

    private val _closeViewState = MutableLiveData<Unit>()
    val closeViewState: LiveData<Unit> get() = _closeViewState

    /**
     * Widget Event
     */
    // edit text changed
    private val _editTextChanged = MutableSharedFlow<EditTextWidgetUiModel>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // drop-down changed
    private val _dropDownChanged = MutableSharedFlow<DropDownWidgetUiModel>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    /**
     * supporting view
     * - select list from menu
     */
    private val _menuList = MutableLiveData<Pair<DropDownWidgetUiModel, List<Resource>>>()
    val menuList: LiveData<Pair<DropDownWidgetUiModel, List<Resource>>> get() = _menuList

    // submit success state
    private val _onSubmitSuccess = MutableLiveData<Unit>()
    val onSubmitSuccess: LiveData<Unit> get() = _onSubmitSuccess

    init {
        observerEvent()
    }

    fun onLoad(layoutId: String, letterName: String) = viewModelScope.launch {
        showLoadingWidget()

        when (val response = getLayout.invoke(letterTypeId = layoutId, letterName = letterName)) {
            is NetworkResponse.Success -> {
                _layoutData.value = response.body
                _widgetList.value = response.body.asUiModel()
            }
            else -> {
                onResponseNotSuccess(response = response)
                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    private fun observerEvent() {
        viewModelScope.launch {
            _editTextChanged.debounce(500).collectLatest {
                doEditTextChanged(uiModel = it)
            }
        }

        viewModelScope.launch {
            _dropDownChanged.collectLatest {
                doDropDownChanged(uiModel = it)
            }
        }
    }

    /**
     * Update widget field when changed
     */
    private fun updateWidget(uiModel: BaseWidgetUiModel) {
        val components = _widgetList.value.orEmpty().toMutableList()

        components.forEachIndexed { index, baseLetterInputModel ->
            if (baseLetterInputModel.name == uiModel.name) {
                components[index] = uiModel
                return@forEachIndexed
            }
        }

        _widgetList.value = components
    }

    /**
     * process edit text changed from view holder
     */
    private fun doEditTextChanged(uiModel: EditTextWidgetUiModel) {
        updateWidget(uiModel = uiModel)
    }

    /**
     * process drop-down selected changed from view holder
     */
    private fun doDropDownChanged(uiModel: DropDownWidgetUiModel) {
        if (uiModel.api.isNotBlank()) {
            getResources(uiModel = uiModel)
        } else {
            mServerErrorState.value = GenericErrorResponse.customError(
                status = "no route not found"
            )
        }
    }

    private fun getResources(uiModel: DropDownWidgetUiModel) = viewModelScope.launch {
        showLoadingWidget()

        val components = _widgetList.value.orEmpty().toMutableList()

        when (val response =
            getResourcesUseCase.invoke(uiModel.api, uiModel.getApiParam(components))) {
            is NetworkResponse.Success -> {
                _menuList.value = Pair(uiModel, response.body)
            }
            else -> onResponseNotSuccess(response)
        }

        hideLoadingWidget()
    }

    /**
     * listening edit text changed from view-holder
     */
    override fun onEditTextChanged(model: EditTextWidgetUiModel) {
        _editTextChanged.tryEmit(model)
    }

    /**
     * listening drop-down clicked from view-holder
     */
    override fun onDropDownClick(model: DropDownWidgetUiModel) {
        _dropDownChanged.tryEmit(model)
    }

    /**
     * Submit letter submission
     */
    fun onSubmit() = viewModelScope.launch {
        showLoadingWidget()

        val letterTypeId = _layoutData.value?.letterTypeId
        val components = _widgetList.value.orEmpty()

        when (val result =
            saveLetterUseCase.invoke(letterTypeId = letterTypeId.orZero(), widget = components)) {
            is NetworkResponse.Success -> {
                _onSubmitSuccess.value = Unit
            }
            else -> onResponseNotSuccess(response = result)
        }

        hideLoadingWidget()
    }

    /**
     * close activity
     */
    fun onFinish() = viewModelScope.launch {
        _closeViewState.value = Unit
    }

    fun onMenuSelected(uiModel: DropDownWidgetUiModel, selected: Resource) = viewModelScope.launch {
        val model = uiModel.copy(value = selected.id.toString(), selectedText = selected.name)
        updateWidget(uiModel = model)
    }
}