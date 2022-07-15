package com.example.containertracker.data.history.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class HistoryModel(
    @SerializedName("id_tracking_container") var id: String,
    @SerializedName("code_container") var codeContainer: String,
    @SerializedName("origin") var origin: String?,
    @SerializedName("destination") var destination: String?,
    @SerializedName("location_name") var location: String,
    @SerializedName("status") var status: String?,
    @SerializedName("batch_id") var batchId: String?,
    @SerializedName("datetime") var dateTime: String?,
    @SerializedName("name_account") var nameAccount: String?,
    @SerializedName("seal_id") var sealId: String?,
    @SerializedName("rightside_condition") var rightSideCondition: String? = null,
    @SerializedName("leftside_condition") var leftSideCondition: String? = null,
    @SerializedName("roofside_condition") var roofSideCondition: String? = null,
    @SerializedName("floorside_condition") var floorSideCondition: String? = null,
    @SerializedName("frontdoor_condition") var frontDoorSideCondition: String? = null,
    @SerializedName("backdoor_condition") var backDoorSideCondition: String? = null
)