package com.gov.sidesa.data.region.mapper

import com.gov.sidesa.data.region.models.*
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

fun RtRwResponse.asDomain() = Region(
    id = id,
    parentId = null,
    name = name
)

fun List<ProvinceResponse>.asProvinceDomain() = map { it.asDomain() }
fun List<CityResponse>.asCityDomain() = map { it.asDomain() }
fun List<DistrictResponse>.asDistrictDomain() = map { it.asDomain() }
fun List<VillageResponse>.asVillageDomain() = map { it.asDomain() }
fun List<RtRwResponse>.asRwDomain() = map { it.asDomain() }
fun List<RtRwResponse>.asRtDomain() = map { it.asDomain() }