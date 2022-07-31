package com.gov.sidesa.domain.letter.input.models.layout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class LetterLayout(
    val letterTypeId: String,
    val widgets: List<Widget>
): Parcelable
