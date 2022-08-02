package com.gov.sidesa.ui.profile.edit.kk.mapper

import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.profile.edit.kk.models.AccountKKUiModel
import com.gov.sidesa.utils.NetworkUtil
import com.gov.sidesa.utils.extension.orZero

/**
 * Created by yovi.putra on 02/08/22"
 * Project name: SIDESA
 **/
 
fun ProfileFamily.asEditKK() = AccountKKUiModel(
    kk = account.kk,
    familyHeadName = account.familyHead.orEmpty(),
    address = account.addressKK.orEmpty(),
    rt = account.rtKK.orEmpty(),
    rw = account.rwKK.orEmpty(),
    province = Region(id = account.provinceKK?.id.orZero(), name = account.provinceKK?.name.orEmpty()),
    city = Region(
        id = account.cityKK?.id.orZero(),
        parentId = account.provinceKK?.id.orZero(),
        name = account.cityKK?.name.orEmpty()
    ),
    district = Region(
        id = account.districtKK?.id.orZero(),
        parentId = account.cityKK?.id.orZero(),
        name = account.districtKK?.name.orEmpty()
    ),
    village = Region(
        id = account.villageKK?.id.orZero(),
        parentId = account.districtKK?.id.orZero(),
        name = account.villageKK?.name.orEmpty()
    ),
    kkImageUri = NetworkUtil.SERVER_HOST + account.imageKK.orEmpty()
)