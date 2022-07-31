package com.gov.sidesa.data.letterlist.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileLetterApprovalResponse(
    @SerializedName("id_account") var accountId: String,
    @SerializedName("nik") var nik: String,
    @SerializedName("id_kelurahan") var villageId: String?,
    @SerializedName("rt") var rt: String?,
    @SerializedName("rw") var rw: String?,
    @SerializedName("is_rt") var isRt: Boolean = false,
    @SerializedName("is_rw") var isRw: Boolean = false
): Parcelable