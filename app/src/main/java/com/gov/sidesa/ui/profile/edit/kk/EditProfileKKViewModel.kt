package com.gov.sidesa.ui.profile.edit.kk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.profile.edit.kk.models.AccountKKUiModel
import com.gov.sidesa.ui.profile.edit.kk.models.EditProfileKKUiEvent
import com.gov.sidesa.utils.response.GenericErrorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/


class EditProfileKKViewModel : BaseViewModel() {

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

    /**
     * Callback other view
     */
    fun onProvinceSelected(region: Region) {
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

    fun onCitySelected(region: Region) {
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

    fun onDistrictSelected(region: Region) {
        if (region.id != _uiModel.value.district.id) {
            _uiModel.update {
                it.copy(
                    district = region,
                    village = Region()
                )
            }
        }
    }

    fun onVillageSelected(region: Region) {
        if (region.id != _uiModel.value.village.id) {
            _uiModel.update {
                it.copy(village = region)
            }
        }
    }

    fun onImageSelected(file: File) {
        _uiModel.update { it.copy(kkImageUri = file.absolutePath) }
    }
}