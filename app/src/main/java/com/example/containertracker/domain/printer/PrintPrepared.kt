package com.example.containertracker.domain.printer

data class PrintPrepared(
    val widthMM: Float,
    val dpi: Int,
    val lineSpan: Int,
    val printShareCommand: List<PrintShareCommand>
)