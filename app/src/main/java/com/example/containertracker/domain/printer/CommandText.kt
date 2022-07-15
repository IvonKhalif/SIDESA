package com.example.containertracker.domain.printer

import com.dantsu.escposprinter.EscPosPrinter
import com.example.containertracker.utils.enums.ESCFontSizeEnum
import com.example.containertracker.utils.enums.PrintShareAlign

open class CommandText(
    open val content: String,
    open val align: PrintShareAlign = PrintShareAlign.LEFT,
    open val isBold: Boolean = false,
    open val size: ESCFontSizeEnum = ESCFontSizeEnum.NORMAL
): PrintShareCommand() {

    override fun write(printer: EscPosPrinter): String {
        return toESCText(content, align, isBold, size, printer) + "\n"
    }

    protected fun toESCText(
        content: String,
        align: PrintShareAlign,
        isBold: Boolean,
        size: ESCFontSizeEnum,
        printer: EscPosPrinter,
        maxChar: Int = printer.printerNbrCharactersPerLine
    ) : String {
        val result = StringBuilder()
        val posAlign: String = toESCAlign(align)

        content.chunked(maxChar).forEach {
            val value = it.trim()
            var body = when (size) {
                ESCFontSizeEnum.NORMAL -> value
                else -> "<font size='${size.value}'>$value</font>"
            }

            if (isBold) {
                body = "<b>$body</b>"
            }

            result.append("$posAlign$body\n")
        }

        return if (result.endsWith("\n")) {
            result.dropLast(1).toString()
        } else {
            result.toString()
        }
    }
}