package com.gov.sidesa.data.letter.repository

import com.gov.sidesa.data.service.RegistrationService
import com.gov.sidesa.domain.registration.RegistrationRepository
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

class RegistrationRepositoryImpl(
    private val registrationService: RegistrationService
) : RegistrationRepository {

    override suspend fun registerNewAccount(
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
        return registrationService.registerNewAccount(
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
        ).asDomain {
            status
        }
    }
}