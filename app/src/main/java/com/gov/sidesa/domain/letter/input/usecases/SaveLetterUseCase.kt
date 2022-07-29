package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


class SaveLetterUseCase(
    private val repository: LetterRepository
) {

    suspend operator fun invoke(
        letterTypeId: Int,
        widget: List<BaseWidgetUiModel>
    ) {

    }
}