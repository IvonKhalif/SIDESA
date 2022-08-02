package com.gov.sidesa.data.letter.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class ResourceResponse(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String
) : Parcelable
