package com.gov.sidesa.data.letter.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/

data class LetterTemplateResponse(
    @SerializedName("id_type_surat")
    @Expose
    val letterTypeId: Int,
    @SerializedName("type_surat")
    @Expose
    val letterName: String
)
