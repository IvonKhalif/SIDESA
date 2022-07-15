package com.example.containertracker.data.container

import com.example.containertracker.data.container.models.Container
import com.example.containertracker.data.container.models.ContainerDetail
import com.example.containertracker.data.container.requests.SaveContainerHistoryRequest
import com.example.containertracker.utils.constants.ContentTypeConstant
import com.example.containertracker.utils.response.GenericErrorResponse
import com.example.containertracker.utils.response.RetrofitResponse
import com.example.containertracker.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

interface ContainerService {
    @GET("tracking/container")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getContainer(
        @Query("qr_code") qrCode: String
    ): NetworkResponse<RetrofitResponse<Container>, GenericErrorResponse>

    @POST("tracking/container/save")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun saveContainerHistory(
        @Body request: SaveContainerHistoryRequest
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    @GET("container")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getContainerList(): NetworkResponse<RetrofitResponse<List<ContainerDetail>>, GenericErrorResponse>

    @GET("container/detail")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getContainerQR(
        @Query("id_container") id: String
    ): NetworkResponse<RetrofitResponse<ContainerDetail>, GenericErrorResponse>
}