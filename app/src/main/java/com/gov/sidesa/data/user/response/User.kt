package com.gov.sidesa.data.user.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id_account") var id: String,
    @SerializedName("name_account") var name: String?,
    @SerializedName("expired_date") var expiredDate: String?
): Parcelable