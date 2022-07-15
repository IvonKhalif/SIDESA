package com.example.containertracker.domain.printer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.containertracker.R
import java.math.BigDecimal

class QRContainerPrintComposer(
    val context: Context,
    data: String
) : PrintShareComposer<String>(data) {

    override fun compose(model: String): List<PrintShareCommand> {
        val commands = mutableListOf<PrintShareCommand>()
        val summary = model

        commands.apply {
            //HEADER
            val icon: Bitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.qr_code_test
            )
            addAll(composeImage())
            add(CommandBlank())

            val footer = composeFooter(model)
            if (footer.isNotEmpty()) {
                addAll(composeFooter(model))
            }
        }
        return commands
    }
}