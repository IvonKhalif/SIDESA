package com.gov.sidesa.ui.letter.input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.letter.input.models.InputType
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.models.divider.DividerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.header.HeaderWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.text_view.TextViewWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener
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
class LetterInputViewModel : BaseViewModel(), LetterInputViewHolderListener {
    private val _editText = MutableSharedFlow<EditTextWidgetUiModel>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val editText
        get() = _editText.debounce(750)


    private val _componentData = MutableLiveData<List<BaseLetterInputModel>>()
    val componentData: LiveData<List<BaseLetterInputModel>> get() = _componentData

    init {
        viewModelScope.launch {
            editText.collectLatest {
                val components = _componentData.value.orEmpty().toMutableList()

                components.forEachIndexed { index, baseLetterInputModel ->
                    if (baseLetterInputModel.name == it.name) {
                        components[index] = it
                        return@forEachIndexed
                    }
                }

                _componentData.value = components
            }
        }
    }

    fun onLoad() {
        _componentData.value = listOf(
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
                name = "districts",
                inputType = InputType.Text,
                title = "Kecamatan",
                api = "/test/test",
                apiType = "list",
                apiParam = null,
                value = "1",
                selectedText = "Pondok Kacang"
            ),
            DropDownWidgetUiModel(
                name = "country",
                inputType = InputType.Text,
                title = "Desa/Kelurahan",
                api = "/test/test",
                apiType = "list",
                apiParam = "districts",
                value = "1",
                selectedText = "Pondok Kacang Barat"
            )
        )
    }

    override fun onEditTextChanged(model: EditTextWidgetUiModel) {
        _editText.tryEmit(model)
    }

    override fun onDropDownClick(model: DropDownWidgetUiModel) {

    }

    fun onSubmit() {
        val components = _componentData.value.orEmpty().map {
            "${it.name} : ${it.value}"
        }
        Log.d("letter_input", components.joinToString("\n"))
    }
}