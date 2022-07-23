package com.gov.sidesa.utils.enums

enum class StatusResponseEnum(val status: String?) {
    SUCCESS("success"),
    FAILED("failed"),
    REGISTERED("registered"),
    NOT_REGISTERED("not-registered")
}