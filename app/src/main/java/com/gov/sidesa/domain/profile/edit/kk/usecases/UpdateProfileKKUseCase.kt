package com.gov.sidesa.domain.profile.edit.kk.usecases

import com.gov.sidesa.domain.profile.ProfileRepository
import com.gov.sidesa.domain.profile.edit.kk.models.SaveKK
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/


class UpdateProfileKKUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        kk: SaveKK
    ): NetworkResponse<String, GenericErrorResponse> {
        return repository.updateKK(kk = kk)
    }
}