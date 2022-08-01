package com.gov.sidesa.domain.letter.detail.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import kotlinx.parcelize.Parcelize

@Parcelize
class DetailApprovalModel(
//    val documentTypeId: Int,
    val letterType: String?,
    val submissionLetterId: String? = "",
    val userName: String? = "",
    val nik: String? = "",
    val address: String? = "",
    val createdDate: String? = "",
    val letterNumber: String? = "",
    val status: String? = "",
    val description: String? = "",
    val documentFilled: List<Widget>,
    val historyApproval: List<HistoryApprovalModel>
) : Parcelable