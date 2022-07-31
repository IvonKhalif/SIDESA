package com.gov.sidesa.domain.user.usecase

import com.gov.sidesa.domain.user.UserRepository

class ValidateNIKUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        nik: String
    ) = userRepository.validateNIK(nik)
}