package com.example.containertracker.domain.printer

import com.dantsu.escposprinter.EscPosPrinter
import com.example.containertracker.utils.enums.PrintShareAlign

abstract class PrintShareCommand {
    abstract fun write(printer: EscPosPrinter): String

    fun toESCAlign(align: PrintShareAlign): String = when (align) {
        PrintShareAlign.LEFT -> "[L]"
        PrintShareAlign.RIGHT -> "[R]"
        PrintShareAlign.CENTER -> "[C]"
    }
}