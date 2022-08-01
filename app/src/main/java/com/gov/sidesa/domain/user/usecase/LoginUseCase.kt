package com.gov.sidesa.domain.user.usecase

import com.gov.sidesa.domain.user.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        userName: String,
        password: String
    ) = userRepository.login(userName, password)
}