package com.gov.sidesa.domain.letter.repository

import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface LetterDetailRepository {
    suspend fun getDetail(
        letterId: String,
        accountId: String
    ): NetworkResponse<DetailApprovalModel, GenericErrorResponse>

    suspend fun doApproval(request: DoApprovalRequest):
            NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}