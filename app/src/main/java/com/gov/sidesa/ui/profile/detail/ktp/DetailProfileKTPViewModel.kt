package com.gov.sidesa.ui.profile.detail.ktp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.detail.family.usecases.GetFamilyUseCase
import com.gov.sidesa.ui.profile.detail.kk.mapper.asUiModel
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class DetailProfileKTPViewModel(
    private val getFamilyUseCase: GetFamilyUseCase
) : BaseViewModel() {

    private val _profileFamilyData = MutableLiveData<ProfileFamily>()

    val profileData: LiveData<AccountUiModel>
        get() = _profileFamilyData.map {
            it.account.asUiModel()
        }

    init {
        onLoadKTP()
    }

    fun onLoadKTP() {
        viewModelScope.launch {
            showLoadingWidget()

            when (val result = getFamilyUseCase.invoke()) {
                is NetworkResponse.Success -> {
                    _profileFamilyData.value = result.body
                }
                else -> onResponseNotSuccess(response = result)
            }

            hideLoadingWidget()
        }
    }
}