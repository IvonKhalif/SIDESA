package com.gov.sidesa.utils.enums

import android.graphics.Paint

enum class PrintShareAlign {
    LEFT, RIGHT, CENTER
}

fun PrintShareAlign.toPaintAlign(): Paint.Align {
    return when (this) {
        PrintShareAlign.RIGHT -> Paint.Align.RIGHT
        PrintShareAlign.CENTER -> Paint.Align.CENTER
        PrintShareAlign.LEFT -> Paint.Align.LEFT
    }
}