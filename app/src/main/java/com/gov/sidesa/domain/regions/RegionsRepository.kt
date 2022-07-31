package com.gov.sidesa.domain.regions

import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

interface RegionsRepository {

    suspend fun getProvince(): NetworkResponse<List<Region>, GenericErrorResponse>

    suspend fun getCity(provinceId: Long): NetworkResponse<List<Region>, GenericErrorResponse>

    suspend fun getDistrict(cityId: Long): NetworkResponse<List<Region>, GenericErrorResponse>

    suspend fun getVillage(districtId: Long): NetworkResponse<List<Region>, GenericErrorResponse>
}