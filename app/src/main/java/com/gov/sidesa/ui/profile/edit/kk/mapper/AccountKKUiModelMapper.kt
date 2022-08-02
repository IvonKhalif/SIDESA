package com.gov.sidesa.ui.profile.edit.kk.mapper

import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.domain.profile.edit.kk.models.SaveKK
import com.gov.sidesa.ui.profile.edit.kk.models.AccountKKUiModel
import com.gov.sidesa.utils.picker.filePathToBase64

/**
 * Created by yovi.putra on 02/08/22"
 * Project name: SIDESA
 **/

fun AccountKKUiModel.asDomain(account: Account) = SaveKK(
    accountId = account.id,
    kk = kk,
    familyHeadName = familyHeadName,
    address = address,
    rt = rt,
    rw =rw,
    province = province,
    city = city,
    district = district,
    village = village,
    kkImageUri = kkImageUri.filePathToBase64()
)