package com.gov.sidesa.data.letterdetail.models

import com.google.gson.annotations.SerializedName

data class DocumentFilledModel(
    @SerializedName("title") var title: String,
    @SerializedName("value") var value: String = ""
)