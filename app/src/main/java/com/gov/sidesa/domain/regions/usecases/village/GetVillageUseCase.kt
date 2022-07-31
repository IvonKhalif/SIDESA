package com.gov.sidesa.domain.regions.usecases.village

import com.gov.sidesa.domain.regions.RegionsRepository
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class GetVillageUseCase(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(
        districtId: Long
    ): NetworkResponse<List<Region>, GenericErrorResponse> =
        repository.getVillage(districtId = districtId)
}