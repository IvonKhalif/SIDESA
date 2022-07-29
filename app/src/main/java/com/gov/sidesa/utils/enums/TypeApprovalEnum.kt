package com.gov.sidesa.utils.enums

enum class TypeApprovalEnum(val type: String?) {
    SUBMISSION("PENGAJUAN"),
    RT_WAITING_APPROVAL("RT WAITING_APPROVAL"),
    RT_APPROVAL("RT APPROVAL"),
    RW_WAITING_APPROVAL("RW WAITING_APPROVAL"),
    RW_APPROVAL("RW APPROVAL"),
    FINISH("SELESAI"),
}