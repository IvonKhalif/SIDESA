package com.gov.sidesa.domain.user

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        userName: String,
        password: String
    ) = userRepository.login(userName, password)
}