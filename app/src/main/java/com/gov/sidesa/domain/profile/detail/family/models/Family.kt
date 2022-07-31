package com.gov.sidesa.domain.profile.detail.family.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class Family(
    val relationFamily: String = "",
    val name: String = "",
    val ktpNumber: String = "",
    val birthPlace: String = "",
    val birthDate: String = "",
    val address: String = "",
    val differentAddress: Boolean = false
): Parcelable
