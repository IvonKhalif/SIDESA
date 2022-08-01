package com.gov.sidesa.data.user.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class UserResponse(
    @SerializedName("id_account") var id: Long,
    @SerializedName("nik") var nik: String?,
    @SerializedName("no_kk") var kk: String?,
    @SerializedName("nama_lengkap") var name: String?,
    @SerializedName("tempat_lahir") var birthPlace: String?,
    @SerializedName("tanggal_lahir") var birthDate: Date?,
    @SerializedName("jenis_kelamin") var gender: String?,
    @SerializedName("gol_darah") var blood: String?,
    @SerializedName("alamat") var address: String?,
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
    @SerializedName("expired_date") var expiredDate: String?,

    @SerializedName("kepala_keluarga_kk") var familyHead: String?,
    @SerializedName("foto_kk") var imageKK: String?,
    @SerializedName("alamat_kk") var addressKK: String?,
    @SerializedName("rt_kk") var rtKK: String?,
    @SerializedName("rw_kk") var rwKK: String?,
    @SerializedName("id_provinsi_kk") var provinceIdKK: String?,
    @SerializedName("id_kota_kk") var cityIdKK: String?,
    @SerializedName("id_kecamatan_kk") var districtIdKK: String?,
    @SerializedName("id_kelurahan_kk") var villageIdKK: String?,
): Parcelable