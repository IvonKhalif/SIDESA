package com.gov.sidesa.data.region.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

data class ProvinceResponse(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String
)
