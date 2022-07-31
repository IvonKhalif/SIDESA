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
): Parcelable
