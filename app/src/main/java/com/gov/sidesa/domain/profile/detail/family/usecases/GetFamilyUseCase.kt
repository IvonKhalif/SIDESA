package com.gov.sidesa.domain.profile.detail.family.usecases

import com.gov.sidesa.domain.profile.ProfileRepository
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class GetFamilyUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): NetworkResponse<ProfileFamily, GenericErrorResponse> =
        repository.getProfileFamily(accountId = "3")
}