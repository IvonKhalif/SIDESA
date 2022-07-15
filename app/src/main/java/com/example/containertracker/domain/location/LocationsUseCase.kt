package com.example.containertracker.domain.location

class LocationsUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(accountId: String) = repository.getLocations(accountId)
}