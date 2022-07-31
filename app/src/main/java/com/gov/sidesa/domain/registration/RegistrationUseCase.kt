package com.gov.sidesa.domain.registration

import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

class RegistrationUseCase(
    private val registrationRepo: RegistrationRepository
) {
    suspend fun registrationNewAccount(
        idAccount: String,
        nik: String,
        noKk: String,
        fullName: String,
        dateOfBirth: String,
        bloodType: String,
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
    ): NetworkResponse<String, GenericErrorResponse> {
        return registrationRepo.registerNewAccount(
            idAccount,
            nik,
            noKk,
            fullName,
            dateOfBirth,
            bloodType,
            religion,
            marriageStatus,
            job,
            address,
            provinceId,
            cityId,
            kecamatanId,
            kelurahanId,
            rt,
            rw,
            nationality,
            ktpBase64,
            kkBase64,
            familyMap
        )
    }

}