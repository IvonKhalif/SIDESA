package com.gov.sidesa.domain.letter.repository

import com.gov.sidesa.domain.letter.input.models.LetterLayout
import com.gov.sidesa.domain.letter.input.models.Resource
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

interface LetterRepository {

    suspend fun getResources(
        url: String,
        params: Map<String, String>
    ): NetworkResponse<List<Resource>, GenericErrorResponse>

    suspend fun getLayout(
        letterTypeId: String
    ): NetworkResponse<LetterLayout, GenericErrorResponse>
}