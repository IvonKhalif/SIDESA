package com.example.containertracker.data.history

import com.example.containertracker.data.history.model.HistoryModel
import com.example.containertracker.data.history.requests.HistoryRequest
import com.example.containertracker.domain.history.HistoryRepository
import com.example.containertracker.utils.response.GenericErrorResponse
import com.example.containertracker.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse

class HistoryRepositoryImpl(private val service: HistoryService): HistoryRepository {
    override suspend fun getHistory(request: HistoryRequest): NetworkResponse<RetrofitResponse<List<HistoryModel>>, GenericErrorResponse> {
        return service.getHistory(
            qrCode = request.qrCode,
            locationId = request.locationId,
            userId = request.userId,
            status = request.status,
            startDate = request.startDate,
            endDate = request.endDate,
            containerCode = request.containerCode
        )
    }
}