package com.gov.sidesa.domain.profile.detail.family.usecases

import com.gov.sidesa.data.profile.request.EditProfileKTPRequest
import com.gov.sidesa.domain.profile.ProfileRepository

class UpdateDataKTPUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        request: EditProfileKTPRequest
    ) = repository.updateKTP(request)
}