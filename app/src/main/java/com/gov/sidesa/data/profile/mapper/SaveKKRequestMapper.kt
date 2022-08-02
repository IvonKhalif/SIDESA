package com.gov.sidesa.data.profile.mapper

import com.gov.sidesa.data.profile.models.SaveKKRequest
import com.gov.sidesa.domain.profile.edit.kk.models.SaveKK

/**
 * Created by yovi.putra on 02/08/22"
 * Project name: SIDESA
 **/


fun SaveKK.asData() = SaveKKRequest(
    accountId = accountId,
    kk = kk,
    familyHeadName = familyHeadName,
    address = address,
    rt = rt,
    rw = rw,
    provinceId = province?.id,
    cityId = city?.id,
    districtId = district?.id,
    villageId = village?.id,
    kkImageUri = kkImageUri
)