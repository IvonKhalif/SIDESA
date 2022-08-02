package com.gov.sidesa.domain.letter.input.models.save

/**
 * Created by yovi.putra on 29/07/22"
 * Project name: SIDESA
 **/

data class SaveLetter(
    val accountId: Long,
    val letterTypeId: Long,
    val contents: List<LetterContent>
)
