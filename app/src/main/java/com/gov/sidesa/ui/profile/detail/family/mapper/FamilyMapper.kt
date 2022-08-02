package com.gov.sidesa.ui.profile.detail.family.mapper

import com.gov.sidesa.domain.profile.detail.family.models.Family
import com.gov.sidesa.ui.profile.detail.family.model.FamilyUiModel
import com.gov.sidesa.ui.profile.edit.family.models.RelationType

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


fun Family.asUiModel(childCount: Int) = FamilyUiModel(
    relationType = RelationType.find(relationType, childCount),
    name = name,
    ktpNumber = ktpNumber,
    birthPlace = birthPlace,
    birthDate = birthDate,
    address = address,
    rt = rt,
    rw = rw,
    province = province,
    city = city,
    district = district,
    village = village
)

fun List<Family>.asUiModel(): List<FamilyUiModel> {
    var childCount = 0

    return map {
        if (it.relationType.lowercase() == "anak") {
            childCount++
        }
        it.asUiModel(childCount)
    }
}