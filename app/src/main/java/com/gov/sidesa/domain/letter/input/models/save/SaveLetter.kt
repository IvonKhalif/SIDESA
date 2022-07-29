package com.gov.sidesa.domain.letter.input.models.save

/**
 * Created by yovi.putra on 29/07/22"
 * Project name: SIDESA
 **/

data class SaveLetter(
    val accountId: String,
    val letterTypeId: String,
    val contents: List<LetterContent>
)
