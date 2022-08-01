package com.gov.sidesa.domain.letter.list.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LetterApprovalModel(
    val profile: ProfileLetterApprovalModel,
    val letters: List<LetterListApprovalModel>
) : Parcelable
