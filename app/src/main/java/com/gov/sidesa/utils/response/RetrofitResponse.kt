package com.gov.sidesa.utils.response

import com.google.gson.annotations.SerializedName

data class RetrofitResponse<T>(
    @SerializedName("data") var data: T?,
    @SerializedName("desc") var desc: String
)
data class RetrofitStatusResponse(
    @SerializedName("status") var status: String,
    @SerializedName("desc") var desc: String
)
data class RetrofitURLResponse(
    @SerializedName("url") var url: String,
    @SerializedName("desc") var desc: String
)
data class RetrofitListResponse<T>(
    @SerializedName("data") var data: List<T>,
    @SerializedName("desc") var desc: String
)