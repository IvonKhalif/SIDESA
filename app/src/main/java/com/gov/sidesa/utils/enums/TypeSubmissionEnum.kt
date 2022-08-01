package com.gov.sidesa.utils.enums

enum class TypeSubmissionEnum(val type: String?) {
    SUBMISSION("PENGAJUAN"),
    RT_WAITING_Submission("RT WAITING_APPROVAL"),
    RT_Submission("RT APPROVAL"),
    RT_REJECTED("RT REJECTED"),
    RW_WAITING_Submission("RW WAITING_APPROVAL"),
    RW_Submission("RW APPROVAL"),
    RW_REJECTED("RW REJECTED"),
    FINISH("SELESAI"),
}