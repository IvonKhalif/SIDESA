package com.gov.sidesa.domain.profile.detail.family.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class Account(
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

    val familyHead: String? = null,
    val imageKK: String? = null,
    val addressKK: String? = null,
    val rtKK: String? = null,
    val rwKK: String? = null,
    val provinceIdKK: String? = null,
    val cityIdKK: String? = null,
    val districtIdKK: String? = null,
    val villageIdKK: String? = null
) : Parcelable
