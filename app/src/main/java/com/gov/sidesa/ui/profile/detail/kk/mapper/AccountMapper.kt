package com.gov.sidesa.ui.profile.detail.kk.mapper

import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

fun Account.asUiModel() = AccountUiModel(
    id,
    nik,
    kk,
    name,
    birthPlace,
    birthDate,
    gender,
    blood,
    address,
    rt,
    rw,
    province,
    city,
    district,
    village,
    religion,
    maritalStatus,
    job,
    citizenship,
    imageKTP,
    email,
    statusUser,
    expiredDate
)