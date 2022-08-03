package com.gov.sidesa.ui.profile.detail.kk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.detail.family.models.asData
import com.gov.sidesa.domain.profile.detail.family.usecases.GetFamilyUseCase
import com.gov.sidesa.ui.profile.detail.kk.mapper.asUiModel
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.gov.sidesa.utils.PreferenceUtils
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/


class DetailProfileKKViewModel(
    private val getFamilyUseCase: GetFamilyUseCase
) : BaseViewModel() {

    private val _profileFamilyData = MutableLiveData<ProfileFamily>()

    val profileKKData: LiveData<AccountUiModel>
        get() = _profileFamilyData.map {
            it.account.asUiModel()
        }

    init {
        onLoad()
    }

    private fun onLoad() = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getFamilyUseCase.invoke()) {
            is NetworkResponse.Success -> {
                _profileFamilyData.value = result.body
                PreferenceUtils.putUser(result.body.account.asData())
            }
            else -> onResponseNotSuccess(response = result)
        }

        hideLoadingWidget()
    }

    fun onEditResult() {
        onLoad()
    }
}