package com.gov.sidesa.data.profile.mapper

import com.gov.sidesa.data.profile.models.SaveFamilyRequest
import com.gov.sidesa.domain.profile.edit.family.models.SaveFamily
import com.gov.sidesa.utils.extension.orZero

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

fun SaveFamily.asData() = SaveFamilyRequest(
    accountId,
    relationType,
    name,
    ktpNumber,
    kkNumber,
    birthPlace,
    birthDate,
    address,
    rt,
    rw,
    provinceId.orZero().toString(),
    cityId.orZero().toString(),
    districtId.orZero().toString(),
    villageId.orZero().toString()
)

fun List<SaveFamily>.asData() = map { it.asData() }