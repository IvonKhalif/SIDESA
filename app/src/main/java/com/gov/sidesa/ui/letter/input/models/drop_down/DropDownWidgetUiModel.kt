package com.gov.sidesa.ui.letter.input.models.drop_down

import com.gov.sidesa.domain.letter.input.models.InputType
import com.gov.sidesa.domain.letter.input.models.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class DropDownWidgetUiModel(
    override val name: String,
    override val value: String?,
    val selectedText: String?,
    val inputType: InputType,
    val title: String?,
    val api: String,
    val apiType: String?,
    val apiParam: String?
) : BaseWidgetUiModel(type = WidgetType.DropDown, name = name, value = value) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseWidgetUiModel): Boolean {
        val dropDown = newItem as? DropDownWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = dropDown)
                && inputType == dropDown.inputType
                && title == dropDown.title
                && api == dropDown.api
                && apiParam == dropDown.apiParam
                && value == dropDown.value // ensure update when selected item
    }

    fun getApiParam(components: List<BaseWidgetUiModel>): Map<String, String> {
        // return empty if api param is empty
        if (apiParam.isNullOrBlank()) return emptyMap()
        val params = hashMapOf<String, String>()

        // split api param if has mare param -> example: id_kecamatan, id_desa etc
        apiParam.split(";").forEach { p ->
            // cari component name yang berisikan nama parameter
            // contoh component name `kecamatan`, apiParam id_kecamatan.
            // maka ini di anggap ditemukan dan tidak null
            val param = components.firstOrNull {
                p.trim().contains(it.name)
            }

            if (param != null) {
                params[p] = param.value.orEmpty()
            }
        }

        return params
    }
}
