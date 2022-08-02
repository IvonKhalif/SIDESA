package com.gov.sidesa.data.profile.service

import com.gov.sidesa.data.profile.models.ProfileFamilyResponse
import com.gov.sidesa.data.profile.models.SaveFamilyRequest
import com.gov.sidesa.data.profile.models.SaveKKRequest
import com.gov.sidesa.data.profile.request.EditProfileKTPRequest
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

interface ProfileService {

    @GET("account/detail")
    suspend fun getProfileFamily(
        @Query("id_account") accountId: Long
    ): NetworkResponse<ProfileFamilyResponse, GenericErrorResponse>

    @POST("account/update-family")
    suspend fun updateProfileFamily(
        @Body family: List<SaveFamilyRequest>
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    @POST("account/update-ktp")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun updateKTP(
        @Body request: EditProfileKTPRequest
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    @POST("account/update-kk")
    suspend fun updateKK(
        @Body kk: SaveKKRequest
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}