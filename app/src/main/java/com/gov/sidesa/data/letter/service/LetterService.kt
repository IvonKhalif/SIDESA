package com.gov.sidesa.data.letter.service

import com.gov.sidesa.data.letter.models.ResourceResponse
import com.gov.sidesa.data.letter.models.WidgetResponse
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

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
}