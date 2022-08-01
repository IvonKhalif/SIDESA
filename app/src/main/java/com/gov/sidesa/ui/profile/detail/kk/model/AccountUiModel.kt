package com.gov.sidesa.ui.profile.detail.kk.model

import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class AccountUiModel(
    val id: Long = 0,
    val nik: String = "",
    val kk: String = "",
    val name: String = "",
    val birthPlace: String = "",
    val birthDate: Date = Date(),
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
    val expiredDate: String = "",

    val familyHead: String = "",
    val imageKK: String = "",
    val addressKK: String = "",
    val rtKK: String = "",
    val rwKK: String = "",
    val provinceIdKK: String = "",
    val cityIdKK: String = "",
    val districtIdKK: String = "",
    val villageIdKK: String = ""
) {

    val birthPlaceAndDate
        get() = "$birthPlace, $birthDate"

    val fullAddress
        get() = address
}
