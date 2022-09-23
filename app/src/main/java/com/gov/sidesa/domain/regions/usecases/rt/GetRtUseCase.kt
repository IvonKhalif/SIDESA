package com.gov.sidesa.domain.regions.usecases.rt

import com.gov.sidesa.domain.regions.RegionsRepository
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

class GetRtUseCase(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(
        villageId: Long,
        rw: String
    ): NetworkResponse<List<Region>, GenericErrorResponse> =
        repository.getRT(villageId = villageId, rw = rw)
}