package com.example.containertracker.ui.history.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.data.history.model.HistoryModel
import com.example.containertracker.data.history.requests.HistoryRequest
import com.example.containertracker.domain.history.GetHistoryUseCase
import com.example.containertracker.utils.PostLiveData
import com.example.containertracker.utils.enums.StatusContainerEnum
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class HistoryDetailViewModel(
    private val getHistoryUseCase: GetHistoryUseCase
): BaseViewModel() {

    val historyListLiveData = PostLiveData<List<HistoryModel>?>()

    fun getHistory(qrCode: String, batchId: String){
        showLoadingWidget()
        val requestParam = HistoryRequest(qrCode = qrCode, batchId = batchId)
        viewModelScope.launch {
            when (val response = getHistoryUseCase(requestParam)) {
                is NetworkResponse.Success -> {
                    response.body.data.let {
                        hideLoadingWidget()
                        historyListLiveData.post(it)
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