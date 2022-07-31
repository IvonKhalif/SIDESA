package com.gov.sidesa.data.user.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id_account") var id: String?,
    @SerializedName("id_level") var idLevel: String?,
    @SerializedName("nik") var nik: String?,
    @SerializedName("nama_lengkap") var name: String?,
    @SerializedName("tempat_lahir") var birthPlace: String?,
    @SerializedName("tanggal_lahir") var birthDate: String?,
    @SerializedName("jenis_kelamin") var gender: String?,
    @SerializedName("gol_darah") var blood: String?,
    @SerializedName("alamat") var addres: String?,
    @SerializedName("rt") var rt: String?,
    @SerializedName("rw") var rw: String?,
    @SerializedName("id_provinsi") var province: String?,
    @SerializedName("id_kota") var city: String?,
    @SerializedName("id_kecamatan") var district: String?,
    @SerializedName("id_kelurahan") var village: String?,
    @SerializedName("id_agama") var religion: String?,
    @SerializedName("status_perkawinan") var maritalStatus: String?,
    @SerializedName("pekerjaan") var job: String?,
    @SerializedName("kewarganegaraan") var citizenship: String?,
    @SerializedName("foto_ktp") var imageKTP: String?,
    @SerializedName("email_account") var email: String?,
    @SerializedName("status") var statusUser: String?,
    @SerializedName("expired_date") var expiredDate: String?
): Parcelable