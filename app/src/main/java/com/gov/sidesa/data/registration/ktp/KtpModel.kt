package com.gov.sidesa.data.registration.ktp

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
    val kecamatan: String,
    val kelurahan: String
)

data class GeneralKtpModel(
    val religion: String,
    val marriageStatus: String,
    val job: String,
    val nationality: String,
)