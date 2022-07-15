package com.gov.sidesa.utils.enums

enum class PrinterPaperTypeEnum {
    SIZE_58, SIZE_80;

    val dpi: Int
        get() = 203

    val widthMM: Float
        get() {
            return when (this) {
                SIZE_58 -> 48f
                SIZE_80 -> 72f
            }
        }

    val lineSpan: Int
        get() {
            return when (this) {
                SIZE_58 -> 32
                SIZE_80 -> 48
            }
        }
}