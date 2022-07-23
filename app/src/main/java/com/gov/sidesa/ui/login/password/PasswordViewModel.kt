package com.gov.sidesa.ui.login.password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.domain.user.CreatePasswordUseCase
import com.gov.sidesa.domain.user.LoginUseCase
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class PasswordViewModel(
    private val loginUseCase: LoginUseCase,
    private val createPasswordUseCase: CreatePasswordUseCase
): BaseViewModel() {

    val userLiveData = PostLiveData<User?>()
    val statusCreatePassword = PostLiveData<String?>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val response = loginUseCase(username, password)) {
                is NetworkResponse.Success -> {
                    if (response.body.data == null) {
                        genericErrorLiveData.value = GenericErrorResponse(message = response.body.desc)
                    }
                    response.body.data?.let {
                        hideLoadingWidget()
                        userLiveData.post(it)
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

    fun createPassword(nik: String, password: String) {
        viewModelScope.launch {
            when (val response = createPasswordUseCase(nik, password)) {
                is NetworkResponse.Success -> {
                    response.body.status.let {
                        hideLoadingWidget()
                        statusCreatePassword.post(it)
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