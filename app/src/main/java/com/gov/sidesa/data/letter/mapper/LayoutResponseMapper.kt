package com.gov.sidesa.data.letter.mapper

import com.gov.sidesa.data.letter.models.WidgetResponse
import com.gov.sidesa.domain.letter.input.models.LetterLayout
import com.gov.sidesa.domain.letter.input.models.Widget

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


fun WidgetResponse.asDomain() = Widget(
    name = name,
    type = type,
    inputType = inputType,
    title = title,
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