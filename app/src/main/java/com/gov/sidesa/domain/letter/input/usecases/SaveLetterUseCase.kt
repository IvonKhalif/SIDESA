package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.input.models.save.LetterContent
import com.gov.sidesa.domain.letter.input.models.save.SaveLetter
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


class SaveLetterUseCase(
    private val repository: LetterRepository
) {

    suspend operator fun invoke(
        letterTypeId: String,
        widget: List<BaseWidgetUiModel>
    ): NetworkResponse<String, GenericErrorResponse> {
        val haveEmptyField = widget.any { it.value.isNullOrBlank() }

        if (haveEmptyField)
            return NetworkResponse.ServerError(GenericErrorResponse(
                status = "Mohon Lengkapi Data Sebelum Dikirim"
            ), 0)

        val letter = SaveLetter(
            accountId = "3",
            letterTypeId = letterTypeId,
            contents = widget.filterNot {
                it.type == WidgetType.Header
            }.map {
                LetterContent(field = it.name, value = it.value.orEmpty())
            }
        )

        return repository.save(letter = letter)
    }
}