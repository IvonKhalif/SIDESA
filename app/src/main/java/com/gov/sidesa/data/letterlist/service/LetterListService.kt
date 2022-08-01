package com.gov.sidesa.data.letterlist.service

import com.gov.sidesa.data.letterlist.response.LetterApprovalResponse
import com.gov.sidesa.data.letterlist.response.LetterSubmissionResponse
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitLetterApprovalResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LetterListService {
    @GET("pengajuan-surat")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getSubmissionLetters(
        @Query("id_account") accountId: Long
    ): NetworkResponse<RetrofitResponse<List<LetterSubmissionResponse>>, GenericErrorResponse>

    @GET("pengajuan-surat/list-approval")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getApprovalLetters(
        @Query("id_account") accountId: Long
    ): NetworkResponse<RetrofitLetterApprovalResponse<List<LetterApprovalResponse>>, GenericErrorResponse>
}