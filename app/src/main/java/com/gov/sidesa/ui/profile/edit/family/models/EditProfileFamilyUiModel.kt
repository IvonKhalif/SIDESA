package com.gov.sidesa.ui.profile.edit.family.models

import com.gov.sidesa.domain.regions.models.Region
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
    val birthDate: Date = Date(),
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

    fun areContentsTheSame(
        newItem: EditProfileFamilyUiModel
    ): Boolean = areItemsTheSame(newItem = newItem)

    val inputStatusVisibilityState
        get() = relationFamily == "istri" || relationFamily == "suami"
}
