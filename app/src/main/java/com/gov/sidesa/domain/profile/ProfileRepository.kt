package com.gov.sidesa.domain.profile

import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.edit.family.models.SaveFamily
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

interface ProfileRepository {

    suspend fun getProfileFamily(
        accountId: Long
    ): NetworkResponse<ProfileFamily, GenericErrorResponse>

    suspend fun updateProfileFamily(
        family: List<SaveFamily>
    ): NetworkResponse<String, GenericErrorResponse>
}