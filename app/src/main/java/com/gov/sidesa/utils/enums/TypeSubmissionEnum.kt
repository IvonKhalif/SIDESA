package com.gov.sidesa.utils.enums

enum class TypeSubmissionEnum(val type: String?) {
    SUBMISSION("PENGAJUAN"),
    RT_WAITING_Submission("RT WAITING_APPROVAL"),
    RT_Submission("RT APPROVAL"),
    RT_REJECTED("RT REJECTED"),
    RW_WAITING_Submission("RW WAITING_APPROVAL"),
    RW_Submission("RW APPROVAL"),
    RW_REJECTED("RW REJECTED"),
    VILLAGE_WAITING_Submission("KELURAHAN WAITING_APPROVAL"),
    VILLAGE_Submission("KELURAHAN APPROVAL"),
    VILLAGE_REJECTED("KELURAHAN REJECTED"),
    FINISH("SELESAI"),
    LETTER_PRINT_OUT("LETTER_PRINT_OUT"),
}