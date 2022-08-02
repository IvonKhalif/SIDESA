package com.gov.sidesa.data.letterdetail.service

import com.gov.sidesa.data.letterdetail.models.DetailApprovalResponse
import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

interface LetterDetailService {
    @GET("pengajuan-surat/detail")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getDetailApproval(
        @Query("id_pengajuan_surat") letterId: String,
        @Query("id_account") accountId: Long,
    ): NetworkResponse<RetrofitResponse<DetailApprovalResponse>, GenericErrorResponse>

    @POST("pengajuan-surat/approval")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun doApproval(
        @Body request: DoApprovalRequest
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}