package com.gov.sidesa.data.region.service

import com.gov.sidesa.data.region.models.CityResponse
import com.gov.sidesa.data.region.models.DistrictResponse
import com.gov.sidesa.data.region.models.ProvinceResponse
import com.gov.sidesa.data.region.models.VillageResponse
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitListResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

interface RegionService {

    @GET("master/provinsi")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getProvince(): NetworkResponse<RetrofitListResponse<ProvinceResponse>, GenericErrorResponse>

    @GET("master/kota")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getCity(
        @Query("id_provinsi") provinceId: Long
    ): NetworkResponse<RetrofitListResponse<CityResponse>, GenericErrorResponse>

    @GET("master/kecamatan")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getDistrict(
        @Query("id_kota") cityId: Long
    ): NetworkResponse<RetrofitListResponse<DistrictResponse>, GenericErrorResponse>

    @GET("master/kelurahan")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getVillage(
        @Query("id_kecamatan") districtId: Long
    ): NetworkResponse<RetrofitListResponse<VillageResponse>, GenericErrorResponse>
}