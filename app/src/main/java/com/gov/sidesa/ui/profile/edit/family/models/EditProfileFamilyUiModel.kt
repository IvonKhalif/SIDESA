package com.gov.sidesa.ui.profile.edit.family.models

import com.gov.sidesa.R
import com.gov.sidesa.domain.regions.models.Region
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

data class EditProfileFamilyUiModel(
    val id: String = UUID.randomUUID().toString(),
    val type: EditProfileFamilyViewType = EditProfileFamilyViewType.Form,
    val relationFamily: String = "",
    val name: String = "",
    val ktpNumber: String = "",
    val birthPlace: String = "",
    val birthDate: Date? = null,
    val address: String = "",
    val differentAddress: Boolean = false,
    val province: Region? = null,
    val city: Region? = null,
    val district: Region? = null,
    val village: Region? = null,
) {

    fun areItemsTheSame(
        newItem: EditProfileFamilyUiModel
    ): Boolean = id == newItem.id
            && province?.id == newItem.province?.id
            && city?.id == newItem.city?.id
            && district?.id == newItem.district?.id
            && village?.id == newItem.village?.id
            && differentAddress == newItem.differentAddress
            && birthDate == newItem.birthDate

    fun areContentsTheSame(
        newItem: EditProfileFamilyUiModel
    ): Boolean = areItemsTheSame(newItem = newItem)

    val nameTitle
        get() = when (relationFamily) {
            "ayah" -> {
                R.string.fullname_father
            }
            "ibu" -> {
                R.string.fullname_mother
            }
            else -> {
                R.string.fullname
            }
        }

    val inputStatusVisibilityState
        get() = relationFamily == "istri" || relationFamily == "suami"

    val birtDateFormatted: String
        get() = run {
            val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            birthDate?.let {
                format.format(it)
            } ?: run { "" }
        }
}
