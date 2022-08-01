package com.gov.sidesa.domain.user.usecase

import com.gov.sidesa.domain.user.UserRepository

class ResetPasswordUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        accountId: String,
        password: String
    ) = userRepository.resetPassword(accountId, password)
}