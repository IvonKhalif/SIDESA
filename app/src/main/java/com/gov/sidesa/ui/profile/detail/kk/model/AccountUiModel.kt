package com.gov.sidesa.ui.profile.detail.kk.model

import android.os.Parcelable
import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.utils.extension.format
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
): Parcelable {

    val birthPlaceAndDate
        get() = "$birthPlace, ${birthDate.format()}"

    val fullAddress
        get() = "$formatAddress $formatRtRw $formatVillage $formatDistrict"

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
        get() = if (village.name.isNotBlank()) "$village," else ""

    private val formatDistrict
        get() = if (district.name.isNotBlank()) "$district," else ""
}
