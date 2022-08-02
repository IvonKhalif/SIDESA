package com.gov.sidesa.domain.profile.edit.kk.models

import com.gov.sidesa.domain.regions.models.Region

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

data class SaveKK(
    val accountId: Long,
    val kk: String?,
    val familyHeadName: String?,
    val address: String?,
    val rt: String?,
    val rw: String?,
    val province: Region?,
    val city: Region?,
    val district: Region?,
    val village: Region?,
    val kkImageUri: String?,
)