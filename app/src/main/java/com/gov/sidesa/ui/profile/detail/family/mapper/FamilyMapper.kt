package com.gov.sidesa.ui.profile.detail.family.mapper

import com.gov.sidesa.domain.profile.detail.family.models.Family
import com.gov.sidesa.ui.profile.detail.family.model.FamilyUiModel

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


fun Family.asUiModel() = FamilyUiModel(
    relationFamily = relationFamily,
    name = name,
    ktpNumber = ktpNumber,
    birthPlace = birthPlace,
    birthDate = birthDate,
    address = address,
    differentAddress = differentAddress
)

fun List<Family>.asUiModel() = map { it.asUiModel() }