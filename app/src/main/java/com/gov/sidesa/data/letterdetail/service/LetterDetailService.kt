package com.gov.sidesa.data.letterdetail.service

import com.gov.sidesa.data.letter.models.WidgetResponse
import com.gov.sidesa.data.letterdetail.models.DetailApprovalModel
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LetterDetailService {
    @GET("pengajuan-surat/detail")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getDetailApproval(
        @Query("id_type_surat") letterTypeId: String,
        @Query("id_account") accountId: String,
    ): NetworkResponse<RetrofitResponse<DetailApprovalModel>, GenericErrorResponse>
}