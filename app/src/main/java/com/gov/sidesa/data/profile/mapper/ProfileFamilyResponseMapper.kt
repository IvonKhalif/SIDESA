package com.gov.sidesa.data.profile.mapper

import com.gov.sidesa.data.profile.models.FamilyResponse
import com.gov.sidesa.data.profile.models.ProfileFamilyResponse
import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.domain.profile.detail.family.models.Family
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

fun ProfileFamilyResponse.asDomain() = ProfileFamily(
    account = account.asDomain(),
    family = family.asDomain()
)

fun User.asDomain() = Account(
    id = id.orEmpty(),
    idLevel = idLevel.orEmpty(),
    nik = nik.orEmpty(),
    name = name.orEmpty(),
    birthPlace = birthPlace.orEmpty(),
    birthDate = birthDate.orEmpty(),
    gender = gender.orEmpty(),
    blood = blood.orEmpty(),
    address = addres.orEmpty(),
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
    expiredDate = expiredDate.orEmpty()
)

fun FamilyResponse.asDomain() = Family(
    relationFamily.orEmpty(),
    name.orEmpty(),
    ktpNumber.orEmpty(),
    birthPlace.orEmpty(),
    birthDate.orEmpty(),
    address.orEmpty(),
    differentAddress ?: false
)

fun List<FamilyResponse>.asDomain() = map { it.asDomain() }