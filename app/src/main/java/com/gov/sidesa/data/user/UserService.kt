package com.gov.sidesa.data.user

import com.gov.sidesa.data.registration.family.FamilyApplicantModel
import com.gov.sidesa.data.registration.family.FamilyChildModel
import com.gov.sidesa.data.registration.family.FamilyFatherModel
import com.gov.sidesa.data.registration.family.FamilyMotherModel
import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

interface UserService {
    @GET("account/validation-nik")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun validateNIK(
        @Query("nik") nik: String
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    @POST("account/login")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun login(
        @Query("username") userName: String,
        @Query("password") password: String
    ): NetworkResponse<RetrofitResponse<User>, GenericErrorResponse>

    @POST("account/create-password")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun submit(
        @Body fatherPayload: FamilyFatherModel,
        @Body motherPayload: FamilyMotherModel,
        @Body applicantPayload: FamilyApplicantModel,
        @Body listChildPayload: List<FamilyChildModel>
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}