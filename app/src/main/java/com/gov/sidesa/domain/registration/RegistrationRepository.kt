package com.gov.sidesa.domain.registration

import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface RegistrationRepository {
    suspend fun registerNewAccount(
        idAccount: String,
        nik: String,
        noKk: String,
        fullName: String,
        placeOfBirth: String,
        dateOfBirth: String,
        bloodType: String,
        gender: String,
        religion: String,
        marriageStatus: String,
        job: String,
        address: String,
        provinceId: String,
        cityId: String,
        kecamatanId: String,
        kelurahanId: String,
        rt: String,
        rw: String,
        nationality: String,
        ktpBase64: String,
        kkBase64: String,
        familyMap: Map<String, String>
    ): NetworkResponse<String, GenericErrorResponse>
}