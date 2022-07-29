package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


class GetLetterLayoutUseCase(
    private val repository: LetterRepository
) {
    suspend operator fun invoke(
        letterTypeId: String,
        letterName: String
    ): NetworkResponse<LetterLayout, GenericErrorResponse> {
        return when (val result = repository.getLayout(letterTypeId = letterTypeId)) {
            is NetworkResponse.Success -> {
                val layout = assignWidgetFromLocal(letterName, result.body)

                NetworkResponse.Success(layout)
            }
            else -> result
        }
    }

    private fun assignWidgetFromLocal(
        letterName: String,
        layout: LetterLayout
    ): LetterLayout {
        val widget = layout.widgets.toMutableList()

        // add header widget
        val header = createHeader(letterName = letterName)
        widget.add(0, header)

        return layout.copy(widgets = widget)
    }

    private fun createHeader(letterName: String) = Widget(
        type = WidgetType.Header.type,
        title = letterName
    )
}