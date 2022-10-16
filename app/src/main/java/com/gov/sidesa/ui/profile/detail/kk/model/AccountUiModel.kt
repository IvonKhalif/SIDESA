package com.gov.sidesa.ui.profile.detail.kk.model

import android.os.Parcelable
import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.utils.NetworkUtil
import com.gov.sidesa.utils.extension.formatFE
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class AccountUiModel(
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

    val familyHead: String = "",
    val imageKK: String = "",
    val addressKK: String = "",
    val rtKK: String = "",
    val rwKK: String = "",
    val provinceKK: Resource = Resource(),
    val cityKK: Resource = Resource(),
    val districtKK: Resource = Resource(),
    val villageKK: Resource = Resource()
) : Parcelable {

    val birthPlaceAndDate
        get() = "$birthPlace, ${birthDate.formatFE()}"

    val imageKKRemote get() = NetworkUtil.SERVER_HOST + imageKK
    /**
     * KTP
     */
    val fullAddress
        get() = ("$formatAddress $formatRtRw $formatVillage $formatDistrict " +
                "$formatCity $formatProvince").trim()

    private val formatAddress
        get() = if (address.isNotBlank()) "$address," else ""

    private val formatRtRw
        get() = when {
            rt.isNotBlank() && rw.isNotBlank() -> "RT $rt RW $rw,"
            rt.isNotBlank() && rw.isBlank() -> "RT $rt,"
            rt.isBlank() && rw.isNotBlank() -> "RW $rw,"
            else -> ""
        }

    private val formatVillage
        get() = if (village.name.isNotBlank()) "${village.name}," else ""

    private val formatDistrict
        get() = if (district.name.isNotBlank()) "${district.name}," else ""

    private val formatCity
        get() = if (city.name.isNotBlank()) "${city.name}," else ""

    private val formatProvince
        get() = province.name

    /**
     * KK
     */
    val fullAddressKK
        get() = ("$formatAddressKK $formatRtRwKK $formatVillageKK " +
                "$formatDistrictKK $formatCityKK $formatProvinceKK").trim()

    private val formatAddressKK
        get() = if (addressKK.isNotBlank()) "$addressKK," else ""

    private val formatRtRwKK
        get() = when {
            rtKK.isNotBlank() && rwKK.isNotBlank() -> "RT $rtKK RW $rwKK,"
            rtKK.isNotBlank() && rwKK.isBlank() -> "RT $rtKK,"
            rtKK.isBlank() && rwKK.isNotBlank() -> "RW $rwKK,"
            else -> ""
        }

    private val formatVillageKK
        get() = if (villageKK.name.isNotBlank()) "${villageKK.name}," else ""

    private val formatDistrictKK
        get() = if (districtKK.name.isNotBlank()) "${districtKK.name}," else ""

    private val formatCityKK
        get() = if (cityKK.name.isNotBlank()) "${cityKK.name}," else ""

    private val formatProvinceKK
        get() = provinceKK.name
}
