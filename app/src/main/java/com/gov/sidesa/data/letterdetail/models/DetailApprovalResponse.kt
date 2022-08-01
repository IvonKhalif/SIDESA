package com.gov.sidesa.data.letterdetail.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.sidesa.data.letter.models.WidgetResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailApprovalResponse(
//    @SerializedName("id_type_surat_document")
//    @Expose
//    val documentTypeId: Int,
    @SerializedName("type_surat")
    @Expose
    val letterType: String?,
    @SerializedName("id_pengajuan_surat")
    @Expose
    val submissionLetterId: String? = "",
    @SerializedName("nama_lengkap")
    @Expose
    val userName: String? = "",
    @SerializedName("nik")
    @Expose
    val nik: String? = "",
    @SerializedName("alamat")
    @Expose
    val address: String? = "",
    @SerializedName("created_date")
    @Expose
    val createdDate: String? = "",
    @SerializedName("no_surat")
    @Expose
    val letterNumber: String? = "",
    @SerializedName("status")
    @Expose
    val status: String? = "",
    @SerializedName("description")
    @Expose
    val description: String? = "",
    @SerializedName("document_filled")
    @Expose
    val documentFilled: List<WidgetResponse>,
    @SerializedName("history_approval")
    @Expose
    val historyApproval: List<HistoryApprovalResponse>
): Parcelable