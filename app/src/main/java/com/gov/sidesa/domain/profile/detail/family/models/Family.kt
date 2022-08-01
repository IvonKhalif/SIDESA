package com.gov.sidesa.domain.profile.detail.family.models

import android.os.Parcelable
import com.gov.sidesa.domain.regions.models.Region
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class Family(
    val relationType: String = "",
    val name: String = "",
    val ktpNumber: String = "",
    val birthPlace: String = "",
    val birthDate: Date = Date(),
    val address: String = "",
    val rt: String = "",
    val rw: String = "",
    val province: Region = Region(),
    val city: Region = Region(),
    val district: Region = Region(),
    val village: Region = Region(),
): Parcelable
