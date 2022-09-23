package com.gov.sidesa.data.region.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RtRwResponse(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String
)
