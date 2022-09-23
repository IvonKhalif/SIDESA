package com.gov.sidesa.utils.enums

enum class StatusResponseEnum(val status: String?) {
    SUCCESS("success"),
    FAILED("failed"),
    REGISTERED("registered"),
    UNREGISTERED("unregistered"),
    RESET_PASSWORD("reset_password"),
    RESET_PASSWORD_SUCCESS("reset_password_success"),
}