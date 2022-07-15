package com.example.containertracker.domain.printer

import android.graphics.Bitmap
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.example.containertracker.utils.enums.PrintShareAlign
import com.example.containertracker.utils.extension.toGrayscale

open class CommandImage(
    val bitmap: Bitmap,
    val align: PrintShareAlign = PrintShareAlign.CENTER
): PrintShareCommand() {

    override fun write(printer: EscPosPrinter): String {
        val escAlign = toESCAlign(align)
        val newBitmap = bitmap.toGrayscale()
        val imageString = PrinterTextParserImg.bitmapToHexadecimalString(printer, newBitmap)
        return "$escAlign<img>$imageString</img>\n"
    }

}