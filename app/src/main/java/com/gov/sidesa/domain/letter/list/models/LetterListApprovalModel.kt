package com.gov.sidesa.domain.letter.list.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LetterListApprovalModel(
    val letterId: String,
    val letterType: String,
    val submitDate: String,
    val letterNumber: String?,
    val letterDetail: String?
): Parcelable
