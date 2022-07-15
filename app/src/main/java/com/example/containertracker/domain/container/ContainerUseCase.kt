package com.example.containertracker.domain.container

class ContainerUseCase(private val repository: ContainerRepository) {
    suspend operator fun invoke(
        qrCode: String
    ) = repository.getContainer(qrCode)
}