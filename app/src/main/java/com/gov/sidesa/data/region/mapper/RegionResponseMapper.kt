package com.gov.sidesa.data.region.mapper

import com.gov.sidesa.data.region.models.CityResponse
import com.gov.sidesa.data.region.models.DistrictResponse
import com.gov.sidesa.data.region.models.ProvinceResponse
import com.gov.sidesa.data.region.models.VillageResponse
import com.gov.sidesa.domain.regions.models.Region

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/
 
fun ProvinceResponse.asDomain() = Region(
    id = id,
    parentId = null,
    name = name
)

fun CityResponse.asDomain() = Region(
    id = id,
    parentId = parentId,
    name = name
)

fun DistrictResponse.asDomain() = Region(
    id = id,
    parentId = parentId,
    name = name
)

fun VillageResponse.asDomain() = Region(
    id = id,
    parentId = parentId,
    name = name
)

fun List<ProvinceResponse>.asProvinceDomain() = map { it.asDomain() }
fun List<CityResponse>.asCityDomain() = map { it.asDomain() }
fun List<DistrictResponse>.asDistrictDomain() = map { it.asDomain() }
fun List<VillageResponse>.asVillageDomain() = map { it.asDomain() }