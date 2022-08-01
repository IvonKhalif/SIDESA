package com.gov.sidesa.data.profile.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.sidesa.data.user.response.User
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class ProfileFamilyResponse(
    @SerializedName("account")
    @Expose
    val account: User,
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
    @SerializedName("id_provinsi")
    @Expose
    val provinceId: String?,
    @SerializedName("id_kota")
    @Expose
    val cityId: String?,
    @SerializedName("id_kecamatan")
    @Expose
    val districtId: String?,
    @SerializedName("id_kelurahan")
    @Expose
    val villageId: String?,
    @SerializedName("provinsi")
    @Expose
    val province: String?,
    @SerializedName("kota")
    @Expose
    val city: String?,
    @SerializedName("kecamatan")
    @Expose
    val district: String?,
    @SerializedName("kelurahan")
    @Expose
    val village: String?,
)