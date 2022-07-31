package com.gov.sidesa.ui.profile.edit.family.adapter

import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyUiModel

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

interface EditProfileFamilyListener {

    fun onRelationStatusClicked(uiModel: EditProfileFamilyUiModel)

    fun onNameTextChanged(uiModel: EditProfileFamilyUiModel)

    fun onKTPChanged(uiModel: EditProfileFamilyUiModel)

    fun onBirthPlace(uiModel: EditProfileFamilyUiModel)

    fun onBirthDateClicked(uiModel: EditProfileFamilyUiModel)

    fun onSameAddressClicked(uiModel: EditProfileFamilyUiModel)

    fun onAddressChanged(uiModel: EditProfileFamilyUiModel)

    fun onRTChanged(uiModel: EditProfileFamilyUiModel)

    fun onRWChanged(uiModel: EditProfileFamilyUiModel)

    fun onProvinceClicked(uiModel: EditProfileFamilyUiModel)

    fun onCityClicked(uiModel: EditProfileFamilyUiModel)

    fun onDistrictClicked(uiModel: EditProfileFamilyUiModel)

    fun onVillageClicked(uiModel: EditProfileFamilyUiModel)

    fun onAddChild()
}