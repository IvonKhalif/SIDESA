package com.gov.sidesa.domain.letter.repository

import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface LetterListRepository {
    suspend fun getSubmissionLetters(
        account_id: Long
    ): NetworkResponse<List<LetterSubmissionModel>, GenericErrorResponse>

    suspend fun getApprovalLetters(
        account_id: Long
    ): NetworkResponse<LetterApprovalModel, GenericErrorResponse>
}