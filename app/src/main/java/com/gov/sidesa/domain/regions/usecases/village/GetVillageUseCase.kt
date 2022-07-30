package com.gov.sidesa.domain.regions.usecases.village

import com.gov.sidesa.domain.regions.RegionsRepository

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class GetVillageUseCase(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(districtId: Int) {

    }
}