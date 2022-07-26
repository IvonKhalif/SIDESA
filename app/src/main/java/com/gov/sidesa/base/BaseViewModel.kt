package com.gov.sidesa.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.response.GenericErrorResponse
import kotlinx.coroutines.launch
import java.io.IOException

open class BaseViewModel: ViewModel() {
    @Deprecated("broken observe when save history")
    val genericErrorLiveData = PostLiveData<GenericErrorResponse?>()
    @Deprecated("broken observe when save history")
    val networkErrorLiveData = PostLiveData<IOException?>()
    @Deprecated("broken observe when save history")
    val loadingWidgetLiveData = PostLiveData<Boolean?>()

    protected val _serverErrorState = MutableLiveData<GenericErrorResponse>()
    val serverErrorState: LiveData<GenericErrorResponse> get() = _serverErrorState

    protected val _networkErrorState = MutableLiveData<IOException>()
    val networkErrorState: LiveData<IOException> get() = _networkErrorState

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    fun showLoadingWidget(){
        viewModelScope.launch {
            loadingWidgetLiveData.value = true
            _loadingState.value = true
        }
    }

    fun hideLoadingWidget(){
        viewModelScope.launch {
            loadingWidgetLiveData.value = false
            _loadingState.value = false
        }
    }
}