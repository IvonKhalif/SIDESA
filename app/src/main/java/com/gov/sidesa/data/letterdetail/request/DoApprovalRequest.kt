package com.gov.sidesa.data.letterdetail.request

import com.google.gson.annotations.SerializedName

data class DoApprovalRequest(
    @SerializedName("id_pengajuan_surat") val letterId: String? = null,
    @SerializedName("id_account_approval") val accountId: Long? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("actor") val actor: String? = null,
    @SerializedName("description") val description: String? = null,
)
