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

        // assign text view value
        widgets.forEachIndexed { index, widget ->
            if (widget.type == WidgetType.TextView.type) {
                widgets[index] = assignTextView(widget = widget)
            }
        }

        return layout.copy(widgets = widgets)
    }

    private fun createHeader(letterName: String) = Widget(
        type = WidgetType.Header.type,
        title = letterName
    )

    // TODO set data from local storage
    private fun assignTextView(widget: Widget) = when (widget.name) {
        "nama" -> widget.copy(value = "Ivon Khalif")
        "alamat" -> widget.copy(value = "Jalanin aja, RT 001 RW 001, Bojong Utara, Bojong, Kabupaten Tangerang, Banten.")
        "pekerjaan" -> widget.copy(value = "Swasta")
        else -> widget
    }
}