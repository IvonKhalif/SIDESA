package com.gov.sidesa.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.response.GenericErrorResponse
import kotlinx.coroutines.launch
import java.io.IOException

open class BaseViewModel: ViewModel() {
    val genericErrorLiveData = PostLiveData<GenericErrorResponse?>()
    val networkErrorLiveData = PostLiveData<IOException?>()
    val loadingWidgetLiveData = PostLiveData<Boolean?>()

    fun showLoadingWidget(){
        viewModelScope.launch {
            loadingWidgetLiveData.value = true
        }
    }

    fun hideLoadingWidget(){
        viewModelScope.launch {
            loadingWidgetLiveData.value = false
        }
    }
}