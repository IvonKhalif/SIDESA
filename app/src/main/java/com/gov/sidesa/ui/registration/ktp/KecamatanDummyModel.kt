package com.gov.sidesa.ui.registration.ktp

data class KecamatanDummyModel(
    val id: Int,
    val id_kab_kota: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}

data class KelurahanDummyModel(
    val id: Long,
    val id_kab_kota: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}


fun getDummyKelurahanList() = listOf(
    KelurahanDummyModel(
        3603030001,
        "3603030",
        "BUDI MULYA"
    ),
    KelurahanDummyModel(
        3603030002,
        "3603030",
        "BOJONG"
    ),
    KelurahanDummyModel(
        3603030003,
        "3603030",
        "SUKA MULYA"
    ),
    KelurahanDummyModel(
        3603030004,
        "3603030",
        "CIKUPA"
    ),
    KelurahanDummyModel(
        3603030006,
        "3603030",
        "BITUNG JAYA"
    )
)

fun getDummyKecamatanList() = listOf(
    KecamatanDummyModel(
        3603010,
        "3603",
        "CISOKA"
    ),
    KecamatanDummyModel(
        3603011,
        "3603",
        "SOLEAR"
    ),
    KecamatanDummyModel(
        3603020,
        "3603",
        "TIGARAKSA"
    ),
    KecamatanDummyModel(
        3603021,
        "3603",
        "JAMBE"
    ),
    KecamatanDummyModel(
        3603030,
        "3603",
        "CIKUPA"
    ),
    KecamatanDummyModel(
        3603040,
        "3603",
        "PANONGAN"
    ),
    KecamatanDummyModel(
        3603050,
        "3603",
        "CURUG"
    ),
    KecamatanDummyModel(
        3603051,
        "3603",
        "KELAPA DUA"
    ),
    KecamatanDummyModel(
        3603060,
        "3603",
        "LEGOK"
    ),
)