package com.gov.sidesa.domain.letter.detail.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryApprovalModel(
    var typeApproval: String,
    var descriptionType: String,
    var statusApproval: String,
    var createdDate: String?
):Parcelable
