package com.gov.sidesa.ui.login.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.user.usecase.CreatePasswordUseCase
import com.gov.sidesa.domain.user.usecase.LoginUseCase
import com.gov.sidesa.domain.user.usecase.ResetPasswordUseCase
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class PasswordViewModel(
    private val loginUseCase: LoginUseCase,
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel() {

    val statusCreatePassword = PostLiveData<String?>()
    private val _closeScreenView = MutableLiveData<Unit>()
    val closeScreenView: LiveData<Unit> get() = _closeScreenView

    fun login(username: String, password: String) {
        viewModelScope.launch {
            showLoadingWidget()
            when (val response = loginUseCase(username, password)) {
                is NetworkResponse.Success -> {
                    if (response.body.data == null) {
                        mServerErrorState.value =
                            GenericErrorResponse(message = response.body.desc)
                    }
                    response.body.data?.let {
                        PreferenceUtils.put(it, PreferenceUtils.USER_RESPONSE_PREFERENCE)
                        _closeScreenView.value = Unit
                    }
                    hideLoadingWidget()
                }
                else -> onResponseNotSuccess(response = response)
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
                else -> onResponseNotSuccess(response = response)
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