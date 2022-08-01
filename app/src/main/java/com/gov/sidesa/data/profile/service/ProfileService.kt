package com.gov.sidesa.data.profile.service

import com.gov.sidesa.data.profile.models.ProfileFamilyResponse
import com.gov.sidesa.domain.profile.edit.family.models.SaveFamily
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

interface ProfileService {

    @GET("account/detail")
    suspend fun getProfileFamily(
        @Query("id_account") accountId: String
    ): NetworkResponse<ProfileFamilyResponse, GenericErrorResponse>

    @POST("account/update-family")
    suspend fun updateProfileFamily(
        @Body family: List<SaveFamily>
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}