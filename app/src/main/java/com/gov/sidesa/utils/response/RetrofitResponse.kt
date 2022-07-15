package com.gov.sidesa.utils.response

import com.google.gson.annotations.SerializedName

data class RetrofitResponse<T>(@SerializedName("data") var data: T)
data class RetrofitStatusResponse(@SerializedName("status") var status: String)
data class RetrofitURLResponse(@SerializedName("url") var url: String)
data class RetrofitListResponse<T>(@SerializedName("data") var data: List<T>)