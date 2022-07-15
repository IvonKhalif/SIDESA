package com.example.containertracker.domain.history

import com.example.containertracker.data.history.model.HistoryModel
import com.example.containertracker.data.history.requests.HistoryRequest
import com.example.containertracker.utils.response.GenericErrorResponse
import com.example.containertracker.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface HistoryRepository {
    suspend fun getHistory(request: HistoryRequest):
            NetworkResponse<RetrofitResponse<List<HistoryModel>>, GenericErrorResponse>
}