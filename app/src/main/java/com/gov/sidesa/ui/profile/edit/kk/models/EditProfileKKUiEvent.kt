package com.gov.sidesa.ui.profile.edit.kk.models

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/

sealed class EditProfileKKUiEvent {
    data class OnKKChanged(val text: String): EditProfileKKUiEvent()
    data class OnFamilyHeadChanged(val text: String): EditProfileKKUiEvent()
    data class OnAddressChanged(val text: String): EditProfileKKUiEvent()
    data class OnRTChanged(val text: String): EditProfileKKUiEvent()
    data class OnRWChanged(val text: String): EditProfileKKUiEvent()
    object OnProvinceClicked: EditProfileKKUiEvent()
    object OnCityClicked: EditProfileKKUiEvent()
    object OnDistrictClicked: EditProfileKKUiEvent()
    object OnVillageClicked: EditProfileKKUiEvent()
    object OnKKImageClicked: EditProfileKKUiEvent()
    object OnSubmit : EditProfileKKUiEvent()
}