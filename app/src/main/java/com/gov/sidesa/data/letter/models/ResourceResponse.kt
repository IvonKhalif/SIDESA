package com.gov.sidesa.data.letter.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

data class ResourceResponse(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String
)
