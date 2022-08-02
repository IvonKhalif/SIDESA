package com.gov.sidesa.domain.letter.input.models.resource

import android.os.Parcelable
import com.gov.sidesa.domain.regions.models.Region
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class Resource(
    val id: Long = 0L,
    val name: String = ""
) : Parcelable

fun Resource?.default() = this ?: Resource()

fun Resource.asRegion(parentId: Long? = null) = Region(id = id, name = name, parentId = parentId)