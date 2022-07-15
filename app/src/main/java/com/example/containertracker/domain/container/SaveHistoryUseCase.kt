package com.example.containertracker.domain.container

import com.example.containertracker.data.container.requests.SaveContainerHistoryRequest

class SaveHistoryUseCase(private val repository: ContainerRepository) {
    suspend operator fun invoke(
        request: SaveContainerHistoryRequest
    ) = repository.saveHistory(request)
}