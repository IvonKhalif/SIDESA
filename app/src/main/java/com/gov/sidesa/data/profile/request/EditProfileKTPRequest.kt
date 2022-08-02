package com.gov.sidesa.data.profile.request

import com.google.gson.annotations.SerializedName
import java.util.*

data class EditProfileKTPRequest(
    @SerializedName("id_account") val accountId: String? = null,
    @SerializedName("nik") val nik: String? = null,
    @SerializedName("nama_lengkap") val accountName: String? = null,
    @SerializedName("tempat_lahir") val birthPlace: String? = null,
    @SerializedName("tanggal_lahir") val birthDate: Date? = null,
    @SerializedName("jenis_kelamin") val gender: String? = null,
    @SerializedName("gol_darah") val blood: String? = null,
    @SerializedName("alamat") val address: String? = null,
    @SerializedName("id_kelurahan") val villageId: String? = null,
    @SerializedName("id_kecamatan") val districtId: String? = null,
    @SerializedName("rt") val rt: String? = null,
    @SerializedName("rw") val rw: String? = null,
    @SerializedName("id_agama") val religion: String? = null,
    @SerializedName("pekerjaan") val job: String? = null,
    @SerializedName("status_perkawinan") val marriage: String? = null,
    @SerializedName("kewarganegaraan") val nationality: String? = null,
    @SerializedName("foto_ktp") val imageKTP: String? = null,
)
