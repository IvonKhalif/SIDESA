package com.gov.sidesa.domain.profile.detail.family.usecases

import com.gov.sidesa.domain.profile.ProfileRepository
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.isNullOrZero
import com.gov.sidesa.utils.extension.orZero
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class GetFamilyUseCase(
    private val repository: ProfileRepository
) {
    private val user by lazy {
        PreferenceUtils.getAccountUserResponse()
    }
    suspend operator fun invoke(id: Long? = null): NetworkResponse<ProfileFamily, GenericErrorResponse> =
        repository.getProfileFamily(accountId = if (id.isNullOrZero()) user?.id.orZero() else id.orZero())
}