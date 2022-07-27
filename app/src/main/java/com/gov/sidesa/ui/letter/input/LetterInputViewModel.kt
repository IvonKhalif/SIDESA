package com.gov.sidesa.ui.letter.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.models.divider.DividerWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.header.HeaderWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.text_view.TextViewWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: SIDESA
 **/


class LetterInputViewModel : BaseViewModel(), LetterInputViewHolderListener {

    private val _componentData = MutableLiveData<List<BaseLetterInputModel>>()
    val componentData: LiveData<List<BaseLetterInputModel>> get() = _componentData

    fun onLoad() {
        _componentData.value = listOf(
            HeaderWidgetUiModel(title = "Surat Izin Tempat Usaha (SITU)"),
            TextViewWidgetUiModel(name = "name", title = "Nama", value = "Yovi Eka Putra"),
            TextViewWidgetUiModel(name = "address", title = "Alamat", value = "Jalanin aja, RT 001 RW 001, Bojong Utara, Bojong, Kabupaten Tangerang, Banten."),
            TextViewWidgetUiModel(name = "work", title = "Pekerjaan", value = "Pelajar/Mahasiswa"),
            DividerWidgetUiModel(name = "divider"),
            EditTextWidgetUiModel(
                name = "type_of_business",
                inputType = "text",
                title = "Jenis Usaha"
            ),
            EditTextWidgetUiModel(
                name = "company_name",
                inputType = "text",
                title = "Nama Usaha"
            ),
            EditTextWidgetUiModel(
                name = "company_address",
                inputType = "text",
                title = "Alamat Tempat Usaha"
            ),
            DropDownWidgetUiModel(
                name = "districts",
                inputType = "text",
                title = "Kecamatan",
                api = "/test/test",
                apiType = "list",
                apiParam = null,
                selectedId = null,
            ),
            DropDownWidgetUiModel(
                name = "country",
                inputType = "text",
                title = "Desa/Kelurahan",
                api = "/test/test",
                apiType = "list",
                apiParam = "districts",
                selectedId = null
            )
        )
    }

    override fun onEditTextChanged(model: EditTextWidgetUiModel) {

    }

    override fun onDropDownClick(model: DropDownWidgetUiModel) {

    }
}