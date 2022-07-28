package com.gov.sidesa.domain.letter.template.usecases

import com.gov.sidesa.domain.letter.repository.LetterRepository

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


class GetTemplateUseCase(
    private val repository: LetterRepository
) {

    suspend operator fun invoke() = repository.getTemplate()
}