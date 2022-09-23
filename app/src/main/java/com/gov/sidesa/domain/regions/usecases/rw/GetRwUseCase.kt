package com.gov.sidesa.domain.regions.usecases.rw

import com.gov.sidesa.domain.regions.RegionsRepository
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

class GetRwUseCase(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(
        villageId: Long
    ): NetworkResponse<List<Region>, GenericErrorResponse> =
        repository.getRW(villageId = villageId)
}