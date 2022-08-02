package com.gov.sidesa.ui.profile.detail.kk.mapper

import com.gov.sidesa.domain.letter.input.models.resource.default
import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

fun Account.asUiModel() = AccountUiModel(
    id = id,
    nik = nik,
    kk = kk,
    name = name,
    birthPlace = birthPlace,
    birthDate = birthDate,
    gender = gender,
    blood = blood,
    address = address,
    rt = rt,
    rw = rw,
    province = province,
    city = city,
    district = district,
    village = village,
    religion = religion,
    maritalStatus = maritalStatus,
    job = job,
    citizenship = citizenship,
    imageKTP = imageKTP,
    email = email,
    statusUser = statusUser,
    expiredDate = expiredDate,
    familyHead = familyHead.orEmpty(),
    imageKK = imageKK.orEmpty(),
    addressKK = addressKK.orEmpty(),
    rtKK = rtKK.orEmpty(),
    rwKK = rwKK.orEmpty(),
    provinceKK = provinceKK.default(),
    cityKK = cityKK.default(),
    districtKK = districtKK.default(),
    villageKK = villageKK.default()
)