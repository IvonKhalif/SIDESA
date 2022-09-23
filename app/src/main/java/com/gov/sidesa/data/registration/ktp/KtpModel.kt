package com.gov.sidesa.data.registration.ktp

import com.gov.sidesa.domain.regions.models.Region

data class BiodataKtpModel(
    val nik: String,
    val fullName: String,
    val placeOdBirth: String,
    val dateOfBirth: String,
    val gender: String,
    val bloodType: String
)

data class AddressKtpModel(
    val address: String,
    val rt: String,
    val rw: String,
    val province: Region? = null,
    val city: Region? = null,
    val kecamatan: Region? = null,
    val kelurahan: Region? = null
)

data class GeneralKtpModel(
    val religion: String,
    val marriageStatus: String,
    val job: String,
    val nationality: String,
)