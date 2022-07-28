package com.gov.sidesa.data.letter.mapper

import com.gov.sidesa.data.letter.models.LetterTemplateResponse
import com.gov.sidesa.domain.letter.template.models.Template

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/
 
fun LetterTemplateResponse.asDomain() = Template(
    id = letterTypeId,
    name = letterName
)

fun List<LetterTemplateResponse>.asDomain() = map {
    it.asDomain()
}