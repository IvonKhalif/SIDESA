package com.gov.sidesa.data.profile.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.sidesa.data.letter.models.ResourceResponse
import com.gov.sidesa.data.user.response.UserResponse
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class ProfileFamilyResponse(
    @SerializedName("account")
    @Expose
    val account: UserResponse,
    @SerializedName("family")
    @Expose
    val family: List<FamilyResponse>
)

data class FamilyResponse(
    @SerializedName("relation_type")
    @Expose
    val relationType: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("no_ktp")
    @Expose
    val ktpNumber: String?,
    @SerializedName("birth_place")
    @Expose
    val birthPlace: String?,
    @SerializedName("birth_date")
    @Expose
    val birthDate: Date?,
    @SerializedName("address")
    @Expose
    val address: String?,
    @SerializedName("rt")
    @Expose
    val rt: String?,
    @SerializedName("rw")
    @Expose
    val rw: String?,
    @SerializedName("province")
    @Expose
    val province: ResourceResponse?,
    @SerializedName("kota")
    @Expose
    val city: ResourceResponse?,
    @SerializedName("kecamatan")
    @Expose
    val district: ResourceResponse?,
    @SerializedName("kelurahan")
    @Expose
    val village: ResourceResponse?,
)