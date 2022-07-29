package com.gov.sidesa.ui.letter.template.models

import com.gov.sidesa.domain.letter.template.models.Template

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/

data class LetterTemplateUiModel(
    val id: Int,
    val name: String
)

fun Template.asUiModel() = LetterTemplateUiModel(
    id = id,
    name = name
)

fun List<Template>.asUiModel() = map { it.asUiModel() }