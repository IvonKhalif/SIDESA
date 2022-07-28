package com.gov.sidesa.ui.letter.input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.letter.input.models.InputType
import com.gov.sidesa.domain.letter.input.models.Resource
import com.gov.sidesa.domain.letter.input.usecases.GetResourcesUseCase
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.models.divider.DividerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.header.HeaderWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.text_view.TextViewWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
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
    private val getResourcesUseCase: GetResourcesUseCase
) : BaseViewModel(), LetterInputViewHolderListener {

    /**
     * widget list should render to view
     */
    private val _widgetList = MutableLiveData<List<BaseLetterInputModel>>()
    val widgetList: LiveData<List<BaseLetterInputModel>> get() = _widgetList

    val btnSubmitVisibilityState: LiveData<Boolean> get() = mLoadingState

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

    init {
        observerEvent()
    }

    fun onLoad(layoutId: String) = viewModelScope.launch {
        showLoadingWidget()
        delay(2000)
        _widgetList.value = listOf(
            HeaderWidgetUiModel(title = "Surat Izin Tempat Usaha (SITU)"),
            TextViewWidgetUiModel(name = "name", title = "Nama", value = "Yovi Eka Putra"),
            TextViewWidgetUiModel(
                name = "address",
                title = "Alamat",
                value = "Jalanin aja, RT 001 RW 001, Bojong Utara, Bojong, Kabupaten Tangerang, Banten."
            ),
            TextViewWidgetUiModel(name = "work", title = "Pekerjaan", value = "Pelajar/Mahasiswa"),
            DividerWidgetUiModel(name = "divider"),
            EditTextWidgetUiModel(
                name = "type_of_business",
                inputType = InputType.Text,
                title = "Jenis Usaha"
            ),
            EditTextWidgetUiModel(
                name = "company_name",
                inputType = InputType.Email,
                title = "Nama Usaha"
            ),
            EditTextWidgetUiModel(
                name = "company_address",
                inputType = InputType.Number,
                title = "Alamat Tempat Usaha"
            ),
            DropDownWidgetUiModel(
                name = "kecamatan",
                inputType = InputType.Text,
                title = "Kecamatan",
                api = "http://desa.digidana.id/api/v1/master/kecamatan?id_kota=3603",
                apiType = "list",
                apiParam = null,
                value = "3603030",
                selectedText = "Pondok Kacang"
            ),
            DropDownWidgetUiModel(
                name = "country",
                inputType = InputType.Text,
                title = "Desa/Kelurahan",
                api = "http://desa.digidana.id/api/v1/master/kelurahan",
                apiType = "list",
                apiParam = "id_kecamatan",
                value = "1",
                selectedText = "Pondok Kacang Barat"
            )
        )
        hideLoadingWidget()
    }

    private fun observerEvent() {
        viewModelScope.launch {
            _editTextChanged.debounce(750).collectLatest {
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
    private fun updateWidget(uiModel: BaseLetterInputModel) {
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

        when (val response = getResourcesUseCase.invoke(uiModel.api, uiModel.getApiParam(components))) {
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
    fun onSubmit() {
        val components = _widgetList.value.orEmpty().map {
            "${it.name} : ${it.value}"
        }
        Log.d("letter_input", components.joinToString("\n"))
    }

    fun onMenuSelected(uiModel: DropDownWidgetUiModel, selected: Resource) = viewModelScope.launch {
        val model = uiModel.copy(value = selected.id, selectedText = selected.name)
        updateWidget(uiModel = model)
    }
}