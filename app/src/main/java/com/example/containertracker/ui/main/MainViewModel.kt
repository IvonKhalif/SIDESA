package com.example.containertracker.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.data.location.models.Location
import com.example.containertracker.data.salesorder.models.SalesOrderNumber
import com.example.containertracker.data.user.models.User
import com.example.containertracker.domain.location.LocationsUseCase
import com.example.containertracker.utils.PostLiveData
import com.example.containertracker.utils.PreferenceUtils
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class MainViewModel(
    private val locationsUseCase: LocationsUseCase
) : BaseViewModel() {
    val locationLiveData = MutableLiveData<Location>()
    val locationListLiveData = MutableLiveData<List<Location>>()
    val soNumberListLiveData = MutableLiveData<List<SalesOrderNumber>>()

    fun getLocations(userId: String) {
        showLoadingWidget()
        viewModelScope.launch {
            when (val response = locationsUseCase(userId)) {
                is NetworkResponse.Success -> {
                    response.body.data.let {
                        locationListLiveData.value = it
                    }
                }
                is NetworkResponse.ServerError -> {
                    genericErrorLiveData.value = response.body
                }
                is NetworkResponse.NetworkError -> {
                    networkErrorLiveData.value = response.error
                }
            }
            hideLoadingWidget()
        }
    }
}