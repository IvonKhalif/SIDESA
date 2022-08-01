package com.gov.sidesa.domain.regions.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class Region(
    val id: Long = 0L,
    val parentId: Long? = null,
    val name: String = ""
) : Parcelable
