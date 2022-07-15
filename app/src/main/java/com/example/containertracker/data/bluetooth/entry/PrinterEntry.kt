package com.example.containertracker.data.bluetooth.entry

import com.example.containertracker.utils.enums.PrinterPaperTypeEnum
import com.example.containertracker.utils.enums.PrinterSystemEnum
import java.io.Serializable

data class PrinterEntry(
    val system: PrinterSystemEnum,
    val uid: String,
    var name: String,
    val deviceName: String,
    val deviceAddress: String,
    var paperType: PrinterPaperTypeEnum
) : Serializable {

    val text: String
        get() = if (name.isNotBlank()) name else deviceText

    val deviceText: String
        get() = if (deviceName.isNotBlank()) deviceName else deviceAddress

}