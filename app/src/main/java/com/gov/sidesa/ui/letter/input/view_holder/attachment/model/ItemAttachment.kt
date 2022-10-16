package com.gov.sidesa.ui.letter.input.view_holder.attachment.model

import java.io.File

/**
 * Created by yovi.putra on 06/10/22"
 * Project name: SIDESA
 **/


data class ItemAttachment(
    val type: Int,
    val file: File?
) {

    companion object {
        const val ADD = 1
        const val IMAGE = 2
    }
}