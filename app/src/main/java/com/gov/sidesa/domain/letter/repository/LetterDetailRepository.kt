package com.gov.sidesa.domain.letter.repository

import com.gov.sidesa.data.letterdetail.models.DetailApprovalResponse
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface LetterDetailRepository {
    suspend fun getDetail(
        letterId: String,
        accountId: String
    ): NetworkResponse<DetailApprovalModel, GenericErrorResponse>
}