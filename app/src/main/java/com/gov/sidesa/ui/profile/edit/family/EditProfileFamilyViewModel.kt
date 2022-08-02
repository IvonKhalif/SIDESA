package com.gov.sidesa.ui.profile.edit.family

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.detail.family.usecases.GetFamilyUseCase
import com.gov.sidesa.domain.profile.edit.family.usecases.UpdateProfileFamilyUseCase
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.profile.edit.family.adapter.EditProfileFamilyListener
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyUiModel
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyViewType
import com.gov.sidesa.ui.profile.edit.family.models.RelationType
import com.gov.sidesa.ui.profile.edit.family.mapper.asEditFamily
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class EditProfileFamilyViewModel(
    private val getFamilyUseCase: GetFamilyUseCase,
    private val updateFamilyUseCase: UpdateProfileFamilyUseCase
) : BaseViewModel(), EditProfileFamilyListener {

    private val _profileFamily = MutableLiveData<ProfileFamily>()

    private val _componentData = MutableLiveData<List<EditProfileFamilyUiModel>>()
    val componentData: LiveData<List<EditProfileFamilyUiModel>> get() = _componentData

    private val _closeScreenState = MutableLiveData<Unit>()
    val closeScreenState: LiveData<Unit> get() = _closeScreenState

    private val _selectProvinceState = MutableLiveData<EditProfileFamilyUiModel>()
    val selectProvinceState: LiveData<EditProfileFamilyUiModel> get() = _selectProvinceState

    private val _selectDistrictState = MutableLiveData<EditProfileFamilyUiModel>()
    val selectDistrictState: LiveData<EditProfileFamilyUiModel> get() = _selectDistrictState

    private val _selectCityState = MutableLiveData<EditProfileFamilyUiModel>()
    val selectCityState: LiveData<EditProfileFamilyUiModel> get() = _selectCityState

    private val _selectVillageState = MutableLiveData<EditProfileFamilyUiModel>()
    val selectVillageState: LiveData<EditProfileFamilyUiModel> get() = _selectVillageState

    private val _selectBirthDateState = MutableLiveData<EditProfileFamilyUiModel>()
    val selectBirthDateState: LiveData<EditProfileFamilyUiModel> get() = _selectBirthDateState

    private val _submitSuccessState = MutableLiveData<Int>()
    val submitSuccessState: LiveData<Int> get() = _submitSuccessState

    init {
        viewModelScope.launch {
            showLoadingWidget()

            when (val result = getFamilyUseCase.invoke()) {
                is NetworkResponse.Success -> {
                    _profileFamily.value = result.body
                    _componentData.value = result.body.asEditFamily()
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
     * View Holder Listener Implementation
     */
    override fun onRelationStatusChanged(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(relationFamily = uiModel.relationFamily))
    }

    override fun onNameTextChanged(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(name = uiModel.name))
    }

    override fun onKTPChanged(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(ktpNumber = uiModel.ktpNumber))
    }

    override fun onBirthPlace(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(birthPlace = uiModel.birthPlace))
    }

    override fun onBirthDateClicked(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        _selectBirthDateState.value = upToDate
    }

    override fun onSameAddressClicked(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(differentAddress = uiModel.differentAddress))
    }

    override fun onAddressChanged(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(address = uiModel.address))
    }

    override fun onRTChanged(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(rt = uiModel.rt))
    }

    override fun onRWChanged(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        updateWidget(upToDate.copy(rw = uiModel.rw))
    }

    override fun onProvinceClicked(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)
        _selectProvinceState.value = upToDate
    }

    override fun onCityClicked(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)

        if (upToDate.province == null) {
            mServerErrorState.value = GenericErrorResponse(message = "Provinsi belum dipilih")
        } else {
            _selectCityState.value = upToDate
        }
    }

    override fun onDistrictClicked(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)

        if (upToDate.city == null) {
            mServerErrorState.value = GenericErrorResponse(message = "Kabupaten/Kota belum dipilih")
        } else {
            _selectDistrictState.value = upToDate
        }
    }

    override fun onVillageClicked(uiModel: EditProfileFamilyUiModel) {
        val upToDate = getComponentUpToDate(id = uiModel.id)

        if (upToDate.district == null) {
            mServerErrorState.value = GenericErrorResponse(message = "Kecamatan belum dipilih")
        } else {
            _selectVillageState.value = upToDate
        }
    }

    override fun onAddChild() {
        val component = _componentData.value.orEmpty()
            .filterNot { it.type == EditProfileFamilyViewType.AddChild }
            .toMutableList()
        val countChild = _componentData.value.orEmpty()
            .count { it.relationFamily is RelationType.Child }
        component.add(
            EditProfileFamilyUiModel(
                relationFamily = RelationType.Child(
                    countChild + 1
                )
            )
        )
        component.add(EditProfileFamilyUiModel(type = EditProfileFamilyViewType.AddChild))
        _componentData.value = component
    }

    /**
     * Callback other view
     */
    fun onProvinceSelected(uiModel: EditProfileFamilyUiModel, region: Region) {
        if (region.id != uiModel.province?.id) {
            updateWidget(
                uiModel = uiModel.copy(
                    province = region,
                    city = null,
                    district = null,
                    village = null
                )
            )
        }
    }

    fun onCitySelected(uiModel: EditProfileFamilyUiModel, region: Region) {
        if (region.id != uiModel.city?.id) {
            updateWidget(
                uiModel = uiModel.copy(
                    city = region,
                    district = null,
                    village = null
                )
            )
        }
    }

    fun onDistrictSelected(uiModel: EditProfileFamilyUiModel, region: Region) {
        if (region.id != uiModel.district?.id) {
            updateWidget(
                uiModel = uiModel.copy(
                    district = region,
                    village = null
                )
            )
        }
    }

    fun onVillageSelected(uiModel: EditProfileFamilyUiModel, region: Region) {
        updateWidget(uiModel = uiModel.copy(village = region))
    }

    fun onBirthDateSelected(uiModel: EditProfileFamilyUiModel, millis: Long) {
        updateWidget(uiModel = uiModel.copy(birthDate = Date(millis)))
    }

    /**
     * Update widget field when changed
     */
    private fun updateWidget(uiModel: EditProfileFamilyUiModel) {
        val components = _componentData.value.orEmpty().toMutableList()

        for (index in 0 until components.size - 1) {
            if (components[index].id == uiModel.id) {
                components[index] = uiModel
                break
            }
        }

        _componentData.postValue(components)
    }

    /**
     * Get data up-to-date
     */
    @Synchronized
    private fun getComponentUpToDate(id: Long): EditProfileFamilyUiModel {
        return _componentData.value.orEmpty().first { it.id == id }
    }

    fun onSubmit() = viewModelScope.launch {
        showLoadingWidget()

        val account = _profileFamily.value?.account ?: Account()
        val components = _componentData.value.orEmpty()
            .filter { it.type == EditProfileFamilyViewType.Form }

        when (val result = updateFamilyUseCase.invoke(account = account, components = components)) {
            is NetworkResponse.Success -> {
                _submitSuccessState.value = R.string.profile_family_edit_success
            }
            else -> onResponseNotSuccess(response = result)
        }

        hideLoadingWidget()
    }
}