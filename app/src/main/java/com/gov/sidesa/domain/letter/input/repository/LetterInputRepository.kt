package com.gov.sidesa.domain.letter.input.repository

import com.gov.sidesa.domain.letter.input.models.Resource
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

interface LetterInputRepository {

    suspend fun getResources(
        url: String,
        params: Map<String, String>
    ): NetworkResponse<List<Resource>, GenericErrorResponse>

}