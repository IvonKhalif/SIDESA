package com.example.containertracker.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.containertracker.utils.PostLiveData
import com.example.containertracker.utils.response.GenericErrorResponse
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