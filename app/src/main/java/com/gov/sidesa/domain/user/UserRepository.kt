package com.gov.sidesa.domain.user

import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface UserRepository {
    suspend fun validateNIK(
        nik: String
    ): NetworkResponse<RetrofitResponse<User>, GenericErrorResponse>

    suspend fun login(
        userName: String,
        password: String
    ): NetworkResponse<RetrofitResponse<User>, GenericErrorResponse>

    suspend fun createPassword(
        nik: String,
        password: String
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    suspend fun resetPassword(
        accountId: String,
        password: String
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>
}