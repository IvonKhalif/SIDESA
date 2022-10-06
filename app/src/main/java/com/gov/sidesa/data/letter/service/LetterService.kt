package com.gov.sidesa.data.letter.service

import com.gov.sidesa.data.letter.models.LetterTemplateResponse
import com.gov.sidesa.data.letter.models.ResourceResponse
import com.gov.sidesa.data.letter.models.WidgetResponse
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface LetterService {

    @GET
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getResources(
        @Url url: String
    ): NetworkResponse<RetrofitResponse<List<ResourceResponse>>, GenericErrorResponse>

    @GET("master/surat/template")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getLetterLayout(
        @Query("id_type_surat") letterTypeId: String
    ): NetworkResponse<RetrofitResponse<List<WidgetResponse>>, GenericErrorResponse>

    @GET("master/surat")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getTemplates()
            : NetworkResponse<RetrofitResponse<List<LetterTemplateResponse>>, GenericErrorResponse>

    @Multipart
    @POST("pengajuan-surat/save")
    suspend fun save(
        @PartMap letter: Map<String, String>,
        @Part files: List<MultipartBody.Part>,
        @Part filesCount: MultipartBody.Part
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}