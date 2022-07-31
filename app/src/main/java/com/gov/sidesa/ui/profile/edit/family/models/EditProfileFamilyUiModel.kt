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
    val relationFamily: RelationType = RelationType.Father,
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

    val titleVisibilityState
        get() = relationFamily is RelationType.Child

    val titleText: String
        get() {
            val child = (relationFamily as? RelationType.Child)
            return "Anak ${child?.count}"
        }

    val nameTitle
        get() = when (relationFamily) {
            is RelationType.Father -> {
                R.string.fullname_father
            }
            is RelationType.Mother -> {
                R.string.fullname_mother
            }
            else -> {
                R.string.fullname
            }
        }

    val inputStatusVisibilityState
        get() = relationFamily == RelationType.Husband || relationFamily == RelationType.Wife

    val birtDateFormatted: String
        get() = run {
            val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            birthDate?.let {
                format.format(it)
            } ?: run { "" }
        }
}
