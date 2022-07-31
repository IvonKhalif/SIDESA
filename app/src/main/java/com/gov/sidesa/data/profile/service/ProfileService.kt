package com.gov.sidesa.data.profile.service

import com.gov.sidesa.data.profile.models.ProfileFamilyResponse
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
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
}