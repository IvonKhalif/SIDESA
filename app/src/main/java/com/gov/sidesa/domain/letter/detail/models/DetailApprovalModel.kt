package com.gov.sidesa.domain.letter.detail.models

import android.os.Parcelable
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import kotlinx.parcelize.Parcelize

@Parcelize
class DetailApprovalModel(
    val documentTypeId: Int,
    val letterType: String,
    val status: String = "",
    val documentFilled: List<Widget>,
    val historyApproval: List<HistoryApprovalModel>
) : Parcelable