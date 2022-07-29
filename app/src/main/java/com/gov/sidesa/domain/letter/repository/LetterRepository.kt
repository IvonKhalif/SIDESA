package com.gov.sidesa.domain.letter.repository

import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.domain.letter.input.models.save.SaveLetter
import com.gov.sidesa.domain.letter.template.models.Template
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

    suspend fun getTemplate(): NetworkResponse<List<Template>, GenericErrorResponse>

    suspend fun save(letter: SaveLetter): NetworkResponse<String, GenericErrorResponse>
}