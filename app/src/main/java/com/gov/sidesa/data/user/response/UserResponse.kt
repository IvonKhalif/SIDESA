package com.gov.sidesa.data.user.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.gov.sidesa.data.letter.models.ResourceResponse
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class UserResponse(
    @SerializedName("id_account") val id: Long,
    @SerializedName("nik") val nik: String?,
    @SerializedName("no_kk") val kk: String?,
    @SerializedName("nama_lengkap") val name: String?,
    @SerializedName("tempat_lahir") val birthPlace: String?,
    @SerializedName("tanggal_lahir") val birthDate: Date?,
    @SerializedName("jenis_kelamin") val gender: String?,
    @SerializedName("gol_darah") val blood: String?,
    @SerializedName("alamat") val address: String?,
    @SerializedName("rt") val rt: String?,
    @SerializedName("rw") val rw: String?,
    @SerializedName("province") val province: ResourceResponse?,
    @SerializedName("kota") val city: ResourceResponse?,
    @SerializedName("kecamatan") val district: ResourceResponse?,
    @SerializedName("kelurahan") val village: ResourceResponse?,
    @SerializedName("id_agama") val religion: String?,
    @SerializedName("status_perkawinan") val maritalStatus: String?,
    @SerializedName("pekerjaan") val job: String?,
    @SerializedName("kewarganegaraan") val citizenship: String?,
    @SerializedName("foto_ktp") val imageKTP: String?,
    @SerializedName("email_account") val email: String?,
    @SerializedName("status") val statusUser: String?,
    @SerializedName("expired_date") val expiredDate: String?,

    @SerializedName("kepala_keluarga_kk") val familyHead: String?,
    @SerializedName("foto_kk") val imageKK: String?,
    @SerializedName("alamat_kk") val addressKK: String?,
    @SerializedName("rt_kk") val rtKK: String?,
    @SerializedName("rw_kk") val rwKK: String?,
    @SerializedName("province_kk") val provinceKK: ResourceResponse?,
    @SerializedName("kota_kk") val cityKK: ResourceResponse?,
    @SerializedName("kecamatan_kk") val districtKK: ResourceResponse?,
    @SerializedName("kelurahan_kk") val villageKK: ResourceResponse?,
): Parcelable