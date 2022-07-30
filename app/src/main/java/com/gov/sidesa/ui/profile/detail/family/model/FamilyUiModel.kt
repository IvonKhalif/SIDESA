package com.gov.sidesa.ui.profile.detail.family.model

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

    val birthPlaceAndDate
        get() = "$birthPlace, $birthDate"
}
