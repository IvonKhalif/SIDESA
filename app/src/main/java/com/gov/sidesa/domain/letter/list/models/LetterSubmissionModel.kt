package com.gov.sidesa.domain.letter.list.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LetterSubmissionModel(
    val letterId: String,
    val letterType: String,
    val letterStatus: String,
    val letterNumber: String?
): Parcelable