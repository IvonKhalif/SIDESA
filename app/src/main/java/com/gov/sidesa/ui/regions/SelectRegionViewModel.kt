package com.gov.sidesa.ui.regions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.domain.regions.usecases.city.GetCityUseCase
import com.gov.sidesa.domain.regions.usecases.district.GetDistrictUseCase
import com.gov.sidesa.domain.regions.usecases.province.GetProvinceUseCase
import com.gov.sidesa.domain.regions.usecases.rt.GetRtUseCase
import com.gov.sidesa.domain.regions.usecases.rw.GetRwUseCase
import com.gov.sidesa.domain.regions.usecases.village.GetVillageUseCase
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class SelectRegionViewModel(
    private val getProvinceUseCase: GetProvinceUseCase,
    private val getCityUseCase: GetCityUseCase,
    private val getDistrictUseCase: GetDistrictUseCase,
    private val getVillageUseCase: GetVillageUseCase,
    private val getRwUseCase: GetRwUseCase,
    private val getRtUseCase: GetRtUseCase
) : BaseViewModel() {

    private val _titleText = MutableLiveData<Int>()
    val titleText: LiveData<Int> get() = _titleText

    private val _closeViewState = MutableLiveData<Unit>()
    val closeViewState: LiveData<Unit> get() = _closeViewState

    private val _regionData = MutableLiveData<List<Region>>()
    val regionData: LiveData<List<Region>> get() = _regionData

    fun onLoad(
        regionType: Int,
        provinceId: Long,
        cityId: Long,
        districtId: Long,
        villageId: Long,
        rw: String
    ) = viewModelScope.launch {
        setTitle(regionType = regionType)
        getRegions(
            regionType = regionType,
            provinceId = provinceId,
            cityId = cityId,
            districtId = districtId,
            villageId = villageId,
            rw = rw
        )
    }

    private fun setTitle(regionType: Int) {
        _titleText.value = when (regionType) {
            RegionType.Province.type -> R.string.province
            RegionType.City.type -> R.string.city
            RegionType.District.type -> R.string.district
            else -> R.string.village
        }
    }

    private fun getRegions(
        regionType: Int,
        provinceId: Long,
        cityId: Long,
        districtId: Long,
        villageId: Long,
        rw: String
    ) {
        when (regionType) {
            RegionType.Province.type -> getProvince()
            RegionType.City.type -> getCity(provinceId = provinceId)
            RegionType.District.type -> getDistrict(cityId = cityId)
            RegionType.Village.type -> getVillage(districtId = districtId)
            RegionType.Rw.type -> getRw(villageId = villageId)
            else -> getRt(villageId = villageId, rw = rw)
        }
    }

    private fun getProvince() = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getProvinceUseCase.invoke()) {
            is NetworkResponse.Success -> {
                _regionData.value = result.body
            }
            else -> {
                onResponseNotSuccess(response = result)
                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    private fun getCity(provinceId: Long) = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getCityUseCase.invoke(provinceId = provinceId)) {
            is NetworkResponse.Success -> {
                _regionData.value = result.body
            }
            else -> {
                onResponseNotSuccess(response = result)
                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    private fun getDistrict(cityId: Long) = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getDistrictUseCase.invoke(cityId = cityId)) {
            is NetworkResponse.Success -> {
                _regionData.value = result.body
            }
            else -> {
                onResponseNotSuccess(response = result)
                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    private fun getVillage(districtId: Long) = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getVillageUseCase.invoke(districtId = districtId)) {
            is NetworkResponse.Success -> {
                _regionData.value = result.body
            }
            else -> {
                onResponseNotSuccess(response = result)
                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    private fun getRw(villageId: Long) = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getRwUseCase.invoke(villageId = villageId)) {
            is NetworkResponse.Success -> {
                _regionData.value = result.body
            }
            else -> {
                onResponseNotSuccess(response = result)
                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    private fun getRt(villageId: Long, rw: String) = viewModelScope.launch {
        showLoadingWidget()

        when (val result = getRtUseCase.invoke(villageId = villageId, rw = rw)) {
            is NetworkResponse.Success -> {
                _regionData.value = result.body
            }
            else -> {
                onResponseNotSuccess(response = result)
                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }
}