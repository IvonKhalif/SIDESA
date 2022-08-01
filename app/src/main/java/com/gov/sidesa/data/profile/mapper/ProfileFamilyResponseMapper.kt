package com.gov.sidesa.data.profile.mapper

import com.gov.sidesa.data.profile.models.FamilyResponse
import com.gov.sidesa.data.profile.models.ProfileFamilyResponse
import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.domain.profile.detail.family.models.Family
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.extension.orZero
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

fun ProfileFamilyResponse.asDomain() = ProfileFamily(
    account = account.asDomain(),
    family = family.asDomain()
)

fun UserResponse.asDomain() = Account(
    id = id.orZero(),
    nik = nik.orEmpty(),
    name = name.orEmpty(),
    birthPlace = birthPlace.orEmpty(),
    birthDate = birthDate ?: Date(),
    gender = gender.orEmpty(),
    blood = blood.orEmpty(),
    address = address.orEmpty(),
    rt = rt.orEmpty(),
    rw = rw.orEmpty(),
    province = province.orEmpty(),
    city = city.orEmpty(),
    district = district.orEmpty(),
    village = village.orEmpty(),
    religion = religion.orEmpty(),
    maritalStatus = maritalStatus.orEmpty(),
    job = job.orEmpty(),
    citizenship = citizenship.orEmpty(),
    imageKTP = imageKTP.orEmpty(),
    email = email.orEmpty(),
    statusUser = statusUser.orEmpty(),
    expiredDate = expiredDate.orEmpty(),
    kk = kk.orEmpty(),
    familyHead = familyHead.orEmpty(),
    imageKK = imageKK.orEmpty(),
    addressKK = addressKK.orEmpty(),
    cityIdKK = cityIdKK.orEmpty(),
    districtIdKK = districtIdKK.orEmpty(),
    provinceIdKK = provinceIdKK.orEmpty(),
    rtKK = rtKK.orEmpty(),
    rwKK = rwKK.orEmpty(),
    villageIdKK = villageIdKK.orEmpty()
)

fun FamilyResponse.asDomain() = Family(
    relationType.orEmpty(),
    name.orEmpty(),
    ktpNumber.orEmpty(),
    birthPlace.orEmpty(),
    birthDate = birthDate ?: Date(),
    address.orEmpty(),
    rt = rt.orEmpty(),
    rw = rw.orEmpty(),
    province = Region(id = province?.id.orZero(), name = province?.name.orEmpty()),
    city = Region(
        id = city?.id.orZero(),
        parentId = province?.id,
        name = city?.name.orEmpty()
    ),
    district = Region(
        id = district?.id.orZero(),
        parentId = city?.id,
        name = district?.name.orEmpty()
    ),
    village = Region(
        id = village?.id.orZero(),
        parentId = district?.id,
        name = village?.name.orEmpty()
    )
)

fun List<FamilyResponse>.asDomain() = map { it.asDomain() }