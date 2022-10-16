package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.input.models.save.LetterContent
import com.gov.sidesa.domain.letter.input.models.save.SaveLetter
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.ui.letter.input.models.attachment.AttachmentWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.orZero
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
        letterTypeId: Long,
        widget: List<BaseWidgetUiModel>
    ): NetworkResponse<String, GenericErrorResponse> {
        val haveEmptyField = widget.filter {
            it.type == WidgetType.EditText && it.value.isNullOrBlank()
        }.joinToString(",") { it.name }

        if (haveEmptyField.isNotBlank())
            return NetworkResponse.ServerError(
                body = GenericErrorResponse(
                    status = "Mohon Lengkapi Data Sebelum Dikirim\n$haveEmptyField"
                ),
                code = 0
            )

        val letter = SaveLetter(
            accountId = PreferenceUtils.getAccount()?.id.orZero(),
            letterTypeId = letterTypeId,
            contents = widget.filterNot {
                it.type == WidgetType.Header || it.type == WidgetType.Attachment
            }.map {
                LetterContent(field = it.name, value = it.value.orEmpty())
            },
            attachments = widget.filter { it.type == WidgetType.Attachment }
                .map { it as AttachmentWidgetUiModel }
                .map { it.files }
                .reduce { acc, files -> acc + files }
        )

        return repository.save(letter = letter)
    }
}