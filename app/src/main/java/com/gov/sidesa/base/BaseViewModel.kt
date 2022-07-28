package com.gov.sidesa.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch
import java.io.IOException

open class BaseViewModel : ViewModel() {
    @Deprecated("broken observe when save history")
    val genericErrorLiveData = PostLiveData<GenericErrorResponse?>()

    @Deprecated("broken observe when save history")
    val networkErrorLiveData = PostLiveData<IOException?>()

    @Deprecated("broken observe when save history")
    val loadingWidgetLiveData = PostLiveData<Boolean?>()

    protected val mServerErrorState = MutableLiveData<GenericErrorResponse>()
    val serverErrorState: LiveData<GenericErrorResponse> get() = mServerErrorState

    protected val mNetworkErrorState = MutableLiveData<IOException>()
    val networkErrorState: LiveData<IOException> get() = mNetworkErrorState

    protected val mLoadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = mLoadingState

    fun showLoadingWidget() {
        viewModelScope.launch {
            loadingWidgetLiveData.value = true
            mLoadingState.value = true
        }
    }

    fun hideLoadingWidget() {
        viewModelScope.launch {
            loadingWidgetLiveData.value = false
            mLoadingState.value = false
        }
    }

    protected fun <T : Any> onResponseNotSuccess(response: NetworkResponse<T, GenericErrorResponse>) {
        when (response) {
            is NetworkResponse.ServerError -> mServerErrorState.value =
                response.body ?: GenericErrorResponse.generalError()
            is NetworkResponse.NetworkError -> mNetworkErrorState.value = response.error
            else -> return
        }
    }
}