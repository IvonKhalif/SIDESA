package com.gov.sidesa.data.profile.repository

import com.gov.sidesa.data.profile.mapper.asData
import com.gov.sidesa.data.profile.mapper.asDomain
import com.gov.sidesa.data.profile.service.ProfileService
import com.gov.sidesa.domain.profile.ProfileRepository
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.edit.family.models.SaveFamily
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class ProfileRepositoryImpl(
    private val service: ProfileService
) : ProfileRepository {

    override suspend fun getProfileFamily(accountId: Long): NetworkResponse<ProfileFamily, GenericErrorResponse> {
        return service.getProfileFamily(accountId = accountId).asDomain {
            asDomain()
        }
    }

    override suspend fun updateProfileFamily(family: List<SaveFamily>): NetworkResponse<String, GenericErrorResponse> {
        return service.updateProfileFamily(family = family.asData()).asDomain {
            desc
        }
    }
}