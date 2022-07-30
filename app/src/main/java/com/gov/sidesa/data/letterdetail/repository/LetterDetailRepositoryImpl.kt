package com.gov.sidesa.data.letterdetail.repository

import com.gov.sidesa.data.letterdetail.mapper.asDomain
import com.gov.sidesa.data.letterdetail.models.DetailApprovalResponse
import com.gov.sidesa.data.letterdetail.service.LetterDetailService
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.domain.letter.repository.LetterDetailRepository
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse

class LetterDetailRepositoryImpl(
    private val service: LetterDetailService
) : LetterDetailRepository {
    override suspend fun getDetail(
        letterId: String,
        accountId: String
    ): NetworkResponse<DetailApprovalModel, GenericErrorResponse> {
        return service.getDetailApproval(letterId, accountId).asDomain {
            data!!.asDomain()
        }
    }
}