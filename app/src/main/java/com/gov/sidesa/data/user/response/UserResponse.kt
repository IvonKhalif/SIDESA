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
): Parcelable {

    /**
     * KTP
     */
    val fullAddress
        get() = ("$formatAddress $formatRtRw $formatVillage $formatDistrict " +
                "$formatCity $formatProvince").trim()

    private val formatAddress
        get() = if (!address.isNullOrBlank()) "$address," else ""

    private val formatRtRw
        get() = when {
            !rt.isNullOrBlank() && !rw.isNullOrBlank() -> "RT $rt RW $rw,"
            !rt.isNullOrBlank() && rw.isNullOrBlank() -> "RT $rt,"
            rt.isNullOrBlank() && !rw.isNullOrBlank() -> "RW $rw,"
            else -> ""
        }

    private val formatVillage
        get() = if (!village?.name.isNullOrBlank()) "${village?.name}," else ""

    private val formatDistrict
        get() = if (!district?.name.isNullOrBlank()) "${district?.name}," else ""

    private val formatCity
        get() = if (!city?.name.isNullOrBlank()) "${city?.name}," else ""

    private val formatProvince
        get() = province?.name.orEmpty()

    /**
     * KK
     */
    val fullAddressKK
        get() = ("$formatAddressKK $formatRtRwKK $formatVillageKK " +
                "$formatDistrictKK $formatCityKK $formatProvinceKK").trim()

    private val formatAddressKK
        get() = if (!addressKK.isNullOrBlank()) "$addressKK," else ""

    private val formatRtRwKK
        get() = when {
            !rtKK.isNullOrBlank() && !rwKK.isNullOrBlank() -> "RT $rtKK RW $rwKK,"
            !rtKK.isNullOrBlank() && rwKK.isNullOrBlank() -> "RT $rtKK,"
            rtKK.isNullOrBlank() && !rwKK.isNullOrBlank() -> "RW $rwKK,"
            else -> ""
        }

    private val formatVillageKK
        get() = if (!villageKK?.name.isNullOrBlank()) "${villageKK?.name.orEmpty()}," else ""

    private val formatDistrictKK
        get() = if (!districtKK?.name.isNullOrBlank()) "${districtKK?.name.orEmpty()}," else ""

    private val formatCityKK
        get() = if (!cityKK?.name.isNullOrBlank()) "${cityKK?.name.orEmpty()}," else ""

    private val formatProvinceKK
        get() = provinceKK?.name.orEmpty()
}