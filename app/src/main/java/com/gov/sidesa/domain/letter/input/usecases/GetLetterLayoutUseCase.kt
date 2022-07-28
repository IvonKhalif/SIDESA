package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.input.models.LetterLayout
import com.gov.sidesa.domain.letter.input.models.Widget
import com.gov.sidesa.domain.letter.input.models.WidgetType
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
        return when(val result = repository.getLayout(letterTypeId = letterTypeId)) {
            is NetworkResponse.Success -> {
                val widget = result.body.widgets.toMutableList()
                val header = Widget(
                    type = WidgetType.Header.type,
                    title = letterName
                )
                widget.add(0, header)
                NetworkResponse.Success(result.body.copy(widgets = widget))
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