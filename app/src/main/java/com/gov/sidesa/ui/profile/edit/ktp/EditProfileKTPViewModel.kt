package com.gov.sidesa.ui.profile.edit.ktp

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.data.profile.request.EditProfileKTPRequest
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.domain.profile.detail.family.usecases.UpdateDataKTPUseCase
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.profile.detail.kk.mapper.asUiModel
import com.gov.sidesa.ui.profile.detail.kk.model.AccountUiModel
import com.gov.sidesa.utils.enums.StatusResponseEnum
import com.gov.sidesa.utils.extension.format
import com.gov.sidesa.utils.extension.orZero
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class EditProfileKTPViewModel(
    private val updateDataKTPUseCase: UpdateDataKTPUseCase
): BaseViewModel() {
    val inputKtpNik = MutableLiveData<String>()
    val inputKtpName = MutableLiveData<String>()
    val inputKtpPlace = MutableLiveData<String>()
    val inputKtpDob = MutableLiveData<Date>()
    val inputKtpAddress = MutableLiveData<String>()
    val inputKtpRt = MutableLiveData<String>()
    val inputKtpRw = MutableLiveData<String>()
    val inputKtpProvince = MutableLiveData<Region>()
    val inputKtpCity = MutableLiveData<Region>()
    val inputKtpKecamatan = MutableLiveData<Region>()
    val inputKtpKelurahan = MutableLiveData<Region>()
    val inputKtpReligion = MutableLiveData<String>()
    val inputKtpMarriage = MutableLiveData<String>()
    val inputKtpJob = MutableLiveData<String>()
    val inputKtpNationality = MutableLiveData<String>()
    val imageKTPBase64 = MutableLiveData<String>()

    private val _statusUpdateData = MutableLiveData<String>()
    val statusUpdateData: LiveData<String> get() = _statusUpdateData

    fun setDataProfile(detail: AccountUiModel) {
        inputKtpNik.value = detail.nik
        inputKtpName.value = detail.name
        inputKtpPlace.value = detail.birthPlace
        inputKtpDob.value = detail.birthDate
        inputKtpAddress.value = detail.address
        inputKtpRt.value = detail.rt
        inputKtpRw.value = detail.rw
        inputKtpProvince.value = Region(detail.provinceId.orZero(), null,detail.province)
        inputKtpCity.value = Region(detail.cityId.orZero(), null,detail.city)
        inputKtpKecamatan.value = Region(detail.districtId.orZero(), null,detail.district)
        inputKtpKelurahan.value = Region(detail.villageId.orZero(), null,detail.village)
        inputKtpReligion.value = detail.religion
        inputKtpMarriage.value = detail.maritalStatus
        inputKtpJob.value = detail.job
        inputKtpNationality.value = detail.citizenship
    }

    fun updateKTP(request: EditProfileKTPRequest) = viewModelScope.launch {
        showLoadingWidget()

        when (val result = updateDataKTPUseCase.invoke(request)) {
            is NetworkResponse.Success -> {
                result.body.status.let {
                    if (it == StatusResponseEnum.SUCCESS.status) {
                        _statusUpdateData.value = it
                    }
                }
            }
            else -> onResponseNotSuccess(response = result)
        }


        hideLoadingWidget()
    }

    fun base46Image(filePath: String) {
        val file = File(filePath)
        imageKTPBase64.value = Base64.encodeToString(file.readBytes(), Base64.NO_WRAP)
    }
}