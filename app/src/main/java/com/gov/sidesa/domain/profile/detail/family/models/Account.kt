package com.gov.sidesa.domain.profile.detail.family.models

import android.os.Parcelable
import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.domain.letter.input.models.resource.asData
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class Account(
    val id: Long = 0,
    val nik: String = "",
    val kk: String = "",
    val name: String = "",
    val birthPlace: String = "",
    val birthDate: Date = Date(),
    val gender: String = "",
    val blood: String = "",
    val address: String = "",
    val rt: String = "",
    val rw: String = "",
    val province: Resource = Resource(),
    val city: Resource = Resource(),
    val district: Resource = Resource(),
    val village: Resource = Resource(),
    val religion: String = "",
    val maritalStatus: String = "",
    val job: String = "",
    val citizenship: String = "",
    val imageKTP: String = "",
    val email: String = "",
    val statusUser: String = "",
    val expiredDate: String = "",

    val familyHead: String? = null,
    val imageKK: String? = null,
    val addressKK: String? = null,
    val rtKK: String? = null,
    val rwKK: String? = null,
    val provinceKK: Resource? = null,
    val cityKK: Resource? = null,
    val districtKK: Resource? = null,
    val villageKK: Resource? = null
) : Parcelable

fun Account.asData() = UserResponse(
    id = id,
    nik = nik,
    rwKK = rwKK,
    name = name,
    familyHead = familyHead,
    city = city.asData(),
    address = address,
    kk = kk,
    province = province.asData(),
    rtKK = rtKK,
    rt = rt,
    addressKK = addressKK,
    imageKK =imageKK,
    birthDate = birthDate,
    village = village.asData(),
    district = district.asData(),
    birthPlace = birthPlace,
    blood = blood,
    citizenship = citizenship,
    cityKK = cityKK?.asData(),
    districtKK = districtKK?.asData(),
    email = email,
    expiredDate = expiredDate,
    gender = gender,
    imageKTP = imageKTP,
    job = job,
    maritalStatus = maritalStatus,
    provinceKK = provinceKK?.asData(),
    religion = religion,
    rw = rw,
    statusUser = statusUser,
    villageKK = villageKK?.asData()
)
