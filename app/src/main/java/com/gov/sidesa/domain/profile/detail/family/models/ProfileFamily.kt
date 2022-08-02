package com.gov.sidesa.domain.profile.detail.family.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class ProfileFamily(
    val account: Account = Account(),
    val family: List<Family> = emptyList()
): Parcelable {

    fun sameAddress(family: Family): Boolean = account.address.trim() == family.address.trim()
            && account.rt == family.rt
            && account.rw == family.rw
            && account.province.id == family.province.id
            && account.city.id == family.city.id
            && account.district.id == family.district.id
            && account.village.id == family.village.id
}
