package com.example.containertracker.ui.selectlocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.data.location.models.Location
import com.example.containertracker.data.user.models.User
import com.example.containertracker.domain.location.LocationsUseCase
import com.example.containertracker.utils.PostLiveData
import com.example.containertracker.utils.PreferenceUtils
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class SelectLocationViewModel(
    private val locationsUseCase: LocationsUseCase
): BaseViewModel() {
    val locationsLiveData = MutableLiveData<List<Location>>()

    init {
        getLocations()
    }

    private fun getLocations(){
        val user = PreferenceUtils.get<User>(PreferenceUtils.USER_PREFERENCE)
        viewModelScope.launch {
            when (val response = locationsUseCase(user?.id.orEmpty())) {
                is NetworkResponse.Success -> {
                    response.body.data.let {
                        locationsLiveData.value = it
                    }
                }
                is NetworkResponse.ServerError -> {
                    genericErrorLiveData.value = response.body
                }
                is NetworkResponse.NetworkError -> {
                    networkErrorLiveData.value = response.error
                }
            }
        }
    }
}