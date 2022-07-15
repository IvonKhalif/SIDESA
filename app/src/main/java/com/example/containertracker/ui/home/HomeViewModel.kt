package com.example.containertracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.data.container.models.Container
import com.example.containertracker.data.container.models.ContainerConditionModel
import com.example.containertracker.data.container.requests.SaveContainerHistoryRequest
import com.example.containertracker.data.location.models.Location
import com.example.containertracker.data.salesorder.models.SalesOrderNumber
import com.example.containertracker.domain.container.ContainerUseCase
import com.example.containertracker.domain.container.SaveHistoryUseCase
import com.example.containertracker.domain.salesorder.GetSalesOrderNumberUseCase
import com.example.containertracker.utils.PostLiveData
import com.example.containertracker.utils.enums.StatusResponseEnum
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class HomeViewModel(
    private val containerUseCase: ContainerUseCase,
    private val saveHistoryUseCase: SaveHistoryUseCase,
    private val getSalesOrderNumberUseCase: GetSalesOrderNumberUseCase
) : BaseViewModel() {

    val containerLiveData = MutableLiveData<Container>()
    val statusSaveLiveData = PostLiveData<String?>()

    val voyageId = MutableLiveData<String>()
    val voyageIdIn = MutableLiveData<String>()
    val voyageIdOut = MutableLiveData<String>()
    val containerConditionModel = MutableLiveData<ContainerConditionModel>()
    val soNumberList = PostLiveData<List<SalesOrderNumber>?>()

    fun getContainer(qrCode: String) {
        showLoadingWidget()
        viewModelScope.launch {
            when (val response = containerUseCase(qrCode)) {
                is NetworkResponse.Success -> {
                    response.body.data.let { dataContainer ->
                        when (val respSO = getSalesOrderNumberUseCase()) {
                            is NetworkResponse.Success -> {
                                soNumberList.value = respSO.body.data
                                containerLiveData.value = dataContainer
                            }
                            is NetworkResponse.ServerError -> {
                                genericErrorLiveData.value = respSO.body
                            }
                            is NetworkResponse.NetworkError -> {
                                networkErrorLiveData.value = respSO.error
                            }
                        }
                    }
                }
                is NetworkResponse.ServerError -> {
                    genericErrorLiveData.value = response.body
                    hideLoadingWidget()
                }
                is NetworkResponse.NetworkError -> {
                    networkErrorLiveData.value = response.error
                    hideLoadingWidget()
                }
            }
        }
    }

    fun saveHistory(qrCode: String, userId: String, locationId: String) {
        val condition = containerConditionModel.value
        val requestParam = SaveContainerHistoryRequest(
            qrCode = qrCode,
            locationId = locationId,
            userId = userId,
            voyageIdOut = voyageIdOut.value,
            voyageIdIn = voyageIdIn.value,
            sealId = condition?.sealNumber,
            soNumber = condition?.salesOrderNumber,
            rightSideCondition = condition?.rightCondition?.type,
            leftSideCondition = condition?.leftCondition?.type,
            roofSideCondition = condition?.upperCondition?.type,
            floorSideCondition = condition?.floorCondition?.type,
            frontDoorSideCondition = condition?.doorCondition?.type,
            backDoorSideCondition = condition?.backCondition?.type,
        )
        viewModelScope.launch {
            when (val response = saveHistoryUseCase(requestParam)) {
                is NetworkResponse.Success -> {
                    response.body.status.let {
                        hideLoadingWidget()
                        statusSaveLiveData.post(it)
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