package com.gov.sidesa.data.service

import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistrationService {

    @POST("account/register")
    @FormUrlEncoded
    suspend fun registerNewAccount(
        @Field("id_account") idAccount: String,
        @Field("nik") nik: String,
        @Field("no_kk") noKk: String,
        @Field("nama_lengkap") fullName: String,
        @Field("tempat_lahir") placeOfBirth: String,
        @Field("tanggal_lahir") dateOfBirth: String,
        @Field("jenis_kelamin") gender: String,
        @Field("gol_darah") bloodType: String,
        @Field("alamat") address: String,
        @Field("id_provinsi") provinceId: String,
        @Field("id_kota") cityId: String,
        @Field("id_kecamatan") kecamatanId: String,
        @Field("id_kelurahan") kelurahanId: String,
        @Field("rt") rt: String,
        @Field("rw") rw: String,
        @Field("id_agama") religion: String,
        @Field("status_perkawinan") marriageStatus: String,
        @Field("pekerjaan") job: String,
        @Field("kewarganegaraan") nationality: String,
        @Field("foto_ktp") ktpBase64: String,
        @Field("foto_kk") kkBase64: String,
        @Field("kepala_keluarga") familyHead: String,
        @Field("alamat_kk") addressKk: String,
        @Field("id_kota_kk") cityIdKk: String,
        @Field("id_kelurahan_kk") kelurahanIdKk: String,
        @Field("id_kecamatan_kk") kecamatanIdKk: String,
        @Field("id_provinsi_kk") provinceIdKk: String,
        @Field("rt_kk") rtKk: String,
        @Field("rw_kk") rwKk: String,
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

}