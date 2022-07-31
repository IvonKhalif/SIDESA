package com.gov.sidesa.data.registration.family

import com.gov.sidesa.data.registration.ktp.AddressKtpModel

data class FamilyListModel(
    val fatherModel: FamilyFatherModel,
    val motherModel: FamilyMotherModel,
    val applicantModel: FamilyApplicantModel,
    val childModel: FamilyChildModel
)

data class FamilyFatherModel(
    val name: String,
    val nik: String,
    val placeOfBirth: String,
    val dateOfBirth: String,
    val isSameAddress: Boolean,
    var addressKtp: AddressKtpModel? = null
)

data class FamilyMotherModel(
    val name: String,
    val nik: String,
    val placeOfBirth: String,
    val dateOfBirth: String,
    val isSameAddress: Boolean,
    val addressKtp: AddressKtpModel? = null
)

data class FamilyApplicantModel(
    val status: String,
    val name: String,
    val nik: String,
    val placeOfBirth: String,
    val dateOfBirth: String,
    val isSameAddress: Boolean,
    val addressKtp: AddressKtpModel? = null
)

data class FamilyChildModel(
    val name: String,
    val nik: String,
    val placeOfBirth: String,
    val dateOfBirth: String,
    val isSameAddress: Boolean,
    val addressKtp: AddressKtpModel? = null
)