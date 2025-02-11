package com.gov.sidesa.ui.profile.detail.family

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.detail.family.usecases.GetFamilyUseCase
import com.gov.sidesa.ui.profile.detail.family.mapper.asUiModel
import com.gov.sidesa.ui.profile.detail.family.model.FamilyUiModel
import com.gov.sidesa.utils.PreferenceUtils
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class DetailProfileFamilyViewModel(
    private val getFamilyUseCase: GetFamilyUseCase
) : BaseViewModel() {

    private val _profileFamilyData = MutableLiveData<ProfileFamily>()

    val familyData: LiveData<List<FamilyUiModel>>
        get() = _profileFamilyData.map {
            it.family.asUiModel()
        }

    private val _closeScreenState = MutableLiveData<Unit>()
    val closeScreenState: LiveData<Unit> get() = _closeScreenState

    init {
        onLoadData()
    }

    private fun onLoadData() = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getFamilyUseCase.invoke()) {
            is NetworkResponse.Success -> {
                _profileFamilyData.value = result.body
                PreferenceUtils.putProfile(result.body)
            }
            else -> {
                onResponseNotSuccess(response = result)
                _closeScreenState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    fun onEditResult() {
        onLoadData()
    }
}