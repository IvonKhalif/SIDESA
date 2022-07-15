package com.example.containertracker.ui.container

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.data.container.models.Container
import com.example.containertracker.data.container.models.ContainerDetail
import com.example.containertracker.domain.container.ContainerListUseCase
import com.example.containertracker.utils.PostLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class ContainerViewModel(
    private val getContainerListUseCase: ContainerListUseCase
): BaseViewModel() {
    var keywordSearch = MutableLiveData("")
    val containerListLiveData = PostLiveData<List<ContainerDetail>?>()

    init {
        getContainers()
    }

    fun getContainers() {
        showLoadingWidget()
        viewModelScope.launch {
            when (val response = getContainerListUseCase()) {
                is NetworkResponse.Success -> {
                    response.body.data.let {
                        hideLoadingWidget()
                        containerListLiveData.post(it)
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