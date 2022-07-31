package com.gov.sidesa.data.region.repository

import com.gov.sidesa.data.region.mapper.asCityDomain
import com.gov.sidesa.data.region.mapper.asDistrictDomain
import com.gov.sidesa.data.region.mapper.asProvinceDomain
import com.gov.sidesa.data.region.mapper.asVillageDomain
import com.gov.sidesa.data.region.service.RegionService
import com.gov.sidesa.domain.regions.RegionsRepository
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class RegionsRepositoryImpl(
    private val service: RegionService
) : RegionsRepository {

    override suspend fun getProvince(): NetworkResponse<List<Region>, GenericErrorResponse> {
        return service.getProvince().asDomain {
            data.asProvinceDomain()
        }
    }

    override suspend fun getCity(provinceId: Long): NetworkResponse<List<Region>, GenericErrorResponse> {
        return service.getCity(provinceId = provinceId).asDomain {
            data.asCityDomain()
        }
    }

    override suspend fun getDistrict(cityId: Long): NetworkResponse<List<Region>, GenericErrorResponse> {
        return service.getDistrict(cityId = cityId).asDomain {
            data.asDistrictDomain()
        }
    }

    override suspend fun getVillage(districtId: Long): NetworkResponse<List<Region>, GenericErrorResponse> {
        return service.getVillage(districtId = districtId).asDomain {
            data.asVillageDomain()
        }
    }
}