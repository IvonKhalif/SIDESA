package com.gov.sidesa.data.letter.mapper

import com.gov.sidesa.data.letter.models.WidgetResponse
import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


fun WidgetResponse.asDomain() = Widget(
    name = name,
    type = type,
    inputType = inputType,
    title = title.orEmpty(),
    api = api,
    apiType = apiType,
    apiParam = apiParam
)

fun List<WidgetResponse>.asDomain() = LetterLayout(
    letterTypeId = (this.firstOrNull()?.letterTypeId ?: 0).toString(),
    widgets = map {
        it.asDomain()
    }
)