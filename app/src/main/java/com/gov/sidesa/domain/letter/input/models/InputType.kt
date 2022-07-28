package com.gov.sidesa.domain.letter.input.models

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/

sealed class InputType(val type: String) {
    object Text : InputType(type = "text")
    object PhoneNumber : InputType(type = "phone_number")
    object Email : InputType(type = "email")
    object Decimal : InputType(type = "decimal")
    object Number : InputType(type = "number")
}

fun String?.asInputType(): InputType = when (this) {
    InputType.Text.type -> InputType.Text
    InputType.PhoneNumber.type -> InputType.PhoneNumber
    InputType.Email.type -> InputType.Email
    InputType.Decimal.type -> InputType.Decimal
    InputType.Number.type -> InputType.Number
    else -> InputType.Text
}

fun InputType.asAndroidInputText(): Int = when(this) {
    is InputType.Text -> android.text.InputType.TYPE_CLASS_TEXT
    is InputType.PhoneNumber -> android.text.InputType.TYPE_CLASS_PHONE
    is InputType.Email -> android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    is InputType.Decimal -> android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
    is InputType.Number -> android.text.InputType.TYPE_CLASS_NUMBER
}