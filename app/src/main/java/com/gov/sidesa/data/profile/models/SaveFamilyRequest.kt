package com.gov.sidesa.data.profile.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

data class SaveFamilyRequest(
    @SerializedName("id_account")
    @Expose
    val accountId: Long?,
    @SerializedName("type_relation")
    @Expose
    val relationType: String?,
    @SerializedName("nama_lengkap")
    @Expose
    val name: String?,
    @SerializedName("no_ktp")
    @Expose
    val ktpNumber: String?,
    @SerializedName("no_kk")
    @Expose
    val kkNumber: String?,
    @SerializedName("tempat_lahir")
    @Expose
    val birthPlace: String?,
    @SerializedName("tanggal_lahir")
    @Expose
    val birthDate: Date?,
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
)