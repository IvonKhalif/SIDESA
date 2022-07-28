package com.gov.sidesa.data.letter.repository

import com.gov.sidesa.data.letter.mapper.asDomain
import com.gov.sidesa.data.letter.service.LetterService
import com.gov.sidesa.domain.letter.input.models.LetterLayout
import com.gov.sidesa.domain.letter.input.models.Resource
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/


class LetterRepositoryImpl(
    private val service: LetterService
) : LetterRepository {

    override suspend fun getResources(
        url: String,
        params: Map<String, String>
    ): NetworkResponse<List<Resource>, GenericErrorResponse> {
        val query = params.map {
            "${it.key}=${it.value}"
        }.joinToString("&")

        val request = if (query.isBlank()) url else "$url?$query"

        return service.getResources(url = request).asDomain {
            data.orEmpty().asDomain()
        }
    }

    override suspend fun getLayout(
        letterTypeId: String
    ): NetworkResponse<LetterLayout, GenericErrorResponse> {
        return service.getLetterLayout(letterTypeId = letterTypeId).asDomain {
            data.orEmpty().asDomain()
        }
    }
}
