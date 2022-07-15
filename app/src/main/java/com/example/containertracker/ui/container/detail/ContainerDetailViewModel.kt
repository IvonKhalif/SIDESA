package com.example.containertracker.ui.container.detail

import androidx.lifecycle.viewModelScope
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.data.container.models.ContainerDetail
import com.example.containertracker.domain.container.ContainerQRUseCase
import com.example.containertracker.utils.PostLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class ContainerDetailViewModel(
    private val getContainerQRUseCase: ContainerQRUseCase
): BaseViewModel() {

    val qrContainerLiveData = PostLiveData<ContainerDetail?>()

    fun getContainerQR(id: String){
        showLoadingWidget()
        viewModelScope.launch {
            when (val response = getContainerQRUseCase(id)) {
                is NetworkResponse.Success -> {
                    response.body.data.let {
                        hideLoadingWidget()
                        qrContainerLiveData.post(it)
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