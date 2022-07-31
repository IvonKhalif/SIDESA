package com.gov.sidesa.data.user

import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("account/validation-nik")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun validateNIK(
        @Query("nik") nik: String
    ): NetworkResponse<RetrofitResponse<User>, GenericErrorResponse>

    @POST("account/login")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun login(
        @Query("username") userName: String,
        @Query("password") password: String
    ): NetworkResponse<RetrofitResponse<User>, GenericErrorResponse>

    @POST("account/create-password")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun createPassword(
        @Query("nik") nik: String,
        @Query("password") password: String
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    @POST("account/reset-password")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun resetPassword(
        @Query("id_account") accountId: String,
        @Query("password") password: String
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}