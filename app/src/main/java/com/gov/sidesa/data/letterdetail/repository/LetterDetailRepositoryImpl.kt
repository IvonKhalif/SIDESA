package com.gov.sidesa.data.letterdetail.repository

import com.gov.sidesa.data.letterdetail.mapper.asDomain
import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.data.letterdetail.service.LetterDetailService
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.domain.letter.repository.LetterDetailRepository
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse

class LetterDetailRepositoryImpl(
    private val service: LetterDetailService
) : LetterDetailRepository {
    override suspend fun getDetail(
        letterId: String,
        accountId: Long
    ): NetworkResponse<DetailApprovalModel, GenericErrorResponse> {
        return service.getDetailApproval(letterId, accountId).asDomain {
            data!!.asDomain()
        }
    }

    override suspend fun doApproval(request: DoApprovalRequest): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse> =
        service.doApproval(request)
}