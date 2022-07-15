package com.example.containertracker.domain.printer

import com.example.containertracker.utils.enums.ESCFontSizeEnum
import com.example.containertracker.utils.enums.PrintShareAlign

open class CommandBlank : CommandText(
    " ",
    PrintShareAlign.LEFT,
    false,
    ESCFontSizeEnum.NORMAL
)