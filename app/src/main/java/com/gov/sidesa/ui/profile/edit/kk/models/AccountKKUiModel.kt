package com.gov.sidesa.ui.profile.edit.kk.models

import com.gov.sidesa.domain.regions.models.Region

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class AccountKKUiModel(
    val kk: String = "",
    val familyHeadName: String = "",
    val address: String = "",
    val rt: String = "",
    val rw: String = "",
    val province: Region = Region(),
    val city: Region = Region(),
    val district: Region = Region(),
    val village: Region = Region(),
    val kkImageUri: String = "",
)