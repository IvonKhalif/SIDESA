package com.gov.sidesa.domain.letter.list.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileLetterApprovalModel(
    var accountId: String,
    var nik: String,
    var villageId: String?,
    var rt: String?,
    var rw: String?,
    var isRt: Boolean = false,
    var isRw: Boolean = false
) : Parcelable