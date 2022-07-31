package com.gov.sidesa.domain.regions.usecases.city

import com.gov.sidesa.domain.regions.RegionsRepository
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class GetCityUseCase(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(
        provinceId: Long
    ): NetworkResponse<List<Region>, GenericErrorResponse> =
        repository.getCity(provinceId = provinceId)
}