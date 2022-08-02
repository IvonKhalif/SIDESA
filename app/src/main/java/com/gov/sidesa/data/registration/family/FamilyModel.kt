package com.gov.sidesa.data.registration.family

import com.gov.sidesa.data.registration.ktp.AddressKtpModel

sealed class FamilyListModel {
    data class FatherModel(val familyFatherModel: FamilyFatherModel) : FamilyListModel()
    data class MotherModel(val familyMotherModel: FamilyMotherModel) : FamilyListModel()
    data class ApplicantModel(val familyApplicantModel: FamilyApplicantModel) : FamilyListModel()
    data class ChildModel(val familyChildModel: FamilyChildModel) : FamilyListModel()
}

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