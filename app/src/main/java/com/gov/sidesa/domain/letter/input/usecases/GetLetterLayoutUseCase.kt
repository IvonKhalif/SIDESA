package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


class GetLetterLayoutUseCase(
    private val repository: LetterRepository,
    private val updateWidgetFromLocalUseCase: UpdateWidgetFromLocalUseCase
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

    /**
     * Modify widget from be response
     */
    private fun assignWidgetFromLocal(
        letterName: String,
        layout: LetterLayout
    ): LetterLayout {
        val widgets = layout.widgets.toMutableList()

        // add header widget
        val header = createHeader(letterName = letterName)
        widgets.add(0, header)

        // assign value from local
        val user = PreferenceUtils.getUser()
        if (user != null) {
            widgets.forEachIndexed { index, widget ->
                widgets[index] = updateWidgetFromLocalUseCase.invoke(widget = widget, user = user)
            }
        }

        return layout.copy(widgets = widgets)
    }

    private fun createHeader(letterName: String) = Widget(
        type = WidgetType.Header.type,
        title = letterName
    )
}