package com.gov.sidesa.domain.letter.repository

import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface LetterListRepository {
    suspend fun getSubmissionLetters(
        account_id: String
    ): NetworkResponse<List<LetterSubmissionModel>, GenericErrorResponse>

    suspend fun getApprovalLetters(
        account_id: String
    ): NetworkResponse<LetterApprovalModel, GenericErrorResponse>
}