package com.gov.sidesa.data.letterlist.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LettersModel(
    val id: String,
    val createDate: String,
    val title: String,
    val number: String
): Parcelable
