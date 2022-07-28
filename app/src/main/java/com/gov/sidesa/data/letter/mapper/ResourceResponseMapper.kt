package com.gov.sidesa.data.letter.mapper

import com.gov.sidesa.data.letter.models.ResourceResponse
import com.gov.sidesa.domain.letter.input.models.Resource

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/
 
fun ResourceResponse.asDomain() = Resource(
    id = id.toString(),
    name = name
)

fun List<ResourceResponse>.asDomain() = this.map {
    it.asDomain()
}