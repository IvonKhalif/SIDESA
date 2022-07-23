package com.gov.sidesa.domain.user

class ValidateNIKUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        nik: String
    ) = userRepository.validateNIK(nik)
}