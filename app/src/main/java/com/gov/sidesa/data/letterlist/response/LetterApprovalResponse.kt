package com.gov.sidesa.data.letterlist.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LetterApprovalResponse(
    @SerializedName("id_pengajuan_surat") var letterId: String,
    @SerializedName("type_surat") var letterType: String,
    @SerializedName("no_surat") var letterNumber: String?,
    @SerializedName("date") var submitDate: String?,
    @SerializedName("detail") var letterDetail: String?
) : Parcelable
