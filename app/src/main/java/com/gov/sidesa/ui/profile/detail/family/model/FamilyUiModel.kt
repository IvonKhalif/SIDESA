package com.gov.sidesa.ui.profile.detail.family.model

import android.content.Context
import com.gov.sidesa.R

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class FamilyUiModel(
    val relationFamily: String = "",
    val name: String = "",
    val ktpNumber: String = "",
    val birthPlace: String = "",
    val birthDate: String = "",
    val address: String = "",
    val differentAddress: Boolean = false
) {
    fun getRelation(context: Context) = when (relationFamily) {
        "father" -> context.getString(R.string.family_data_father)
        "mother" -> context.getString(R.string.family_data_mother)
        "husband" -> context.getString(R.string.family_data_husband)
        "wife" -> context.getString(R.string.family_data_wife)
        else -> context.getString(
            R.string.family_data_child_to,
            relationFamily.replace("child_", "")
        )
    }

    val birthPlaceAndDate
        get() = "$birthPlace, $birthDate"
}
