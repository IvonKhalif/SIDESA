package com.gov.sidesa.domain.profile.edit.family.models

import java.util.*

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

data class SaveFamily(
    val accountId: Long?,
    val relationType: String?,
    val name: String?,
    val ktpNumber: String?,
    val kkNumber: String?,
    val birthPlace: String?,
    val birthDate: Date?,
    val address: String?,
    val rt: String?,
    val rw: String?,
    val provinceId: Long?,
    val cityId: Long?,
    val districtId: Long?,
    val villageId: Long?,
)