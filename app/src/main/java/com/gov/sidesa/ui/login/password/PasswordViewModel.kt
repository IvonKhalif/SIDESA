package com.gov.sidesa.ui.login.password

import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.domain.user.usecase.CreatePasswordUseCase
import com.gov.sidesa.domain.user.usecase.LoginUseCase
import com.gov.sidesa.domain.user.usecase.ResetPasswordUseCase
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class PasswordViewModel(
    private val loginUseCase: LoginUseCase,
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel() {

    val userResponseLiveData = PostLiveData<UserResponse?>()
    val statusCreatePassword = PostLiveData<String?>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            showLoadingWidget()
            when (val response = loginUseCase(username, password)) {
                is NetworkResponse.Success -> {
                    if (response.body.data == null) {
                        genericErrorLiveData.value =
                            GenericErrorResponse(message = response.body.desc)
                    }
                    response.body.data?.let {
                        userResponseLiveData.post(it)
                    }
                    hideLoadingWidget()
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
            showLoadingWidget()
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

    fun resetPassword(accountId: String, password: String) = viewModelScope.launch {
        showLoadingWidget()

        when (val result =
            resetPasswordUseCase.invoke(accountId = accountId, password = password)) {
            is NetworkResponse.Success -> {
                result.body.status.let {
                    statusCreatePassword.post(it)
                }
            }
            else -> onResponseNotSuccess(response = result)
        }


        hideLoadingWidget()
    }
}