package com.gov.sidesa.data.letterdetail.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryApprovalResponse(
    @SerializedName("type_approval") var typeApproval: String,
    @SerializedName("description_type") var descriptionType: String,
    @SerializedName("status") var statusApproval: String,
    @SerializedName("created_date") var createdDate: String?
): Parcelable
