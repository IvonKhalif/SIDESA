package com.gov.sidesa.domain.user

class CreatePasswordUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        nik: String,
        password: String
    ) = userRepository.createPassword(nik, password)
}