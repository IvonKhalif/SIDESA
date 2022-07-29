package com.gov.sidesa.domain.letter.repository

import com.gov.sidesa.data.letterdetail.models.DetailApprovalModel
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface LetterDetailRepository {
    suspend fun getDetail(
        letterTypeId: String,
        accountId: String
    ): NetworkResponse<RetrofitResponse<DetailApprovalModel>, GenericErrorResponse>
}