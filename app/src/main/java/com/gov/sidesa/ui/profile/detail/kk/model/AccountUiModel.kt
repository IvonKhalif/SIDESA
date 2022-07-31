package com.gov.sidesa.ui.profile.detail.kk.model

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class AccountUiModel(
    val idLevel: String = "",
    val nik: String = "",
    val kk: String = "",
    val name: String = "",
    val birthPlace: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val blood: String = "",
    val address: String = "",
    val rt: String = "",
    val rw: String = "",
    val province: String = "",
    val city: String = "",
    val district: String = "",
    val village: String = "",
    val religion: String = "",
    val maritalStatus: String = "",
    val job: String = "",
    val citizenship: String = "",
    val imageKTP: String = "",
    val email: String = "",
    val statusUser: String = "",
    val expiredDate: String = ""
) {

    val birthPlaceAndDate
        get() = "$birthPlace, $birthDate"

    val fullAddress
        get() = address
}
