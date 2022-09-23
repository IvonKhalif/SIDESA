package com.gov.sidesa.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.domain.user.usecase.ValidateNIKUseCase
import com.gov.sidesa.utils.PostLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val validateNIKUseCase: ValidateNIKUseCase): BaseViewModel() {
    val statusNIKLiveData = PostLiveData<String?>()
    val userResponseLiveData = MutableLiveData<UserResponse>()
    fun validateNIK(nik: String) {
        showLoadingWidget()
        viewModelScope.launch {
            when (val response = validateNIKUseCase(nik)) {
                is NetworkResponse.Success -> {
                    response.body.data?.let {
                        hideLoadingWidget()
                        userResponseLiveData.value = it
                    }
                    response.body.status?.let {
                        hideLoadingWidget()
                        statusNIKLiveData.post(it)
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