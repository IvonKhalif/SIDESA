package com.gov.sidesa.data.user

import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.domain.user.UserRepository
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse

class UserRepositoryImpl(
    private val userService: UserService
): UserRepository {
    override suspend fun validateNIK(
        nik: String
    ): NetworkResponse<RetrofitResponse<User>, GenericErrorResponse> {
        return userService.validateNIK(nik)
    }

    override suspend fun login(
        userName: String,
        password: String
    ): NetworkResponse<RetrofitResponse<User>, GenericErrorResponse> {
        return userService.login(userName, password)
    }

    override suspend fun createPassword(
        nik: String,
        password: String
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse> {
        return userService.createPassword(nik, password)
    }

    override suspend fun resetPassword(
        accountId: String,
        password: String
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse> {
        return userService.resetPassword(accountId, password)
    }
}