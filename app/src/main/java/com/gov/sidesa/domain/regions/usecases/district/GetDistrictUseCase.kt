package com.gov.sidesa.domain.regions.usecases.district

import com.gov.sidesa.domain.regions.RegionsRepository

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class GetDistrictUseCase(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(cityId: Int) {

    }
}