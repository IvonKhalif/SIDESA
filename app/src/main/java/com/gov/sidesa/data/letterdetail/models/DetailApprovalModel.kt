package com.gov.sidesa.data.letterdetail.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailApprovalModel(
    @SerializedName("id_type_surat_document")
    @Expose
    val documentTypeId: Int,
    @SerializedName("type_surat")
    @Expose
    val letterType: String,
    @SerializedName("status")
    @Expose
    val status: String = "",
    @SerializedName("document_filled")
    @Expose
    val documentFilled: List<Widget>,
    @SerializedName("history_approval")
    @Expose
    val historyApproval: List<HistoryApprovalModel>
): Parcelable