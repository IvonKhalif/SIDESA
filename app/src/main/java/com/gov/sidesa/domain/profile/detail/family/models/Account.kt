package com.gov.sidesa.domain.profile.detail.family.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class Account(
    val id: String = "",
    val idLevel: String = "",
    val nik: String = "",
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
) : Parcelable
