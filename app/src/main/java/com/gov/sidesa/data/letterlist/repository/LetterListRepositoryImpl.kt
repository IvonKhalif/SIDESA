package com.gov.sidesa.data.letterlist.repository

import com.gov.sidesa.data.letterlist.mapper.asDomain
import com.gov.sidesa.data.letterlist.service.LetterListService
import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.domain.letter.repository.LetterListRepository
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

class LetterListRepositoryImpl(
    private val service: LetterListService
) : LetterListRepository {
    override suspend fun getSubmissionLetters(
        account_id: Long
    ): NetworkResponse<List<LetterSubmissionModel>, GenericErrorResponse> {
        return service.getSubmissionLetters(account_id).asDomain {
            data.takeIf { it != null }?.map {
                it.asDomain()
            } ?: emptyList()
        }
    }

    override suspend fun getApprovalLetters(account_id: Long): NetworkResponse<LetterApprovalModel, GenericErrorResponse> {
        return service.getApprovalLetters(account_id).asDomain {
            this.asDomain()!!
        }
    }
}