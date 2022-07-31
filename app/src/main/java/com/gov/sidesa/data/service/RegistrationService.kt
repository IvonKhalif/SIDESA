package com.gov.sidesa.data.service

import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RegistrationService {

    @POST("account/register")
    suspend fun registerNewAccount(
        @Query("id_account") idAccount: String,
        @Query("nik") nik: String,
        @Query("no_kk") noKk: String,
        @Query("nama_lengkap") fullName: String,
        @Query("tanggal_lahir") dateOfBirth: String,
        @Query("gol_darah") bloodType: String,
        @Query("id_agama") religion: String,
        @Query("status_perkawinan") marriageStatus: String,
        @Query("pekerjaan") job: String,
        @Query("alamat") address: String,
        @Query("id_provinsi") provinceId: String,
        @Query("id_kota") cityId: String,
        @Query("id_kecamatan") kecamatanId: String,
        @Query("id_kelurahan") kelurahanId: String,
        @Query("rt") rt: String,
        @Query("rw") rw: String,
        @Query("kewarganegaraan") nationality: String,
        @Query("foto_ktp") ktpBase64: String,
        @Query("foto_kk") kkBase64: String,
        @Body familyMap: Map<String, String>
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

}