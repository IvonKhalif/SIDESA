package com.gov.sidesa.ui.profile.edit.family.models

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

sealed class EditProfileFamilyViewType(val type: Int) {
    object Header : EditProfileFamilyViewType(type = 1)
    object Form : EditProfileFamilyViewType(type = 2)
    object AddChild : EditProfileFamilyViewType(type = 3)
}
