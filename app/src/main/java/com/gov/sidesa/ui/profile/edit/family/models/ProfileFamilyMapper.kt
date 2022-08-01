package com.gov.sidesa.ui.profile.edit.family.models

import com.gov.sidesa.domain.profile.detail.family.models.Family
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

fun ProfileFamily.asEditFamily(): List<EditProfileFamilyUiModel> {
    var childCount = 0
    val components = arrayListOf<EditProfileFamilyUiModel>()
    components.add(EditProfileFamilyUiModel(type = EditProfileFamilyViewType.Header))

    family.forEach {
        val family = it.asEditFamily()
        val isSameAddress = sameAddress(it)

        if (family.relationFamily is RelationType.Child) {
            childCount++
            components.add(
                family.copy(
                    relationFamily = RelationType.Child(childCount),
                    differentAddress = isSameAddress.not()
                )
            )
        } else {
            components.add(family.copy(differentAddress = isSameAddress.not()))
        }
    }

    components.add(EditProfileFamilyUiModel(type = EditProfileFamilyViewType.AddChild))

    return components
}

fun Family.asEditFamily() = EditProfileFamilyUiModel(
    relationFamily = RelationType.find(relationType),
    name = name,
    ktpNumber = ktpNumber,
    birthDate = birthDate,
    birthPlace = birthPlace,
    address = address,
    rt = rt,
    rw = rw,
    province = province,
    city = city,
    district = district,
    village = village
)