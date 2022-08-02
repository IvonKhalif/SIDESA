package com.gov.sidesa.data.profile.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SaveKKRequest(
    @SerializedName("id_account")
    @Expose
    val accountId: Long?,
    @SerializedName("no_kk")
    @Expose
    val kk: String?,
    @SerializedName("kepala_keluarga")
    @Expose
    val familyHeadName: String?,
    @SerializedName("alamat")
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
    val provinceId: Long?,
    @SerializedName("id_kota")
    @Expose
    val cityId: Long?,
    @SerializedName("id_kecamatan")
    @Expose
    val districtId: Long?,
    @SerializedName("id_kelurahan")
    @Expose
    val villageId: Long?,
    @SerializedName("foto_kk")
    @Expose
    val kkImageUri: String?,
)