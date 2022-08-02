package com.gov.sidesa.ui.profile.edit.kk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.detail.family.usecases.GetFamilyUseCase
import com.gov.sidesa.domain.profile.edit.kk.usecases.UpdateProfileKKUseCase
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.profile.edit.kk.mapper.asDomain
import com.gov.sidesa.ui.profile.edit.kk.mapper.asEditKK
import com.gov.sidesa.ui.profile.edit.kk.models.AccountKKUiModel
import com.gov.sidesa.ui.profile.edit.kk.models.EditProfileKKUiEvent
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/


class EditProfileKKViewModel(
    private val getFamilyUseCase: GetFamilyUseCase,
    private val updateProfileKKUseCase: UpdateProfileKKUseCase
) : BaseViewModel() {

    private val _profileFamily = MutableLiveData<ProfileFamily>()

    private val _closeScreenState = MutableLiveData<Unit>()
    val closeScreenState: LiveData<Unit> get() = _closeScreenState

    private val _selectProvinceState = MutableLiveData<Unit>()
    val selectProvinceState: LiveData<Unit> get() = _selectProvinceState

    private val _selectDistrictState = MutableLiveData<Long>()
    val selectDistrictState: LiveData<Long> get() = _selectDistrictState

    private val _selectCityState = MutableLiveData<Long>()
    val selectCityState: LiveData<Long> get() = _selectCityState

    private val _selectVillageState = MutableLiveData<Long>()
    val selectVillageState: LiveData<Long> get() = _selectVillageState

    private val _selectPhotoState = MutableLiveData<Unit>()
    val selectPhotoState: LiveData<Unit> get() = _selectPhotoState

    private val _uiModel = MutableStateFlow(AccountKKUiModel())
    val uiModel get() = _uiModel.asStateFlow()

    private val _submitSuccessState = MutableLiveData<Int>()
    val submitSuccessState: LiveData<Int> get() = _submitSuccessState

    init {
        viewModelScope.launch {
            showLoadingWidget()

            when (val result = getFamilyUseCase.invoke()) {
                is NetworkResponse.Success -> {
                    _profileFamily.value = result.body
                    _uiModel.value = result.body.asEditKK()
                }
                else -> {
                    onResponseNotSuccess(response = result)
                    _closeScreenState.value = Unit
                }
            }

            hideLoadingWidget()
        }
    }

    /**
     * event handling
     */
    fun onEvent(event: EditProfileKKUiEvent) = viewModelScope.launch {
        when (event) {
            is EditProfileKKUiEvent.OnProvinceClicked -> {
                _selectProvinceState.value = Unit
            }
            is EditProfileKKUiEvent.OnCityClicked -> {
                onCityClicked()
            }
            is EditProfileKKUiEvent.OnDistrictClicked -> {
                onDistrictClicked()
            }
            is EditProfileKKUiEvent.OnVillageClicked -> {
                onVillageClicked()
            }
            is EditProfileKKUiEvent.OnKKImageClicked -> {
                _selectPhotoState.value = Unit
            }
            is EditProfileKKUiEvent.OnKKChanged -> {
                _uiModel.update { it.copy(kk = event.text) }
            }
            is EditProfileKKUiEvent.OnFamilyHeadChanged -> {
                _uiModel.update { it.copy(familyHeadName = event.text) }
            }
            is EditProfileKKUiEvent.OnAddressChanged -> {
                _uiModel.update { it.copy(address = event.text) }
            }
            is EditProfileKKUiEvent.OnRTChanged -> {
                _uiModel.update { it.copy(rt = event.text) }
            }
            is EditProfileKKUiEvent.OnRWChanged -> {
                _uiModel.update { it.copy(rw = event.text) }
            }
            is EditProfileKKUiEvent.OnSubmit -> {
                onSubmit()
            }
        }
    }

    private fun onCityClicked() {
        if (_uiModel.value.province.id == 0L) {
            mServerErrorState.value = GenericErrorResponse(message = "Provinsi belum dipilih")
        } else {
            _selectCityState.value = _uiModel.value.province.id
        }
    }

    private fun onDistrictClicked() {
        if (_uiModel.value.city.id == 0L) {
            mServerErrorState.value = GenericErrorResponse(message = "Kabupaten/Kota belum dipilih")
        } else {
            _selectDistrictState.value = _uiModel.value.city.id
        }
    }

    private fun onVillageClicked() {
        if (_uiModel.value.district.id == 0L) {
            mServerErrorState.value = GenericErrorResponse(message = "Kecamatan belum dipilih")
        } else {
            _selectVillageState.value = _uiModel.value.district.id
        }
    }

    private fun onSubmit() = viewModelScope.launch {
        showLoadingWidget()

        val account = _profileFamily.value?.account ?: Account()
        val accountKK = _uiModel.value

        when (val result = updateProfileKKUseCase.invoke(accountKK.asDomain(account))) {
            is NetworkResponse.Success -> {
                _submitSuccessState.value = R.string.profile_kk_edit_success
            }
            else -> onResponseNotSuccess(response = result)
        }

        hideLoadingWidget()
    }

    /**
     * Callback other view
     */
    fun onProvinceSelected(region: Region) = viewModelScope.launch {
        if (region.id != _uiModel.value.province.id) {
            _uiModel.update {
                it.copy(
                    province = region,
                    city = Region(),
                    district = Region(),
                    village = Region()
                )
            }
        }
    }

    fun onCitySelected(region: Region) = viewModelScope.launch {
        if (region.id != _uiModel.value.city.id) {
            _uiModel.update {
                it.copy(
                    city = region,
                    district = Region(),
                    village = Region()
                )
            }
        }
    }

    fun onDistrictSelected(region: Region) = viewModelScope.launch {
        if (region.id != _uiModel.value.district.id) {
            _uiModel.update {
                it.copy(
                    district = region,
                    village = Region()
                )
            }
        }
    }

    fun onVillageSelected(region: Region) = viewModelScope.launch {
        if (region.id != _uiModel.value.village.id) {
            _uiModel.update {
                it.copy(village = region)
            }
        }
    }

    fun onImageSelected(file: File) = viewModelScope.launch {
        _uiModel.update { it.copy(kkImageUri = file.absolutePath) }
    }
}