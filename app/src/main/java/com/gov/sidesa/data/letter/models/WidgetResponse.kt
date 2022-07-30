package com.gov.sidesa.data.letter.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/

@Parcelize
data class WidgetResponse(
    @SerializedName("id_type_surat_document")
    @Expose
    val documentTypeId: Int? = null,
    @SerializedName("id_type_surat")
    @Expose
    val letterTypeId: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("type")
    @Expose
    val type: String = "",
    @SerializedName("input_type")
    @Expose
    val inputType: String? = null,
    @SerializedName("title")
    @Expose
    val title: String? = "",
    @SerializedName("value")
    @Expose
    val value: String? = "",
    @SerializedName("api")
    @Expose
    val api: String? = null,
    @SerializedName("api_type")
    @Expose
    val apiType: String? = null,
    @SerializedName("param")
    @Expose
    val apiParam: String? = null
): Parcelable
