package com.gov.sidesa.data.service

import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

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
        @FieldMap familyMap: Map<String, String>
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

}