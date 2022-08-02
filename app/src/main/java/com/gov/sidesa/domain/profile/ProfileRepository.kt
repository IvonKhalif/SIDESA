package com.gov.sidesa.domain.profile

import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.data.profile.request.EditProfileKTPRequest
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.edit.family.models.SaveFamily
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

interface ProfileRepository {

    suspend fun getProfileFamily(
        accountId: Long
    ): NetworkResponse<ProfileFamily, GenericErrorResponse>

    suspend fun updateKTP(request: EditProfileKTPRequest):
            NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    suspend fun updateProfileFamily(
        family: List<SaveFamily>
    ): NetworkResponse<String, GenericErrorResponse>
}