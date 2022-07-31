package com.gov.sidesa.data.registration.kk

data class KkBiodataModel(
    val kkNumber: String,
    val kepalaKeluargaName: String
)

data class KkAddressModel(
    val address: String,
    val rt: String,
    val rw: String,
    val province: String,
    val city: String,
    val kecamatan: String,
    val kelurahan: String
)