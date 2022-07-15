package com.example.containertracker.data.container.requests

import com.google.gson.annotations.SerializedName

data class SaveContainerHistoryRequest(
    @SerializedName("qr_code") val qrCode: String? = null,
    @SerializedName("id_location") val locationId: String? = null,
    @SerializedName("account") val userId: String? = null,
    @SerializedName("seal_id") val sealId: String? = null,
    @SerializedName("voyage_id_out") val voyageIdOut: String? = null,
    @SerializedName("voyage_id_in") val voyageIdIn: String? = null,
    @SerializedName("so_number") val soNumber: String? = null,
    @SerializedName("rightside_condition") var rightSideCondition: String? = null,
    @SerializedName("leftside_condition") var leftSideCondition: String? = null,
    @SerializedName("roofside_condition") var roofSideCondition: String? = null,
    @SerializedName("floorside_condition") var floorSideCondition: String? = null,
    @SerializedName("frontdoor_condition") var frontDoorSideCondition: String? = null,
    @SerializedName("backdoor_condition") var backDoorSideCondition: String? = null,
)