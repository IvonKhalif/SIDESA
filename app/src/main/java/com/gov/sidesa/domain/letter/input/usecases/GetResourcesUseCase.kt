package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.repository.LetterRepository

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/


class GetResourcesUseCase(
    private val repository: LetterRepository
) {

    suspend operator fun invoke(
        url: String,
        params: Map<String, String>
    ) = repository.getResources(url = url, params = params)
}